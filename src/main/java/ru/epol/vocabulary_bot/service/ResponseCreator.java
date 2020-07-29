package ru.epol.vocabulary_bot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.epol.vocabulary_bot.user.User;


@Component
public class ResponseCreator {

   public SendMessage response(User user, BotCommand botCommand) {
       SendMessage reply;
       switch (botCommand) {
           case BOT_ADD:
               reply = user.add();
               break;
           case BOT_READ:
               reply = user.read();
               break;
           case BOT_DELETE:
               reply = user.delete();
               break;
           case BOT_SETTINGS:
               reply = user.settings();
               break;
           default:
               reply = user.help();
               break;
       }
       return reply;
   }
}
