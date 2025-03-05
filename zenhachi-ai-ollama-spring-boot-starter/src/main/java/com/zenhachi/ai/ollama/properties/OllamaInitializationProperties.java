/*
 * SPDX-License-Identifier: GPL-3.0
 * Copyright (C) 2025 Zenhachi
 */

package com.zenhachi.ai.ollama.properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.ai.ollama.management.PullModelStrategy;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class OllamaInitializationProperties {

    /**
     * Chat models initialization settings.
     */
    private final ModelTypeInit chat = new ModelTypeInit();

    /**
     * Embedding models initialization settings.
     */
    private final ModelTypeInit embedding = new ModelTypeInit();

    /**
     * Whether to pull models at startup-time and how.
     */
    private PullModelStrategy pullModelStrategy = PullModelStrategy.NEVER;

    /**
     * How long to wait for a model to be pulled.
     */
    private Duration timeout = Duration.ofMinutes(5);

    /**
     * Maximum number of retries for the model pull operation.
     */
    private int maxRetries = 0;

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ModelTypeInit {

        /**
         * Include this type of models in the initialization task.
         */
        private boolean include = true;

        /**
         * Additional models to initialize besides the ones configured via default
         * properties.
         */
        private List<String> additionalModels = new ArrayList<>();

    }

}
