package ru.epol.vocabulary_bot.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.epol.vocabulary_bot.VocabularyBot;
import ru.epol.vocabulary_bot.cash.UserData;
import ru.epol.vocabulary_bot.entity.Word;
import ru.epol.vocabulary_bot.user.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ScheduledService {
    private final VocabularyBot vocabularyBot;
    private final UserData userData;

    public ScheduledService(VocabularyBot vocabularyBot, UserData userData) {
        this.vocabularyBot = vocabularyBot;
        this.userData = userData;
    }

    @Scheduled(cron = "18 18 20 * * ?", zone = "Europe/Moscow")
    public void schedulerMessage() {
        Date currentDate = new Date();
        for (Map.Entry<Long, User> entry : userData.getMap().entrySet()) {
            if (entry.getValue().isMention()) {
                User user =  entry.getValue();
                vocabularyBot.sendMessage(user.getChatID(),
                        conditionalTextFormat(currentDate, user.readAllWord()).toString());
            }
        }
    }

    private StringBuilder conditionalTextFormat(Date currentDate, List<Word> list) {
        StringBuilder text = new StringBuilder("<b>New words for this day:\n</b><pre>");
        for (Word word : list) {
            if (word.getDate().getDay() == currentDate.getDay()) {
                text.append(String.format("%-15s--%15s", word.getWord(), word.getTranslation())).append("\n");
            }
        }

        return text.append("</pre>");
    }

}
