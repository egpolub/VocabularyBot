package ru.epol.vocabulary_bot.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Word {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private Long chatID;
    private String word;
    private String translation;
    private Date date;


    public Word() {
    }

    public Word(Long chatID, String word, String translation) {
        this.chatID = chatID;
        this.word = word;
        this.translation = translation;
        this.date = new Date();
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

    public Date getDate() {
        return date;
    }
}
