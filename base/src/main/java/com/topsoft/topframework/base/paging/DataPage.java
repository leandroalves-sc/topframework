package com.topsoft.topframework.base.paging;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DataPage<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<T> data;
	private Long count;
	private Page page;

	public DataPage(List<T> data, Long count, Page page) {

		this.data = data;
		this.count = count;
		this.page = page;
	}

	public Page getPage() {
		return page;
	}

	public Long getCount() {
		return count;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}