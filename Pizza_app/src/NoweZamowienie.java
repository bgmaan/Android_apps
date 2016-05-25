package com.example.pizza;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import static com.example.pizza.GlowneMenu.glowneMenu;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NoweZamowienie  extends Activity implements OnClickListener,AsyncResponse {
	ArrayList<Produkt> produkty;
	Zamowienie noweZamowienie;
	Context glowny = this;
	Button dalej;
        Button wroc;
	Spinner spinnerLokal;
        ArrayList <Lokal> lokale;
        ArrayList <String> lokaleS;
        ArrayAdapter<String> spinadapter;
       String idLokalu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.zamowienie_glowne);
		lokale = new ArrayList<Lokal>();
                lokaleS = new ArrayList<String>();
                wroc = (Button) findViewById(R.id.button_prodwroc);
                wroc.setOnClickListener(this);
		spinnerLokal = (Spinner) findViewById(R.id.spinner_lokale);

		noweZamowienie =  GlowneMenu.getActivity().getNoweZamowienie();
		produkty = new ArrayList<Produkt>();
		dalej = (Button) findViewById(R.id.button_proddalej);
		dalej.setOnClickListener(this);
		pobierzListe();

      	
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

private void pobierzLokal() {
	
	PolaczZserwerem polaczenie = new PolaczZserwerem(this);
	polaczenie.newWst(this);
        String sampleURL = "http://pizzaserwer-bgmaan.rhcloud.com/mavenproject1-1.0-SNAPSHOT/rest/person/pobierzLokale";
	  try {

        	polaczenie.wyslijZapytanie(1, sampleURL);
    
        } catch (Exception e) {
        	e.printStackTrace();
        }
	 
	
}

private void dodajSpinLok() {
    
    for (int i = 0; i < lokale.size(); i++) {
        lokaleS.add(lokale.get(i).nazwa);
    }
}

   private void pobierzListe() {
	   
	   
	   //połącz z 
	   PolaczZserwerem polaczenie = new PolaczZserwerem(this);
	   polaczenie.newWst(this);
           String sampleURL = "http://pizzaserwer-bgmaan.rhcloud.com/mavenproject1-1.0-SNAPSHOT/rest/person/pobierzProdukty";
       
       try {

        	polaczenie.wyslijZapytanie(1, sampleURL);
    
        } catch (Exception e) {
        	e.printStackTrace();
        }
   
	    
	   
		
	   
   }
   @Override
	public void setJso(JSONObject jso) {
	   
	  
	   
		try {
		    JSONObject json = jso;
		    JSONArray jarray ;
		    
		    if(jso.has("lokale")) {
		    jarray = jso.getJSONArray("lokale");
		    for (int i = 0; i < jarray.length(); i++) {
		    	
		    	json = jarray.getJSONObject(i);
		    	lokale.add(new Lokal(Integer.toString(json.getInt("Id")),json.getString("Nazwa")));
				
			
			    
		    	}
                    dodajSpinLok();
                    spinadapter = new ArrayAdapter<String>(this,
				 android.R.layout.simple_spinner_item, lokaleS);
                     spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spinnerLokal.setAdapter(spinadapter);
		    }
		    else {
		    	
		    	jarray = jso.getJSONArray("lista");
		        for (int i = 0; i < jarray.length(); i++) {
		    	json = jarray.getJSONObject(i);
				Produkt pro = new Produkt(json.getString("Id"),json.getString("Nazwa"),json.getString("Cena"),json.getString("Opis"),"");
			
			    produkty.add(pro);
		    	
				
	        
		    }
                        pobierzLokal();
                    }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    final ListView myLayout = (ListView) findViewById(R.id.list);
	   	
	   	
	    ZamowienieAdapter adapter = new ZamowienieAdapter(myLayout.getContext(), produkty,noweZamowienie);
	    ((ListView) myLayout).setAdapter(adapter);
		
		
		
        }	
		
	
		
private void getIdLokal() {
    String idSpin=   spinnerLokal.getSelectedItem().toString();
   
    
    for (int i = 0; i < lokale.size(); i++) {
        
        if(lokale.get(i).nazwa.equals(idSpin)) {
            idLokalu = lokale.get(i).id;
        }
    }
 
    
    
}




@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	
	
	switch (v.getId()) {
    case  R.id.button_proddalej: {
   
    
    	Intent intent = new Intent(this, TwojeZamowienie.class);
                getIdLokal();
		intent.putExtra("id",idLokalu );
                intent.putExtra("nazwa",spinnerLokal.getSelectedItem().toString());
		startActivity(intent);

         
        break;
    }
    case  R.id.button_prodwroc: {
    	
         
    	this.finish();
    	
        break;
    }
	
}}









}
