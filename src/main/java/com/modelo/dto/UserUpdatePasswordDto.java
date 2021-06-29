package com.modelo.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdatePasswordDto {

	@NotBlank
	private String passwordOld;
	@NotBlank
	private String passwordNew;
}
