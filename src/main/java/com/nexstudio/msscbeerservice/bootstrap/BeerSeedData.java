package com.nexstudio.msscbeerservice.bootstrap;

import java.math.BigDecimal;

import com.nexstudio.msscbeerservice.constants.BeerStyleEnum;
import com.nexstudio.msscbeerservice.model.Beer;
import com.nexstudio.msscbeerservice.repositories.BeerRespository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class BeerSeedData implements CommandLineRunner {

    @Autowired
    private final BeerRespository beerRespository;

    @Override
    public void run(String... args) throws Exception {
        if (beerRespository.count() == 0) {
            this.loadBeerData();
        }
    }

    private void loadBeerData() {
        beerRespository.save(Beer.builder().beerName("Castle Lagar").beerStyle(BeerStyleEnum.LAGER).upc(485960000000L)
                .price(new BigDecimal("17.99")).minOnHand(20).quantityToBrew(100).build());

        beerRespository.save(Beer.builder().beerName("Black Label").beerStyle(BeerStyleEnum.LAGER).upc(23450000000L)
                .price(new BigDecimal("15.99")).minOnHand(5).quantityToBrew(5000).build());

        beerRespository.save(Beer.builder().beerName("Milk Stout").beerStyle(BeerStyleEnum.STOUT).upc(112340000050L)
                .price(new BigDecimal("21.99")).minOnHand(80).quantityToBrew(50).build());
    }

}
