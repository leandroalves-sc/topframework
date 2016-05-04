package com.topsoft.topframework.base.mail.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.mail.SimpleMailMessage;

import com.topsoft.topframework.base.validator.Validator;
import com.topsoft.topframework.base.validator.impl.EmailValidator;

public class Mail extends SimpleMailMessage {

	private static final long serialVersionUID = 8725999284161288816L;

	private List<Attachment> attachments;

	public Mail() {

		this.setFrom("No Reply <no_reply@gmail.com>");
		this.attachments = new ArrayList<Attachment>();
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public void addAttachment(Attachment attachments) {
		this.attachments.add(attachments);
	}

	public void addTo(String to) {
		ArrayUtils.add(getTo(), to);
	}

	public static List<String> getEmails(String email) {

		if (email == null)
			return null;

		String[] separador = { ",", ";" };
		List<String> retorno = new ArrayList<String>();

		boolean isAdd = false;

		for (String sep : separador) {

			if (email.indexOf(sep) != -1) {

				for (String aux : email.split(sep))
					if (Validator.use(EmailValidator.class).isValid(aux))
						retorno.add(aux.trim());

				isAdd = true;
			}
		}

		if (!isAdd && Validator.use(EmailValidator.class).isValid(email))
			retorno.add(email);

		return retorno;
	}

	public static List<String> getEmails(List<String> emails) {

		List<String> retorno = new ArrayList<String>();

		for (String email : emails)
			retorno.addAll(getEmails(email));

		return retorno;
	}
}