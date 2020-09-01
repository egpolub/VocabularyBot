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

    private boolean isMention = false;

    public SendMessage help() {
        return new SendMessage(chatID, help).enableMarkdown(true);
    }

    public void add(String word, String translate) {
        dataService.updateDataBase(getChatID(), word, translate);
    }

    public SendMessage read() {
        return readAllWord().size() > 0 ? new SendMessage(getChatID(),
                textFormat(readAllWord()).toString()).enableHtml(true) : null;
    }

    public List<Word> readAllWord() {
        return dataService.readChatID(getChatID());
    }

    public SendMessage sortWord() {
        return new SendMessage(getChatID(), textFormat(dataService.sortWord(getChatID()))
                .toString()).enableHtml(true);
    }

    public SendMessage sortTranslation() {
        return new SendMessage(getChatID(), textFormat(dataService.sortTranslation(getChatID()))
                .toString()).enableHtml(true);
    }

    private StringBuilder textFormat(List<Word> list) {
        StringBuilder text = new StringBuilder("<pre>");
        for (Word word : list) {
            text.append(String.format("%-15s--%15s", word.getWord(), word.getTranslation())).append("\n");
        }
        text.append("</pre>");
        return text;
    }

    public void delete(String word) {
        dataService.delete(getChatID(), word);
    }

    public SendMessage settings() {
        String text = " *off*)";
        if (isMention) text = " *on*)";
        return new SendMessage(chatID, settings + text).enableMarkdown(true);
    }

    public boolean isWord(String word) { return dataService.isWord(getChatID(), word, word); }

    public boolean isMention() {
        return isMention;
    }

    public void setMention(boolean mention) {
        isMention = mention;
    }

    public void setChatID(Long chatID) {
        this.chatID = chatID;
    }

    public Long getChatID() {
        return chatID;
    }

}


