package com.nexstudio.msscbeerservice.web.controller;

import java.util.UUID;

import com.nexstudio.msscbeerservice.web.model.BeerDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({BeerController.BASE_API_URI})
public class BeerController {

    public static final String BASE_API_URI = "/api/v1/beer";

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDTO> getBeer(@PathVariable("beerId") UUID id) {
        return new ResponseEntity<>(
                BeerDTO.builder()
                    .id(id)
                    .beerName("Test")
                    .build(),
                HttpStatus.OK
            );
    }

    @PostMapping
    public ResponseEntity<Void> saveNewBeer(@RequestBody BeerDTO beerDTO) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<Void> updateBeer(@PathVariable("beerId") UUID id, @RequestBody BeerDTO beerDTO) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
