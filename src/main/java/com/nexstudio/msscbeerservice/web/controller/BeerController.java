package com.nexstudio.msscbeerservice.web.controller;

import java.util.UUID;

import javax.validation.Valid;

import com.nexstudio.msscbeerservice.services.BeerService;
import com.nexstudio.msscbeerservice.web.model.BeerDTO;
import com.nexstudio.msscbeerservice.web.model.BeerPagedList;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping({ BeerController.BASE_API_URI })
public class BeerController {

    public static final String BASE_API_URI = "/api/v1/";

    private static final Integer DEFAULT_PAGE_NUMBER = 0 ;
    private static final Integer DEFAULT_PAGE_SIZE = 25 ;
    private static final Boolean SHOW_INVENTORY_ON_HAND = false;

    private final BeerService beerService;

    @GetMapping(path="beer", produces = { "application/json" })
    public ResponseEntity<BeerPagedList> listBeers(
            @RequestParam(value = "PageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "25", required = false) Integer pageSize,
            @RequestParam(value = "beerName", required = false) String beerName,
            @RequestParam(value = "beerStyle", required = false) String beerStyle,
            @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {

        if (pageNumber == null || pageNumber < 0) pageNumber = DEFAULT_PAGE_NUMBER;
        if (pageSize == null || pageSize < 1) pageSize = DEFAULT_PAGE_SIZE;
        if (showInventoryOnHand == null || !showInventoryOnHand) showInventoryOnHand = SHOW_INVENTORY_ON_HAND;

        BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, showInventoryOnHand, PageRequest.of(pageNumber, pageSize));

        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }

    @GetMapping("beer/{beerId}")
    public ResponseEntity<BeerDTO> getBeer(@PathVariable("beerId") UUID id, 
            @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {

        if (showInventoryOnHand == null || !showInventoryOnHand) showInventoryOnHand = SHOW_INVENTORY_ON_HAND;
        return new ResponseEntity<>(beerService.getById(id, showInventoryOnHand), HttpStatus.OK);
    }

    @GetMapping("beerUpc/{upc}")
    public ResponseEntity<BeerDTO> getBeerByUpc(@PathVariable("upc") String upc)  {
        return new ResponseEntity<>(beerService.getByUpc(upc), HttpStatus.OK);
    }

    @PostMapping("beer")
    public ResponseEntity<BeerDTO> saveNewBeer(@Valid @RequestBody BeerDTO beerDTO) {
        return new ResponseEntity<>(beerService.saveNewBeer(beerDTO), HttpStatus.CREATED);
    }

    @PutMapping("beer/{beerId}")
    public ResponseEntity<BeerDTO> updateBeer(@PathVariable("beerId") UUID id, @Valid @RequestBody BeerDTO beerDTO) {
        return new ResponseEntity<>(beerService.updateBeer(id, beerDTO), HttpStatus.NO_CONTENT);
    }
}
