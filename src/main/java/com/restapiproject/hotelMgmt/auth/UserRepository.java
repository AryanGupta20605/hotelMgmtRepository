package com.restapiproject.hotelMgmt.auth;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
 
@Repository //DAO
public class UserRepository {
 
	//responsible for interacting with user table
	//check - username already exist
	//fetch a user by username
	//insert a new user
	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<User> userRowMapper = (rs,rowNum) -> {
		User u = new User();
		u.setId(rs.getInt("id"));
		u.setUsername(rs.getString("username"));
		u.setPassword(rs.getString("password"));
		u.setRole(rs.getString("role"));
		return u;

	};
	public UserRepository(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate= jdbcTemplate;
	}
	//method to check username exist or not
	public boolean existByusername(String username) {
		Integer cnt = jdbcTemplate.queryForObject("Select count(*) from users where username = ?", Integer.class,username);
		return cnt!= null && cnt>0;
		//count>0 username already exist-> trye
		//used to prevent duplicate username
	}
	//method to fetch by username
	public User findByUsername(String username) {
		try {
			return jdbcTemplate.queryForObject("select * from users where username=?", userRowMapper,username);
		}
		catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	//method- save new user
	public int save(User user) {
		return jdbcTemplate.update("Insert into users (username,password,role) values (?,?,?)",
				user.getUsername(),user.getPassword(),user.getRole());
	}

}