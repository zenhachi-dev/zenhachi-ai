/*
 * SPDX-License-Identifier: GPL-3.0
 * Copyright (C) 2025 Zenhachi
 */

package com.zenhachi.ai.gemini.properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ConfigurationProperties("zenhachi.ai")
public class GeminiProperties {

    private Map<String, GeminiClientProperties> gemini;

}
