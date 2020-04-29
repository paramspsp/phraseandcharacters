package com.phraseandcharacter.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phraseandcharacter.model.Characters;
import com.phraseandcharacter.model.Phrases;
import com.phraseandcharacter.service.CharacterService;
import com.phraseandcharacter.service.PhraseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;
import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = "com.phraseandcharacter")
@EntityScan(basePackages = {"com.phraseandcharacter.model"})
@EnableJpaRepositories(basePackages = {"com.phraseandcharacter.repository"})
public class PhraseandcharactersApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PhraseandcharactersApplication.class, args);
    }
    private CharacterService characterService;
    private PhraseService phraseService;
    @Autowired
    public PhraseandcharactersApplication(CharacterService characterService, PhraseService phraseService) {
        this.characterService = characterService;
        this.phraseService = phraseService;

    }

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String CharactersPathFile = "src/main/resources/data/characters.json";
        String PhrasesPathFile = "src/main/resources/data/phrases.json";
        Characters characters = objectMapper.readValue(new File(CharactersPathFile), Characters.class);
        Phrases phrases = objectMapper.readValue(new File(PhrasesPathFile), Phrases.class);

        if (characters.getData() != null)
            Arrays.stream(characters.getData()).forEach(characterData -> {
            characterService.addCharacter(characterData);
        });

        if (phrases.getData() != null)
            Arrays.stream(phrases.getData()).forEach(phraseData -> {
            phraseService.addPhrase(phraseData);
        });

    }
}

