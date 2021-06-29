package com.modelo.resources;

import java.io.IOException;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.modelo.dto.RecoveryPasswordDto;
import com.modelo.dto.UserChangePasswordDto;
import com.modelo.dto.UserUpdatePasswordDto;
import com.modelo.entities.User;
import com.modelo.services.UserService;

@CrossOrigin
@RestController
@RequestMapping(value = "/user")
public class UserResource {

	@Autowired
	private UserService userService;

	@GetMapping
	public Page<User> findAll(@RequestParam(required = true, defaultValue = "10") int qtd,
			@RequestParam(required = true, defaultValue = "0") int page,
			@RequestParam(value = "nome", required = false) Optional<String> nome) {
		Pageable paginacao = PageRequest.of(page, qtd);
		return userService.findAll(paginacao, nome.orElse(null));
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		userService.delete(id);
	}

	@PutMapping(value = "/update_password")
	public User updatePassword(HttpServletRequest request, @RequestBody UserUpdatePasswordDto userPassword)
			throws MessagingException {
		String auth = request.getHeader("Authorization");
		return userService.updatePassword(userPassword, auth);
	}

	@PostMapping(value = "/recover_password")
	public User recorevyPassword(@RequestBody RecoveryPasswordDto userEmail) throws MessagingException, IOException {
		return userService.recorevyPassword(userEmail);
	}

	@PostMapping(value = "/change_password")
	public User changePassword(@RequestBody UserChangePasswordDto changePassword) throws MessagingException {
		return userService.changePassword(changePassword);
	}
}
