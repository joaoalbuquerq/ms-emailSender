package com.ms.email.consumers;

import com.ms.email.dtos.EmailRecordDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    //Ouvinte da fila lá no AMQP cloud
    @RabbitListener(queues = "${broker.queue.email.name}") //Via propriedade o código entende que tem que ouvir esta fila
    public void ouvirFilaEmail(@Payload EmailRecordDto emailDto){
        System.out.println(emailDto.emailDestino());
    }
}
