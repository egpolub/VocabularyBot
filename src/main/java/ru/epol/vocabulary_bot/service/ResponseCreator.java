package ru.epol.vocabulary_bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.epol.vocabulary_bot.user.User;


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
                    break;
                case "/d":
                    user.delete(textSplit[1]);
                    reply = new SendMessage(user.getChatID(), "The word has been deleted");
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
}
