package com.example.waterandgasdevliveryappmvp.model.local;


import android.util.Log;

import com.example.waterandgasdevliveryappmvp.BuildConfig;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class OTPModel {

    // Put your Gmail credentials here (Model only)
    private final String fromEmail = "trickmic2@gmail.com";
    private final String appPassword = BuildConfig.MY_SECRET_API_KEY;

    public interface OTPListener {
        void onSent(String otp);
        void onError(Exception e);
    }

    public void sendOTP(String toEmail, OTPListener listener) {
        new Thread(() -> {
            try {
                // Generate 6-digit OTP
                String otp = String.valueOf(new Random().nextInt(900000) + 100000);

                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");
                //asd

                Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, appPassword);
                    }
                });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(fromEmail));
                message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(toEmail)
                );
                message.setSubject("Your OTP Code");
                message.setText("Your OTP is: " + otp);

                Transport.send(message);

                listener.onSent(otp);

            } catch (MessagingException e) {
                e.printStackTrace();
                listener.onError(e);
            }
        }).start();
    }
}