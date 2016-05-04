package com.topsoft.topframework.ftp;

public class FTPLoadConfiguration {

	private String serverAddress;
	private String userId;
	private String password;
	private String fromPath;
	private String toPath;
	private boolean copySubFolder;
	private boolean deleteAfterCopy;

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFromPath() {
		return fromPath;
	}

	public void setFromPath(String fromPath) {
		this.fromPath = fromPath;
	}

	public String getToPath() {
		return toPath;
	}

	public void setToPath(String toPath) {
		this.toPath = toPath;
	}

	public boolean isCopySubFolder() {
		return copySubFolder;
	}

	public void setCopySubFolder(boolean copySubFolder) {
		this.copySubFolder = copySubFolder;
	}

	public boolean isDeleteAfterCopy() {
		return deleteAfterCopy;
	}

	public void setDeleteAfterCopy(boolean deleteAfterCopy) {
		this.deleteAfterCopy = deleteAfterCopy;
	}

	@Override
	public String toString() {
		return "FTPLoadConfiguration [serverAddress=" + serverAddress + ", userId=" + userId + ", password=" + password + ", fromPath=" + getFromPath() + ", toPath=" + getToPath() + "]";
	}
}