package com.phraseandcharacter.exception;

import org.springframework.http.HttpStatus;
        import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.SEE_OTHER)
public class PhraseAndCharacterException extends Exception{
    public PhraseAndCharacterException(String message){
        super(message);
    }
}