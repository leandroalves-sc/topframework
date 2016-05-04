package com.topsoft.topframework.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.alee.managers.notification.NotificationIcon;
import com.topsoft.topframework.base.exception.BusinessException;
import com.topsoft.topframework.base.util.LazImage;
import com.topsoft.topframework.base.validator.Validatable;
import com.topsoft.topframework.swing.event.LazActionDispatcher;
import com.topsoft.topframework.swing.event.LazEvent;
import com.topsoft.topframework.swing.event.LazFormEvent;
import com.topsoft.topframework.swing.event.LazFormListener;
import com.topsoft.topframework.swing.util.LazSwingUtils;
import com.topsoft.topframework.swing.util.ValidatorUtil;

import net.miginfocom.swing.MigLayout;

public abstract class LazForm<T extends Object> extends LazPanel implements ApplicationContextAware, LazActionDispatcher<LazFormListener>, LazFormListener {

	private static final long serialVersionUID = -1698688570478040923L;

	protected T dto;
	protected ApplicationContext appContext;

	private boolean readOnly;

	private Map<LazButtonType, LazButton> buttons;

	public LazForm() {

		this.buttons = new HashMap<LazButtonType, LazButton>();
	}

	protected void initForm() {

		if (getComponentCount() == 0) {

			createForm();

			LazButtonType[] buttons = getButtons();

			if (buttons != null) {

				int gap = 0;

				if (MigLayout.class.isAssignableFrom(getLayout().getClass())) {

					MigLayout layout = (MigLayout) getLayout();
					Object LC = layout.getLayoutConstraints();

					if (LC != null && LC.toString().contains("ins")) {

						String ins = LC.toString();
						ins = ins.substring(ins.indexOf("ins"), ins.length());
						ins = ins.substring(3, ins.contains(",") ? ins.indexOf(",") : ins.length()).trim();

						if (StringUtils.isNumeric(ins))
							gap = Integer.parseInt(ins);
					}
				}

				add(getPanelButtons(buttons), "dock south, gaptop 5" + (gap != 0 ? ", gapleft " + gap + ", gapright " + gap + ", gapbottom " + gap : ""));
			}

			addListeners();
		}
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {

		this.readOnly = readOnly;
		setEnabled(!readOnly);
	}

	@Override
	public void addActionListener(LazFormListener l) {
		listenerList.add(LazFormListener.class, l);
	}

	@Override
	public void removeActionListener(LazFormListener l) {
		listenerList.remove(LazFormListener.class, l);
	}

	@Override
	public LazFormListener[] getActionListeners() {
		return (LazFormListener[]) (listenerList.getListeners(LazFormListener.class));
	}

	public boolean hasEvent(LazFormListener l) {

		for (EventListener listener : getActionListeners())
			if (listener == l)
				return true;

		return false;
	}

	public void refresh() {
		refreshFor(dto, isReadOnly());
	}

	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		this.appContext = appContext;
		initForm();
	}

	public void refreshFor(T dto) {
		refreshFor(dto, isReadOnly());
	}

	public void refreshFor(T dto, boolean readOnly) {

		this.dto = dto;

		if (this.readOnly != readOnly)
			setReadOnly(readOnly);

		if (dto != null) {

			loadForm();
			revalidateForm();

			dispatchFormEvent(LazFormEvent.LOAD);
		}
	}

	protected LazPanel getPanelButtons(LazButtonType[] buttonTypes) {

		LazPanel panel = new LazPanel(new MigLayout("fill, ins 0, nogrid"));

		for (LazButtonType type : buttonTypes) {

			LazButton button = new LazButton(type);
			buttons.put(type, button);
			panel.add(button, "tag right");
		}

		return panel;
	}

	public T getDTO() {
		return dto;
	}

	public LazButton getButton(LazButtonType type) {
		return buttons.get(type);
	}

	public void isFormValid() throws BusinessException {

		for (Component comp : LazSwingUtils.getAllComponents(this))
			if (JComponent.class.isAssignableFrom(comp.getClass()) && Validatable.class.isAssignableFrom(comp.getClass()))
				if (!ValidatorUtil.validate(comp))
					throw new BusinessException("Invalid fields. Please valite!");
	}

	public void revalidateForm() {

		for (Component comp : LazSwingUtils.getAllComponents(this)) {

			if (JComponent.class.isAssignableFrom(comp.getClass()) && Validatable.class.isAssignableFrom(comp.getClass())) {

				ValidatorUtil.validate(comp);
				((Validatable) comp).validateData();
			}
		}

		repaint();
	}

	public boolean save() {

		try {

			isFormValid();
			dispatchFormEvent(LazFormEvent.BEFORE_SAVE);
			saveForm();
			dispatchFormEvent(LazFormEvent.SAVE);

			return true;
		}
		catch (BusinessException be) {

			LazAlert.showError(be.getMessage());
			return false;
		}
		catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}

	public void dispose() {

		JFrame frame = LazSwingUtils.getParent(this, JFrame.class);

		if (frame != null)
			frame.dispose();
	}

	protected boolean showSavedMessage() {
		return true;
	}

	public void dispatchFormEvent(int id) throws BusinessException {
		LazEvent.dispatchEvent(this, new LazFormEvent(this, id));
	}

	protected void onSaveClicked() {

		if (LazAlert.showQuestion("Confirm Save?") == JOptionPane.YES_OPTION && save()) {

			if (showSavedMessage())
				LazNotification.showNotification(this, "Successfully saved data", NotificationIcon.database);

			dispose();
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		if (LazButton.class.isAssignableFrom(event.getSource().getClass())) {

			LazButton button = (LazButton) event.getSource();

			if (button.getType() == LazButtonType.SAVE || (button.getIcon() != null && button.getIcon().equals(LazImage.SAVE.getIcon())))
				onSaveClicked();
			else if (button.getType() == LazButtonType.CANCEL)
				dispose();
		}
	}

	public void resetForm() {
	}

	protected abstract void createForm();

	protected abstract void loadForm();

	protected abstract void saveForm();

	protected abstract LazButtonType[] getButtons();

	@Override
	public void onSave(LazFormEvent event) {
	}

	@Override
	public void onLoad(LazFormEvent event) {
	}

	@Override
	public void onBeforeSave(LazFormEvent event) throws BusinessException {
	}
}