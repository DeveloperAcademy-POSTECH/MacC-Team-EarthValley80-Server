package com.ada.earthvalley.yomojomo.auth.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TempAuthLoginRequest {
	private String userCode;
}
