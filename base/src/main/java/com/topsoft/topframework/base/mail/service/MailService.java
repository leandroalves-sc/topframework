package com.topsoft.topframework.base.mail.service;

import org.springframework.mail.MailException;

import com.topsoft.topframework.base.mail.domain.Mail;

public interface MailService {
	
	public void send( Mail mail ) throws MailException;
}