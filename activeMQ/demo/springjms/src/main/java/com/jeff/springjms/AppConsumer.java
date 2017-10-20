package com.jeff.springjms;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

public class AppConsumer {

    public static void main(String[] args) {
        ApplicationContext acc = new ClassPathXmlApplicationContext("consumer.xml");
    }
}
