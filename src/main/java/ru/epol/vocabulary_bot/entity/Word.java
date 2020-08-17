package ru.epol.vocabulary_bot.entity;

import javax.persistence.*;

@Entity
public class Word {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private Long chatID;
    private String word;
    private String translation;

    public Word() {
    }

    public Word(Long chatID, String word, String translation) {
        this.chatID = chatID;
        this.word = word;
        this.translation = translation;
    }

    public Integer getId() {
        return id;
    }

    public Long getChatID() {
        return chatID;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }
}
