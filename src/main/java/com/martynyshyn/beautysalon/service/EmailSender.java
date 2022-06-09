package com.martynyshyn.beautysalon.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

public class EmailSender {
    private Message message;

    {
        try {
            Properties prop = new Properties();
            prop.put("mail.smtp.auth", true);
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", "smtp.mailtrap.io");
            prop.put("mail.smtp.port", "25");
            prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("bfactortest@gmail.com", "bfactor12345");
                }
            });
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress("bfactortest@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse("jacobodecastillo@gmail.com"));
            message.setSubject("Test");

            String msg = "This is my first email using JavaMailer";

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void sendMail() throws MessagingException {
        Transport.send(message);
    }

    public static void main(String[] args) {

        try {
            new EmailSender().sendMail();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        /*Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Test it's work or NO!");
            }
        };

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 30);

        timer.schedule(timerTask, calendar.getTime(), 10000);*/
    }
}