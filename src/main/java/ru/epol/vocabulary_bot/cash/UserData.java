package ru.epol.vocabulary_bot.cash;

import ru.epol.vocabulary_bot.user.User;

public interface UserData {
    void setUserDataCash(Long chatID, User user);
    User getUserDataCash(Long chatID);
}
