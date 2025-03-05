/*
 * SPDX-License-Identifier: GPL-3.0
 * Copyright (C) 2025 Zenhachi
 */

package com.zenhachi.ai.openai.properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAiEmbeddingProperties {

    public static final String DEFAULT_EMBEDDING_MODEL = "text-embedding-ada-002";

    /**
     * Enable OpenAI embedding model.
     */
    private boolean enabled = true;

    private MetadataMode metadataMode = MetadataMode.EMBED;

    @NestedConfigurationProperty
    private OpenAiEmbeddingOptions options = OpenAiEmbeddingOptions.builder().model(DEFAULT_EMBEDDING_MODEL).build();

}
