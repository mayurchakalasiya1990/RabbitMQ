package com.rabbitlistener.messagelistner;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQExchangeConfiguration {

    @Bean
    Exchange exchange(){
        return new TopicExchange("ExampleExchange");
    }

    @Bean
    Exchange directExchange(){
        return ExchangeBuilder.directExchange("Example2Exchange").autoDelete().internal().build();
    }

    @Bean
    Exchange topicExchange(){
        return ExchangeBuilder.topicExchange("TopicExchange").autoDelete().internal().durable(true).build();
    }

    @Bean
    Exchange fanoutExchange(){
        return ExchangeBuilder.topicExchange("FanoutExchange").autoDelete().internal().durable(true).build();
    }

    @Bean
    Exchange headerExchange(){
        return ExchangeBuilder.topicExchange("headerExchange").autoDelete().internal().durable(true).build();
    }
}
