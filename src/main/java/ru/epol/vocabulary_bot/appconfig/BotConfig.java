package ru.epol.vocabulary_bot.appconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.epol.vocabulary_bot.VocabularyBot;
import ru.epol.vocabulary_bot.service.MessageHandler;


@Configuration
@ConfigurationProperties(prefix = "vocabularybot")
public class BotConfig {
    private String botUsername;
    private String botToken;
    private String botPath;



    @Bean
    public VocabularyBot vocabularyBot(MessageHandler messageHandler) {
        VocabularyBot vocabularyBot = new VocabularyBot(messageHandler);
        vocabularyBot.setBotUsername(botUsername);
        vocabularyBot.setBotToken(botToken);
        vocabularyBot.setBotPath(botPath);

        return vocabularyBot;
    }

    public String getBotUsername() {
        return botUsername;
    }

    public void setBotUsername(String botUsername) {
        this.botUsername = botUsername;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public String getBotPath() {
        return botPath;
    }

    public void setBotPath(String botPath) {
        this.botPath = botPath;
    }
}
