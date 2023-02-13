package org.spring.Notifications.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {
    private String exchangeName="todo-exchange";
    private String registerQueue="todo-queue";
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(exchangeName);
    }
    @Bean
    public Queue registerQueue(){
        return new Queue(registerQueue);
    }
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    Binding bindingUser(DirectExchange exchange, Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("todo-routing");
    }

}
