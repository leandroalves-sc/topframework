package com.topsoft.topframework.swing.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import com.topsoft.topframework.base.validator.Validator;
import com.topsoft.topframework.base.validator.impl.HostnameValidator;
import com.topsoft.topframework.ftp.domain.FTPConfiguration;
import com.topsoft.topframework.ftp.domain.type.FTPType;
import com.topsoft.topframework.swing.LazButton;
import com.topsoft.topframework.swing.LazButtonType;
import com.topsoft.topframework.swing.LazComboBox;
import com.topsoft.topframework.swing.LazForm;
import com.topsoft.topframework.swing.LazPasswordField;
import com.topsoft.topframework.swing.LazSpinner;
import com.topsoft.topframework.swing.LazTextField;
import com.topsoft.topframework.swing.LazViewCapable;
import com.topsoft.topframework.swing.model.LazSpinnerNumberModel;

import net.miginfocom.swing.MigLayout;

public class FTPConnectionView extends LazForm<FTPConfiguration>implements LazViewCapable<FTPConfiguration> {

	private static final long serialVersionUID = -3919308045939211891L;

	private LazTextField txfServer;
	private LazTextField txfUsername;
	private LazPasswordField txfPassword;
	private LazSpinner<Integer> spnPort;
	private LazComboBox<FTPType> cmbType;

	@Override
	protected void createForm() {

		setLayout(new MigLayout("fillx, ins 0, wrap 2", "[110][grow,fill]"));

		add("Servidor FTP: ", txfServer = new LazTextField());
		add("Tipo: ", cmbType = new LazComboBox<FTPType>(FTPType.values()));
		add("Usu�rio: ", txfUsername = new LazTextField());
		add("Senha: ", txfPassword = new LazPasswordField());
		add("Porta: ", spnPort = new LazSpinner<Integer>(new LazSpinnerNumberModel<Integer>(1, 1, 65535, 1)));

		txfServer.setRequired(true);
		txfUsername.setRequired(true);
		txfPassword.setRequired(true);

		txfServer.addValidators(Validator.use(HostnameValidator.class));
	}

	@Override
	public Dimension getSize() {
		return new Dimension(500, 280);
	}

	@Override
	protected void loadForm() {

		txfServer.setText(dto.getServer());
		cmbType.setSelectedItem(dto.getType());
		txfUsername.setText(dto.getUsername());
		txfPassword.setText(dto.getPassword());
		spnPort.setValue(dto.getPort());
	}

	@Override
	protected void saveForm() {

		if (dto == null)
			dto = new FTPConfiguration();

		dto.setServer(txfServer.getText());
		dto.setType(cmbType.getSelectedItem());
		dto.setUsername(txfUsername.getText());
		dto.setPassword(txfPassword.getPasswordText());
		dto.setPort(spnPort.getValue());
	}

	@Override
	protected LazButtonType[] getButtons() {
		return new LazButtonType[]{ LazButtonType.CONNECT, LazButtonType.CANCEL };
	}

	@Override
	public String getTitle() {
		return "Conex�o FTP";
	}

	@Override
	public boolean isResizable() {
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		super.actionPerformed(event);

		Object source = event.getSource();

		if (LazButton.class.isAssignableFrom(source.getClass())) {

			LazButton button = (LazButton) source;

			if (button.getType() != null && button.getType() == LazButtonType.CONNECT)
				save();
		}
	}
}