package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Ima_priloge database table.
 * 
 */
@Entity
@NamedQuery(name="Ima_priloge.findAll", query="SELECT i FROM Ima_priloge i")
public class Ima_priloge implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="IMA_PRILOGE_IDIMAPRILOGE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IMA_PRILOGE_IDIMAPRILOGE_GENERATOR")
	@Column(name="id_ima_priloge")
	private int idImaPriloge;

	//bi-directional many-to-one association to Prilog
	@ManyToOne
	@JoinColumn(name="id_priloga")
	private Prilog prilog;

	//bi-directional many-to-one association to Sadrzi
	@ManyToOne
	@JoinColumn(name="id_sadrzi")
	private Sadrzi sadrzi;

	public Ima_priloge() {
	}

	public int getIdImaPriloge() {
		return this.idImaPriloge;
	}

	public void setIdImaPriloge(int idImaPriloge) {
		this.idImaPriloge = idImaPriloge;
	}

	public Prilog getPrilog() {
		return this.prilog;
	}

	public void setPrilog(Prilog prilog) {
		this.prilog = prilog;
	}

	public Sadrzi getSadrzi() {
		return this.sadrzi;
	}

	public void setSadrzi(Sadrzi sadrzi) {
		this.sadrzi = sadrzi;
	}

}