package com.nexstudio.msscbeerservice.web.mappers;

import com.nexstudio.msscbeerservice.model.Beer;
import com.nexstudio.msscbeerservice.web.model.BeerDTO;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO beerDTO);

    BeerDTO beerToBeerDTO(Beer beer);
}
