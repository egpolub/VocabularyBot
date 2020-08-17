package ru.epol.vocabulary_bot.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.epol.vocabulary_bot.entity.Word;
import ru.epol.vocabulary_bot.service.WordDataService;

import java.util.List;


@Component
@PropertySource("classpath:text.properties")
@Scope(value = "prototype")
public class User {
    @Autowired
    private  WordDataService dataService;

    private Long chatID;

    @Value("${text.help}")
    private String help;

    @Value ("${text.settings}")
    private String settings;


    public SendMessage help() {
        return new SendMessage(chatID, help).enableMarkdown(true);
    }

    public void add(String word, String translate) {
        dataService.updateDataBase(getChatID(), word, translate);
    }

    public SendMessage read() {
        StringBuilder text = new StringBuilder("<pre>");
        List<Word> list = dataService.readChatID(getChatID());
        for (Word word : list) {
            text.append(String.format("%-15s--%15s", word.getWord(), word.getTranslation())).append("\n");
        }

        return new SendMessage(getChatID(), text.append("</pre>").toString()).enableHtml(true);
    }

    public void delete(String word) {
        dataService.delete(getChatID(), word);
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
