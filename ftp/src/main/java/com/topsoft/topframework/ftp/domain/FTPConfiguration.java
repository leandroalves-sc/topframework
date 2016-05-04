package com.topsoft.topframework.ftp.domain;

import com.topsoft.topframework.ftp.domain.type.FTPType;

public class FTPConfiguration {

	private String server;
	private String username;
	private String password;
	private FTPType type;
	private int port;

	public FTPConfiguration() {
		this.type = FTPType.SFTP;
	}

	public FTPConfiguration(String server, String username, String password) {
		this(server, username, password, 22);
	}

	public FTPConfiguration(String server, String username, String password, int port) {

		this();

		this.server = server;
		this.username = username;
		this.password = password;
		this.port = port;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public FTPType getType() {
		return type;
	}

	public void setType(FTPType type) {
		this.type = type;
	}

	public String getBaseURL() {

		StringBuilder url = new StringBuilder();

		url.append((type == FTPType.FTP ? "ftp" : "sftp") + "://");

		url.append(getUsername().replaceAll("@", "%40") + ":");
		url.append(getPassword().replaceAll("@", "%40") + "@");
		url.append(getServer() + ":" + getPort());

		return url.toString();
	}

	public String getPrintBaseURL() {

		StringBuilder url = new StringBuilder();

		url.append((type == FTPType.FTP ? "ftp" : "sftp") + "://");

		url.append(getUsername().replaceAll("@", "%40") + ":");
		url.append("***" + "@");
		url.append(getServer() + ":" + getPort());

		return url.toString();
	}
}