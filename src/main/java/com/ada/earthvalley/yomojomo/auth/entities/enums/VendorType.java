package com.ada.earthvalley.yomojomo.auth.entities.enums;

public enum VendorType {
	KAKAO, APPLE;

	public static VendorType typeOf(String typeString) {
		switch (typeString) {
			case "kakao":
				return KAKAO;
			case "apple":
				return APPLE;
			default:
				return null;
		}
	}
}
