package com.phraseandcharacter.controller;

import com.phraseandcharacter.model.CharacterImageData;
import com.phraseandcharacter.service.CharacterImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/characterImages")
public class CharacterImageController {

    @Autowired
    private CharacterImageService characterImageService;

    @GetMapping(path = "/allCharacterImages", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<CharacterImageData> getAllCharacters() {
        return characterImageService.findAll();
    }
}
