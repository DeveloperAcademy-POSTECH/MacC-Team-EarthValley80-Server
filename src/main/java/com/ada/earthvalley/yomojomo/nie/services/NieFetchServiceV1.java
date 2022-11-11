package com.ada.earthvalley.yomojomo.nie.services;

import static com.ada.earthvalley.yomojomo.nie.exceptions.NieError.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ada.earthvalley.yomojomo.nie.dtos.FetchNieResponse;
import com.ada.earthvalley.yomojomo.nie.entities.Nie;
import com.ada.earthvalley.yomojomo.nie.exceptions.YomojomoNieException;
import com.ada.earthvalley.yomojomo.nie.repositories.NieRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class NieFetchServiceV1 {
	private final NieRepository nieRepository;

	public FetchNieResponse fetchNie(Long nieId) throws YomojomoNieException {
		Nie nie = nieRepository.findById(nieId)
			.orElseThrow(() -> new YomojomoNieException(NIE_NOT_FOUND));
		return FetchNieResponse.of(nie);
	}
}
