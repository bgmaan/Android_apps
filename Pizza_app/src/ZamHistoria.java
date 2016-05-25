package com.example.pizza;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ZamHistoria extends Activity implements OnClickListener,AsyncResponse{
	Button odswierz;
	ArrayList<Zamowienia> zam = new ArrayList<Zamowienia>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.status_zamowien);
		 Button odswierz = (Button) findViewById(R.id.hits_odswierz);
                  Button wroc = (Button) findViewById(R.id.hits_wroc);
                  wroc.setOnClickListener(this);
		 odswierz.setOnClickListener(this);
         
	   	pobierzHist();
	   	
		
		
      	
	}
	@Override
	public void setJso(JSONObject jso) {
		// TODO Auto-generated method stub
		
		
		JSONArray jarray ;
		JSONObject json;
		JSONArray listaProd;
		JSONObject prod;
		try {
			jarray = jso.getJSONArray("lista");
			for (int i = 0; i < jarray.length(); i++) {
		    	json = jarray.getJSONObject(i);
		    	listaProd = json.getJSONArray("LisProd");
				Zamowienia zm = new Zamowienia();
				zm.setStatus(json.getString("Status"));
				for(int j = 0; j < listaProd.length(); j++) {
				prod = listaProd.getJSONObject(j);
		    	zm.dodajProdukt(new Produkt(prod.getString("IdProduktu"),prod.getString("Nazwa"),prod.getString("Cena"),"",""));
				}
				zam.add(zm);
	        
		    }
			
			
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final ListView myLayout = (ListView) findViewById(R.id.hist_lista);
	    HistoriaAdapter adapter = new HistoriaAdapter(myLayout.getContext(),zam);
	    ((ListView) myLayout).setAdapter(adapter);
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		
		switch (v.getId()) {
        case  R.id.hits_odswierz: {
        	zam = new ArrayList<Zamowienia>();
        	pobierzHist();
             
             
            break;
        }
             
            case  R.id.hits_wroc: {
        	this.finish();
             
            break;
            
        }}
		
	}

  public void pobierzHist() {
	  
	  PolaczZserwerem polaczenie = new PolaczZserwerem(this);
	   polaczenie.newWst(this);
       String sampleURL = "http://pizzaserwer-bgmaan.rhcloud.com/mavenproject1-1.0-SNAPSHOT/rest/person/pobierzHist";
       polaczenie.wst.dodajParametryDanych("id", GlowneMenu.getActivity().getIdKlient());
      
      try {

       	polaczenie.wyslijZapytanie(1, sampleURL);
   
       } catch (Exception e) {
       	e.printStackTrace();
       }
  
	  
	  
  }
	
}
