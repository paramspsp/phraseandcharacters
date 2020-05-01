package com.phraseandcharacter.service;

import com.phraseandcharacter.model.CharacterData;
import com.phraseandcharacter.model.PhraseData;
import com.phraseandcharacter.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    public List<CharacterData> findAll() {
        Iterable<CharacterData> it = characterRepository.findAll();
        List<CharacterData>  characeters = new ArrayList<CharacterData>();
        it.forEach(e -> characeters.add(e));
        return characeters;
    }

    public CharacterData findCharacterDataBy_id(String id){
        return characterRepository.findCharacterDataBy_id(id);
    }

    public List<CharacterData> findCharacterDataByFirstNameContains(String firstName) {
        Iterable<CharacterData> it = characterRepository.findCharacterDataByFirstNameContains(firstName);
        List<CharacterData>  characeters = new ArrayList<CharacterData>();
        it.forEach(e -> characeters.add(e));
        return characeters;
    }

    public List<CharacterData> findCharacterDataByLastNameContains(String lastName) {
        Iterable<CharacterData> it = characterRepository.findCharacterDataByLastNameContains(lastName);
        List<CharacterData>  characeters = new ArrayList<CharacterData>();
        it.forEach(e -> characeters.add(e));
        return characeters;
    }

    public Long count() {
        return characterRepository.count();
    }

    public synchronized CharacterData addCharacter(CharacterData character){
        List<CharacterData> list = characterRepository.findCharacterDataByFirstNameAndLastName(character.getFirstName(), character.getLastName());
        if (list.size() > 0) {
            return null;
        } else {
            CharacterData createdCharacterData = characterRepository.save(character);
            return createdCharacterData;
        }
    }

    public void deleteCharacter(String characterId) {
        CharacterData character = characterRepository.findCharacterDataBy_id(characterId);
        if(character != null && character.get_id() != null){
            characterRepository.delete(character);
        }
    }

    public CharacterData updateCharacter(CharacterData character) {
        CharacterData updatedCharacterData = null;
        CharacterData existingCharacterData = null;
        if (character != null && character.get_id() != null) {
            existingCharacterData = characterRepository.findCharacterDataBy_id(character.get_id());
        }
        if (existingCharacterData != null && existingCharacterData.get_id() != null) {
            updatedCharacterData = characterRepository.save(character);
        } else {
            return null;
        }
        return updatedCharacterData;
    }

}
