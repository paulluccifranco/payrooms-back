package com.payroom.util;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.stereotype.Component;

@Component
public class Email {
	public void enviarMensaje(String replyAddress, String to, String asunto, String textoCorreoElectronico) {

		String CORREO_ELECTRONICO_ENVIO_USUARIO = "payrooms024@gmail.com";
		String CORREO_ELECTRONICO_ENVIO_CONTRASENA = "495ExPeNsE4My";

		Session session = crearSesion(CORREO_ELECTRONICO_ENVIO_USUARIO, CORREO_ELECTRONICO_ENVIO_CONTRASENA);
		try {
			MimeMessage mensaje = new MimeMessage(session);
			mensaje.setFrom(new InternetAddress(CORREO_ELECTRONICO_ENVIO_USUARIO));
			Address[] addresses = new Address[] { new InternetAddress(replyAddress) };
			mensaje.setReplyTo(addresses);
			mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			mensaje.setSubject(asunto);
			mensaje.setText(textoCorreoElectronico);
			Transport.send(mensaje);
			String mensajeLog = "Se envio el correo electronico de asunto:" + asunto + " a " + to;
			System.out.println(mensajeLog);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void enviarMensajeHtml(String replyAddress, String to, String asunto, String textoCorreoElectronico) {

		String CORREO_ELECTRONICO_ENVIO_USUARIO = "payrooms024@gmail.com";
		String CORREO_ELECTRONICO_ENVIO_CONTRASENA = "495ExPeNsE4My";
		Session session = crearSesion(CORREO_ELECTRONICO_ENVIO_USUARIO, CORREO_ELECTRONICO_ENVIO_CONTRASENA);
		try {
			MimeMessage mensaje = new MimeMessage(session);
			mensaje.setFrom(new InternetAddress(CORREO_ELECTRONICO_ENVIO_USUARIO));
			Address[] addresses = new Address[] { new InternetAddress(replyAddress) };
			mensaje.setSentDate(new Date());
			mensaje.setReplyTo(addresses);
			mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			mensaje.setSubject(asunto);
			mensaje.setContent(textoCorreoElectronico, "text/html");
			Transport.send(mensaje);
			String mensajeLog = "Se envio el correo electronico de asunto:" + asunto + " a " + to;
			System.out.println(mensajeLog);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	public void enviarMensajeConAttachment(String replyAddress, String to, List<String> cco, String asunto,
			String textoCorreoElectronico, byte[] attachment, String filename, String typeAttachment) {

		String CORREO_ELECTRONICO_ENVIO_USUARIO = "payrooms024@gmail.com";
		String CORREO_ELECTRONICO_ENVIO_CONTRASENA = "495ExPeNsE4My";

		Session session = crearSesion(CORREO_ELECTRONICO_ENVIO_USUARIO, CORREO_ELECTRONICO_ENVIO_CONTRASENA);
		try {
			MimeMessage mensaje = new MimeMessage(session);
			// ------ FROM -------
			mensaje.setFrom(new InternetAddress(CORREO_ELECTRONICO_ENVIO_USUARIO));
			// ------ TO --------
			Address[] addresses = new Address[] { new InternetAddress(replyAddress) };
			mensaje.setReplyTo(addresses);
			mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// --------- CCO --------------
			InternetAddress[] ccoAdress = new InternetAddress[cco.size()];
			for (int i = 0; i < cco.size(); i++) {
				ccoAdress[i] = new InternetAddress(cco.get(i));
			}
			mensaje.addRecipients(Message.RecipientType.BCC, ccoAdress);
			// -----------------------------
			mensaje.setSubject(asunto);

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(textoCorreoElectronico);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();

			DataSource source = new ByteArrayDataSource(attachment, typeAttachment);
			messageBodyPart.setDataHandler(new DataHandler(source));

			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			mensaje.setContent(multipart);

			// ---
			Transport.send(mensaje);
			String mensajeLog = "Se envio el correo electronico de asunto:" + asunto + " a " + to;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private Session crearSesion(final String from, final String pass) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, pass);
			}
		});
		return session;
	}

}
