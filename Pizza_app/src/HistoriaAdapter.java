package com.example.pizza;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class HistoriaAdapter  extends ArrayAdapter<Zamowienia> implements OnClickListener {

	
	private final Context kontekst;
	private final ArrayList<Zamowienia> zamowienia;
	
	
	 public HistoriaAdapter(Context kontekst, ArrayList<Zamowienia> zamowienia) {
			
		 super(kontekst, R.layout.his_produkt, zamowienia);
		 this.kontekst= kontekst;
		 this.zamowienia = zamowienia;
		 
		 
	}
	 
	 @Override
	  public View getView(final int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) kontekst
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.his_produkt, parent, false);
	    TextView status = (TextView) rowView.findViewById(R.id.status);
	    
	    Spinner listaprod = (Spinner) rowView.findViewById(R.id.hist_spinner);
	   
	    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(kontekst,
	    		android.R.layout.simple_spinner_item, zamowienia.get(position).zwrocProd());
	    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    listaprod.setAdapter(spinnerArrayAdapter);
	    String statusZ = zamowienia.get(position).getStatus();
	    if(statusZ.equals("0")){
	    status.setText("Przyjęte");
	    }
            else if(statusZ.equals("1")) {
                 status.setText("Wysłane");
            }
	    return rowView;
	  }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


     
}
