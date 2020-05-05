package com.phraseandcharacters.controller;

import com.phraseandcharacter.controller.CharacterController;
import com.phraseandcharacter.exception.*;
import com.phraseandcharacter.model.CharacterData;
import com.phraseandcharacter.service.CharacterService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CharacterControllerTest {

    @InjectMocks
    CharacterController characterController;

    @Mock
    CharacterService characterService;

    private static CharacterData characterData;

    @BeforeAll
    public static void setUp(){
        characterData = new CharacterData();
        characterData.setFirstName("Paramasivam");
        characterData.setLastName("Palanisamy");
        characterData.setPicture("http://www.trbimg.com/img-573a089a/turbine/ct-homer-simpson-live-pizza-debate-met-0517-20160516");
        characterData.setAge(37);
    }

    @Test
    public void testAddCharacter() throws BadGatewayExcepton, ResourceNotFoundException, InternalServerException, BadDataRequestException, PhraseAndCharacterException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(characterService.addCharacter(any(CharacterData.class))).thenReturn(characterData);
        ResponseEntity<Object> responseEntity = characterController.addCharacter(characterData);
        System.out.println(responseEntity.getStatusCodeValue());
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void testgetSpecificCharactersByFirstNameContains() throws BadGatewayExcepton, ResourceNotFoundException, InternalServerException, BadDataRequestException, PhraseAndCharacterException {
        when(characterService.findCharacterDataByFirstNameContains("Paramasivam")).thenReturn(Arrays.asList(characterData));
        List<CharacterData> characterResults = characterController.getSpecificCharactersByFirstNameContains("Paramasivam");
        assertThat(characterResults.size()).isEqualTo(1);
        assertThat(characterResults.get(0).getFirstName()).isEqualTo(characterData.getFirstName());
    }
}
