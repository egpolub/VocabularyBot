package ru.epol.vocabulary_bot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.epol.vocabulary_bot.keyboard.KeyBoardFacade;


@Component
@PropertySource("classpath:text.properties")
public class ResponseCreator {
    @Value ("${text.help}")
    private String help;

    @Value ("${text.add}")
    private String add;

    @Value ("${text.read}")
    private String read;

    @Value ("${text.delete}")
    private String delete;

    @Value ("${text.settings}")
    private String settings;

    @Value ("${text.another}")
    private String another;

    public SendMessage response(long chatID, BotCommand botCommand) {
        SendMessage reply;
        if (botCommand.equals(BotCommand.BOT_HELP)) {
            reply = new SendMessage(chatID, help);
            KeyBoardFacade.setButtons(reply);
        } else if (botCommand.equals(BotCommand.BOT_ADD)) {
            reply = new SendMessage(chatID, add);
        } else if (botCommand.equals(BotCommand.BOT_READ)) {
            reply = new SendMessage(chatID, read);
        } else if (botCommand.equals(BotCommand.BOT_DELETE)) {
            reply = new SendMessage(chatID, delete);
        } else if (botCommand.equals(BotCommand.BOT_SETTINGS)) {
            reply = new SendMessage(chatID, settings);
        }
        else {
            reply = new SendMessage(chatID, another);
        }

        return reply;
    }
}
