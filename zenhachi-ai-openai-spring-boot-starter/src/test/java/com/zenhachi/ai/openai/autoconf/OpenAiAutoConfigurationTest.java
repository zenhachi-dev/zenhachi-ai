/*
 * SPDX-License-Identifier: GPL-3.0
 * Copyright (C) 2025 Zenhachi
 */

package com.zenhachi.ai.openai.autoconf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(classes = {OpenAiAutoConfiguration.class})
@TestPropertySource("classpath:test.properties")
class OpenAiAutoConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void multipleOpenAiChatModelsRegistered() {

        assertThat(applicationContext.containsBean("serverOne"), equalTo(true));
        assertThat(applicationContext.containsBean("serverTwo"), equalTo(true));

        assertThat(applicationContext.containsBean("modelOne"), equalTo(true));
        assertThat(applicationContext.containsBean("modelTwo"), equalTo(true));
        assertThat(applicationContext.containsBean("modelThree"), equalTo(true));
    }

    @Test
    void multipleOpenAiImageModelsRegistered() {

        assertThat(applicationContext.containsBean("serverOne"), equalTo(true));
        assertThat(applicationContext.containsBean("serverTwo"), equalTo(true));

        assertThat(applicationContext.containsBean("imageModelOne"), equalTo(true));
        assertThat(applicationContext.containsBean("imageModelTwo"), equalTo(true));
        assertThat(applicationContext.containsBean("imageModelThree"), equalTo(true));
    }

}