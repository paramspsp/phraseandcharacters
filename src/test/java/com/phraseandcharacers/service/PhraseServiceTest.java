package com.phraseandcharacers.service;


import com.phraseandcharacter.model.PhraseData;
import com.phraseandcharacter.repository.PhraseRepository;
import com.phraseandcharacter.service.PhraseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhraseServiceTest {

    @Mock
    private PhraseRepository phraseRepository;

    @InjectMocks
    private PhraseService phraseService = new PhraseService();

    private static PhraseData phraseData1, phraseData2;

    @BeforeAll
    public static void setUp() {
        phraseData1 = new PhraseData();
        phraseData1.set_id("59edff6419f9d6df24d593fe");
        phraseData1.setCharacter("59edee68706374dfa957842f");
        phraseData1.setPhrase("Now we play the waiting game…Ahh, the waiting game sucks. Let’s play");
        phraseData2 = new PhraseData();
        phraseData2.set_id("59edff643d4737e47c71835c");
        phraseData2.setCharacter("59edee683406c7834ee7cdd8");
        phraseData2.setPhrase("You monster! You monster!");
    }

    @Test
    void testfindPhraseDataBy_id() {
        when(phraseRepository.findPhraseDataBy_id("59edff6419f9d6df24d593fe")).thenReturn(phraseData1);
        PhraseData phraseData = phraseService.findPhraseDataBy_id("59edff6419f9d6df24d593fe");
        assertThat(phraseData.get_id().equalsIgnoreCase("59edff6419f9d6df24d593fe"));
    }

    @Test
    void testfindPhraseAll() {
        when(phraseRepository.findAll()).thenReturn(Arrays.asList(phraseData1, phraseData2));
        List<PhraseData> phraseDataResults = phraseService.findAll();
        assertThat(phraseDataResults.size()).isEqualTo(2);
    }
}


