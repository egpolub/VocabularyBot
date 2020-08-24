package ru.epol.vocabulary_bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.epol.vocabulary_bot.user.User;

import java.util.ArrayList;
import java.util.List;


@Service
public class ResponseCreator {

    public SendMessage response(User user, String text) {
        SendMessage reply;
        String[] textSplit = text.split(" ");

        try {
            switch (textSplit[0]) {
                case "/a":
                    textSplit = textParser(text);
                    user.add(textSplit[0].trim().toLowerCase(),
                            textSplit[1].trim().toLowerCase());
                    reply = new SendMessage(user.getChatID(), "" +
                            "The dictionary has been updated");
                    break;
                case "/r":
                    reply = user.read();
                    reply.setReplyMarkup(getInlineMessageButtons());
                    break;
                case "/d":
                    if (user.isWord(textSplit[1].toLowerCase())) {
                        user.delete(textSplit[1].toLowerCase());
                        reply = new SendMessage(user.getChatID(), "The word has been deleted");
                    }
                    else {
                        reply = new SendMessage(user.getChatID(), "There is no such word in the dictionary");
                    }
                    break;
                case "/s":
                    reply = user.settings();
                    break;
                default:
                    reply = user.help();
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            reply = new SendMessage(user.getChatID(), "Maybe that you wrote wrong command");
        }
        return reply;
    }

    private String[] textParser(String text) {
        return text.split("/a")[1].split("[*]");
    }

    private InlineKeyboardMarkup getInlineMessageButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonYes = new InlineKeyboardButton().setText("Sort by word").setCallbackData("byWord");
        InlineKeyboardButton buttonNo = new InlineKeyboardButton().setText("Sort by translation").
                setCallbackData("byTranslation");

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(buttonYes);
        keyboardButtonsRow.add(buttonNo);

        List<List<InlineKeyboardButton>> listKeyboardRow = new ArrayList<>();
        listKeyboardRow.add(keyboardButtonsRow);

        return inlineKeyboardMarkup.setKeyboard(listKeyboardRow);
    }

    public BotApiMethod<?> callbackResponse(User user, CallbackQuery callbackQuery) {
        BotApiMethod<?> callbackAnswer = null;

        if (callbackQuery.getData().equals("byWord")) callbackAnswer = user.sortWord();

        if (callbackQuery.getData().equals("byTranslation")) callbackAnswer = user.sortTranslation();

        return callbackAnswer;
    }
}
