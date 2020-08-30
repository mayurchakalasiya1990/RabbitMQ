package com.rabbitlistener.messagelistner;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/*
    1) Define Queue
    2) Provide Connection to Queue
    3) Bind the Queue, Connection and Listener

* */

@Configuration
public class RabbitMQConfig {

    private static final String myQueue="MyQueue";

    @Bean
    public Queue myQueue() {
        return new Queue(myQueue,true);
    }

    @Bean
    Exchange myExchange(){
        return ExchangeBuilder.topicExchange("MyTopicExchange")
                .durable(true)
                .build();
    }

    @Bean
    Binding binding(){
        //return new Binding(myQueue,Binding.DestinationType.QUEUE,"MyTopicExchange","topic",null);
            return BindingBuilder.bind(myQueue())
                .to(myExchange())
                .with("topic")
                .noargs();

    }

    @Bean
    ConnectionFactory connectionFactory(){
        CachingConnectionFactory cachingConnectionFactory=new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");
        return cachingConnectionFactory;
    }

    @Bean
    MessageListenerContainer messageListenerContainer(){
        SimpleMessageListenerContainer simpleMessageListenerContainer=new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory());
        simpleMessageListenerContainer.setQueues(myQueue());
        simpleMessageListenerContainer.setMessageListener(new RabbitMQMessageListener());

        return simpleMessageListenerContainer;
    }
}
