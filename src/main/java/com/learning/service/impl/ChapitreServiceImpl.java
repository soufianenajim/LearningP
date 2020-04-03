package com.learning.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.learning.dao.ChapitreRepository;
import com.learning.dto.ChapitreDTO;
import com.learning.model.Chapitre;
import com.learning.model.Cour;
import com.learning.model.base.Demande;
import com.learning.model.base.PartialList;
import com.learning.service.ChapitreService;
import com.learning.service.CourService;

@Service
public class ChapitreServiceImpl implements ChapitreService {

	@Autowired
	private ChapitreRepository chapitreRepository;
	@Autowired
	private CourService courService;

	// save or update
	@Override
	public ChapitreDTO save(ChapitreDTO chapitreDTO) {
		Chapitre chapitre = convertDTOtoModel(chapitreDTO);
		chapitre = chapitreRepository.save(chapitre);
		return convertModelToDTO(chapitre);
	}

	@Override
	public ChapitreDTO findById(long idOut) {
		Optional<Chapitre> optional = chapitreRepository.findById(idOut);

		if (optional.isPresent()) {
			Chapitre chapitreFromDb = optional.get();
			return convertModelToDTO(chapitreFromDb);
		}
		return null;
	}

	@Override
	public void delete(Chapitre chapitre) {
		chapitreRepository.delete(chapitre);
	}

	@Override
	public PartialList<ChapitreDTO> findByCriteres(Demande<ChapitreDTO> demande) {

		ChapitreDTO chapitre = demande.getModel();
		int page = demande.getPage();
		int size = demande.getSize();
		Page<Chapitre> pageChapitre = null;
		String name = chapitre.getName();
		Long idCour = chapitre.getCour() != null ? chapitre.getCour().getId() : null;

		pageChapitre = idCour != null ? chapitreRepository.findByNameAndCour(name, idCour, PageRequest.of(page, size))
				: chapitreRepository.findByName(name, PageRequest.of(page, size));

		List<ChapitreDTO> list = convertEntitiesToDtos(pageChapitre.getContent());
		Long totalElement = pageChapitre.getTotalElements();
		return new PartialList<ChapitreDTO>(totalElement, list);
	}

	@Override
	public Chapitre convertDTOtoModel(ChapitreDTO chapitreDTO) {
		Chapitre chapitre = new Chapitre();
		chapitre.setId(chapitreDTO.getId());
		chapitre.setName(chapitreDTO.getName());

		if (chapitreDTO.getCour() != null) {
			chapitre.setCour(courService.convertDTOtoModel(chapitreDTO.getCour()));
		}
		return chapitre;
	}

	@Override
	public ChapitreDTO convertModelToDTO(Chapitre chapitre) {
		ChapitreDTO chapitreDTO = new ChapitreDTO();
		chapitreDTO.setId(chapitre.getId());
		chapitreDTO.setName(chapitre.getName());
		Cour cour = chapitre.getCour();
		if (cour != null) {
			chapitreDTO.setCour(courService.convertModelToDTO(chapitre.getCour()));
			
		}
		
		chapitreDTO.setCreatedAt(chapitre.getCreatedAt());
		chapitreDTO.setUpdatedAt(chapitre.getUpdatedAt());
		return chapitreDTO;
	}

	@Override
	public PartialList<ChapitreDTO> convertToListDTO(PartialList<Chapitre> list) {

		return null;
	}

	@Override
	public void deleteById(Long id) {
		chapitreRepository.deleteById(id);

	}

	@Override
	public List<ChapitreDTO> convertEntitiesToDtos(List<Chapitre> chapitres) {
		// basic methode
		List<ChapitreDTO> list = new ArrayList<ChapitreDTO>();
		for (Chapitre chapitre : chapitres) {
			list.add(convertModelToDTO(chapitre));
		}
		return list;
	}

	@Override
	public List<Chapitre> convertDtosToEntities(List<ChapitreDTO> chapitresDTO) {
		List<Chapitre> list = new ArrayList<Chapitre>();
		for (ChapitreDTO chapitreDTO : chapitresDTO) {
			list.add(convertDTOtoModel(chapitreDTO));
		}
		return list;
	}

	@Override
	public List<ChapitreDTO> findAll() {
		List<Chapitre> list = chapitreRepository.findAll();
		return convertEntitiesToDtos(list);
	}

}
