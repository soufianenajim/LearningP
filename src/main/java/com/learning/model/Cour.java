package com.learning.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cour")
public class Cour extends Historized {

	private static final long serialVersionUID = -8858004000210805400L;

	@Column(name = "name", length = 100)
	private String name;

	@Lob
	private String content;
	
	private boolean isLaunched;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "module_id")
	private Module module;

	@OneToMany(mappedBy = "cour", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<ProgressionCour> progressionCours;

	@OneToMany(mappedBy = "cour", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Exercices> exercices;


	public Cour() {
		super();
	}

	public Cour(String name) {
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

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}



	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	


	

	public List<Exercices> getExercices() {
		return exercices;
	}

	public void setExercices(List<Exercices> exercices) {
		this.exercices = exercices;
	}
	
	

	
	public boolean isLaunched() {
		return isLaunched;
	}

	public void setLaunched(boolean isLaunched) {
		this.isLaunched = isLaunched;
	}

	@Override
	public String toString() {
		return "Cour [name=" + name + ", content=" + content + ", module=" + module + ", exercices" + exercices + "]";
	}

}
