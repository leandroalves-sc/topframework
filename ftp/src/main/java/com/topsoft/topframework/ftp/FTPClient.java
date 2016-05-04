package com.topsoft.topframework.ftp;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.topsoft.topframework.base.exception.BusinessException;
import com.topsoft.topframework.ftp.domain.FTPConfiguration;

public class FTPClient {

	private static Logger log = LoggerFactory.getLogger(FTPClient.class);

	private StandardFileSystemManager manager;
	private FileSystemOptions opts;

	public FTPClient() {
	}

	public void transfer(FTPConfiguration config, String fromPath, String toPath, boolean recursive, boolean deleteAfterCopy) {

		try {

			log.info("Inicializando...");

			if (manager == null) {

				manager = new StandardFileSystemManager();
				manager.init();

				opts = new FileSystemOptions();
				SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
				SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
				SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);
			}

			log.info("Iniciando transfer�ncia");

			while (fromPath.contains("\\"))
				fromPath = fromPath.replace("\\", "/");

			while (toPath.contains("\\"))
				toPath = toPath.replace("\\", "/");

			copyFiles(config, fromPath, toPath, recursive, deleteAfterCopy);

			log.info("Transfer�ncia efetuada");
		}
		catch (Exception e) {
			throw new BusinessException("Erro ao copiar arquivos via FTP", e);
		}
		finally {

			if (manager != null)
				manager.close();
		}
	}

	private void copyFiles(FTPConfiguration config, String fromPath, String toPath, boolean recursive, boolean deleteAfterCopy) throws FileSystemException {

		FileObject toFile;
		FileObject fromFile = manager.resolveFile(fromPath);

		for (FileObject file : fromFile.getChildren()) {

			if (file.getType() == FileType.FOLDER && recursive) {

				copyFiles(config, file.getURL()
					.getPath(), toPath + file.getURL().getPath().replace(fromPath, ""), recursive, deleteAfterCopy);
			}
			else if (file.getType() == FileType.FILE) {

				toFile = manager
					.resolveFile(config.getBaseURL() + toPath + file.getURL().getPath().replace(fromPath, ""), opts);

				log.info("Copiando DE: " + file.getURL() + " - PARA: " + toFile.getName().getFriendlyURI());
				toFile.copyFrom(file, Selectors.SELECT_SELF);

				if (deleteAfterCopy)
					file.delete(Selectors.SELECT_SELF);
			}
		}
	}

	public void close() {
		manager.close();
	}
}