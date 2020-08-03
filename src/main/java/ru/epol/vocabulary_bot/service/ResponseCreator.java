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
                   reply = user.add(textSplit[1]);
                   break;
               case "/r":
                   reply = user.read();
                   break;
               case "/d":
                   reply = user.delete();
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
}
