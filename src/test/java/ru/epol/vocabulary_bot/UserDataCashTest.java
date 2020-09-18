package ru.epol.vocabulary_bot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.epol.vocabulary_bot.cash.UserData;
import ru.epol.vocabulary_bot.cash.UserDataCash;
import ru.epol.vocabulary_bot.user.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDataCashTest {
    private static final long chatID = 1000;
    private User user;
    private UserData userData;

    @BeforeEach
    void setUp() {
        userData = new UserDataCash();
        user = new User();
        user.setChatID(chatID);
    }
    @Test
    void setUserDataCashShouldHaveUnitMapSize() {
        userData.setUserDataCash(chatID, user);
        assertEquals(userData.getMap().size(), 1);
    }

    @Test
    void setUserDataCashShouldHaveIncreaseMapSize() {
        userData.setUserDataCash(chatID, user);
        userData.setUserDataCash(chatID + 1, user);
        assertEquals(userData.getMap().size(), 2);
    }

    @Test
    void getUserDataCashShouldHaveNotNullIfFindChatID() {
        userData.setUserDataCash(chatID, user);
        assertNotNull(userData.getUserDataCash(chatID));
    }

    @Test
    void getUserDataCashShouldHaveNullIfNotFindChatID() {
        assertNull(userData.getUserDataCash(chatID));
    }
}