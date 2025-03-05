/*
 * SPDX-License-Identifier: GPL-3.0
 * Copyright (C) 2025 Zenhachi
 */

package com.zenhachi.ai.openai.autoconf;

import com.zenhachi.ai.openai.properties.OpenAiChatProperties;
import com.zenhachi.ai.openai.properties.OpenAiClientProperties;
import com.zenhachi.ai.openai.properties.OpenAiImageProperties;
import com.zenhachi.ai.openai.properties.OpenAiProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@AutoConfiguration
public class OpenAiAutoConfiguration {

    @Bean
    public BeanFactoryPostProcessor registerOpenAiBeans(Environment environment) {
        return beanFactory -> {
            OpenAiProperties properties = Binder.get(environment).bind("zenhachi.ai", Bindable.of(OpenAiProperties.class)).orElse(new OpenAiProperties());

            if (properties.getOpenai() != null) {
                for (Map.Entry<String, OpenAiClientProperties> clientProperties : properties.getOpenai().entrySet()) {
                    OpenAiApi ollamaClient = buildOpenAiClient(clientProperties.getValue());
                    beanFactory.registerSingleton(clientProperties.getKey(), ollamaClient);
                    log.info("Registered OpenAiApi bean with name: {}", clientProperties.getKey());

                    if (clientProperties.getValue().getChat() != null) {
                        for (Map.Entry<String, OpenAiChatProperties> modelProperties : clientProperties.getValue().getChat().entrySet()) {
                            beanFactory.registerSingleton(modelProperties.getKey(), buildOpenAiChatModel(modelProperties.getValue(), ollamaClient));
                            log.info("Registered OpenAiChatModel bean with name: {}", modelProperties.getKey());
                        }
                    }

                    if (clientProperties.getValue().getImage() != null) {
                        for (Map.Entry<String, OpenAiImageProperties> modelProperties : clientProperties.getValue().getImage().entrySet()) {
                            beanFactory.registerSingleton(modelProperties.getKey(), buildOpenAiImageModel(clientProperties.getValue(), modelProperties.getValue()));
                            log.info("Registered OpenAiImageModel bean with name: {}", modelProperties.getKey());
                        }
                    }
                }
            }
        };
    }

    private OpenAiChatModel buildOpenAiChatModel(OpenAiChatProperties properties, OpenAiApi aiClient) {
        return OpenAiChatModel.builder()
                .openAiApi(aiClient)
                .defaultOptions(properties.getOptions())
                .build();
    }

    private OpenAiApi buildOpenAiClient(OpenAiClientProperties properties) {
        return OpenAiApi.builder()
                .baseUrl(properties.getBaseUrl())
                .apiKey(properties.getApiKey())
                .headers(buildOpenAiClientHeaders(properties))
                .completionsPath(properties.getCompletionsPath())
                .embeddingsPath(properties.getEmbeddingsPath())
                .build();
    }

    private MultiValueMap<String, String> buildOpenAiClientHeaders(OpenAiClientProperties properties) {
        Map<String, List<String>> connectionHeaders = new HashMap();
        if (StringUtils.hasText(properties.getProjectId())) {
            connectionHeaders.put("OpenAI-Project", List.of(properties.getProjectId()));
        }

        if (StringUtils.hasText(properties.getOrganizationId())) {
            connectionHeaders.put("OpenAI-Organization", List.of(properties.getOrganizationId()));
        }
        return CollectionUtils.toMultiValueMap(connectionHeaders);
    }

    private OpenAiImageModel buildOpenAiImageModel(OpenAiClientProperties clientProperties, OpenAiImageProperties value) {
        return new OpenAiImageModel(buildOpenAiImageApi(clientProperties), value.getOptions(), RetryTemplate.defaultInstance());
    }

    private OpenAiImageApi buildOpenAiImageApi(OpenAiClientProperties clientProperties) {
        return OpenAiImageApi.builder()
                .apiKey(clientProperties.getApiKey())
                .baseUrl(clientProperties.getBaseUrl())
                .restClientBuilder(buildOpenAiImageApiRestClientBuilder(clientProperties))
                .build();
    }

    private RestClient.Builder buildOpenAiImageApiRestClientBuilder(OpenAiClientProperties clientProperties) {
        if (clientProperties.getConnectTimeout() != null || clientProperties.getReadTimeout() != null) {
            return RestClient.builder()
                    .requestFactory(ClientHttpRequestFactoryBuilder
                            .detect()
                            .build(new ClientHttpRequestFactorySettings(null, getConnectTimeoutOrDefault(clientProperties), getReadTimeoutOrDefault(clientProperties), null)
                            )
                    );
        }
        return RestClient.builder();
    }

    private Duration getReadTimeoutOrDefault(OpenAiClientProperties clientProperties) {
        return Optional.ofNullable(clientProperties.getReadTimeout()).orElse(Duration.ofSeconds(30));
    }

    private Duration getConnectTimeoutOrDefault(OpenAiClientProperties clientProperties) {
        return Optional.ofNullable(clientProperties.getConnectTimeout()).orElse(Duration.ofSeconds(30));
    }

}
