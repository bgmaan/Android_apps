package com.example.pizza;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TwojeZamowienie extends Activity implements OnClickListener,AsyncResponse {
	ListView myLayout; 
	Zamowienie zamowienie;
	TextView cena;
	Button wroc;
	Button wyslij;
	ZamowienieAdapterKoniec adapter;
	PolaczZserwerem polaczenie;
	Spinner spinnerLokal;
        String idLokal;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twoje_zamowienie_koszyk);
		 
         myLayout = (ListView) findViewById(R.id.zam_lista);
         cena = (TextView) findViewById(R.id.zam_cena);
         wroc = (Button) findViewById(R.id.button_zamwroc);
         wroc.setOnClickListener(this);
         
         wyslij = (Button) findViewById(R.id.button_zamwyslij);
         
         Intent intent = getIntent();
        idLokal = intent.getStringExtra("id");
         String nazwaLokal = intent.getStringExtra("nazwa");
         wyslij.setOnClickListener(this);
		 zamowienie = GlowneMenu.getActivity().getNoweZamowienie();
		 cena.setText("Razem: "+zamowienie.obliczCene() +" zł LOKAL:"+nazwaLokal);
	   
		if(zamowienie.getZamowienie().size()==0) {
		
			wyslij.setEnabled(false);
			
		}
		
		else {
			
			 adapter = new ZamowienieAdapterKoniec(myLayout.getContext(), zamowienie.getZamowienie(),zamowienie,cena,this);
		    ((ListView) myLayout).setAdapter(adapter);
			
		}
		Toast.makeText(this, ""+idLokal + nazwaLokal,
	                    Toast.LENGTH_LONG).show();
	}
	
	public void sprawRoz() {
            
            if(zamowienie.getZamowienie().size()==0) {
		
			wyslij.setEnabled(false);
			
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

	
	private void getIdLokal() {
            
        }
	
	
	
	@Override
	public void setJso(JSONObject jso) {
		
		Toast.makeText(this, "zamowienie wysłane",
                Toast.LENGTH_LONG).show();
		
		zamowienie.clearZam();
        adapter.notifyDataSetChanged();
        finish();
        startActivity(getIntent());
       
        
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		
		switch (v.getId()) {
        case  R.id.button_zamwroc: {
       
        	this.finish();
             
             
            break;
        }
     
		
		
       
		
        case  R.id. button_zamwyslij: {
            
        	wyslij();
        	
        }
		
	}

	
	}
	
	
	
	
	private void wyslij() {
            if(GlowneMenu.getActivity().idAdres.equals("0")) {
                Toast.makeText(this, "Brak Adresu",
                Toast.LENGTH_LONG).show();
            }
            else {
		polaczenie = new PolaczZserwerem(this);
		 polaczenie.newWst(this);
		polaczenie.newWst(this);
		try {
        String sampleURL = "http://pizzaserwer-bgmaan.rhcloud.com/mavenproject1-1.0-SNAPSHOT/rest/person/dodajZamowienie";
        //dodaj id lokalu
        JSONObject oneProd = new JSONObject();
        JSONArray prodArray = new JSONArray();
        oneProd.put("IK",GlowneMenu.getActivity().getIdKlient());
        prodArray.put(oneProd);
        oneProd = new JSONObject();
        oneProd.put("IL", idLokal);
        prodArray.put(oneProd);
		for(int i = 0; i< zamowienie.getZamowienie().size() ;i++) {
		
			oneProd = new JSONObject();
			oneProd.put("Id",zamowienie.getZamowienie().get(i).getId());
			oneProd.put("Cena",zamowienie.getZamowienie().get(i).getCena());
			prodArray.put(oneProd);

	    }
		oneProd = new JSONObject();
		
		oneProd.put("lista", prodArray);
		polaczenie.wst.dodajParametryDanych("zam", oneProd.toString());
		 

	          	polaczenie.wyslijZapytanie(1, sampleURL);
	      
	          } catch (Exception e) {
	          	e.printStackTrace();
	          }
		}
		
        }
	
	
	
	
}
