package com.example.pizza;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Zamowienia extends Zamowienie {
	
	
	String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public ArrayList<String>  zwrocProd() {
		
		ArrayList<String> prod = new ArrayList<String>();
		for (int i = 0; i < this.getZamowienie().size(); i++) {
			
		   prod.add(this.getZamowienie().get(i).getNazwa() +" Cena: "+ this.getZamowienie().get(i).getCena());
			
		}
                prod.add("Razem: "+this.obliczCene() + " zł");
		return prod;
		
	}
	

}
