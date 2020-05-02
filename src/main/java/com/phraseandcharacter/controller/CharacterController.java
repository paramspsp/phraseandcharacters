package com.phraseandcharacter.controller;

import com.phraseandcharacter.exception.*;
import com.phraseandcharacter.model.CharacterData;
import com.phraseandcharacter.model.PhraseData;
import com.phraseandcharacter.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @GetMapping(path = "/allCharacters", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<CharacterData> getAllCharacters() {
        return characterService.findAll();
    }

    @GetMapping(path = "/specificCharactersByFirstName/{firstName}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<CharacterData> getSpecificCharactersByFirstNameContains(@PathVariable String firstName)
            throws ResourceNotFoundException, BadDataRequestException, BadGatewayExcepton, InternalServerException, PhraseAndCharacterException {
        List<CharacterData> characterDataList = null;
        try {
            characterDataList = characterService.findCharacterDataByFirstNameContains(firstName);
        } catch (HttpClientErrorException.BadRequest e) {
            throw new BadDataRequestException("Bad HTTP Data Request! Please resolve the request :: " + firstName + " : " + e.getMessage());
        } catch (HttpServerErrorException.BadGateway e) {
            throw new BadGatewayExcepton("Bad Gateway Error! Please resolve the error :: " + firstName + " : " + e.getMessage());
        } catch (HttpServerErrorException.InternalServerError e) {
            throw new InternalServerException("Bad Gateway Error! Please resolve the error :: " + firstName + " : " + e.getMessage());
        } catch (Exception e) {
            throw new PhraseAndCharacterException("Bad Gateway Error! Please resolve the request :: " + firstName + " : " + e.getMessage());
        }

        if (characterDataList.isEmpty()) {
            throw new ResourceNotFoundException("Character data object not found with this character name :: " + firstName);
        }

        return characterDataList;
    }

    @GetMapping(path = "/specificCharactersByLastName/{lastName}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<CharacterData> getSpecificCharactersByLastNameContains(@PathVariable String lastName) {
        return characterService.findCharacterDataByLastNameContains(lastName);
    }

    @GetMapping(path = "/specificPhrase/{characterId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getSpecificCharacterById(@PathVariable String characterId) throws ResourceNotFoundException, BadDataRequestException, BadGatewayExcepton, InternalServerException, PhraseAndCharacterException {
        Optional<CharacterData> characterData;
        try {
            characterData = Optional.ofNullable(characterService.findCharacterDataBy_id(characterId));
        } catch (HttpClientErrorException.BadRequest e) {
            throw new BadDataRequestException("Bad HTTP Data Request! Please resolve the request :: " + characterId + " : " + e.getMessage());
        } catch (HttpServerErrorException.BadGateway e) {
            throw new BadGatewayExcepton("Bad Gateway Error! Please resolve the error :: " + characterId + " : " + e.getMessage());
        } catch (HttpServerErrorException.InternalServerError e) {
            throw new InternalServerException("Bad Gateway Error! Please resolve the error :: " + characterId + " : " + e.getMessage());
        } catch (Exception e) {
            throw new PhraseAndCharacterException("Bad Gateway Error! Please resolve the request :: " + characterId + " : " + e.getMessage());
        }

        //Check the service status and return the HTTP status values
        if (characterData.isPresent()) {
            return new ResponseEntity<Object>(characterData, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Character object not found for this id :: " + characterId);
        }
    }

    @PostMapping(path = "/addCharacter", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> addCharacter(@RequestBody CharacterData characterData) throws BadDataRequestException, BadGatewayExcepton, InternalServerException, PhraseAndCharacterException, ResourceNotFoundException {

        Optional<CharacterData> createdCharacterData;
        //Call the service and return the created object from database
        try {
            createdCharacterData = Optional.ofNullable(characterService.addCharacter(characterData));
        } catch (HttpClientErrorException.BadRequest e) {
            throw new BadDataRequestException("Bad HTTP Data Request! Please resolve the request :: " + e.getMessage());
        } catch (HttpServerErrorException.BadGateway e) {
            throw new BadGatewayExcepton("Bad Gateway Error! Please resolve the error :: " + e.getMessage());
        } catch (HttpServerErrorException.InternalServerError e) {
            throw new InternalServerException("Bad Gateway Error! Please resolve the error :: " + e.getMessage());
        } catch (Exception e) {
            throw new PhraseAndCharacterException("Bad Gateway Error! Please resolve the request :: " + e.getMessage());
        }

        //Check the service status and return the HTTP status values
        if (createdCharacterData.isPresent()) {
            return new ResponseEntity<>(createdCharacterData, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Character object not created for this :: ");
        }
    }

    @DeleteMapping("/deleteCharacter/{characterId}")
    public ResponseEntity<Boolean> deleteCharacter(@PathVariable String characterId) throws BadDataRequestException, BadGatewayExcepton, InternalServerException, PhraseAndCharacterException {
        try {
            characterService.deleteCharacter(characterId);
        } catch (HttpClientErrorException.BadRequest e) {
            throw new BadDataRequestException("Bad HTTP Data Request! Please resolve the request :: " + characterId + " : " + e.getMessage());
        } catch (HttpServerErrorException.BadGateway e) {
            throw new BadGatewayExcepton("Bad Gateway Error! Please resolve the error :: " + characterId + " : " + e.getMessage());
        } catch (HttpServerErrorException.InternalServerError e) {
            throw new InternalServerException("Bad Gateway Error! Please resolve the error :: " + characterId + " : " + e.getMessage());
        } catch (Exception e) {
            throw new PhraseAndCharacterException("Bad Gateway Error! Please resolve the request :: " + characterId + " : " + e.getMessage());
        }
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

    @PutMapping(path = "/editCharacter", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> editCharacterData(@RequestBody CharacterData characterData) throws BadGatewayExcepton, InternalServerException, PhraseAndCharacterException, BadDataRequestException {
        Optional<CharacterData> updatedCharacterData;

        try {
            updatedCharacterData = Optional.ofNullable(characterService.updateCharacter(characterData));
        } catch (HttpClientErrorException.BadRequest e) {
            throw new BadDataRequestException("Bad HTTP Data Request! Please resolve the request :: " + e.getMessage());
        } catch (HttpServerErrorException.BadGateway e) {
            throw new BadGatewayExcepton("Bad Gateway Error! Please resolve the error :: " + e.getMessage());
        } catch (HttpServerErrorException.InternalServerError e) {
            throw new InternalServerException("Bad Gateway Error! Please resolve the error :: " + e.getMessage());
        } catch (Exception e) {
            throw new PhraseAndCharacterException("Bad Gateway Error! Please resolve the request :: " + e.getMessage());
        }
        return new ResponseEntity<Object>(updatedCharacterData, HttpStatus.OK);
    }
}
