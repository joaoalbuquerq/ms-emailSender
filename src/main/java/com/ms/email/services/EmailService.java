package com.ms.email.services;

import com.ms.email.dtos.EmailRecordDto;
import com.ms.email.enums.StatusEmail;
import com.ms.email.models.EmailModel;
import com.ms.email.repositories.EmailRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmailService {

    @Autowired
    EmailRepository repository;

    @Autowired
    JavaMailSender sender;

    @Value(value = "${spring.mail.username}")
    private String emailEnvio;

    @Transactional
    public EmailModel enviarEmail(EmailModel model){

        try{
            model.setDataEnvio(LocalDate.now());
            model.setEmailEnvio(emailEnvio);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(model.getEmailDestino());
            message.setSubject(model.getTitulo());
            message.setText(model.getConteudo());
            sender.send(message);

            model.setStatusEmail(StatusEmail.ENVIADO);
        }catch (MailException e){
            model.setStatusEmail(StatusEmail.ERRO_ENVIO);
        }finally {
            return repository.save(model);
        }
    }

}
