package ru.epol.vocabulary_bot.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.epol.vocabulary_bot.VocabularyBot;
import ru.epol.vocabulary_bot.cash.UserData;
import ru.epol.vocabulary_bot.entity.Word;
import ru.epol.vocabulary_bot.user.User;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Sends the user new words that have been added
 * to the dictionary during the day.
 *
 * Reminds every day at 20:18 Moscow time.
 */
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
            if (dateToCalendar(word.getDate()).get(Calendar.DAY_OF_WEEK) ==
                    dateToCalendar(currentDate).get(Calendar.DAY_OF_WEEK)) {
                text.append(String.format("%-15s--%15s", word.getWord(), word.getTranslation())).append("\n");
            }
        }

        return text.append("</pre>");
    }

    /**
     * Convert Date to Calendar.
     */
    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

}
