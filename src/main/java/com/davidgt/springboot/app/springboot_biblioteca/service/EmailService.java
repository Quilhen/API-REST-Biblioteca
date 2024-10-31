package com.davidgt.springboot.app.springboot_biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {


    @Autowired
    private JavaMailSender remitenteCorreo;
    

    public void enviarMensaje(String destinatario, String asunto, String texto){
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject(asunto);
        mensaje.setText(texto);
        mensaje.setFrom("sandbox.smtp.mailtrap.io");
        remitenteCorreo.send(mensaje);
    }
}
