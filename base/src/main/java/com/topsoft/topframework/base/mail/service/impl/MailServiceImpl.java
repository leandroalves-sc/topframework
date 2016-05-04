package com.topsoft.topframework.base.mail.service.impl;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.topsoft.topframework.base.mail.domain.Attachment;
import com.topsoft.topframework.base.mail.domain.Mail;
import com.topsoft.topframework.base.mail.service.MailService;
import com.topsoft.topframework.base.security.SecurityContext;

public class MailServiceImpl implements MailService {

	private final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

	@Autowired
	public JavaMailSender mailSender;
	@Autowired
	public SecurityContext securityContext;

	private List<String> emailAdmin;
	private List<String> emailDev;
	private Integer maxAttachmentSize;

	@Override
	public void send(Mail mail) throws MailException {

		try {

			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");

			helper.setFrom(mail.getFrom());

			String body = mail.getText();

			if (!securityContext.getEnvironment().isProduction()) {

				mail.setTo(emailDev.toArray(new String[] {}));
				mail.setCc(new String[] {});
			}

			if (mail.getTo() == null || mail.getTo().length == 0) {
				mail.setTo(emailAdmin.toArray(new String[] {}));
			}
			else {

				helper.setTo(mail.getTo());

				if (mail.getCc().length != 0)
					helper.setCc(mail.getCc());
			}

			body = body.replaceAll("\n", "<br>");

			StringBuilder subject = new StringBuilder();
			subject.append(securityContext.getEnvironment().isDevelopment() ? "[DEV] " : "");
			subject.append(securityContext.getEnvironment().isQA() ? "[QA] " : "");
			subject.append(mail.getSubject());

			helper.setSubject(subject.toString());
			helper.setText(body, true);
			helper.setSentDate(new Date());

			if (!mail.getAttachments().isEmpty()) {
				List<ByteArrayResource> resources = zipAttachmentsIfNecessary(mail.getAttachments());
				for (ByteArrayResource resource : resources) {
					helper.addAttachment(resource.getDescription(), resource);
				}
			}

			mailSender.send(msg);
		}
		catch (Exception e) {
			logger.error("Error while sending email", e);
			throw new MailSendException("Error while sending email.");
		}
	}

	private List<ByteArrayResource> zipAttachmentsIfNecessary(List<Attachment> attachments) throws IOException {

		List<ByteArrayResource> allResources = new ArrayList<ByteArrayResource>();
		List<ByteArrayResource> retorno = new ArrayList<ByteArrayResource>();

		long size = 0;

		for (Attachment attachment : attachments) {

			byte[] byteArray = IOUtils.toByteArray(attachment.getInput());
			size += byteArray.length;
			allResources.add(new ByteArrayResource(byteArray, attachment.getName()));
		}

		size = size / 1024;
		logger.info("Attach file size: {} Kb", size);

		if (size > maxAttachmentSize) {

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(out));

			for (ByteArrayResource resource : allResources) {
				zipResource(resource, zos);
			}

			zos.close();
			out.close();

			byte[] compact = out.toByteArray();
			logger.info("Compacted file size: {} Kb", compact.length / 1024);

			ByteArrayResource resource = new ByteArrayResource(compact, "Attachments.zip");
			retorno.add(resource);
		}
		else {

			retorno.addAll(allResources);
		}

		return retorno;
	}

	private static void zipResource(ByteArrayResource resource, ZipOutputStream zos) throws FileNotFoundException, IOException {

		InputStream is = resource.getInputStream();
		ZipEntry zipEntry = new ZipEntry(resource.getDescription());
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[2048];
		int length;

		while ((length = is.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}

		zos.closeEntry();
		is.close();
	}

	public List<String> getEmailAdmin() {
		return emailAdmin;
	}

	public void setEmailAdmin(List<String> emailAdmin) {
		this.emailAdmin = emailAdmin;
	}

	public List<String> getEmailDev() {
		return emailDev;
	}

	public void setEmailDev(List<String> emailDev) {
		this.emailDev = emailDev;
	}

	public void setMaxAttachmentSize(Integer maxAttachmentSize) {
		this.maxAttachmentSize = maxAttachmentSize;
	}
}
