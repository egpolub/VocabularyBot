package ru.epol.vocabulary_bot;

import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.epol.vocabulary_bot.service.MessageHandler;


public class VocabularyBot extends TelegramWebhookBot {
    private String botUsername;
    private String botToken;
    private String botPath;


    private final MessageHandler messageHandler;

    public VocabularyBot(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return messageHandler.sendMsg(update);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return botPath;
    }

    public void sendMessage(Long chatID, String text) {
        SendMessage message = new SendMessage();

        message.setChatId(chatID);
        message.setText(text);
        message.enableHtml(true);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setBotUsername(String botUsername) {
        this.botUsername = botUsername;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public void setBotPath(String botPath) {
        this.botPath = botPath;
    }
}
