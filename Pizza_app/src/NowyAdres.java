package com.example.pizza;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NowyAdres extends Activity implements AsyncResponse {

	
	EditText imie;
	EditText nazwisko;
	EditText miasto;
	EditText ulica;
	EditText telefon;
	String idKlient;
	public Activity activity; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	   

	   // setContentView(R.layout.activity_database_list);
	  
	}
	public NowyAdres(Activity activ, String idKlient ) {
		
		this.activity = activ;
	    this.idKlient = idKlient;
	   // ((Activity) this.activity).setContentView(R.layout.nowy_adres);
	   
		imie = (EditText) this.activity.findViewById(R.id.adres_editImie);
		nazwisko = (EditText) this.activity.findViewById(R.id.adres_editNaziwsko);
		miasto = (EditText) this.activity.findViewById(R.id.adres_editMiasto);
		ulica = (EditText) this.activity.findViewById(R.id.adres_editUlica);
		telefon = (EditText) this.activity.findViewById(R.id.adres_editTelefon);
		
	}
	
	
	

	
    public void dodajAdres() {
    	 
    	 PolaczZserwerem polaczenie = new PolaczZserwerem(this);
 		 polaczenie.newWst(this);
         String sampleURL = "http://pizzaserwer-bgmaan.rhcloud.com/mavenproject1-1.0-SNAPSHOT/rest/person/dodajAdres";
         polaczenie.wst.dodajParametryDanych("idKlient", idKlient);
         polaczenie.wst.dodajParametryDanych("imie", imie.getText().toString());
         polaczenie.wst.dodajParametryDanych("nazwisko", nazwisko.getText().toString());
         polaczenie.wst.dodajParametryDanych("miasto", miasto.getText().toString());
         polaczenie.wst.dodajParametryDanych("ulica", ulica.getText().toString());
         polaczenie.wst.dodajParametryDanych("telefon", telefon.getText().toString());
    	
    	 try {

          	polaczenie.wyslijZapytanie(1, sampleURL);
      
          } catch (Exception e) {
          	e.printStackTrace();
          }
    	 
     }

	@Override
	public void setJso(JSONObject jso) {
		
	
		
		 JSONObject json = jso;
         
		 View myView = findViewById(R.id.nowy_adres);
         ViewGroup parent = (ViewGroup) myView.getParent();
         parent.removeView(myView);}
	     
}
