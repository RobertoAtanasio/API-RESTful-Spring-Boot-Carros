package com.rapl.carros.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rapl.carros.domain.User;
import com.rapl.carros.domain.UserRepository;

@Service(value = "userDetailsService")
public class UserDatailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByLogin(username);
		if (user == null) {
			throw new UsernameNotFoundException("Usuário não encontrado");			
		}
		// como o User implementa o UserDetails, basta retornar a classe user.
		return user;
		
//		com.rapl.carros.domain.User user = userRepository.findByLogin(username);
//		if (user == null) {
//			throw new UsernameNotFoundException("Usuário não encontrado");			
//		}
//		return User.withUsername(username).password(user.getSenha()).roles("USER").build();
		
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
//		if (username.equals("user")) {
//			return User.withUsername(username).password(encoder.encode(username)).roles("USER").build();
//		} else if (username.equals("admin")) {
//			return User.withUsername(username).password(encoder.encode(username)).roles("USER", "ADMIN").build();
//		}
//		throw new UsernameNotFoundException("Usuário não encontrado");
	}

}
