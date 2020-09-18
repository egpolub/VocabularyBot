package ru.epol.vocabulary_bot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.epol.vocabulary_bot.keyboard.KeyboardButtons;
import ru.epol.vocabulary_bot.service.ResponseCreator;
import ru.epol.vocabulary_bot.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ResponseCreatorTest {
    private static final long chatID = 1000;
    private User user;

    @Autowired
    private ResponseCreator responseCreator;

    @MockBean
    private User userCase;

    @MockBean
    private KeyboardButtons button;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setChatID(chatID);
    }

    @Test
    void responseShouldHaveCaseForWrongCommandWhereTextIsSimple() {
        String reply =  responseCreator.response(user, "/a").getText();
        assertEquals(reply, "Maybe that you wrote wrong command");
    }

    @Test
    void responseShouldHaveCaseForWrongCommandWhereTextIsComplex() {
        String reply =  responseCreator.response(user, "/a word").getText();
        assertEquals(reply, "Maybe that you wrote wrong command");
    }

    @Test
    void responseShouldHaveSettingsCaseWhereUseCommandForComplexSettings() {
        SendMessage reply = responseCreator.response(user, "/s word");
        reply.setReplyMarkup(button.getInlineMessageButtons("", ""));
        assertEquals(reply.getText(), user.settings().getText());
    }

    @Test
    void responseShouldHaveSettingsCaseWhereUseCommandFor() {
        SendMessage reply = responseCreator.response(user, "/s");
        reply.setReplyMarkup(button.getInlineMessageButtons("", ""));
        assertEquals(reply.getText(), user.settings().getText());
    }

    @Test
    void responseExpectedNullPointerExceptionWhereTextIsEmpty() {
        assertThrows(NullPointerException.class, () -> responseCreator.response(userCase, "").getText());
    }

    @Test
    void responseShouldUpdateWhereUseLegalCommandForAddCase() {
        assertEquals(responseCreator.response(userCase, "/a word * translation").getText(),
                "The dictionary has been updated");
    }

    @Test
    void responseShouldIgnoredWhereUseLegalCommandForDeleteCase() {
        assertEquals(responseCreator.response(userCase, "/d word").getText(),
                "There is no such word in the dictionary");
    }

    @Test
    void responseShouldIgnoredWhereUseLegalCommandForReadCase() {
        assertEquals(responseCreator.response(userCase, "/r").getText(),
                "Your vocabulary is empty");
    }
}