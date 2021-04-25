package com.georgidinov.jmsexample.listener;

import com.georgidinov.jmsexample.config.JMSConfig;
import com.georgidinov.jmsexample.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HelloWorldMessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JMSConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers,
                       Message message) {
    }


    @JmsListener(destination = JMSConfig.MY_SEN_AND_RECEIVE_QUEUE)
    public void listenForHelloSendAndReceive(@Payload HelloWorldMessage helloWorldMessage,
                                             @Headers MessageHeaders headers,
                                             Message message) throws JMSException {
        HelloWorldMessage replyMessage = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("World!")
                .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), replyMessage);
    }

}