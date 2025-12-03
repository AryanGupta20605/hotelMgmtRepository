package com.restapiproject.hotelMgmt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.restapiproject.hotelMgmt.model.Hotel;
import com.restapiproject.hotelMgmt.repository.HotelDao;

@Service
public class HotelServiceImpl implements HotelService {
	private HotelDao hotelDao;
	public HotelServiceImpl(HotelDao hotelDao) {
		this.hotelDao = hotelDao;
	}

	@Override
	public Hotel createHotel(Hotel hotel) {
		return hotelDao.save(hotel);
	}

	@Override
	public Hotel getHotelById(Long id) {
		return hotelDao.findById(id).orElseThrow(null);
	}
 
	@Override
	public List<Hotel> getAllHotels() {
		return hotelDao.findAll();
	}
 
	@Override
	public Hotel updateHotel(Long id, Hotel hotel) {
		Hotel existing = getHotelById(id);
		existing.setName(hotel.getName());
		existing.setAddress(hotel.getAddress());
		existing.setTotal_rooms(hotel.getTotal_rooms());
		existing.setAvailable_rooms(hotel.getAvailable_rooms());
		existing.setPrice_per_night(hotel.getPrice_per_night());
		int rows = hotelDao.update(existing);
		if (rows <= 0)
			throw new RuntimeException("update failed for hotel id :" + id);
		return existing;
	}
 
	@Override
	public void deleteHotel(Long id) {
		getHotelById(id);
		int rows = hotelDao.deleteById(id);
		if (rows <= 0)
			throw new RuntimeException("delete failed for hotel id " + id);
	}

	@Override
	public List<Hotel> searchHotelsByName(String name) {
	    return hotelDao.findByName(name);
	}
} 