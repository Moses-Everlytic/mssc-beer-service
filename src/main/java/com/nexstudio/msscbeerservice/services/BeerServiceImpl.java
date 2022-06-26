package com.nexstudio.msscbeerservice.services;

import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.nexstudio.msscbeerservice.constants.BeerStyleEnum;
import com.nexstudio.msscbeerservice.exception.BeerNotFoundException;
import com.nexstudio.msscbeerservice.model.Beer;
import com.nexstudio.msscbeerservice.repositories.BeerRespository;
import com.nexstudio.msscbeerservice.web.mappers.BeerMapper;
import com.nexstudio.msscbeerservice.web.model.BeerDTO;
import com.nexstudio.msscbeerservice.web.model.BeerPagedList;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRespository beerRespository;
    private final BeerMapper beerMapper;

    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false")
    @Override
    public BeerPagedList listBeers(String beerName, String beerStyle, Boolean showInventoryOnHand, PageRequest pageRequest) {
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;
        BeerStyleEnum validBeerStyle;

        if (StringUtils.hasLength(beerName) && ObjectUtils.containsConstant(BeerStyleEnum.values(), beerStyle)) {
            validBeerStyle = BeerStyleEnum.valueOf(beerStyle);

            beerPage = beerRespository.findAllByBeerNameAndBeerStyle(beerName, validBeerStyle, pageRequest);
        } else if (StringUtils.hasLength(beerName)
                && !ObjectUtils.containsConstant(BeerStyleEnum.values(), beerStyle)) {
            beerPage = beerRespository.findAllByBeerName(beerName, pageRequest);
        } else if (!StringUtils.hasLength(beerName)
                && ObjectUtils.containsConstant(BeerStyleEnum.values(), beerStyle)) {
            validBeerStyle = BeerStyleEnum.valueOf(beerStyle);

            beerPage = beerRespository.findAllByBeerStyle(validBeerStyle, pageRequest);
        } else {
            beerPage = beerRespository.findAll(pageRequest);
        }

        beerPagedList = new BeerPagedList(
                beerPage.getContent()
                        .stream()
                        .map(Boolean.TRUE.equals(showInventoryOnHand) ? beerMapper::beerToBeerDTO : beerMapper::beerToBeerDTONoInventory)
                        .collect(Collectors.toList()),
                        PageRequest
                                .of(beerPage.getPageable().getPageNumber(), 
                                        beerPage.getPageable().getPageSize()),
                        beerPage.getTotalElements());

        return beerPagedList;
    }

    @Cacheable(cacheNames = "beerCache", key = "#id", condition = "#showInventoryOnHand == false")
    @Override
    public BeerDTO getById(UUID id, Boolean showInventoryOnHand) {
        try {
            return Boolean.TRUE.equals(showInventoryOnHand) 
                ? beerMapper
                    .beerToBeerDTO(beerRespository.findById(id).orElseThrow(() -> new BeerNotFoundException(id)))
                : beerMapper
                    .beerToBeerDTONoInventory(beerRespository.findById(id).orElseThrow(() -> new BeerNotFoundException(id)));

        } catch (BeerNotFoundException e) {
            return null;
        }
    }

    @Cacheable(cacheNames = "beerUpcCache", key = "#upc", condition = "#showInventoryOnHand == true")
    @Override
    public BeerDTO getByUpc(String upc) {
        return beerMapper.beerToBeerDTONoInventory(beerRespository.findBeerByUpc(upc));
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
