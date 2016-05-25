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
import android.widget.TextView;
import android.widget.Toast;

public class ZamowienieAdapter extends ArrayAdapter<Produkt> implements OnClickListener{
	
	
	private final Context kontekst;
	private final ArrayList<Produkt> produkty;
	private final Zamowienie zamowienie;
	
	 public ZamowienieAdapter(Context kontekst, ArrayList<Produkt> produkty,Zamowienie zamowienie) {
		
		 super(kontekst, R.layout.produkt_wczytaj, produkty);
		 this.kontekst= kontekst;
		 this.produkty= produkty;
		 this.zamowienie = zamowienie;
	}


	
	
	
	 @Override
	  public View getView(final int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) kontekst
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.produkt_wczytaj, parent, false);
	    TextView nazwa = (TextView) rowView.findViewById(R.id.produkt_nazwa);
	    TextView opis = (TextView) rowView.findViewById(R.id.produkt_opis);
	    TextView cena= (TextView) rowView.findViewById(R.id.produkt_cena);
	    Button dodaj = (Button) rowView.findViewById(R.id.produkt_dodaj);
	    nazwa.setText(produkty.get(position).getNazwa());
	    opis.setText(produkty.get(position).getOpis());
	    cena.setText("Cena "+produkty.get(position).getCena() +" zł");
	    dodaj.setOnClickListener(new OnClickListener() {
	    
            @Override
            public void onClick(View arg0) {
            	
            	
            	
                if(zamowienie.getZamowienie().size()-1>=10) {
                	Toast.makeText(kontekst, " Zamowienie może mieć max 10 produktów " ,
 	                       Toast.LENGTH_LONG).show();
            	}
            	else
            	{
            	zamowienie.dodajProdukt(produkty.get(position));
            	Toast.makeText(kontekst, " Dodałeś nowy produkt! " +Integer.toString(zamowienie.getZamowienie().size()-1),
	                       Toast.LENGTH_LONG).show();
            	}
            }
            	
        });
	    return rowView;
	  }





	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
