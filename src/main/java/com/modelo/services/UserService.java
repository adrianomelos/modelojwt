package com.modelo.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.modelo.dto.RecoveryPasswordDto;
import com.modelo.dto.UserChangePasswordDto;
import com.modelo.dto.UserUpdatePasswordDto;
import com.modelo.entities.SecurityUser;
import com.modelo.entities.User;
import com.modelo.exceptions.DatabaseException;
import com.modelo.exceptions.RequisicaoInvalidaExceptionBadRequest;
import com.modelo.exceptions.RequisicaoInvalidaExceptionForbiden;
import com.modelo.exceptions.ResourceNotFoundException;
import com.modelo.repository.UserRepository;

import net.bytebuddy.utility.RandomString;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;

	public User save(@Valid User user) {

		Optional<User> u = userRepository.findByUsername(user.getUsername());
		if (u.isPresent()) {
			throw new RequisicaoInvalidaExceptionBadRequest("email já cadastrado no app");
		}

		SecurityUser securirtyUser = new SecurityUser();
		securirtyUser.setAtivo(true);
		securirtyUser.setDateCreate(LocalDateTime.now());

		String password = criptografarPassword(user.getPassword());
		user.setPassword(password);
		user.setSecurity(securirtyUser);

		try {
			return userRepository.save(user);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	private String criptografarPassword(String password) {
		String passCriptografado = BCrypt.hashpw(password, BCrypt.gensalt());
		return passCriptografado;
	}

	public User updatePassword(UserUpdatePasswordDto userPassword, String hash) {
		Optional<User> user = userRepository.findByHash(hash);
		if (!user.isPresent()) {
			throw new ResourceNotFoundException("Usuario não logado");
		}
		boolean autenticacaoBateu = BCrypt.checkpw(userPassword.getPasswordOld(), user.get().getPassword());
		if (autenticacaoBateu) {
			String novaSenha = criptografarPassword(userPassword.getPasswordNew());
			user.get().setPassword(novaSenha);
			User u = userRepository.save(user.get());
			return u;
		} else {
			throw new RequisicaoInvalidaExceptionBadRequest("A senha não confere");
		}
	}

	public User registerAdmin(User user) {
		Optional<User> u = userRepository.findByUsername(user.getUsername());
		if (u.isPresent()) {
			throw new RequisicaoInvalidaExceptionBadRequest("email já cadastrado no app");
		}

		SecurityUser s = new SecurityUser();
		s.setDateCreate(LocalDateTime.now());
		s.setAtivo(true);

		user.setPassword(criptografarPassword(user.getPassword()));
		user.setSecurity(s);
		return userRepository.save(user);
	}

	public void delete(Long id) {
		try {
			Optional<User> u = userRepository.findById(id);
			u.get().getAuthorities().clear();
			userRepository.delete(u.get());
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (Exception e) {
			throw new DatabaseException("");
		}
	}

	public User recorevyPassword(RecoveryPasswordDto userEmail) throws IOException {
		String token = RandomString.make(5);
		Optional<User> user = userRepository.findByUsername(userEmail.getEmail());
		if (!user.isPresent()) {
			throw new RequisicaoInvalidaExceptionBadRequest("email não encontrado");
		}
		user.get().getSecurity().setToken(token);

		userRepository.save(user.get());
		emailService.sendEmail(user.get().getUsername(), token);
		return user.get();
	}

	public User changePassword(UserChangePasswordDto changePassword) {
		Optional<User> obj = userRepository.findBySecurityToken(changePassword.getToken());
		if (!obj.isPresent()) {
			throw new RequisicaoInvalidaExceptionBadRequest("Token não encontrado");
		}

		obj.get().setPassword(criptografarPassword(changePassword.getPassword()));
		obj.get().getSecurity().setToken(null);

		userRepository.save(obj.get());
		return obj.get();
	}

	public User firstAccessApp(RecoveryPasswordDto dto) throws IOException {
		Optional<User> user = userRepository.findByUsername(dto.getEmail());
		if (!user.isPresent()) {
			throw new RequisicaoInvalidaExceptionBadRequest("Usuario não encontrado");
		}

		if (!user.get().getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_STUDENT"))) {
			throw new RequisicaoInvalidaExceptionForbiden("Usuario não tem permissão para acesso");
		}
		String token = RandomString.make(5);

		user.get().getSecurity().setToken(token);
		user.get().getSecurity().setAtivo(true);

		userRepository.save(user.get());
		emailService.sendEmail(user.get().getUsername(), token);
		return user.get();

	}

	public User firstAccessSchool(RecoveryPasswordDto dto) throws IOException {
		Optional<User> user = userRepository.findByUsername(dto.getEmail());
		if (!user.isPresent()) {
			throw new RequisicaoInvalidaExceptionBadRequest("Usuario não encontrado");
		}

		if (!user.get().getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_SCHOOL"))) {
			throw new RequisicaoInvalidaExceptionForbiden("Usuario não tem permissão para acesso");
		}
		String token = RandomString.make(5);

		user.get().getSecurity().setToken(token);
		user.get().getSecurity().setAtivo(true);

		userRepository.save(user.get());
		emailService.sendEmail(user.get().getUsername(), token);
		return user.get();
	}

	public Page<User> findAll(Pageable paginacao, String nome) {
		return userRepository.findAll(paginacao);
	}
}
