package com.nexstudio.msscbeerservice.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexstudio.msscbeerservice.bootstrap.BeerSeedData;
import com.nexstudio.msscbeerservice.constants.BeerStyleEnum;
import com.nexstudio.msscbeerservice.services.BeerService;
import com.nexstudio.msscbeerservice.web.model.BeerDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs(uriScheme = "https", uriHost = "lacafenex.com", uriPort = 80)
@WebMvcTest(BeerController.class)
@ExtendWith(RestDocumentationExtension.class)
public class BeerControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	BeerService beerService;

	private String apiURI = BeerController.BASE_API_URI + "/";
	private UUID id = UUID.fromString("7098adda-618a-4258-8396-f014bcddac1e");

	@Test
	public void shouldGetBeerById() throws Exception {

		given(beerService.getById(any())).willReturn(getValidBeerDto());

		mockMvc.perform(
				get(apiURI + "{beerId}", id).param("example-param", "example").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("v1/beer-get",
						pathParameters(parameterWithName("beerId").description("UUID of desired beer to get.")),
						// requestParameters(parameterWithName("exmaple-param").description("Example
						// parameter")),
						responseFields(fieldWithPath("id").description("Id of Beer"),
								fieldWithPath("version").description("The Beer version number"),
								fieldWithPath("beerName").description("The name of the Beer"),
								fieldWithPath("beerStyle").description("Beer type"),
								fieldWithPath("price").description("Price of Beer"),
								fieldWithPath("quantityOnHand").description("Number of beers on available"),
								fieldWithPath("upc").description("Barcode of Beer"),
								fieldWithPath("createdDate").description("Date when Beer was created"),
								fieldWithPath("lastModified").description("Date when Beer was last modified"))));
	}

	@Test
	public void shouldSaveNewBeer() throws Exception {
		BeerDTO beer = BeerDTO.builder().beerName("Test").beerStyle(BeerStyleEnum.ALE).price(new BigDecimal(34.99))
				.upc(BeerSeedData.BEER_1_UPC).build();
		String beerDTOJson = objectMapper.writeValueAsString(beer);

		given(beerService.saveNewBeer(any())).willReturn(getValidBeerDto());

		mockMvc.perform(post(apiURI).contentType(MediaType.APPLICATION_JSON).content(beerDTOJson))
				.andExpect(status().isCreated())
				.andDo(document("v1/beer-new",
						requestFields(fieldWithPath("id").ignored(), fieldWithPath("createdDate").ignored(),
								fieldWithPath("lastModified").ignored(), fieldWithPath("version").ignored(),
								fieldWithPath("quantityOnHand").ignored(),
								fieldWithPath("beerName").description("The name of the Beer"),
								fieldWithPath("beerStyle").description("Beer type"),
								fieldWithPath("price").description("Price of Beer"),
								fieldWithPath("upc").description("Beer UPC").attributes())));
	}

	@Test
	public void shouldUpdateBeer() throws Exception {
		BeerDTO beer = BeerDTO.builder().beerName("Test").beerStyle(BeerStyleEnum.ALE).price(new BigDecimal(34.99))
				.upc(BeerSeedData.BEER_1_UPC).build();
		String beerDTOJson = objectMapper.writeValueAsString(beer);

		given(beerService.updateBeer(any(), any())).willReturn(getValidBeerDto());

		mockMvc.perform(put(apiURI + "{beerId}", id).contentType(MediaType.APPLICATION_JSON).content(beerDTOJson))
				.andExpect(status().isNoContent())
				.andDo(document("v1/beer-update", pathParameters(parameterWithName("beerId").description("Id of Beer")),
						requestFields(fieldWithPath("id").ignored(), fieldWithPath("createdDate").ignored(),
								fieldWithPath("lastModified").ignored(), fieldWithPath("version").ignored(),
								fieldWithPath("beerName").description("The name of the Beer"),
								fieldWithPath("beerStyle").description("Beer type"),
								fieldWithPath("price").description("Price of Beer"), fieldWithPath("quantityOnHand")
										.description("Number of beers on available").attributes(),
								fieldWithPath("upc").description("Barcode of Beer"))));
	}

	private BeerDTO getValidBeerDto() {
		return BeerDTO.builder()
				.beerName("My Beer")
				.beerStyle(BeerStyleEnum.ALE)
				.price(new BigDecimal("13.99"))
				.upc(BeerSeedData.BEER_1_UPC)
				.build();
	}
}
