package com.jeff.springjms;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

public class AppProducer {

    public static void main(String[] args) {

        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-test.xml");
        ProducerServiceImpl producerService = ac.getBean(ProducerServiceImpl.class);
        for (int i = 0; i < 100; i++) {
            producerService.sendMessage(""+i);
        }

    }

}
