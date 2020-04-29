package com.phraseandcharacter.repository;

import com.phraseandcharacter.model.CharacterData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CharacterRepository extends CrudRepository<CharacterData,String> {
    List<CharacterData> findCharacterDataByFirstName(String firstName);
    List<CharacterData> findCharacterDataByLastName(String lastName);
    List<CharacterData> findCharacterDataByFirstNameAndLastName(String firstName,String lastName);
    List<CharacterData> findCharacterDataByFirstNameContains(String firstName);
    List<CharacterData> findCharacterDataByLastNameContains(String lastName);
    CharacterData findCharacterDataBy_id(String id);
}
