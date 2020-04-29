package com.phraseandcharacters.controller;

import com.phraseandcharacter.controller.PhraseController;
import com.phraseandcharacter.model.PhraseData;
import com.phraseandcharacter.service.PhraseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
public class PhraseControllerTest {

    private static PhraseData phraseData;

    @InjectMocks
    PhraseController phraseController;

    @Mock
    PhraseService phraseService;

    @BeforeAll
    public static void setUp(){
        phraseData = new PhraseData();
        phraseData.setCharacter("59edee68706374dfa957842f");
        phraseData.setPhrase("Now we play the waiting game…Ahh, the waiting game sucks. Let’s play");
    }

    @Test
    public void testAddPhrase()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(this.phraseService.addPhrase(any(PhraseData.class))).thenReturn(true);
        ResponseEntity<Object> responseEntity = this.phraseController.addPhrase(this.phraseData);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/");
    }

    @Test
    public void testgetSpecificCharactersByFirstNameContains()
    {
        when(this.phraseService.findPhraseDataByPhraseContains("game")).thenReturn(Arrays.asList(this.phraseData));
        List<PhraseData> phraseResults = this.phraseController.getSpecificPhrasesByPhraseName("game");
        assertThat(phraseResults.size()).isEqualTo(1);
        assertThat(phraseResults.get(0).getPhrase().contains(this.phraseData.getPhrase()));
    }
}
