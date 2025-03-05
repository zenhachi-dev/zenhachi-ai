/*
 * SPDX-License-Identifier: GPL-3.0
 * Copyright (C) 2025 Zenhachi
 */

package com.zenhachi.ai.gemini.autoconf;

import com.google.genai.Client;
import com.zenhachi.ai.gemini.GeminiChatModel;
import com.zenhachi.ai.gemini.properties.GeminiChatProperties;
import com.zenhachi.ai.gemini.properties.GeminiClientProperties;
import com.zenhachi.ai.gemini.properties.GeminiProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.Map;
import java.util.Optional;

@Slf4j
@AutoConfiguration
public class GeminiAutoConfiguration {

    @Bean
    public BeanFactoryPostProcessor registerGeminiBeans(Environment environment) {
        return beanFactory -> {
            GeminiProperties properties = Binder.get(environment).bind("zenhachi.ai", Bindable.of(GeminiProperties.class)).orElse(new GeminiProperties());

            if (properties.getGemini() != null) {
                for (Map.Entry<String, GeminiClientProperties> clientProperties : properties.getGemini().entrySet()) {
                    Client geminiClient = buildGeminiClient(clientProperties.getValue());
                    beanFactory.registerSingleton(clientProperties.getKey(), geminiClient);
                    log.info("Registered Gemini Client bean with name: {}", clientProperties.getKey());

                    if (clientProperties.getValue().getChat() != null) {
                        for (Map.Entry<String, GeminiChatProperties> modelProperties : clientProperties.getValue().getChat().entrySet()) {
                            beanFactory.registerSingleton(modelProperties.getKey(), buildGeminiChatModel(modelProperties.getValue(), geminiClient));
                            log.info("Registered GeminiChatModel bean with name: {}", modelProperties.getKey());
                        }
                    }
                }
            }
        };
    }

    private Client buildGeminiClient(GeminiClientProperties properties) {
        return Client.builder().apiKey(properties.getApiKey()).build();
    }

    private GeminiChatModel buildGeminiChatModel(GeminiChatProperties properties, Client aiClient) {
        return new GeminiChatModel(aiClient, Optional.ofNullable(properties.getModel()).orElse("gemini-2.0-flash"));
    }

}
