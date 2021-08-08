package com.nexstudio.msscbeerservice.web.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDTO {

    private UUID id;
    private Integer version;

    private String beerName;
    private BeerStyleEnum beerStyle;
    private BigDecimal price;
    private Integer quantityOnHand;
    private Long upc;

    private OffsetDateTime createdDate;
    private OffsetDateTime lastModified;
}
