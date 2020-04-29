package com.phraseandcharacter.repository;

import com.phraseandcharacter.model.PhraseData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhraseRepository extends CrudRepository<PhraseData,String> {
    PhraseData findPhraseDataBy_id(String id);
    List<PhraseData> findPhraseDataByPhraseContains(String phraseName);
}