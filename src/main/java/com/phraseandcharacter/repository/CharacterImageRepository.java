package com.phraseandcharacter.repository;

import com.phraseandcharacter.model.CharacterData;
import com.phraseandcharacter.model.CharacterImageData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterImageRepository extends CrudRepository<CharacterImageData,String> {

}
