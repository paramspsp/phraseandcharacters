package com.phraseandcharacter.controller;

import com.phraseandcharacter.model.CharacterData;
import com.phraseandcharacter.model.PhraseData;
import com.phraseandcharacter.service.PhraseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/phrases")
public class PhraseController {

    @Autowired
    private PhraseService phraseService;

    @GetMapping(path = "/allPhrases", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<PhraseData> getAllPhrases()
    {
        return phraseService.findAll();
    }

    @GetMapping(path = "/specificPhrases/{phraseName}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<PhraseData> getSpecificPhrasesByPhraseName(@PathVariable String phraseName)
    {
        List<PhraseData> phraseData = phraseService.findPhraseDataByPhraseContains(phraseName);
        return phraseData;
    }

    @GetMapping(path = "/specificPhrase/{phraseId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getSpecificPhraseById(@PathVariable String phraseId)
    {
        Optional<PhraseData> phraseData ;
        try{
            phraseData = Optional.ofNullable(phraseService.findPhraseDataBy_id(phraseId));
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //Check the service status and return the HTTP status values
        if(phraseData.isPresent()) {
            return new ResponseEntity<Object>(phraseData, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(path= "/addPhrase", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> addPhrase(@RequestBody PhraseData phraseData) {

        PhraseData createdPhraseData = null;
        //Call the phrase service and make sure that Phrase data is added into the system
        if(phraseData != null
                && phraseData.getPhrase() != null
                && phraseData.getCharacter() != null){
            createdPhraseData = phraseService.addPhrase(phraseData);
        }else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        //Check the service status and return the HTTP status values
        if(createdPhraseData != null && createdPhraseData.get_id() != null) {
            return new ResponseEntity<>(CharacterData.class, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deletePhrase/{phraseId}")
    public ResponseEntity<Boolean> deletePhrase(@PathVariable String phraseId) {
        try{
            phraseService.deletePhrase(phraseId);
        }catch(Exception e){
            return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

    @PutMapping(path= "/editPhrase", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> editPhrase(@RequestBody PhraseData phraseData) {
        PhraseData updatedPhraseData = null;
        try{
            updatedPhraseData = phraseService.editPhrase(phraseData);
        }catch(Exception e){
            return new ResponseEntity<Object>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Object>(updatedPhraseData, HttpStatus.OK);
    }
}
