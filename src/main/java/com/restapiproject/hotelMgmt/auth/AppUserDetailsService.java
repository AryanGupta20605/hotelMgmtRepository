package com.restapiproject.hotelMgmt.auth;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
 
@Service
public class AppUserDetailsService implements UserDetailsService {
	//security : During login-> authenticate username and password
	//generate JWT token and put username and role inside token
	//during every request - JWT- authorization header
	//filter - extract the username from token
	//security will be using this service class to load the user from db
	//loadUserByUsername - OK - generate JWT token
	//bridge b.w users table in DB and authentication security mechanism
	private final UserRepository userRepository;
	public AppUserDetailsService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByUsername(username);
		if(user == null) throw new UsernameNotFoundException("user not found");
		//convert db user to spring security user
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				List.of(new SimpleGrantedAuthority(user.getRole()))
				);
	}

}