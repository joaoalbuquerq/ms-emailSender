package com.ms.email.consumers;

import com.ms.email.dtos.EmailRecordDto;
import com.ms.email.models.EmailModel;
import com.ms.email.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @Autowired
    EmailService service;

    //Ouvinte da fila lá no AMQP cloud
    @RabbitListener(queues = "${broker.queue.email.name}") //Via propriedade o código entende que tem que ouvir esta fila
    public void ouvirFilaEmail(@Payload EmailRecordDto emailDto){
        var emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);

        //Enviar o email
        service.enviarEmail(emailModel);
    }
}
