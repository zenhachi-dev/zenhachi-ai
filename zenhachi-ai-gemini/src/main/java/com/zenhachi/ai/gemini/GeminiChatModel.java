/*
 * SPDX-License-Identifier: GPL-3.0
 * Copyright (C) 2025 Zenhachi
 */

package com.zenhachi.ai.gemini;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import org.apache.http.HttpException;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;

public class GeminiChatModel implements ChatModel {

    private final Client chatApi;
    private final String model;

    public GeminiChatModel(Client geminiApi, String model) {
        Assert.notNull(geminiApi, "geminiApi must not be null");

        this.chatApi = geminiApi;
        this.model = model;
    }

    @Override
    public ChatResponse call(Prompt prompt) {
        try {
            GenerateContentResponse response = chatApi.models.generateContent(
                    getPromptModelOrDefault(prompt),
                    messagesToContents(prompt.getInstructions()),
                    null);

            return new ChatResponse(singletonList(new Generation(new AssistantMessage(response.text()))));

        } catch (IOException | HttpException e) {
            throw new RuntimeException(e);
        }
    }

    private String getPromptModelOrDefault(Prompt prompt) {
        return Optional.ofNullable(prompt.getOptions()).map(ChatOptions::getModel).orElse(model);
    }

    private List<Content> messagesToContents(List<Message> messages) {
        return messages.stream()
                .map(GeminiChatModel::messageToContent)
                .toList();
    }

    private static Content messageToContent(Message message) {
        return Content.builder()
                .role(message.getMessageType().getValue())
                .parts(singletonList(Part.builder()
                        .text(message.getText())
                        .build()
                ))
                .build();
    }
}
