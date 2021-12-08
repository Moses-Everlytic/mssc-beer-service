package com.nexstudio.msscbeerservice.services;

import java.util.UUID;

import javax.validation.Valid;

import com.nexstudio.msscbeerservice.exception.BeerNotFoundException;
import com.nexstudio.msscbeerservice.model.Beer;
import com.nexstudio.msscbeerservice.repositories.BeerRespository;
import com.nexstudio.msscbeerservice.web.mappers.BeerMapper;
import com.nexstudio.msscbeerservice.web.model.BeerDTO;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRespository beerRespository;
    private final BeerMapper beerMapper;

    @Override
    public BeerDTO getById(UUID id) {
        try {
            return beerMapper.beerToBeerDTO(beerRespository.findById(id).orElseThrow(() -> new BeerNotFoundException(id)));
        } catch (BeerNotFoundException e) {
            return null;
        }
    }

    @Override
    public BeerDTO saveNewBeer(@Valid BeerDTO beerDTO) {
        Beer beer = beerMapper.beerDtoToBeer(beerDTO);
        return beerMapper.beerToBeerDTO(beerRespository.save(beer));
    }

    @Override
    public BeerDTO updateBeer(UUID id, @Valid BeerDTO beerDTO) {
        Beer beer;
        try {
            beer = beerRespository.findById(id).orElseThrow(() -> new BeerNotFoundException(id));
        } catch (BeerNotFoundException e) {
            return null;
        }

        beer.setBeerName(beerDTO.getBeerName());
        beer.setBeerStyle(beerDTO.getBeerStyle());
        beer.setPrice(beerDTO.getPrice());
        beer.setUpc(beerDTO.getUpc());

        return beerMapper.beerToBeerDTO(beerRespository.save(beer));
    }
    
}
