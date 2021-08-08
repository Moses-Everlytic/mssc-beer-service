package com.nexstudio.msscbeerservice.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexstudio.msscbeerservice.web.model.BeerDTO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BeerController.class)
public class BeerControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private String apiURI = BeerController.BASE_API_URI + "/";
	private UUID id = UUID.fromString("7098adda-618a-4258-8396-f014bcddac1e");

	@Test
	public void shouldGetBeer() throws Exception {
		mockMvc.perform(get(apiURI + id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void shouldSaveNewBeer() throws Exception {
		BeerDTO beer = BeerDTO.builder().id(id).beerName("Test").build();
		String beerDTOJson = objectMapper.writeValueAsString(beer);

		mockMvc.perform(post(apiURI)
			.contentType(MediaType.APPLICATION_JSON)
			.content(beerDTOJson))
				.andExpect(status().isCreated());
	}

	@Test
	public void shouldUpdateBeer() throws Exception {
		BeerDTO beer = BeerDTO.builder().id(id).beerName("Test").build();
		String beerDTOJson = objectMapper.writeValueAsString(beer);

		mockMvc.perform(put(apiURI + id)
			.contentType(MediaType.APPLICATION_JSON)
			.content(beerDTOJson))
				.andExpect(status().isNoContent());
	}
}
