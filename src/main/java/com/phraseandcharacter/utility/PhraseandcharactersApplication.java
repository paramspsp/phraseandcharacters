package com.phraseandcharacter.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phraseandcharacter.model.CharacterImages;
import com.phraseandcharacter.model.Characters;
import com.phraseandcharacter.model.Phrases;
import com.phraseandcharacter.service.CharacterImageService;
import com.phraseandcharacter.service.CharacterService;
import com.phraseandcharacter.service.PhraseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = "com.phraseandcharacter")
@EntityScan(basePackages = {"com.phraseandcharacter.model"})
@EnableJpaRepositories(basePackages = {"com.phraseandcharacter.repository"})
@EnableWebMvc
public class PhraseandcharactersApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PhraseandcharactersApplication.class, args);
    }

    private CharacterService characterService;
    private CharacterImageService characterImageService;
    private PhraseService phraseService;

    @Autowired
    public PhraseandcharactersApplication(CharacterService characterService, PhraseService phraseService, CharacterImageService characterImageService) {
        this.characterService = characterService;
        this.phraseService = phraseService;
        this.characterImageService = characterImageService;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String CharactersPathFile = "/data/characters.json";
        String CharacterImagesPathFile = "/data/characterimages.json";
        String PhrasesPathFile = "/data/phrases.json";
        InputStream characterInputStream =  getClass().getResourceAsStream("/data/characters.json");
        InputStream characterImagesInputStream =  getClass().getResourceAsStream("/data/characterimages.json");
        InputStream phraseInputStream =  getClass().getResourceAsStream("/data/phrases.json");
        Characters characters = objectMapper.readValue(characterInputStream, Characters.class);
        CharacterImages characterImages = objectMapper.readValue(characterImagesInputStream, CharacterImages.class);
        Phrases phrases = objectMapper.readValue(phraseInputStream, Phrases.class);

        if (characters.getData() != null)
            Arrays.stream(characters.getData()).forEach(characterData -> {
                characterService.addCharacter(characterData);
            });

        if (characterImages.getData() != null)
            Arrays.stream(characterImages.getData()).forEach(characterImageData -> {
                System.out.println("characterImageData "+characterImageData.getUrl());
                characterImageService.addCharacterImage(characterImageData);
            });

        if (phrases.getData() != null)
            Arrays.stream(phrases.getData()).forEach(phraseData -> {
                phraseService.addPhrase(phraseData);
            });

    }
}

