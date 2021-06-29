package com.modelo.services;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Service
public class EmailService {

	public String sendEmail(String email, String token) throws IOException {

		Email from = new Email("contato@assisteng.com.br");
		String subject = "Recuperar senha - Trilhar";
		Email to = new Email(email);
		Content content = new Content("text/html",
				"Utilize o código a seguir para redefinir sua senha: <b> " + token + "</b>");
		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid("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");
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
		} catch (IOException ex) {
			throw ex;
		}
		return subject;
	}
	
}
