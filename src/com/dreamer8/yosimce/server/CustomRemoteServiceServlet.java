/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamer8.yosimce.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import com.dreamer8.yosimce.server.hibernate.dao.UsuarioDAO;
import com.dreamer8.yosimce.server.hibernate.pojo.Archivo;
import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.utils.AccessControl;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * 
 * @author jorge
 */
public class CustomRemoteServiceServlet extends RemoteServiceServlet {

	public static final String FILES = "Archivos";
	public static final String FILE_TYPES = "Mimes";
	public static final String FILE_NAMES = "Nombres";

	/*
	 * 
	 * Session s = HibernateUtil.getSessionFactory().openSession(); try {
ManagedSessionContext.bind(s);
	 * AccessControl ac = getAccessControl(); if (ac.isLogged() &&
	 * ac.isAllowed(className, "getUser")) {
	 * 
	 * if ( == null) { throw new NullPointerException(
	 * "No se ha especificado un ."); }
	 * 
	 * s.beginTransaction();
	 * 
	 * s.getTransaction().commit(); } } catch (HibernateException ex) {
	 * System.err.println(ex); HibernateUtil.rollback(s); throw new
	 * DBException(); } catch (ConsistencyException ex) {
	 * HibernateUtil.rollbackActiveOnly(s); throw ex; } catch
	 * (NullPointerException ex) { HibernateUtil.rollbackActiveOnly(s); throw
	 * ex; } return;
	 */

	/**
	 * 
	 * @return
	 */
	protected AccessControl getAccessControl() {
		return new AccessControl(this.getThreadLocalRequest());
	}

	protected Usuario getUsuarioActual() {
		return (Usuario) this.getThreadLocalRequest().getSession()
				.getAttribute("usuario");
	}

	protected void sendMail(String message, String subject, String toAddr)
			throws MessagingException, UnsupportedEncodingException {
		/*
		 * String fromAddr = "gestion@araujo.cl"; String fromName = "Gestión
		 * Araujo"; String smtp = "mail.araujo.cl"; String username =
		 * "gestion@araujo.cl"; String password = "arauj_2012"; String port =
		 * "25";
		 */
		// ConfiguracionDAO cdao = new ConfiguracionDAO();
		// List<Configuracion> cs = cdao.findAll();
		// Configuracion c = (cs != null && !cs.isEmpty()) ? cs.get(0) : null;
		// if (c != null) {
		// String fromAddr = c.getFromAddrSmtp();
		// String fromName = c.getFromNameSmtp();
		// String smtp = c.getHostSmtp();
		// String username = c.getUserSmtp();
		// String password = c.getPasswordSmtp();
		// String port = c.getPortSmtp().toString();
		// sendMail(message, subject, toAddr, fromAddr, fromName, smtp,
		// username, password, port);
		// }
	}

	protected void sendMail(String message, String subject, String toAddr,
			String fromAddr, String fromName, String smtp, String username,
			String password, String port) throws MessagingException,
			UnsupportedEncodingException {
		if (toAddr != null) {
			Properties prop = System.getProperties();
			prop.put("mail.smtp.host", smtp);
			prop.put("mail.smtp.user", username);
			prop.put("mail.smtp.password", password);
			prop.put("mail.smtp.port", port);
			prop.put("mail.smtp.auth", true);
			javax.mail.Session mailSession = javax.mail.Session
					.getDefaultInstance(prop, new CustomAuthenticator(username,
							password));
			Message m = new MimeMessage(mailSession);
			m.setFrom(new InternetAddress(fromAddr, fromName));
			m.setRecipient(Message.RecipientType.TO,
					new InternetAddress(toAddr));
			m.setSubject(subject);
			m.setText(message);
			Transport.send(m);
		}
	}

	protected void sendMailWithAttachment(String message, String subject,
			String toAddr, String attachmentName, String attachmentPath,
			String attachmentMime) throws MessagingException,
			UnsupportedEncodingException {
		/*
		 * String fromAddr = "gestion@araujo.cl"; String fromName = "Gestión
		 * Araujo"; String smtp = "mail.araujo.cl"; String username =
		 * "gestion@araujo.cl"; String password = "arauj_2012"; String port =
		 * "25";
		 */
		// ConfiguracionDAO cdao = new ConfiguracionDAO();
		// List<Configuracion> cs = cdao.findAll();
		// Configuracion c = (cs != null && !cs.isEmpty()) ? cs.get(0) : null;
		// if (c != null) {
		// String fromAddr = c.getFromAddrSmtp();
		// String fromName = c.getFromNameSmtp();
		// String smtp = c.getHostSmtp();
		// String username = c.getUserSmtp();
		// String password = c.getPasswordSmtp();
		// String port = c.getPortSmtp().toString();
		// sendMailWithAttachment(message, subject, toAddr, attachmentName,
		// attachmentPath, attachmentMime, fromAddr, fromName, smtp,
		// username, password, port);
		// }
	}

