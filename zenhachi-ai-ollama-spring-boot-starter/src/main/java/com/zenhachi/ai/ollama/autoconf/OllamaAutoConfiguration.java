/*
 * SPDX-License-Identifier: GPL-3.0
 * Copyright (C) 2025 Zenhachi
 */

package com.zenhachi.ai.ollama.autoconf;

import com.zenhachi.ai.ollama.properties.OllamaChatProperties;
import com.zenhachi.ai.ollama.properties.OllamaClientProperties;
import com.zenhachi.ai.ollama.properties.OllamaProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.Map;

@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(OllamaProperties.class)
public class OllamaAutoConfiguration {

    @Bean
    public BeanFactoryPostProcessor registerOllamaClients(Environment environment) {
        return beanFactory -> {
            OllamaProperties properties = Binder.get(environment).bind("zenhachi.ai", Bindable.of(OllamaProperties.class)).orElse(new OllamaProperties());

            if (properties.getOllama() != null) {
                for (Map.Entry<String, OllamaClientProperties> clientProperties : properties.getOllama().entrySet()) {
                    OllamaApi ollamaClient = buildOllamaClient(clientProperties.getValue());
                    beanFactory.registerSingleton(clientProperties.getKey(), ollamaClient);
                    log.info("Registered OllamaApi bean with name: {}", clientProperties.getKey());

                    if (clientProperties.getValue().getChat() != null) {
                        for (Map.Entry<String, OllamaChatProperties> modelProperties : clientProperties.getValue().getChat().entrySet()) {
                            beanFactory.registerSingleton(modelProperties.getKey(), buildOllamaChatClient(modelProperties.getValue(), ollamaClient));
                            log.info("Registered OllamaChatModel bean with name: {}", modelProperties.getKey());
                        }
                    }
                }
            }
        };
    }

    private OllamaApi buildOllamaClient(OllamaClientProperties properties) {
        return new OllamaApi(properties.getBaseUrl());
    }

    private OllamaChatModel buildOllamaChatClient(OllamaChatProperties properties, OllamaApi aiClient) {
        return OllamaChatModel.builder()
                .ollamaApi(aiClient)
                .defaultOptions(properties.getOptions())
                .build();
    }

}
