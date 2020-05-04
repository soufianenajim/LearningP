package com.learning.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.learning.dao.ModuleRepositorySearchCriteria;
import com.learning.dto.ModuleDTO;
import com.learning.model.Module;
import com.learning.model.base.Demande;
import com.learning.model.base.SortOrder;

@Repository
public class ModuleRepositorySearchCriteriaImpl implements ModuleRepositorySearchCriteria {
	
	@Autowired
	private EntityManager em;
	
	private CriteriaBuilder cb = null;

	private Root<Module> module = null;

	private List<Predicate> predicates = null;

	@Override
	public List<Module> findByCriteres(Demande<ModuleDTO> demande) {
		cb = em.getCriteriaBuilder();
		CriteriaQuery<Module> cq = cb.createQuery(Module.class);

		module = cq.from(Module.class);
		predicates = getPredicate(demande);
		int page = demande.getPage();
		int size = demande.getSize();
		if (!StringUtils.isEmpty(demande.getSortField()) && !StringUtils.isEmpty(demande.getSortOrder())
				&& !demande.getSortOrder().equals(SortOrder.NONE)) {
			if (demande.getSortOrder().equals(SortOrder.ASCENDING)) {
				cq.orderBy(cb.asc(module.get(demande.getSortField())));
			} else {
				cq.orderBy(cb.desc(module.get(demande.getSortField())));
			}
		}
		cq.where(predicates.toArray(new Predicate[0]));
		cq.distinct(true);

		return em.createQuery(cq).setFirstResult(page * size).setMaxResults(size).getResultList();

	}

	@Override
	public Long countByCriteres(Demande<ModuleDTO> demande) {
		 cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);

		module = cq.from(Module.class);
		cq.select(cb.count(module));
		predicates = getPredicate(demande);

		cq.where(predicates.toArray(new Predicate[0]));
		cq.distinct(true);
		return em.createQuery(cq).getSingleResult();
	}

	private List<Predicate> getPredicate(Demande<ModuleDTO> demande) {
		List<Predicate> predicates = new ArrayList<>();
		ModuleDTO moduleDTO = demande.getModel();

		if (!StringUtils.isEmpty(moduleDTO.getName())) {
			predicates.add(
					cb.like(cb.lower(module.<String>get("name")), "%" + moduleDTO.getName().toLowerCase() + "%"));
		}

		if (!StringUtils.isEmpty(moduleDTO.getProfessor()) && moduleDTO.getProfessor() != null) {
			predicates.add(cb.equal(module.<Long>get("professor"), moduleDTO.getProfessor().getId()));
		}
		if (!StringUtils.isEmpty(moduleDTO.getIdOrganization()) && moduleDTO.getIdOrganization() != null) {
			predicates.add(cb.equal(module.<Long>get("group").get("level").get("organization"), moduleDTO.getIdOrganization()));
		}

		return predicates;
	}

}
