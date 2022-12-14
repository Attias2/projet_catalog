/**
 * 
 */
package com.nextu.app.servlets.entities.categorie;

import java.util.HashSet;
import java.util.Set;

import com.nextu.app.servlets.entities.produit.Produit;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * @author ILAN ATTIAS
 *
 */
@Entity
@Table(name = "categorie")
public class Categorie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codeCategorie")
	private Long code;
	@Column(name = "name")
	private String name;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "categorie")
	private Set<Produit> produits;
	
	 public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addProduit(Produit produit) {
	      if (this.produits == null) {
	         this.produits = new HashSet<>();
	      }
	      this.produits.add(produit);
	   }

	   public void removeProduit(Produit produit) {
	      if (this.produits != null)
	    	  produits.remove(produit);
	   }
}
