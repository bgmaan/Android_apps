package com.example.pizza;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ZamowienieAdapterKoniec extends ArrayAdapter<Produkt> implements OnClickListener {
	private final Context kontekst;
	private final ArrayList<Produkt> produkty;
	private final Zamowienie zamowienie;
	private final TextView cena;
        private final TwojeZamowienie twoje;
	public ZamowienieAdapterKoniec(Context kontekst, ArrayList<Produkt> produkty, Zamowienie zamowienie,TextView tx,TwojeZamowienie twoje) {
		super(kontekst, R.layout.produkt_wczytaj, produkty);
		 this.kontekst= kontekst;
		 this.produkty= produkty;
		 this.zamowienie = zamowienie;
		 this.cena = tx;
                 this.twoje = twoje;
	}

	
	
	@Override
	  public View getView(final int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) kontekst
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.produkt_na_zam, parent, false);
	    TextView nazwa = (TextView) rowView.findViewById(R.id.produkt_nazwazam);
	    TextView opis = (TextView) rowView.findViewById(R.id.produkt_opiszam);
	    TextView cenaP = (TextView) rowView.findViewById(R.id.produkt_cenazam);
	    Button usun = (Button) rowView.findViewById(R.id.produkt_usunzam);
	    nazwa.setText(produkty.get(position).getNazwa());
	    opis.setText(produkty.get(position).getOpis());
	    cenaP.setText(produkty.get(position).getCena());
	    usun.setOnClickListener(new OnClickListener() {
	    
          public void onClick(View arg0) {
          	
          	
          	if(zamowienie.getZamowienie().size()>0) {
            zamowienie.usunProdukt(position);
            twoje.sprawRoz();
            notifyDataSetChanged();
            cena.setText("Cena "+zamowienie.obliczCene() +" zł");
          	Toast.makeText(kontekst, " Usuneles produkt ! " +Integer.toString(zamowienie.getZamowienie().size()-1),
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
