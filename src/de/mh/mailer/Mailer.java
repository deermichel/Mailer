package de.mh.mailer;

import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JFrame;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;


/** Mailer
 * Copyright (c) 2015 Micha Hanselmann
 * @author Micha Hanselmann
 * Licensed under GNU GPL v2.
 */
public class Mailer {

	private String log = "";
	
	public List<String> recipients;
	public int index = 0;
	public String message;
	public String host;
	public String user;
	public String password;
	public String from;
	public String subject;
	//public List<File> attachments = new ArrayList<>();
	

	public static void main(String[] args) {
		new Mailer();
	}
	
	public Mailer() {
		
		// add shutdown hook
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				if (log.length() > 0) {
					// save log
					try {
				        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
						PrintWriter writer = new PrintWriter("mailer" + sdf.format(new Date()) + ".log");
						writer.print(log);
						writer.close();
						System.out.println("Log saved.");
					} catch (Exception e) {}
				}
			}
		}));
		
		JFrame window = new Window(this);
		window.setVisible(true);
		
	}
	
	public void log(String text) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log = sdf.format(new Date()) + " " + text + "\n" + log;
	}
	
	public boolean send() throws Exception {
		
		// log
		log("Send mail to " + recipients.get(index) + " (index: " + index + ")");
		
		// get properties
		Properties props = System.getProperties();
		
		// setup server
		props.setProperty("mail.smtp.host", host);
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.port", "587");
		// props.setProperty("mail.debug", "true");
		
		// get session
		Session session = Session.getDefaultInstance(props, 
				new Authenticator() {

					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(user, password);
					}
			
		});
			
		// create message
		MimeMessage msg = new MimeMessage(session);

		// setup mail header
		msg.setFrom(new InternetAddress(from));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipients.get(index)));
		msg.setSubject(subject);
		
		// create main part
		BodyPart part = new MimeBodyPart();
		part.setContent(message, "text/html");
		
		// setup multipart
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(part);
		
		/* add attachments
		for (File file : attachments) {
			part = new MimeBodyPart();
			part.setDataHandler(new DataHandler(new FileDataSource(file)));
			part.setHeader("Content-ID", "<" + file.getName() + ">");
			multipart.addBodyPart(part);
		}*/

		// set content
		msg.setContent(multipart);

		// send
		Transport.send(msg);
		
		return true;
	}

}
