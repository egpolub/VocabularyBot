package ru.epol.vocabulary_bot.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.epol.vocabulary_bot.VocabularyBot;

@RestController
public class BotController {
    private final VocabularyBot vocabularyBot;

    public BotController(VocabularyBot vocabularyBot) {
        this.vocabularyBot = vocabularyBot;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return vocabularyBot.onWebhookUpdateReceived(update);
    }
}

