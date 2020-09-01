package ru.epol.vocabulary_bot.service;

import org.springframework.stereotype.Service;
import ru.epol.vocabulary_bot.entity.Word;
import ru.epol.vocabulary_bot.repository.WordRepository;

import java.util.List;

@Service
public class WordDataService {
    private final WordRepository wordRepository;

    public WordDataService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public List<Word> readChatID(Long chatID) {
        return wordRepository.findByChatID(chatID);
    }

    public void updateDataBase(Long chatID, String word, String translation) {
        wordRepository.save(new Word(chatID, word, translation));
    }

    public boolean isWord(Long chatID, String word, String translation) {
        return wordRepository.existsByChatIDAndWordOrTranslation(chatID, word, translation);
    }

    public void delete(Long chatID, String word){
        wordRepository.deleteByChatIDAndWordOrTranslation(chatID, word, word);
    }

    public List<Word> sortWord(Long chatID) {
        return wordRepository.findByChatIDOrderByWord(chatID);
    }

    public List<Word> sortTranslation(Long chatID) {
        return wordRepository.findByChatIDOrderByTranslation(chatID);
    }

}
