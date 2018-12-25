package com.aboutMe.server.service.impl;

import java.util.HashMap;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aboutMe.server.entities.User;
import com.aboutMe.server.repo.UserRepo;
import com.aboutMe.server.service.UserSrvc;
import com.aboutMe.server.utils.GenUtils;
import com.aboutMe.server.utils.HtmlString;

@Service
public class UserSrcvImpl implements UserSrvc {
	
	@Autowired
	UserRepo userRepo;
	
	@Override
	public void addUser(User user) {
		String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashed);
		userRepo.save(user);
	}

	@Override
	public User loginUser(User user) {
		User dbUser = userRepo.getByEmail(user.getEmail());
		if (!BCrypt.checkpw(user.getPassword(), dbUser.getPassword())) {
			return null;
		}
		
		String token = GenUtils.randStringSecure(256);
		dbUser.setUserToken(token);
		userRepo.save(dbUser);
		user.setUserToken(token);
		return user;
	}

	@Override
	public boolean authinticate(User user) {
		User u = userRepo.getByEmail(user.getEmail());
		if (user.getUserToken() == u.getUserToken()) {
			return true;
		}
		
		return false;
	}
	
	public static void sendEmail(String to, String subject, String htmlContent) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("AboutMeMailServer","sho2shaoNgoiphieghiebaeti8thae");
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("AboutMeMailServer@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(subject);
			message.setContent(htmlContent, "text/html");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public User getUser(String email) {
		User user = userRepo.getByEmail(email);
		if (user == null) {
			return null;
		}
		
//		sendEmail();
		user.setPassword(null);
		user.setUserToken(null);
		return user;
	}

	@Override
	public void updatePassword(User user) {
		User dbUser = getUser(user.getEmail());
		String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		dbUser.setPassword(hashed);
		userRepo.save(dbUser);
	}

	@Override
	public void updateEmail(User user, String newEmail) {
		User dbUser = getUser(user.getEmail());
		dbUser.setEmail(newEmail);
		userRepo.save(dbUser);
	}

	@Override
	public void resetPassword(User user) {
		HashMap<String, Object> scope = new HashMap<String, Object>();
		User dbUser = getUser(user.getEmail());
		if(dbUser == null) {
			return;
		}

		scope.put("name", dbUser.getName());
		scope.put("url", "http://www.google.com");
		HtmlString htmlString = new HtmlString(scope, "./src/main/resources/static/emailTemplates/password-reset.html");
		sendEmail(dbUser.getEmail(), "Password Reset", htmlString.toString());
	}
}
