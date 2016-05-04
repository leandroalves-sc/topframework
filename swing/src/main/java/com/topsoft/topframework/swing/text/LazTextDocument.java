package com.topsoft.topframework.swing.text;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LazTextDocument extends PlainDocument {

	private static final long serialVersionUID = -4841441436243287755L;

	private int maxSize;
	private boolean isNumeric;
	private boolean isDecimal;
	private boolean deleteOnly = false;

	public LazTextDocument() {

		maxSize = 0;
		isNumeric = false;
		isDecimal = false;
	}

	public LazTextDocument(int len, boolean aIsNumeric, boolean aIsDecimal) {

		maxSize = len;
		isNumeric = aIsNumeric;
		isDecimal = aIsDecimal;
	}

	public void setValues(int len, boolean aIsNumeric, boolean aIsDecimal) {

		maxSize = len;
		isNumeric = aIsNumeric;
		isDecimal = aIsDecimal;
	}

	public void deleteOnly(boolean setDeleteOnlyMode) {

		deleteOnly = setDeleteOnlyMode;
	}

	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

		if (deleteOnly)
			return;

		if (str == null)
			return;

		char chCharset[] = str.toCharArray();

		if (isNumeric || isDecimal)
			for (int i = 0; i < chCharset.length; i++)
				if (!((isDecimal && (chCharset[i] == ',' || chCharset[i] == '.'))) && !(i == 0 && chCharset[i] == '-') && !Character.isDigit(chCharset[i]))
					return;

		int strlength = this.getEndPosition().getOffset() - 1;

		if (strlength >= maxSize)
			return;

		if ((strlength + str.length()) > maxSize)
			return;

		if (str.length() > 1)
			strlength = offs + str.length();

		if (maxSize > strlength) {

			super.insertString(offs, str, a);
		}
		else {

			if (str.length() > 1) {

				String text = getText(0, offs);

				text = text + str;
				super.remove(this.getStartPosition().getOffset(), getText(0, offs).length());
				super.insertString(0, text.substring(0, maxSize), a);
			}

			return;
		}
	}

	public void remove(int offs, int len) throws BadLocationException {

		super.remove(offs, len);
	}
}