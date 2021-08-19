package com.nexstudio.msscbeerservice.repositories;

import java.util.UUID;

import com.nexstudio.msscbeerservice.model.Beer;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface BeerRespository extends PagingAndSortingRepository<Beer, UUID> {
}
