/*
 * SPDX-License-Identifier: GPL-3.0
 * Copyright (C) 2025 Zenhachi
 */

package com.zenhachi.ai.gemini.properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeminiChatProperties {

    private String model;

}
