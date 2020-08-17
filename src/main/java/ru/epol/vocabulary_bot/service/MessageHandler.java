package ru.epol.vocabulary_bot.service;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.epol.vocabulary_bot.cash.UserData;
import ru.epol.vocabulary_bot.user.User;


@Service
public class MessageHandler {
    private final ResponseCreator responseCreator;
    private final UserData userData;


    public MessageHandler(ResponseCreator responseCreator, UserData userData) {
        this.responseCreator = responseCreator;
        this.userData = userData;
    }

    public SendMessage sendMsg(Update update) {
        SendMessage reply = null;

        if (update.getMessage().getText() != null && update.getMessage().hasText()) {
            Long chatID = update.getMessage().getChatId();
            reply = responseCreator.response(checkUser(chatID), update.getMessage().getText());
        }

        return reply;
    }

    @Lookup
    public User createUser() {
        return null;
    }

    private User checkUser(Long chatID) {
        User user;
        if (userData.getUserDataCash(chatID) == null) {
            user = createUser();
            user.setChatID(chatID);
            userData.setUserDataCash(chatID, user);
        } else {
            user = userData.getUserDataCash(chatID);
        }

        return user;
    }
}

