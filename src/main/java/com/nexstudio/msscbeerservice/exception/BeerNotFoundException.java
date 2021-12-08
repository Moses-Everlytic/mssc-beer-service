package com.nexstudio.msscbeerservice.exception;

import java.util.UUID;

import javassist.NotFoundException;

public class BeerNotFoundException extends NotFoundException {

    public BeerNotFoundException(UUID id) {
        super(String.format("Beer not found with id:", id));
    }
    
}
