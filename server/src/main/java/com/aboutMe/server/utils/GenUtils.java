package com.aboutMe.server.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Date;

import com.goebl.david.Response;
import com.goebl.david.Webb;

public class GenUtils {
	
	public static void main(String...args) {
		ServerSocket ss = null;
//		try {
//			ss = new ServerSocket(7777);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		try (Socket soc = new Socket("localhost/password/get", 9000)) {
			OutputStream is = soc.getOutputStream();
			OutputStreamWriter isr = new OutputStreamWriter(is);
			isr.write("hello world");
			isr.flush();
//			while (true) {
//				System.out.println("nope");
//				try (Socket socket = ss.accept()) { 
//					Date today = new Date(); 
//					String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
//					socket.getOutputStream().write(httpResponse.getBytes("UTF-8")); 
//					byte[] bytes = new byte[20];
//					
//					socket.getInputStream().read(bytes);
//					System.out.println("bytes: " + bytes);
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String randStringSecure(int length) {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[length];
		random.nextBytes(bytes);
		String token = bytes.toString();
		return token;
	}
	
	public static String getSalt(String token) {
		Webb webb = Webb.create();
		Response<String> req = webb.post("http://localhost:9000/password/salt")
		        .param("groupIdentifier", "aboutMe")
		        .param("token", token)
		        .ensureSuccess()
		        .asString();
		
		String password = req.getBody();

		return password;
	}
	
	public static String getPassword(String identifier, String token) {
		Webb webb = Webb.create();
		Response<String> req = webb.post("http://localhost:9000/password/get")
		        .param("groupIdentifier", "aboutMe")
		        .param("passwordIdentifier", identifier)
		        .param("token", token)
		        .ensureSuccess()
		        .asString();
		
		String password = req.getBody();
//		password = password.substring(password.length() - 3);

		return password;
	}
	
	public static String getIdentifer() {
		Webb webb = Webb.create();
		Response<String> req = webb.post("http://localhost:9000/get")
		        .param("groupIdentifier", "aboutMe")
		        .ensureSuccess()
		        .asString();
		
		String password = req.getBody();

		return password;
	}
}
