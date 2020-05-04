package com.learning.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "module")
public class Module extends Historized {

	private static final long serialVersionUID = -8858004000210805400L;

	@Column(name = "name", length = 100)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "professor_id")
	private User professor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	private Group group;
	
	
	private boolean isLaunched;

	

	@OneToMany(mappedBy = "module", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Cour> cours;
	
	@OneToMany(mappedBy = "module", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Exam> exams;
	
	@OneToMany(mappedBy = "module", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<ProgressionModule> progressionModules;
	
	public Module() {
		super();
	}

	public Module(String name) {
		super();
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public User getProfessor() {
		return professor;
	}

	public void setProfessor(User professor) {
		this.professor = professor;
	}

	public List<Cour> getCours() {
		return cours;
	}

	public void setCours(List<Cour> cours) {
		this.cours = cours;
	}
	

	public List<Exam> getExams() {
		return exams;
	}

	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}
	

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public List<ProgressionModule> getProgressionModules() {
		return progressionModules;
	}

	public void setProgressionModules(List<ProgressionModule> progressionModules) {
		this.progressionModules = progressionModules;
	}

	
	public boolean isLaunched() {
		return isLaunched;
	}

	public void setLaunched(boolean isLaunched) {
		this.isLaunched = isLaunched;
	}

	@Override
	public String toString() {
		return "Module [name=" + name + ", user=" + professor + ", group=" + group  + ", cours="
				+ cours + ", exams=" + exams + "]";
	}

	



}
