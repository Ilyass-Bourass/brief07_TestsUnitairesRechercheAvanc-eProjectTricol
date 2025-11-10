package com.example.demo.exception;

import lombok.Getter;

import java.util.List;
@Getter
public class DuplicateResourceException extends RuntimeException{
    private final List<String> errors;
    public DuplicateResourceException(List<String> errors) {
        super("Des erreurs ont été détectées");
        this.errors = errors;
    }

}
