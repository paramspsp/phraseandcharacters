package com.phraseandcharacers.service;
import com.phraseandcharacter.model.CharacterData;
import com.phraseandcharacter.repository.CharacterRepository;
import com.phraseandcharacter.service.CharacterService;
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
public class CharacterServiceTest {

    @Mock
    private CharacterRepository characterRepository;

    @InjectMocks
    private CharacterService characterService = new CharacterService();

    private static CharacterData characterData1, characterData2;

    
    @BeforeAll
    public static void setUp() {
        characterData1 = new com.phraseandcharacter.model.CharacterData();
        characterData1.set_id("59edff6419f9d6df24d593fe");
        characterData1.setFirstName("Sathya");
        characterData1.setLastName("Paramasivam");
        characterData1.setPicture("http://www.trbimg.com/img-573a089a/turbine/ct-homer-simpson-live-pizza-debate-met-0517-20160516");
        characterData1.setAge(35);
        characterData2 = new com.phraseandcharacter.model.CharacterData();
        characterData2.set_id("60fdff6419f9d6df24d593fe");
        characterData2.setFirstName("Kannan");
        characterData2.setLastName("Paramasivam");
        characterData2.setPicture("http://www.trbimg.com/img-573a089a/turbine/ct-homer-simpson-live-pizza-debate-met-0517-20160516");
        characterData2.setAge(34);
    }

    @Test
    void testfindPhraseDataBy_id() {
        when(characterRepository.findCharacterDataBy_id("59edff6419f9d6df24d593fe")).thenReturn(characterData1);
        CharacterData characterData = characterService.findCharacterDataBy_id("59edff6419f9d6df24d593fe");
        assertThat(characterData.get_id().equalsIgnoreCase("59edff6419f9d6df24d593fe"));
    }

    @Test
    void testfindPhraseAll() {
        when(characterRepository.findAll()).thenReturn(Arrays.asList(characterData1, characterData2));
        List<CharacterData> characterDataResults = characterService.findAll();
        assertThat(characterDataResults.size()).isEqualTo(2);
    }
}


