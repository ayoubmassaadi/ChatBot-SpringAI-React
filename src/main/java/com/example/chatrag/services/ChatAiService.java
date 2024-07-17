package com.example.chatrag.services;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@BrowserCallable
@AnonymousAllowed
public class ChatAiService {
    private ChatClient chatClient;
    private VectorStore vectoreStore;


    public ChatAiService(ChatClient.Builder builder,VectorStore vectoreStore) {
        this.chatClient = builder.build();
        this.vectoreStore = vectoreStore;
    }

    public String ragChat(String question){
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }
}
