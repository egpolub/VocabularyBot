package ru.epol.vocabulary_bot.service;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.epol.vocabulary_bot.user.User;

@Component
public class MessageHandler {
    private BotCommand botCommand;
    private final ResponseCreator responseCreator;


    public MessageHandler(ResponseCreator responseCreator) {
        this.responseCreator = responseCreator;
    }

    public SendMessage sendMsg(Update update) {
        SendMessage reply = null;
        Message message = update.getMessage();

        if (message.getText() != null && message.hasText()) {
            Long chatID = message.getChatId();

            textStatus(message.getText());

            User user = createUser();
            user.setChatID(chatID);

            System.out.println(user.getChatID());
            reply = responseCreator.response(user, botCommand);

        }
        return reply;
    }

    @Lookup
    public User createUser() {
        return null;
    }

    private void textStatus(String text) {
        switch (text) {
            case "/help":
                botCommand = BotCommand.BOT_HELP;
                break;
            case "/a":
                botCommand = BotCommand.BOT_ADD;
                break;
            case "/r":
                botCommand = BotCommand.BOT_READ;
                break;
            case "/d":
                botCommand = BotCommand.BOT_DELETE;
                break;
            case "/s":
                botCommand = BotCommand.BOT_SETTINGS;
                break;
            default:
                botCommand = BotCommand.BOT_DEFAULT;
                break;
        }
    }

}

