/*
 * SPDX-License-Identifier: GPL-3.0
 * Copyright (C) 2025 Zenhachi
 */

package com.zenhachi.ai.ollama.properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class OllamaClientProperties {

    /**
     * Base URL where Ollama API server is running.
     */
    private String baseUrl = "http://localhost:11434";

    private OllamaInitializationProperties init;

    private Map<String, OllamaChatProperties> chat;

    private Map<String, OllamaEmbeddingProperties> embedding;

}
