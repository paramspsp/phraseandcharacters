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

    public synchronized boolean addCharacter(CharacterData character){
        List<CharacterData> list = characterRepository.findCharacterDataByFirstNameAndLastName(character.getFirstName(), character.getLastName());
        if (list.size() > 0) {
            return false;
        } else {
            characterRepository.save(character);
            return true;
        }
    }

    public void updateCharacter(CharacterData character) {
        characterRepository.save(character);
    }

    public void deleteCharacter(String characterId) {
        CharacterData character = characterRepository.findCharacterDataBy_id(characterId);
        if(character != null && character.get_id() != null){
            characterRepository.delete(character);
        }
    }

}