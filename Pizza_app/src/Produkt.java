package com.example.pizza;

public class Produkt {
	private String id;
	private String nazwa;
	private String opis;
	private String cena;
	private String urlZdjecie;
	
	
	public Produkt(String id, String nazwa,String  cena,String opis,String urlZdjecie) {
		
		this.id = id;
		this.cena = cena;
		this.opis = opis;
		this.urlZdjecie = urlZdjecie;
		this.nazwa = nazwa;

	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getNazwa() {
		return nazwa;
	}


	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}


	public String getOpis() {
		return opis;
	}


	public void setOpis(String opis) {
		this.opis = opis;
	}


	public String getCena() {
		return cena;
	}


	public void setCena(String cena) {
		this.cena = cena;
	}


	public String getUrlZdjecie() {
		return urlZdjecie;
	}


	public void setUrlZdjecie(String urlZdjecie) {
		this.urlZdjecie = urlZdjecie;
	}
	
	
	
	
	

}