	protected void sendMailWithAttachment(String message, String subject,
			String toAddr, String attachmentName, String attachmentPath,
			String attachmentMime, String fromAddr, String fromName,
			String smtp, String username, String password, String port)
			throws MessagingException, UnsupportedEncodingException {
		if (toAddr != null) {
			Properties prop = System.getProperties();
			prop.put("mail.smtp.host", smtp);
			prop.put("mail.smtp.user", username);
			prop.put("mail.smtp.password", password);
			prop.put("mail.smtp.port", port);
			prop.put("mail.smtp.auth", true);
			javax.mail.Session mailSession = javax.mail.Session
					.getDefaultInstance(prop, new CustomAuthenticator(username,
							password));
			Message m = new MimeMessage(mailSession);
			m.setFrom(new InternetAddress(fromAddr, fromName));
			m.setRecipient(Message.RecipientType.TO,
					new InternetAddress(toAddr));
			m.setSubject(subject);
			MimeBodyPart mbp = new MimeBodyPart();
			mbp.setText(message);
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp);
			File f = new File(attachmentPath);
			if (f != null) {
				DataSource ds = new FileDataSource(f);
				mbp = new MimeBodyPart();
				mbp.setDataHandler(new DataHandler(ds));
				mbp.setFileName(attachmentName);
				mp.addBodyPart(mbp);
			}
			m.setContent(mp);
			Transport.send(m);
		}
	}

	protected void enviarArchivo(String toAddr, String attachmentName,
			String attachmentPath, String attachmentMime)
			throws NullPointerException {
		if (toAddr == null || attachmentName == null || attachmentPath == null) {
			throw new NullPointerException(
					"No se han especificado los parámetros necesarios para el envío");
		}
		String mensaje = "";
		try {
			sendMailWithAttachment(mensaje, "Envío de archivo "
					+ attachmentName, toAddr, attachmentName, attachmentPath,
					attachmentMime);
		} catch (MessagingException ex) {
			System.err.println(ex);
			throw new NullPointerException(
					"Ha ocurrido un error y no se ha podido enviar el correo");
		} catch (UnsupportedEncodingException ex) {
			System.err.println(ex);
			throw new NullPointerException(
					"Ha ocurrido un error y no se ha podido enviar el correo");
		}
	}

	protected Boolean checkPassword(Integer idUsuario, String oldPass,
			String newPass, String repNewPass) {
		if (newPass.equals(repNewPass) && !newPass.isEmpty()) {
			UsuarioDAO udao = new UsuarioDAO();
			Usuario usuario = udao.getById(idUsuario);
			if (usuario.getPassword().equals(getPasswordHash(oldPass))) {
				return true;
			}
		}
		return false;
	}

	public static String getPasswordHash(String password, String salt) {
		String salted = password + "{" + salt + "}";
		byte[] saltedBytes = salted.getBytes();
		byte[] digest = DigestUtils.sha512(saltedBytes);
		ByteArrayOutputStream baos = null;
		for (int i = 1; i < 10; i++) {
			baos = new ByteArrayOutputStream();
			baos.write(digest, 0, digest.length);
			baos.write(saltedBytes, 0, saltedBytes.length);
			digest = DigestUtils.sha512(baos.toByteArray());
		}
		return Base64.encodeBase64String(digest);
	}

	protected String getPasswordHash(String password) {
		return DigestUtils.sha512Hex(password);
	}

	protected Boolean sendNewPassword(Usuario usuario, String password) {
		if (usuario.getEmail() != null) {
			try {
				String message = "Estimad@ "
						+ ((usuario.getNombres().indexOf(" ", 0) != -1) ? usuario
								.getNombres().substring(0,
										usuario.getNombres().indexOf(" ", 0))
								: usuario.getNombres())
						+ ":\n"
						+ "Estos son los datos de cuenta para que puedas ingresar al sistema de Tracking para el Simce 2012:\n\n"
						+ "\tUsuario: " + usuario.getUsername() + "\n"
						+ "\tContraseña: " + password + "\n\n"
						+ "Para acceder al sitio diríjase a " + getBaseURL();
				sendMail(message, "Información de su cuenta Simce",
						usuario.getEmail());
				return true;
			} catch (MessagingException ex) {
				throw new NullPointerException(
						"Ha ocurrido un error y no se ha podido enviar el correo");
			} catch (UnsupportedEncodingException ex) {
				throw new NullPointerException(
						"Ha ocurrido un error y no se ha podido enviar el correo");
			}
		}
		return false;
	}

	protected String generatePassword() {
		Random r = new Random(System.currentTimeMillis());
		String chars = "0123456789abcdfghjkmnpqrstvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int length = chars.length();
		int passLength = 12;
		int i = 0;
		char c;
		String result = "";
		for (i = 0; i < passLength; i++) {
			c = chars.charAt(r.nextInt(length));
			if (!result.contains(String.valueOf(c))) {
				result += c;
			}
		}
		return result;
	}

	protected String getInicial(String str) {
		return (str != null && str.length() >= 1) ? str.substring(0, 1) + "."
				: "";
	}

	protected String getBaseURL() {
		String url = this.getThreadLocalRequest().getRequestURL().toString();
		return url
				.replaceAll(this.getThreadLocalRequest().getServletPath(), "");
	}

	private class CustomAuthenticator extends Authenticator {

		private String username;
		private String password;

		public CustomAuthenticator() {
		}

		public CustomAuthenticator(String username, String password) {
			this.username = username;
			this.password = password;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	}

	protected Archivo guardarArchivo(String nombre) {
		Archivo a = null;
		if (nombre != null) {
			HttpSession s = this.getThreadLocalRequest().getSession();
			Hashtable<String, String> names = (Hashtable<String, String>) s
					.getAttribute(FILE_NAMES);
			String field = (names != null) ? names.get(nombre) : null;
			if (field != null) {
				Hashtable<String, File> files = (Hashtable<String, File>) s
						.getAttribute(FILES);
				File f = (files != null) ? files.get(field) : null;
				if (f != null) {
					a = new Archivo();
					a.setTitulo(nombre);
					try {
						a.setRutaArchivo(f.getCanonicalPath());
					} catch (IOException e) {
						System.err.println(e);
					}
					Hashtable<String, String> types = (Hashtable<String, String>) s
							.getAttribute(FILE_TYPES);
					a.setMimeType((types != null) ? types.get(field) : null);
				}
			}
		}
		return a;
	}

	public static File getUploadDir(){
		return new File("/tmp");
	}
}
