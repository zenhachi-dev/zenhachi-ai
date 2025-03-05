/*
 * SPDX-License-Identifier: GPL-3.0
 * Copyright (C) 2025 Zenhachi
 */

package com.zenhachi.ai.openai.properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAiImageProperties {

    public static final String DEFAULT_IMAGE_MODEL = OpenAiImageApi.ImageModel.DALL_E_3.getValue();

    /**
     * Enable OpenAI image model.
     */
    private boolean enabled = true;

    /**
     * Options for OpenAI Image API.
     */
    @NestedConfigurationProperty
    private OpenAiImageOptions options = OpenAiImageOptions.builder().model(DEFAULT_IMAGE_MODEL).build();

    public String getModel() {
        return this.options.getModel();
    }

    public void setModel(String model) {
        this.options.setModel(model);
    }

}
