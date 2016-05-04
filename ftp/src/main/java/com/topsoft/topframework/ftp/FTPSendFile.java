package com.topsoft.topframework.ftp;

import java.io.File;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTPSendFile {

	private static Logger log = LoggerFactory.getLogger(FTPSendFile.class);

	private FTPLoadConfiguration configuration;
	private StandardFileSystemManager manager;
	private FileSystemOptions opts;

	public FTPSendFile(FTPLoadConfiguration configuration) {

		this.configuration = configuration;
	}

	public boolean startFTP() {

		try {

			manager = new StandardFileSystemManager();
			manager.init();

			opts = new FileSystemOptions();
			SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
			SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
			SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);

			copyFile(new File(configuration.getFromPath()));
		}
		catch (FileSystemException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			manager.close();
		}

		log.info("File upload sucessful");
		return true;
	}

	private void copyFile(File source) throws FileSystemException {

		if (source.isDirectory()) {

			for (File file : source.listFiles())
				if (file.isFile() || configuration.isCopySubFolder())
					copyFile(file);
		}
		else if (source.exists()) {

			String directory = source.getAbsolutePath().replace(configuration.getFromPath(), "");
			String sftpUri = "sftp://" + configuration.getUserId() + ":" + configuration
				.getPassword() + "@" + configuration.getServerAddress() + configuration.getToPath() + directory;

			FileObject localFile = manager.resolveFile(source.getAbsolutePath());
			FileObject remoteFile = manager.resolveFile(sftpUri, opts);

			remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);

			if (configuration.isDeleteAfterCopy())
				source.delete();
		}
	}
}