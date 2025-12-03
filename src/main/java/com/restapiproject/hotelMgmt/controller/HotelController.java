package com.restapiproject.hotelMgmt.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restapiproject.hotelMgmt.dto.HotelDto;
import com.restapiproject.hotelMgmt.model.Hotel;
import com.restapiproject.hotelMgmt.service.HotelService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
	
     
	private final HotelService hotelService;
	public HotelController(HotelService hotelService) {
		this.hotelService=hotelService;
	}
	//DTO to entity
	private Hotel dtoToEntity(HotelDto dto) {
		Hotel h = new Hotel();
		h.setId(dto.getId());
		h.setName(dto.getName());
		h.setAddress(dto.getAddress());
		h.setTotal_rooms(dto.getTotal_rooms());
		h.setAvailable_rooms(dto.getAvailable_rooms());
		h.setPrice_per_night(dto.getPrice_per_night());
		return h;
	}
	
	// EntitytoDto
	private HotelDto entityToDto(Hotel h) {
		HotelDto dto = new HotelDto();
		dto.setId(h.getId());
		dto.setName(h.getName());
		dto.setAddress(h.getAddress());
		dto.setTotal_rooms(h.getTotal_rooms());
		dto.setAvailable_rooms(h.getAvailable_rooms());
		dto.setPrice_per_night(h.getPrice_per_night());
		return dto;
	}
	//Get all Hotels
	// Get request
	@GetMapping
	public ResponseEntity<List<HotelDto>> getAll() {
		List<Hotel> list = hotelService.getAllHotels(); // fetch that from db 
		List<HotelDto> dtoList = list.stream().map(this::entityToDto)
				                              .collect(Collectors.toList());
		return ResponseEntity.ok(dtoList);
 	}
	@GetMapping("/{id}")
	public ResponseEntity<HotelDto> getById(@PathVariable Long id) { 
				
		
		Hotel h = hotelService.getHotelById(id);
		HotelDto dto = entityToDto(h);
		return ResponseEntity.ok(dto);		
	}
	
	// Post Request
	@PostMapping
	public ResponseEntity<HotelDto> create(@Valid @RequestBody HotelDto dto) {
		Hotel h = dtoToEntity(dto);
		Hotel created = hotelService.createHotel(h);
		return ResponseEntity.created(URI.create("/api/hotels/" + created.getId()))
				             .body(entityToDto(created));
	}
	
	// Put Request
	@PutMapping("/{id}")
	public ResponseEntity<HotelDto> update(@PathVariable Long id, @Valid @RequestBody HotelDto dto) {		
		Hotel h = dtoToEntity(dto);
		Hotel updated = hotelService.updateHotel(id, h);
        return ResponseEntity.ok(entityToDto(updated));		
	}
	
	// Delete Request
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		hotelService.deleteHotel(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/search/{name}")
	public ResponseEntity<?> searchByName(@PathVariable String name) {
	    List<Hotel> hotels = hotelService.searchHotelsByName(name);

	    if (hotels.isEmpty()) {
	        // If no match, return all hotels with a message
	        List<Hotel> allHotels = hotelService.getAllHotels();
	        List<HotelDto> dtoList = allHotels.stream()
	                                          .map(this::entityToDto)
	                                          .collect(Collectors.toList());

	        return ResponseEntity.ok(
	            Map.of(
	                "message", "No match found for name: " + name,
	                "allHotels", dtoList
	            )
	        );
	    }
	 // If matches found
	    List<HotelDto> dtoList = hotels.stream()
	                                   .map(this::entityToDto)
	                                   .collect(Collectors.toList());
	    return ResponseEntity.ok(dtoList);
	}
	
}
