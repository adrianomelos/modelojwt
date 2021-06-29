package com.modelo.resources;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modelo.dto.JwtRequest;
import com.modelo.entities.User;
import com.modelo.exceptions.RequisicaoInvalidaExceptionBadRequest;
import com.modelo.exceptions.RequisicaoInvalidaExceptionForbiden;
import com.modelo.exceptions.RequisicaoInvalidaExceptionUnautorized;
import com.modelo.repository.UserRepository;
import com.modelo.services.JwtTokenUtil;
import com.modelo.services.JwtUserDetailsService;

@RestController
@RequestMapping(value = "/login")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@PostMapping("/admin")
	public User createAuthenticationAmin(@RequestBody JwtRequest authenticationRequest) throws Exception {

		Optional<User> user = userRepository.findByUsername(authenticationRequest.getEmail());
		if (!user.isPresent()) {
			throw new RequisicaoInvalidaExceptionBadRequest("Usuario não encontrado");
		}
		
		if(!user.get().getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
			throw new RequisicaoInvalidaExceptionForbiden("Usuario não tem permissão para acesso");		
		}

		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.get().getUsername());
		String hash = jwtTokenUtil.generateToken(userDetails);

		user.get().setHash("Bearer " + hash);
		userRepository.save(user.get());
		return user.get();
	}
	
	@PostMapping("/user")
	public User createAuthenticationUser(@RequestBody JwtRequest authenticationRequest) throws Exception {

		Optional<User> user = userRepository.findByUsername(authenticationRequest.getEmail());
		if (!user.isPresent()) {
			throw new RequisicaoInvalidaExceptionBadRequest("Usuario não encontrado");
		}
		
		if(!user.get().getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_USER"))) {
			throw new RequisicaoInvalidaExceptionForbiden("Usuario não tem permissão para acesso");		
		}

		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.get().getUsername());
		String hash = jwtTokenUtil.generateToken(userDetails);

		user.get().setHash("Bearer " + hash);
		userRepository.save(user.get());
		return user.get();
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new RequisicaoInvalidaExceptionUnautorized("Senha inválida");
		}
	}

}
