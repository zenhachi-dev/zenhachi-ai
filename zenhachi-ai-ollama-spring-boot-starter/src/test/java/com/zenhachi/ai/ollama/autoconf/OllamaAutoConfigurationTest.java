/*
 * SPDX-License-Identifier: GPL-3.0
 * Copyright (C) 2025 Zenhachi
 */

package com.zenhachi.ai.ollama.autoconf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(classes = {OllamaAutoConfiguration.class})
@TestPropertySource("classpath:test.properties")
class OllamaAutoConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void multipleOllamaChatModelsRegistered() {

        assertThat(applicationContext.getEnvironment().getProperty("zenhachi.ai.ollama.serverOne.chat.modelOne.model"), notNullValue());

        assertThat(applicationContext.containsBean("serverOne"), equalTo(true));
        assertThat(applicationContext.containsBean("serverTwo"), equalTo(true));

        assertThat(applicationContext.containsBean("modelOne"), equalTo(true));
        assertThat(applicationContext.containsBean("modelTwo"), equalTo(true));
        assertThat(applicationContext.containsBean("modelThree"), equalTo(true));
    }


}