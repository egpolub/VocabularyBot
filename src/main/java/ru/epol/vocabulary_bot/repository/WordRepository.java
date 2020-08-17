package ru.epol.vocabulary_bot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.epol.vocabulary_bot.entity.Word;

import java.util.List;

@Repository
public interface WordRepository extends CrudRepository<Word, Integer> {
    List<Word> findByChatID(Long chatID);

    @Transactional
    void deleteByChatIDAndWord(Long chatID, String word);
}
