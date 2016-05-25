package com.example.pizza;


	

	import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
	import android.content.Intent;
	import android.os.Bundle;
	import android.view.Menu;
	import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MojAdres  extends Activity implements OnClickListener,AsyncResponse  {
		Button Edytuj;
		Button rejestracja;
		Button brakAdresuWroc;
		String idKlient;
		String idAdres;
		Button adresDodaj;
		Button adresBrakWroc;
		Button adresJestWroc;
		Button adresJestEdytuj;
                Button edytWroc;
                Button edytujWys;
		TextView imie;
		TextView nazwisko;
		TextView miasto;
		TextView ulica;
		TextView telefon;
		Button dodaj;
		Button wroc;
		NowyAdres nowyAdres;
                   EditText eimie ;
		     EditText enazwisko ;
	             EditText emiasto ;
		     EditText eulica ;
		     EditText etelefon ;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.glowny_adres);
			Intent intent = getIntent();
			idKlient = intent.getStringExtra("idKlient");
			
			idAdres = GlowneMenu.getActivity().getIdAdres();
			
			
			if(!idAdres.equals("0")) {
			 
             pobierzAdres();
             
			}
			
			else {
				
				 dodaj = (Button) findViewById(R.id.zapisz_adres);
				 wroc = (Button) findViewById(R.id.wroc_adres);
				 LinearLayout myLayout = (LinearLayout)findViewById(R.id.linearLayout1);
				 View hiddenInfo = getLayoutInflater().inflate(R.layout.brak_adresu, myLayout, false);
	             myLayout.addView(hiddenInfo);
				 
				 adresBrakWroc = (Button) findViewById(R.id.buttonbrakAdresWroc);
				 adresDodaj = (Button) findViewById(R.id.button_adresDodaj);
				 adresDodaj.setOnClickListener(this);
				 adresBrakWroc.setOnClickListener(this);
			}
			
			Toast.makeText(this, idKlient + idAdres,
	                Toast.LENGTH_LONG).show();
		}
		
		
		
		
		@Override
		public void setJso(JSONObject jso) {
			
			JSONObject json = jso;
			
			
			try {
                            
                        if(json.has("Edyt")) {
                            
                            View myView = findViewById(R.id.edytuj_adres);
                            ViewGroup parent = (ViewGroup) myView.getParent();
                            parent.removeView(myView);
                            pobierzAdres();
                            
                            
                        }    
			
                       
                        if(json.has("Dod")) {
                            
                            
                            View myView = findViewById(R.id.nowy_adres);
	                    ViewGroup parent = (ViewGroup) myView.getParent();
	                    parent.removeView(myView);
                            GlowneMenu.getActivity().idAdres = json.getString("Dod");
                            idAdres =json.getString("Dod");
                            pobierzAdres();
                            
                            
                        }   
                        
                        
			if(idAdres.equals("0"))	{
				
				GlowneMenu.getActivity().idAdres = jso.getString("idAdres");
				Toast.makeText(this, GlowneMenu.getActivity().idAdres,
		                Toast.LENGTH_LONG).show();
			}
                        
			else {
                            
                            
			imie.setText(jso.getString("imie"));	
			nazwisko.setText(jso.getString("nazwisko"));	
			miasto.setText(jso.getString("miasto"));	
			ulica.setText(jso.getString("ulica"));
			telefon.setText(jso.getString("telefon"));
                        
                        }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
		
			
		}
	    
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.two, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			int id = item.getItemId();
			if (id == R.id.action_settings) {
				return true;
			}
			return super.onOptionsItemSelected(item);
		}
	
		
		public void pobierzAdres() {
			
			
			LinearLayout hiddenLayout = (LinearLayout)findViewById(R.id.wczytaj_adres);
			LinearLayout myLayout = (LinearLayout)findViewById(R.id.linearLayout1);
			View hiddenInfo = getLayoutInflater().inflate(R.layout.wczytaj_adres, myLayout, false);
            myLayout.addView(hiddenInfo);
            imie = (TextView) findViewById(R.id.adres_Imie);
            nazwisko = (TextView) findViewById(R.id.adres_nazwisko);
            miasto = (TextView) findViewById(R.id.adres_miasto);
            ulica = (TextView) findViewById(R.id.adres_ulica);
            telefon = (TextView) findViewById(R.id.adres_telefon);

            adresJestWroc = (Button) findViewById(R.id.button_adres_wroc);
            adresJestEdytuj = (Button) findViewById(R.id.button_adres_edytuj);
            adresJestWroc.setOnClickListener(this);
            adresJestEdytuj.setOnClickListener(this);
            
			
	     PolaczZserwerem polaczenie = new PolaczZserwerem(this);
             polaczenie.newWst(this);
             String sampleURL = "http://pizzaserwer-bgmaan.rhcloud.com/mavenproject1-1.0-SNAPSHOT/rest/person/pobierzAdres";
             polaczenie.wst.dodajParametryDanych("id", idKlient);
             
         
             
            
             try {

             	polaczenie.wyslijZapytanie(1, sampleURL);
         
             } catch (Exception e) {
             	e.printStackTrace();
             }
	
		}
		
		
		private void edytujAdres() {
			
		     
	         PolaczZserwerem polaczenie = new PolaczZserwerem(this);
	         polaczenie.newWst(this);
	         String sampleURL = "http://pizzaserwer-bgmaan.rhcloud.com/mavenproject1-1.0-SNAPSHOT/rest/person/edytujAdres";
	         polaczenie.wst.dodajParametryDanych("Id", idKlient);
	         polaczenie.wst.dodajParametryDanych("imie", eimie.getText().toString());
	         polaczenie.wst.dodajParametryDanych("nazwisko", enazwisko.getText().toString());
	         polaczenie.wst.dodajParametryDanych("miasto", emiasto.getText().toString());
	         polaczenie.wst.dodajParametryDanych("ulica", eulica.getText().toString());
	         polaczenie.wst.dodajParametryDanych("telefon", etelefon.getText().toString());
	    	
	    	 try {

	          	polaczenie.wyslijZapytanie(1, sampleURL);
	      
	          } catch (Exception e) {
	          	e.printStackTrace();
	          }
			
			
			
		}
		
		public void dodajAdres() {
			EditText eimie = (EditText) findViewById(R.id.adres_editImie);
			EditText enazwisko = (EditText) findViewById(R.id.adres_editNaziwsko);
			EditText emiasto = (EditText) findViewById(R.id.adres_editMiasto);
			EditText eulica = (EditText) findViewById(R.id.adres_editUlica);
			EditText etelefon = (EditText) findViewById(R.id.adres_editTelefon);
	    	 PolaczZserwerem polaczenie = new PolaczZserwerem(this);
	 		 polaczenie.newWst(this);
	         String sampleURL = "http://pizzaserwer-bgmaan.rhcloud.com/mavenproject1-1.0-SNAPSHOT/rest/person/dodajAdres";
	         polaczenie.wst.dodajParametryDanych("idKlient", idKlient);
	         polaczenie.wst.dodajParametryDanych("imie", eimie.getText().toString());
	         polaczenie.wst.dodajParametryDanych("nazwisko", enazwisko.getText().toString());
	         polaczenie.wst.dodajParametryDanych("miasto", emiasto.getText().toString());
	         polaczenie.wst.dodajParametryDanych("ulica", eulica.getText().toString());
	         polaczenie.wst.dodajParametryDanych("telefon", etelefon.getText().toString());
	    	
	    	 try {

	          	polaczenie.wyslijZapytanie(1, sampleURL);
	      
	          } catch (Exception e) {
	          	e.printStackTrace();
	          }
	    	 
	     }
		@Override
		public void onClick(View arg0) {
			
			switch (arg0.getId()) {
	        case  R.id.button_adresDodaj: {
	       
	        	
	        	 // usun brak adresu
	             View myView = findViewById(R.id.brak_adresu);
	             ViewGroup parent = (ViewGroup) myView.getParent();
	             parent.removeView(myView);
                 //dodaj widok nowy adres
				 LinearLayout myLayout = (LinearLayout)findViewById(R.id.linearLayout1);
				 View hiddenInfo = getLayoutInflater().inflate(R.layout.nowy_adres, myLayout, false);
	             myLayout.addView(hiddenInfo);
	             //nowy adres klasa JSOn
	             Button wrocAdres = (Button) findViewById(R.id.wroc_adres);
	             Button zapisz = (Button) findViewById(R.id.zapisz_adres);
	             zapisz.setOnClickListener(this);
	             wrocAdres.setOnClickListener(this);
	             
		        
	        
	            
	             
	             
	            break;
	        }
	        case  R.id.button_adres_wroc: {
	        	
	             
	        	this.finish();
	        	
	            break;
	        }
	        
	        case  R.id.button_adres_edytuj: {
	        	
	             View myView = findViewById(R.id.wczytaj_adres);
	             ViewGroup parent = (ViewGroup) myView.getParent();
	             parent.removeView(myView);
                 //dodaj widok nowy adres
		     LinearLayout myLayout = (LinearLayout)findViewById(R.id.linearLayout1);
		     View hiddenInfo = getLayoutInflater().inflate(R.layout.edytuj_adres, myLayout, false);
	             myLayout.addView(hiddenInfo);
	             edytujWys = (Button) findViewById(R.id.edit_zapisz_adres);
	             edytWroc = (Button) findViewById(R.id.edit_wroc_adres);
                     edytWroc.setOnClickListener(this);
                     edytujWys.setOnClickListener(this);
                     edytWroc.setOnClickListener(this);	
                     eimie = (EditText) findViewById(R.id.dodaj_editImie);
		     enazwisko = (EditText) findViewById(R.id.dodaj_editNaziwsko);
	             emiasto = (EditText) findViewById(R.id.dodaj_editMiasto);
		     eulica = (EditText) findViewById(R.id.dodaj_editUlica);
		     etelefon = (EditText) findViewById(R.id.dodaj_editTelefon);
                     eimie.setText(imie.getText());
                     enazwisko.setText(nazwisko.getText());
                     emiasto.setText(miasto.getText());
                     eulica.setText(ulica.getText());
                     etelefon.setText(telefon.getText());
	        	
                     
	            break;
	        }
              case  R.id.edit_zapisz_adres: {
	        	
	             
                  edytujAdres();
            	
	        	
	            break;
	        }   
                
                
             case  R.id.buttonbrakAdresWroc: {
	        	
	             
                    
            	    this.finish();
	        	
	            break;
	        }
             case  R.id.edit_wroc_adres: {
	        	
	            View myView = findViewById(R.id.edytuj_adres);
	             ViewGroup parent = (ViewGroup) myView.getParent();
	             parent.removeView(myView);
                    
            	    this.finish();
	        	
	            break;
	        }
	        case  R.id.zapisz_adres: {
	            
	        	 
	        	dodajAdres();
	        	
                        
	        	
	            break;
	        }
                
                
                
                
	        case  R.id.wroc_adres: {
	        	
	             View myView = findViewById(R.id.nowy_adres);
	             ViewGroup parent = (ViewGroup) myView.getParent();
	             parent.removeView(myView);
	             LinearLayout myLayout = (LinearLayout)findViewById(R.id.linearLayout1);
	             myView  = getLayoutInflater().inflate(R.layout.brak_adresu, myLayout, false);
	           
	             myLayout.addView(myView);
	             adresDodaj = (Button) findViewById(R.id.button_adresDodaj);
	             adresBrakWroc = (Button) findViewById(R.id.buttonbrakAdresWroc);
				 adresDodaj.setOnClickListener(this);
				 adresBrakWroc.setOnClickListener(this);
	            break;
	        }
			}
			
			
			
		}
}
