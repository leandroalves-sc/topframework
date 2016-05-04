package com.topsoft.topframework.swing;

import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;

import com.topsoft.topframework.base.paging.DataPage;
import com.topsoft.topframework.base.paging.Page;
import com.topsoft.topframework.base.util.LazImage;
import com.topsoft.topframework.swing.event.LazActionDispatcher;
import com.topsoft.topframework.swing.event.LazEvent;
import com.topsoft.topframework.swing.event.LazPageEvent;
import com.topsoft.topframework.swing.event.LazPageListener;
import com.topsoft.topframework.swing.text.LazTextDocument;

import net.miginfocom.swing.MigLayout;

public class LazPaginator extends LazPanel implements LazActionDispatcher<LazPageListener> {

	private static final long serialVersionUID = 5181679866801349457L;

	private static final int DEFAULT_PAGE_SIZE = 30;

	private int pageSize, selectedPage, numPages;
	private LazLabel lblFirst, lblPrev, lblNext, lblLast, lblNumPages;
	private LazTextField txfPage;
	private LazPanel pageLinks;
	private LazButton btnGo;
	private ButtonGroup btnPages;

	public LazPaginator() {
		this(DEFAULT_PAGE_SIZE);
	}

	public LazPaginator(int pageSize) {

		super(new MigLayout("fill, ins 0, gap 0", "[grow,fill][right]"));

		this.pageSize = pageSize;

		add(getPanelPages(), "growx");
		add(getPanelGoTo());

		addListeners();
	}

	private LazPanel getPanelPages() {

		LazPanel panel = new LazPanel(new MigLayout("fill, gapx 5, alignx leading, nogrid"));

		panel.add(lblFirst = new LazLabel(LazImage.FIRST_PAGE, this));
		panel.add(lblPrev = new LazLabel(LazImage.PREV_PAGE, this));
		panel.add(pageLinks = new LazPanel(new MigLayout("ins 0, gapx 3")));
		panel.add(lblNext = new LazLabel(LazImage.NEXT_PAGE, this));
		panel.add(lblLast = new LazLabel(LazImage.LAST_PAGE, this));
		panel.add(lblNumPages = new LazLabel(), "w 50!");

		return panel;
	}

	private LazPanel getPanelGoTo() {

		LazPanel panel = new LazPanel(new MigLayout());

		panel.add(txfPage = new LazTextField("", new LazTextDocument(3, true, false)), "w 50!");
		txfPage.setHorizontalAlignment(SwingConstants.CENTER);

		panel.add(btnGo = new LazButton("Go"), "w 30!");
		btnGo.addActionListener(this);

		return panel;
	}

	public Page getPage() {
		return new Page(Page.getStartRow(selectedPage, pageSize), pageSize);
	}

	public void refreshFor(DataPage<?> dataPage) {

		Long count = dataPage == null ? 0 : dataPage.getCount();
		int dataSize = dataPage == null ? 0 : dataPage.getData().size();
		Page page = dataPage == null ? new Page(0, pageSize) : dataPage.getPage();

		numPages = count.intValue() / page.getPageSize();

		if (count % page.getPageSize() != 0)
			numPages = numPages + 1;

		if (numPages == 0 && dataSize > 0)
			numPages = 1;

		selectedPage = page.getStartRow() == 0 ? 1 : (page.getStartRow() / page.getPageSize()) + 1;

		if (numPages > 0 && dataSize == 0 && dataPage != null) {

			selectedPage--;
			dispatchPageEvent();

			return;
		}

		btnGo.setEnabled(dataPage != null);
		txfPage.setEditable(dataPage != null);
		lblNumPages.setText(numPages == 0 ? "" : selectedPage + " of " + numPages);

		if (numPages <= 1) {

			lblFirst.setEnabled(false);
			lblPrev.setEnabled(false);

			lblNext.setEnabled(false);
			lblLast.setEnabled(false);
		}
		else if (selectedPage == 1) {

			lblFirst.setEnabled(false);
			lblPrev.setEnabled(false);

			lblNext.setEnabled(true);
			lblLast.setEnabled(true);
		}
		else if (selectedPage == numPages) {

			lblFirst.setEnabled(true);
			lblPrev.setEnabled(true);

			lblNext.setEnabled(false);
			lblLast.setEnabled(false);
		}
		else {

			lblFirst.setEnabled(true);
			lblPrev.setEnabled(true);

			lblNext.setEnabled(true);
			lblLast.setEnabled(true);
		}

		pageLinks.removeAll();

		LazButtonGroup searchBtnGroup = new LazButtonGroup();
		searchBtnGroup.setLayout(new MigLayout("fillx, ins 0, gap 0", "[grow,fill]"));

		if (numPages > 0) {

			int startPage = numPages < 10 ? 1 : selectedPage - 5;
			int endPage = numPages < 10 ? numPages : selectedPage + 4;

			while (endPage > numPages) {

				startPage--;
				endPage--;
			}

			while (startPage < 1 && endPage <= numPages) {

				startPage++;
				endPage++;
			}

			for (int iPage = startPage; iPage <= endPage; iPage++)
				searchBtnGroup.add(new LazToggleButton(Integer.toString(iPage), selectedPage == iPage, Integer.toString(iPage)), "w 30!");

			searchBtnGroup.setButtonsDrawFocus(false);
			pageLinks.add(searchBtnGroup);

			btnPages = LazButtonGroup.groupButtons(searchBtnGroup);
		}
	}

	@Override
	public void addActionListener(LazPageListener l) {
		listenerList.add(LazPageListener.class, l);
	}

	@Override
	public void removeActionListener(LazPageListener l) {
		listenerList.remove(LazPageListener.class, l);
	}

	@Override
	public LazPageListener[] getActionListeners() {
		return (LazPageListener[]) (listenerList.getListeners(LazPageListener.class));
	}

	private void dispatchPageEvent() {
		LazEvent.dispatchEvent(this, new LazPageEvent(this, LazPageEvent.PAGE_CHANGED, selectedPage, pageSize));
	}

	private void goToPage(int page) {

		if (page > 0 && page <= numPages) {

			selectedPage = page;
			dispatchPageEvent();
		}
	}

	public void setPageSize(int pageSize) {

		this.pageSize = pageSize;
		dispatchPageEvent();
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		Object source = event.getSource();

		if ((source == txfPage || source == btnGo) && txfPage.getText().trim().length() > 0)
			goToPage(Integer.parseInt(txfPage.getText()));
		else if (source instanceof LazToggleButton)
			goToPage(Integer.parseInt(btnPages.getSelection().getActionCommand()));
		else if (source instanceof LazLabel) {

			LazLabel label = (LazLabel) source;

			if (label == lblFirst)
				goToPage(1);
			else if (label == lblPrev)
				goToPage(selectedPage - 1);
			else if (label == lblNext)
				goToPage(selectedPage + 1);
			else if (label == lblLast)
				goToPage(numPages);
		}
	}
}