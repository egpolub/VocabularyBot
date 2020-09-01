package ru.epol.vocabulary_bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.epol.vocabulary_bot.keyboard.KeyboardButtons;
import ru.epol.vocabulary_bot.user.User;

/**
 * Create response on update and callback messages.
 */
@Service
public class ResponseCreator {
    private final KeyboardButtons button;

    public ResponseCreator(KeyboardButtons button) {
        this.button = button;
    }

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
                case "Read":
                case "/r":
                    if (user.read() != null) {
                    reply = user.read();
                    reply.setReplyMarkup(button.getInlineMessageButtons("Sort by word", "Sort by translation"));
                    }
                    else {
                        reply = new SendMessage(user.getChatID(), "Your vocabulary is empty");
                    }
                    break;
                case "/d":
                    //variable "word" it's a necessary measure for compound words.
                    String word = text.replaceAll("/d", "").trim().toLowerCase();
                    if (user.isWord(word)) {
                        user.delete(word);
                        reply = new SendMessage(user.getChatID(), "The word has been deleted");
                    }
                    else {
                        reply = new SendMessage(user.getChatID(), "There is no such word in the dictionary");
                    }
                    break;
                case "Settings":
                case "/s":
                    reply = user.settings();
                    reply.setReplyMarkup(button.getInlineMessageButtons("Mention on", "Mention off"));
                    break;
                default:
                    reply = user.help();
                    button.setConstantButtons(reply);
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


    /**
     * Return Callback response on pressing buttons.
     */
    public BotApiMethod<?> callbackResponse(User user, CallbackQuery callbackQuery) {
        BotApiMethod<?> callbackAnswer = null;

        if (callbackQuery.getData().equals("Sort by word")) callbackAnswer = user.sortWord();

        if (callbackQuery.getData().equals("Sort by translation")) callbackAnswer = user.sortTranslation();

        if (callbackQuery.getData().equals("Mention on")) {
            user.setMention(true);
        }
        if (callbackQuery.getData().equals("Mention off")) {
            user.setMention(false);
        }

        return callbackAnswer;
    }
}
