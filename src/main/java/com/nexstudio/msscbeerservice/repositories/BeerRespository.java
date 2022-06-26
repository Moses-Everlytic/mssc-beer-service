package com.nexstudio.msscbeerservice.repositories;

import java.util.UUID;

import com.nexstudio.msscbeerservice.constants.BeerStyleEnum;
import com.nexstudio.msscbeerservice.model.Beer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BeerRespository extends PagingAndSortingRepository<Beer, UUID> {

    Page<Beer> findAllByBeerStyle(BeerStyleEnum validBeerStyle, PageRequest pageRequest);

    Page<Beer> findAllByBeerName(String beerName, PageRequest pageRequest);

    Beer findBeerByUpc(String upc);

    Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyleEnum validBeerStyle, PageRequest pageRequest);
}
