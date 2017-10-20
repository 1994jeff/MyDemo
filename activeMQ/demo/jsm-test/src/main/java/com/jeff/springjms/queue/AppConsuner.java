package com.jeff.springjms.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class AppConsuner {

    private static final String url = "tcp://127.0.0.1:61616";

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        Connection connection;
        final Session session;
        Destination destination = null;

        try {

            connection = connectionFactory.createConnection();
            //自动应答
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            //开启连接
            connection.start();
            //建立目标队列
            destination = session.createQueue("queue-test");
            //创建消费者
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    //ObjectMessage objectMessage = (ObjectMessage) message;
                    //objectMessage.getObject();
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("接收消息:"+textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
//            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }


    }

}
