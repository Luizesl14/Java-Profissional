package com.movies.config;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;

@Configuration
public class JMSConfiguration {


    @Bean // Queues
    public JmsListenerContainerFactory myFactory(ConnectionFactory connectionFactory,
                                                 DefaultJmsListenerContainerFactoryConfigurer configurer,
                                                 JmsErrorHandler jmsErrorHandler){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setErrorHandler(jmsErrorHandler);
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean // Topcs
    public JmsListenerContainerFactory topcListenerFactory(ConnectionFactory connectionFactory,
                                                           DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(true);
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean // Converter
    public MessageConverter jacksonJmsMsgConverter(){
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
