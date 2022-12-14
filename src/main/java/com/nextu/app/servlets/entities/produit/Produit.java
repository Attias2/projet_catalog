/**
 * 
 */
package com.nextu.app.servlets.entities.produit;
import com.nextu.app.servlets.entities.categorie.Categorie;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
/**
 * @author ILAN ATTIAS
 *
 */

@Entity
@Table(name = "produit")
public class Produit {
	
	@Column(name = "name")
	private String name;
	@Column(name = "prix")
	private double prix;
	@Column(name = "disponible")
	private boolean disponible;
	@Column(name = "quantite")
	private int quantite;
	@JoinColumn(name = "id_categorie")
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	private Categorie categorie;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long code;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public boolean isDisponible() {
		return disponible;
	}
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	
	
}
