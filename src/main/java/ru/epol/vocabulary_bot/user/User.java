package ru.epol.vocabulary_bot.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


@Component
@PropertySource("classpath:text.properties")
@Scope(value = "prototype")
public class User {
    private Long chatID;

    @Value("${text.help}")
    private String help;

    @Value ("${text.add}")
    private String add;

    @Value ("${text.read}")
    private String read;

    @Value ("${text.delete}")
    private String delete;

    @Value ("${text.settings}")
    private String settings;


    public SendMessage help() {
        return new SendMessage(chatID, help);
    }

    public SendMessage add(String text) {
        return new SendMessage(chatID, add);
    }

    public SendMessage read() {
        return new SendMessage(chatID, read);
    }

    public SendMessage delete() {
        return new SendMessage(chatID, delete);
    }

    public SendMessage settings() {
        return new SendMessage(chatID, settings);
    }

    public void setChatID(Long chatID) {
        this.chatID = chatID;
    }

    public Long getChatID() {
        return chatID;
    }
}
