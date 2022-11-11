package com.ada.earthvalley.yomojomo.nie.dtos;

import com.ada.earthvalley.yomojomo.nie.entities.Nie;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FetchNieResponse {
	private String predict;
	private String where;
	private String why;
	private String what;
	private String when;
	private String who;
	private String how;
	private String summary;

	// TODO: Assembler 로 리팩터링 (by Leo - 22.11.07)
	public static FetchNieResponse of(Nie nie) {
		FetchNieResponse response = new FetchNieResponse();

		nie.getProcesses().stream().forEach(pc -> {
			switch (pc.getType()) {
				case HOW:
					response.how = pc.getContent();
					break;
				case WHO:
					response.who = pc.getContent();
					break;
				case WHY:
					response.why = pc.getContent();
					break;
				case WHAT:
					response.what = pc.getContent();
					break;
				case WHEN:
					response.when = pc.getContent();
					break;
				case WHERE:
					response.where = pc.getContent();
					break;
				case SUMMARY:
					response.summary = pc.getContent();
					break;
				case PREDICT:
					response.predict = pc.getContent();
					break;
				default:
			}
		});
		return response;
	}
}


