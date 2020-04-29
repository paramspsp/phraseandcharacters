package com.phraseandcharacter.controller;

import com.phraseandcharacter.model.CharacterData;
import com.phraseandcharacter.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
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

        //add resource
        characterService.addCharacter(characterData);

        //Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(characterData.get_id())
                .toUri();

        //Send location in response
        return ResponseEntity.created(location).build();
    }
}
