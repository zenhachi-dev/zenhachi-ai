/*
 * SPDX-License-Identifier: GPL-3.0
 * Copyright (C) 2025 Zenhachi
 */

package com.zenhachi.ai.ollama.properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class OllamaChatProperties {

    /**
     * Enable Ollama chat model.
     */
    private boolean enabled = true;

    /**
     * Client lever Ollama options. Use this property to configure generative temperature,
     * topK and topP and alike parameters. The null values are ignored defaulting to the
     * generative's defaults.
     */
    @NestedConfigurationProperty
    private OllamaOptions options = OllamaOptions.builder().model(OllamaModel.MISTRAL.id()).build();

    public String getModel() {
        return this.options.getModel();
    }

    public void setModel(String model) {
        this.options.setModel(model);
    }

}
