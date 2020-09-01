package ru.epol.vocabulary_bot.cash;

import org.springframework.stereotype.Component;
import ru.epol.vocabulary_bot.user.User;

import java.util.HashMap;
import java.util.Map;


/**
 * field Map contains:
 * key = chatID User, value = object User.
 */

@Component
public class UserDataCash implements UserData {
    private Map<Long, User> map = new HashMap<>();

    @Override
    public void setUserDataCash(Long chatID, User user)
    {
        map.put(chatID, user);
    }

    public Map<Long, User> getMap() {
        return map;
    }

    @Override
    public User getUserDataCash(Long chatID) {
        return map.get(chatID);
    }
}
