package com.topsoft.topframework.ftp.domain;

import java.io.File;
import java.io.IOException;

public class FTPFile extends File {

	private static final long serialVersionUID = -2145158303648561007L;

	public FTPFile(String pathname) {
		super(pathname);
	}

	@Override
	public boolean isDirectory() {
		return true;
	}

	@Override
	public String getPath() {
		return normalizePath(super.getPath());
	}

	@Override
	public String getAbsolutePath() {
		return normalizePath(super.getAbsolutePath());
	}

	@Override
	public String getCanonicalPath() throws IOException {
		return normalizePath(super.getAbsolutePath());
	}

	private String normalizePath(String path) {

		if (path == null)
			path = "FTP:\\";

		if (path.contains("FTP:") && !path.startsWith("FTP:"))
			path = path.substring(path.indexOf("FTP:"), path.length());

		if (path.startsWith("C:\\"))
			path = path.replace("C:\\", "FTP:\\");

		if ("FTP:".equals(path))
			path += "\\";

		return path;
	}
}