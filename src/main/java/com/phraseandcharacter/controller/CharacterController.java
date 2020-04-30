package com.phraseandcharacter.controller;

import com.phraseandcharacter.model.CharacterData;
import com.phraseandcharacter.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @GetMapping(path="/allCharacters", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<CharacterData> getAllCharacters()
    {
        return characterService.findAll();
    }

    @GetMapping(path = "/specificCharactersByFirstName/{firstName}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<CharacterData> getSpecificCharactersByFirstNameContains(@PathVariable String firstName)
    {
        List<CharacterData> characterData = characterService.findCharacterDataByFirstNameContains(firstName);
        return characterData;
    }

    @GetMapping(path = "/specificCharactersByLastName/{lastName}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<CharacterData> getSpecificCharactersByLastNameContains(@PathVariable String lastName)
    {
        List<CharacterData> characterData = characterService.findCharacterDataByLastNameContains(lastName);
        return characterData;
    }

    @PostMapping(path= "/addCharacter", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> addCharacter(@RequestBody CharacterData characterData) {

        CharacterData createdCharacterData = null;
        //Call the service and return the created object from database
        if (characterData != null
                && characterData.getFirstName() != null
                && characterData.getAge() != null) {
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                } else {
             createdCharacterData = characterService.addCharacter(characterData);
        }

        //Check the service status and return the HTTP status values
        if(createdCharacterData != null && createdCharacterData.get_id() != null) {
            return new ResponseEntity<>(CharacterData.class, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
