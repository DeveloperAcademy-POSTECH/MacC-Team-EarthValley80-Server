package com.ada.earthvalley.yomojomo.nie.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.ada.earthvalley.yomojomo.nie.entities.enums.NieProcessType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class NieContext {
	@Enumerated(EnumType.STRING)
	private NieProcessType lastProcess;
	private long articleCursor;
}
