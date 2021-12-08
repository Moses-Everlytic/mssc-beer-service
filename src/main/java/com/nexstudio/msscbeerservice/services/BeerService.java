package com.nexstudio.msscbeerservice.services;

import java.util.UUID;

import javax.validation.Valid;

import com.nexstudio.msscbeerservice.web.model.BeerDTO;

public interface BeerService {

    BeerDTO getById(UUID id);

    BeerDTO saveNewBeer(@Valid BeerDTO beerDTO);

    BeerDTO updateBeer(UUID id, @Valid BeerDTO beerDTO);
}
