package com.topsoft.topframework.swing.list;

import java.util.List;
import java.util.Vector;

import javax.swing.AbstractListModel;

public class LazListModel<T extends Object> extends AbstractListModel<T> {

	private static final long serialVersionUID = 4302468779281942837L;

	private Vector<T> data;

	@Override
	public int getSize() {
		return data == null ? 0 : data.size();
	}

	@Override
	public T getElementAt(int row) {

		if (data != null && data.size() > 0 && row >= 0 && row < data.size())
			return data.get(row);

		return null;
	}

	public void removeAll() {

		if (data != null && !data.isEmpty()) {

			this.data.removeAllElements();
			fireIntervalRemoved(this, 0, data.size());
		}
	}

	public void setData(Vector<T> data) {

		this.data = data;

		if (data != null && !data.isEmpty())
			fireIntervalAdded(this, 0, data.size());
	}

	public void setData(List<T> data) {

		this.data = new Vector<T>(data);

		if (data != null && !data.isEmpty())
			fireIntervalAdded(this, 0, data.size());
	}

	public void addItem(T item) {

		if (this.data == null)
			this.data = new Vector<T>();

		this.data.add(item);

		fireContentsChanged(this, 0, data.size());
	}

	public void removeItem(T value) {

		this.data.remove(value);

		if (data != null && !data.isEmpty())
			fireIntervalAdded(this, 0, data.size());
	}

	public Vector<T> getData() {
		return data;
	}
}