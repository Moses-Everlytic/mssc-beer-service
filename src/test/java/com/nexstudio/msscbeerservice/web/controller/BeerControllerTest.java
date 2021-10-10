package com.nexstudio.msscbeerservice.web.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexstudio.msscbeerservice.constants.BeerStyleEnum;
import com.nexstudio.msscbeerservice.web.model.BeerDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
@ExtendWith(RestDocumentationExtension.class)
public class BeerControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	private String apiURI = BeerController.BASE_API_URI + "/";
	private UUID id = UUID.fromString("7098adda-618a-4258-8396-f014bcddac1e");

	@Test
	public void shouldGetBeerById() throws Exception {
		mockMvc.perform(
				get(apiURI + "{beerId}", id).param("example-param", "example").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("v1/beer",
						pathParameters(parameterWithName("beerId").description("UUID of desired beer to get.")),
						requestParameters(parameterWithName("exmaple-param").description("Example parameter"))));
	}

	@Test
	public void shouldSaveNewBeer() throws Exception {
		BeerDTO beer = BeerDTO.builder().beerName("Test").beerStyle(BeerStyleEnum.ALE).price(new BigDecimal(34.99))
				.upc(129087648912L).build();
		String beerDTOJson = objectMapper.writeValueAsString(beer);

		mockMvc.perform(post(apiURI).contentType(MediaType.APPLICATION_JSON).content(beerDTOJson))
				.andExpect(status().isCreated());
	}

	@Test
	public void shouldUpdateBeer() throws Exception {
		BeerDTO beer = BeerDTO.builder().beerName("Test").beerStyle(BeerStyleEnum.ALE).price(new BigDecimal(34.99))
				.upc(129087648912L).build();
		String beerDTOJson = objectMapper.writeValueAsString(beer);

		mockMvc.perform(put(apiURI + id).contentType(MediaType.APPLICATION_JSON).content(beerDTOJson))
				.andExpect(status().isNoContent());
	}
}
