package com.phraseandcharacter.controller;

import com.phraseandcharacter.model.CharacterData;
import com.phraseandcharacter.model.PhraseData;
import com.phraseandcharacter.service.PhraseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @PostMapping(path= "/addPhrase", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> addPhrase(@RequestBody PhraseData phraseData) {

        //add resource
        phraseService.addPhrase(phraseData);

        //Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(phraseData.get_id())
                .toUri();

        //Send location in response
        return ResponseEntity.created(location).build();
    }
}
