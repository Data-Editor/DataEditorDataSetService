package com.niek125.datasetservice.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class HandlerMethodConfig {
    @Bean
    @Autowired
    public List<HandlerMethod> handlerMethods(RowCreatedHandlerMethod rowCreatedHandlerMethod,
                                              RowEditedHandlerMethod rowEditedHandlerMethod,
                                              RowDeletedMethodHandler rowDeletedMethodHandler,
                                              ProjectDeletedHandlerMethod projectDeletedHandlerMethod) {
        final List<HandlerMethod> methods = new ArrayList<>();
        methods.add(rowCreatedHandlerMethod);
        methods.add(rowEditedHandlerMethod);
        methods.add(rowDeletedMethodHandler);
        methods.add(projectDeletedHandlerMethod);
        return methods;
    }
}
