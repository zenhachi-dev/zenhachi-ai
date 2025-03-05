/*
 * SPDX-License-Identifier: GPL-3.0
 * Copyright (C) 2025 Zenhachi
 */

package com.zenhachi.ai.gemini.autoconf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(classes = {GeminiAutoConfiguration.class})
@TestPropertySource("classpath:test.properties")
class GeminiAutoConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void multipleGeminiChatModelsRegistered() {

        assertThat(applicationContext.containsBean("serverOne"), equalTo(true));
        assertThat(applicationContext.containsBean("serverTwo"), equalTo(true));

        assertThat(applicationContext.containsBean("modelOne"), equalTo(true));
        assertThat(applicationContext.containsBean("modelTwo"), equalTo(true));
        assertThat(applicationContext.containsBean("modelThree"), equalTo(true));
    }

}