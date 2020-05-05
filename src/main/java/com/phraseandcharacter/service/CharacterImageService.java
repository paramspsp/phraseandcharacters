package com.phraseandcharacter.service;

import com.phraseandcharacter.model.CharacterImageData;
import com.phraseandcharacter.repository.CharacterImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterImageService {

    @Autowired
    private CharacterImageRepository characterImageRepository;

    public List<CharacterImageData> findAll() {
        Iterable<CharacterImageData> it = characterImageRepository.findAll();
        List<CharacterImageData> characeterImages = new ArrayList<CharacterImageData>();
        it.forEach(e -> characeterImages.add(e));
        return characeterImages;
    }

    public synchronized CharacterImageData addCharacterImage(CharacterImageData characterImageData) {
        return characterImageRepository.save(characterImageData);
    }
}

