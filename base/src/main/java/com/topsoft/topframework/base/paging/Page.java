package com.topsoft.topframework.base.paging;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Page {

	private int startRow;
	private int pageSize;

	public Page() {
	}

	public Page(int startRow, int pageSize) {
		this.startRow = startRow;
		this.pageSize = pageSize;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getPageSize() {
		return pageSize;
	}

	public static boolean isValid(Page p) {
		return p != null && p.getStartRow() >= 0 && p.getPageSize() > 0;
	}

	public static int getStartRow(int page, int pageSize) {
		return ((page == 0 ? 1 : page) - 1) * pageSize;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}