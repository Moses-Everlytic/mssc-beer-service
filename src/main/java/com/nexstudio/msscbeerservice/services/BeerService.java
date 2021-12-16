package com.nexstudio.msscbeerservice.services;

import java.util.UUID;

import javax.validation.Valid;

import com.nexstudio.msscbeerservice.web.model.BeerDTO;
import com.nexstudio.msscbeerservice.web.model.BeerPagedList;

import org.springframework.data.domain.PageRequest;

public interface BeerService {

    BeerDTO getById(UUID id);

    BeerDTO saveNewBeer(@Valid BeerDTO beerDTO);

    BeerDTO updateBeer(UUID id, @Valid BeerDTO beerDTO);

    BeerPagedList listBeers(String beerName, String beerStyle, PageRequest pageRequest);
}
