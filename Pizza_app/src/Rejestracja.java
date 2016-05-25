package com.example.pizza;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Rejestracja extends Activity implements OnClickListener,AsyncResponse  {
	
	String email;
	String haslo;
	String hasloPowtorz;
	static Rejestracja rejestAc;
	EditText emailT;
	EditText  hasloT;
	EditText  hasloTPowtorz;
    int idKlient ;
	Button rejestruj;
	PolaczZserwerem nowePolonczenie;
	
	
	
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.rejestracja);
			MainActivity.getActivity().finish();
			emailT = (EditText) findViewById(R.id.emailRejestracja);
			hasloT = (EditText) findViewById(R.id.hasloRejestracja);
			hasloTPowtorz = (EditText) findViewById(R.id.hasloPowtorzRejestracja);
			rejestruj = (Button) findViewById(R.id.buttonRejestruj);
			rejestruj.setOnClickListener(this);
			rejestAc = this;
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
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			
			switch (arg0.getId()) {
	        case  R.id.buttonRejestruj: {
	       
	        	email = emailT.getText().toString();
	        	haslo = hasloT.getText().toString();
	        	hasloPowtorz = hasloTPowtorz.getText().toString();
	        	rejestruj();
	        	
	            break;
	        }
	        case  R.id.buttonRejestrujWroc: {
	        	
	        	
	            break;
	        }
	        
			
			}}
		
		private void rejestruj(){
			
			
			
			if(walidacja())
			{
				
				
				wyslijZapytanie();
			
			}
			else {
				
				//toast bledne dane
				
				Toast.makeText(this, "Walidacja zla",
	                    Toast.LENGTH_LONG).show();
			}
		}
		
		private boolean walidacja() {
			
		 boolean czyDobre = true;
			
		 if(email == null || email.isEmpty()) czyDobre = false;
		 if(haslo == null || haslo.isEmpty()) czyDobre = false;
		 if(hasloPowtorz == null || hasloPowtorz.isEmpty()) czyDobre = false;
		 if(!haslo.equals(hasloPowtorz)) czyDobre = false;
                 if(!isValidEmailAddress(email)) 
                 {
                 czyDobre=false;
		  Toast.makeText(this, "Zły adres email",
	                    Toast.LENGTH_LONG).show();
                 }
			return czyDobre;
		}
                
                 public boolean isValidEmailAddress(String email) {
           String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
           java.util.regex.Matcher m = p.matcher(email);
           return m.matches();
    }

        public void wyslijZapytanie() {
		
        	 PolaczZserwerem polaczenie = new PolaczZserwerem(this);
     		 polaczenie.newWst(this);
             String sampleURL = "http://pizzaserwer-bgmaan.rhcloud.com/mavenproject1-1.0-SNAPSHOT/rest/person/rejestruj";
             polaczenie.wst.dodajParametryDanych("email", email);
             polaczenie.wst.dodajParametryDanych("haslo", haslo);
           
         
             
            
             try {
             	
             	
             	
                 
             	
             	polaczenie.wyslijZapytanie(1, sampleURL);
             	
             	
             	
             	
             	
                  
             } catch (Exception e) {
             	e.printStackTrace();
             }
     		}
     		
         
        public static Rejestracja getActivity() 
        {
        		
           return  rejestAc ;  
         }
    	
         
		

		public int getIdKlient() {
			return idKlient;
		}

		@Override
		public void setJso(JSONObject jso) {
			Intent intent;
			 //przejdz do menu
			JSONObject json = jso;
			try{  
				// idKlient = Integer.parseInt(json.getString("id"));
		                 
                                if(jso.getString("czyPrawidlowy").equals("0")) {
                                    Toast.makeText(this, "Email zajęty",
	                    Toast.LENGTH_LONG).show();
                                }
		        
                                else {
                                
                                
		        	intent = new Intent(this, GlowneMenu.class);
		        	intent.putExtra("idKlient",json.getString("id")) ;
		        	intent.putExtra("idAdres","0" );
				    startActivity(intent);
		    
                                }
		         
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
		
			
		}
       

}
