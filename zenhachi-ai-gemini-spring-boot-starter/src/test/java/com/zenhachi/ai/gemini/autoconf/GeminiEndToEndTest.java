/*
 * SPDX-License-Identifier: GPL-3.0
 * Copyright (C) 2025 Zenhachi
 */

package com.zenhachi.ai.gemini.autoconf;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@Disabled
@SpringBootTest(
        classes = {GeminiAutoConfiguration.class},
        properties = {
                "zenhachi.ai.gemini.google.api-key=", //Place your key here :)
                "zenhachi.ai.gemini.google.chat.gemini.model=gemini-2.0-flash"
        }
)
class GeminiEndToEndTest {

    @Autowired
    @Qualifier("gemini")
    private ChatModel geminiModel;

    @Test
    void aCallToGeminiCanBeDone() {

        ChatClient chatClient = ChatClient.builder(geminiModel).build();

        String response = chatClient.prompt("Hello, how are you?").call().content();
        System.out.println("Gemini response: " + response);
        assertThat(response, notNullValue());
    }

}