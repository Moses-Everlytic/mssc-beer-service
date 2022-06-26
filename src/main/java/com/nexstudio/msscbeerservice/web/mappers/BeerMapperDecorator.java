package com.nexstudio.msscbeerservice.web.mappers;

import com.nexstudio.msscbeerservice.model.Beer;
import com.nexstudio.msscbeerservice.services.inventory.BeerInventoryService;
import com.nexstudio.msscbeerservice.web.model.BeerDTO;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class BeerMapperDecorator implements BeerMapper {
    private BeerInventoryService beerInventoryService;
    private BeerMapper mapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BeerDTO beerToBeerDTO(Beer beer) {
        BeerDTO dto = mapper.beerToBeerDTO(beer);
        dto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));
        return dto;
    }

    @Override
    public Beer beerDtoToBeer(BeerDTO beerDto) {
        return mapper.beerDtoToBeer(beerDto);
    }

    @Override
    public BeerDTO beerToBeerDTONoInventory(Beer beer) {
        return mapper.beerToBeerDTONoInventory(beer);
    }
}
