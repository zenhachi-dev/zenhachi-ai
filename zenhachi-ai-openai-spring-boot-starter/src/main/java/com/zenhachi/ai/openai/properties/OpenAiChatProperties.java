/*
 * SPDX-License-Identifier: GPL-3.0
 * Copyright (C) 2025 Zenhachi
 */

package com.zenhachi.ai.openai.properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAiChatProperties {

    public static final String DEFAULT_CHAT_MODEL = "gpt-4o-mini";

    private static final Double DEFAULT_TEMPERATURE = 0.7;

    /**
     * Enable OpenAI chat model.
     */
    private boolean enabled = true;

    @NestedConfigurationProperty
    private OpenAiChatOptions options = OpenAiChatOptions.builder()
            .model(DEFAULT_CHAT_MODEL)
            .temperature(DEFAULT_TEMPERATURE)
            .build();

    public String getModel() {
        return this.options.getModel();
    }

    public void setModel(String model) {
        this.options.setModel(model);
    }

}
