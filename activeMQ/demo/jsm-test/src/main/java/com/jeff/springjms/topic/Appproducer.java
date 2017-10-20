package com.jeff.springjms.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Appproducer {

    private static final String url = "tcp://127.0.0.1:61616";

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        Connection connection;
        Session session;
        Destination destination = null;

        try {

            connection = connectionFactory.createConnection();
            //自动应答
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            //开启连接
            connection.start();
            //建立目标队列
            destination = session.createTopic("topic-test");
            //创建消费者
            MessageProducer producer = session.createProducer(destination);
            for (int i = 0; i < 200; i++) {
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText(""+i);
                System.out.println("发送消息："+i);
                producer.send(textMessage);
            }

            producer.close();

            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

}
