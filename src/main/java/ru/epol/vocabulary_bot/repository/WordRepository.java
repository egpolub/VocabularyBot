package ru.epol.vocabulary_bot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.epol.vocabulary_bot.entity.Word;

import java.util.List;

@Repository
public interface WordRepository extends CrudRepository<Word, Integer> {
    List<Word> findByChatID(Long chatID);

    List<Word> findByChatIDOrderByWord(Long chatID);

    List<Word> findByChatIDOrderByTranslation(Long chatID);

    @Transactional
    void deleteByChatIDAndWordOrTranslation(Long chatID, String word, String translation);

    boolean existsByChatIDAndWordOrTranslation(Long chatID, String word, String translation);

    boolean existsByChatIDAndWordAndTranslation(Long chatID, String word, String translation);

}
