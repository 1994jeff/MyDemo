package com.jeff.springjms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;

@Service
public class ProducerServiceImpl implements IProducerService {

    @Autowired
    JmsTemplate jmsTemplate;
    //因为一个项目可能会有多个目的地，所以这里使用name区分bean实例
    @Resource(name="queueDestination")
    Destination destination;

    public void sendMessage(final String msg) {
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(msg);
                return textMessage;
            }
        });
        System.out.println("发送消息："+msg);
    }

}
