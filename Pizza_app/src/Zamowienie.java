package com.example.pizza;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.json.JSONObject;

public class Zamowienie {

	
	
	private ArrayList<Produkt> zamowienie;
	
	public Zamowienie () {
		zamowienie = new ArrayList<Produkt>();
	}
	
	
	public void dodajProdukt(Produkt pr) {
		zamowienie.add(pr);
	}
	
	public void usunProdukt(int id) {
		
		zamowienie.remove(id);
		
	}
	public void clearZam () {
		
		zamowienie.clear();
	}
	
	public JSONObject finalizujZamowienie(int id) {
		
		JSONObject zam = new JSONObject();
		JSONObject tymOb = new JSONObject();
		
		
		
		
		return zam;
		
		
		
	}


	public ArrayList<Produkt> getZamowienie() {
		return zamowienie;
	}


	public void setZamowienie(ArrayList<Produkt> zamowienie) {
		this.zamowienie = zamowienie;
	}
	
	public String obliczCene() {
		float suma = 0;
		for (int i = 0; i < zamowienie.size(); i++) {
			suma =  suma + Float.parseFloat(zamowienie.get(i).getCena());
		}
		String cena = new DecimalFormat("#.##").format(suma) + " zł";
		return cena;
	}
}
