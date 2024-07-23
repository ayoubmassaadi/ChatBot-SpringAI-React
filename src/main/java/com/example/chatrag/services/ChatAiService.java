package com.example.chatrag.services;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;

@BrowserCallable
@AnonymousAllowed
public class ChatAiService {
    private ChatClient chatClient;
    private VectorStore vectoreStore;
    @Value("classpath:/prompts/prompt-template.st")
    private Resource promptResource;


    public ChatAiService(ChatClient.Builder builder,VectorStore vectoreStore) {
        this.chatClient = builder.build();
        this.vectoreStore = vectoreStore;
    }

    public String ragChat(String question){
        List<Document> documents = vectoreStore.similaritySearch(question);
        List<String> context = documents.stream().map(Document::getContent).toList();
        PromptTemplate promptTemplate = new PromptTemplate(promptResource);
        Prompt prompt = promptTemplate.create(Map.of("context",context,"question",question));
        return chatClient.prompt(prompt)
                .call()
                .content();
    }
}
