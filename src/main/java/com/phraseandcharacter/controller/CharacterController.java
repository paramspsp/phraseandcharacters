package com.phraseandcharacter.controller;

import com.phraseandcharacter.model.CharacterData;
import com.phraseandcharacter.model.PhraseData;
import com.phraseandcharacter.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        return characterService.findCharacterDataByFirstNameContains(firstName);
    }

    @GetMapping(path = "/specificCharactersByLastName/{lastName}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<CharacterData> getSpecificCharactersByLastNameContains(@PathVariable String lastName)
    {
        return characterService.findCharacterDataByLastNameContains(lastName);
    }

    @GetMapping(path = "/specificPhrase/{characterId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getSpecificCharacterById(@PathVariable String characterId)
    {
        Optional<CharacterData> characterData ;
        try{
            characterData = Optional.ofNullable(characterService.findCharacterDataBy_id(characterId));
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //Check the service status and return the HTTP status values
        if(characterData.isPresent()) {
            return new ResponseEntity<Object>(characterData, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(path= "/addCharacter", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> addCharacter(@RequestBody CharacterData characterData) {

        CharacterData createdCharacterData = null;
        //Call the service and return the created object from database
        if (characterData != null
                && characterData.getFirstName() != null
                && characterData.getLastName() != null
        && characterData.getPicture() != null
        && characterData.getAge() > 0) {
                    createdCharacterData = characterService.addCharacter(characterData);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        //Check the service status and return the HTTP status values
        if(createdCharacterData != null && createdCharacterData.get_id() != null) {
            return new ResponseEntity<>(createdCharacterData, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteCharacter/{characterId}")
    public ResponseEntity<Boolean> deleteCharacter(@PathVariable String characterId) {
        try{
            characterService.deleteCharacter(characterId);
        }catch(Exception e){
            return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

    @PutMapping(path= "/editCharacter", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> editCharacterData(@RequestBody CharacterData characterData) {
        CharacterData updatedCharacterData = null;
        try{
            updatedCharacterData = characterService.updateCharacter(characterData);
        }catch(Exception e){
            return new ResponseEntity<Object>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Object>(updatedCharacterData, HttpStatus.OK);
    }
}
