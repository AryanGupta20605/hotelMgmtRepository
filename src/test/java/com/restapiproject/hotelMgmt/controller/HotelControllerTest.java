package com.restapiproject.hotelMgmt.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapiproject.hotelMgmt.dto.HotelDto;
import com.restapiproject.hotelMgmt.model.Hotel;
import com.restapiproject.hotelMgmt.service.HotelService;

//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class HotelControllerTest {

	private MockMvc mockMvc;
	// MockMvc -> used to simulate Http request to the controller without starting a
	// webserver

	@Mock
	private HotelService hotelService;

	@InjectMocks
	private HotelController hotelController;

	private Hotel hotel1;
	private Hotel hotel2;

	// mapper will be used to convert the DTO objects into JSON strings for POST/Put
	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(hotelController).build();
		// build a MockMvc instance that registers only the controller
		// standalone setup -> do not want to load spring context, wire only the
		// controller and its dependencies

		hotel1 = new Hotel(1L, "Hotel A", "Address A", 10, 5, new BigDecimal("100.0"));
		hotel2 = new Hotel(2L, "Hotel B", "Address B", 20, 10, new BigDecimal("200.0"));
	}

	// GET: /api/hotels
	@Test
	void testGetAllHotels() throws Exception {
		when(hotelService.getAllHotels()).thenReturn(Arrays.asList(hotel1, hotel2));

		mockMvc.perform(get("/api/hotels")).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(2)) // using
																												// size()
				.andExpect(jsonPath("$[0].name").value("Hotel A")).andExpect(jsonPath("$[1].name").value("Hotel B"));

		verify(hotelService, times(1)).getAllHotels();
	}

	// GET: /api/hotels/{id}
	@Test
	void testGetHotelById() throws Exception {
		when(hotelService.getHotelById(1L)).thenReturn(hotel1);

		mockMvc.perform(get("/api/hotels/1")).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Hotel A"))
				.andExpect(jsonPath("$.total_rooms").value(10));

		verify(hotelService, times(1)).getHotelById(1L);
	}

	// POST: /api/hotels
	@Test
	void testCreateHotel() throws Exception {
		HotelDto dto = new HotelDto();
		dto.setName("Hotel C");
		dto.setAddress("Address C");
		dto.setTotal_rooms(30);
		dto.setAvailable_rooms(20);
		dto.setPrice_per_night(new BigDecimal("300.0"));

		Hotel created = new Hotel(3L, "Hotel C", "Address C", 30, 20, new BigDecimal("300.0"));
		when(hotelService.createHotel(any(Hotel.class))).thenReturn(created);

		mockMvc.perform(post("/api/hotels").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))).andExpect(status().isCreated())
				.andExpect(header().string("Location", "/api/hotels/3")).andExpect(jsonPath("$.id").value(3))
				.andExpect(jsonPath("$.name").value("Hotel C"));

		verify(hotelService, times(1)).createHotel(any(Hotel.class));
	}

	// PUT: /api/hotels/{id}
	@Test
	void testUpdateHotel() throws Exception {
		HotelDto dto = new HotelDto();
		dto.setName("Hotel Updated");
		dto.setAddress("New Address");
		dto.setTotal_rooms(15);
		dto.setAvailable_rooms(7);
		dto.setPrice_per_night(new BigDecimal("150.0"));

		Hotel updated = new Hotel(1L, "Hotel Updated", "New Address", 15, 7, new BigDecimal("150.0"));
		when(hotelService.updateHotel(eq(1L), any(Hotel.class))).thenReturn(updated);

		mockMvc.perform(put("/api/hotels/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Hotel Updated"))
				.andExpect(jsonPath("$.address").value("New Address"));

		verify(hotelService, times(1)).updateHotel(eq(1L), any(Hotel.class));
	}

	// DELETE: /api/hotels/{id}
	@Test
	void testDeleteHotel() throws Exception {
		mockMvc.perform(delete("/api/hotels/1")).andExpect(status().isNoContent());
		verify(hotelService, times(1)).deleteHotel(1L);
	}

	// GET: /api/hotels/search?name=Hotel A
	@Test
	void testSearchHotelByName_matchFound() throws Exception {
		HotelDto hotelDto = new HotelDto();
		hotelDto.setId(1L);
		hotelDto.setName("Hotel A");
		hotelDto.setAddress("Address A");
		hotelDto.setTotal_rooms(100);
		hotelDto.setAvailable_rooms(20);
		hotelDto.setPrice_per_night(new BigDecimal("1500.0"));

		// Convert DTO to entity for stubbing the service (service returns List<Hotel>)
		Hotel hotelEntity = new Hotel();
		hotelEntity.setId(hotelDto.getId());
		hotelEntity.setName(hotelDto.getName());
		hotelEntity.setAddress(hotelDto.getAddress());
		hotelEntity.setTotal_rooms(hotelDto.getTotal_rooms());
		hotelEntity.setAvailable_rooms(hotelDto.getAvailable_rooms());
		hotelEntity.setPrice_per_night(hotelDto.getPrice_per_night());

		when(hotelService.searchHotelsByName("Hotel A")).thenReturn(List.of(hotelEntity));
		mockMvc.perform(get("/api/hotels/search/{name}", "Hotel A").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].name").value("Hotel A"));

		verify(hotelService, times(1)).searchHotelsByName("Hotel A");
		verify(hotelService, never()).getAllHotels(); // not called in match branch
	}

}
