package com.ada.earthvalley.yomojomo.auth.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Data
public class LoginResponse {
	private YomojomoToken accessToken;
	private YomojomoToken refreshToken;
}
