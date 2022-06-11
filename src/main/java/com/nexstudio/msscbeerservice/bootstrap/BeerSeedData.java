package com.nexstudio.msscbeerservice.bootstrap;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.nexstudio.msscbeerservice.model.Beer;
import com.nexstudio.msscbeerservice.repositories.BeerRespository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
// @Component
public class BeerSeedData implements CommandLineRunner {

    public static final String BEER_1_UPC = "0002348987546";
    public static final String BEER_2_UPC = "0006648987549";
    public static final String BEER_3_UPC = "0079148987541";

    @Autowired
    private final BeerRespository beerRespository;

    @Override
    public void run(String... args) throws Exception {
        if (beerRespository.count() == 0) {
            this.loadBeerData();
        }
    }

    private void loadBeerData() {
        beerRespository.save(Beer.builder().beerName("Castle Lagar").beerStyle("LAGER").upc(BEER_1_UPC)
                .price(new BigDecimal("17.99")).minOnHand(20).quantityToBrew(100).build());

        beerRespository.save(Beer.builder().beerName("Black Label").beerStyle("ALE").upc(BEER_2_UPC)
                .price(new BigDecimal("15.99")).minOnHand(5).quantityToBrew(5000).build());

        beerRespository.save(Beer.builder().beerName("Milk Stout").beerStyle("STOUT").upc(BEER_3_UPC)
                .price(new BigDecimal("21.99")).minOnHand(80).quantityToBrew(50).build());
    }

}
