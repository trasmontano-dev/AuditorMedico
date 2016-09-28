package br.com.trasmontano.trasmonmobile.domain.util;

import android.util.Log;
import android.widget.Toast;

import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Address;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by fmuniz on 25/03/2016.
 */
public class EmailAPI {

    public void sendEmailAPI(String nome, String email) throws Exception {

        String password = "987654321";
        String toAddress = "fmuniz@trasmontano.com.br";
        String from = "trasonline2@trasmontano.com.br";
        String subject = "Teste email android";
        String mailText = "Email enviado através do app TrasmontanMobile - [" + nome + "]";

        try {
            Log.d("passou aqui", "");
            //int x = Integer.parseInt("dad");

            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtpauth.vcaremail.com.br");
            //properties.put("mail.smtp.socketFactory.port", "587");
            //properties.put("mail.smtp.socketFactory.", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.port", "587");
            Session session = Session.getDefaultInstance(properties,
                    new javax.mail.Authenticator(){
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication(){
                            return new PasswordAuthentication("trasonline2@trasmontano.com.br", "987654321");
                        }
                    });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            Address[] addresses = new Address[1];
            addresses[0] = new InternetAddress(toAddress);
            message.addRecipients(javax.mail.Message.RecipientType.TO, addresses);
            message.setSubject(subject);
            message.setText(mailText, "UTF-8");

            Transport.send(message);

        } catch (Exception ex) {
            //Snackbar.make(, "Informe nome e email", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            //Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG);
            //Log.d("ERRO SENDEMAIL----->>>>", ex.toString());
            //ex.printStackTrace();
            throw ex;
        }
    }
}
