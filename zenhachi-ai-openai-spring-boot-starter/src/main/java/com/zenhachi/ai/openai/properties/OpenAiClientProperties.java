/*
 * SPDX-License-Identifier: GPL-3.0
 * Copyright (C) 2025 Zenhachi
 */

package com.zenhachi.ai.openai.properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAiClientProperties {

    public static final String DEFAULT_EMBEDDINGS_PATH = "/v1/embeddings";
    public static final String DEFAULT_COMPLETIONS_PATH = "/v1/chat/completions";
    public static final String DEFAULT_BASE_URL = "https://api.openai.com";

    private String apiKey;

    private String baseUrl = DEFAULT_BASE_URL;

    private String projectId;

    private String organizationId;

    private Map<String, OpenAiChatProperties> chat;

    private Map<String, OpenAiEmbeddingProperties> embedding;

    private Map<String, OpenAiImageProperties> image;

    private String completionsPath = DEFAULT_COMPLETIONS_PATH;

    private String embeddingsPath = DEFAULT_EMBEDDINGS_PATH;

    private Duration connectTimeout;

    private Duration readTimeout;

}
