package com.example.pizza;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class GlowneMenu extends Activity implements OnClickListener {
	Button adres;
	Button zamowienie;
	Button zamowienieHistoria;
        Button wyjdz;
	String idKlient;
	String idAdres;
	Zamowienie noweZamowienie;
	 static GlowneMenu glowneMenu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.glowne_menu);
		glowneMenu = this;
		Intent intent = getIntent();
		noweZamowienie = new Zamowienie();
		idKlient = intent.getStringExtra("idKlient");
		idAdres = intent.getStringExtra("idAdres");
                
		if(Rejestracja.getActivity()!=null) {
		Rejestracja.getActivity().finish();
		}
		if(MainActivity.getActivity()!=null)
		MainActivity.getActivity().finish();
		adres = (Button) findViewById(R.id.adres);
		zamowienie = (Button) findViewById(R.id.nowe);
		adres.setOnClickListener(this);
		zamowienie .setOnClickListener(this);
		zamowienieHistoria = (Button) findViewById(R.id.historia);
                wyjdz = (Button) findViewById(R.id.wyjdz);
                wyjdz.setOnClickListener(this);
		zamowienieHistoria.setOnClickListener(this);
		zamowienie .setOnClickListener(this);
		Toast.makeText(this, ""+idKlient+" adres "+idAdres,
                Toast.LENGTH_LONG).show();
	}
	public static GlowneMenu getActivity() 
    {
       return   glowneMenu;  
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
        case  R.id.adres: {
       
        	Intent intent = new Intent(this, MojAdres.class);
    		intent.putExtra("idKlient",idKlient ) ;
    		startActivity(intent);
        	
            break;
        }
        case  R.id.nowe: {
            
        	Intent intent = new Intent(this, NoweZamowienie.class);
    		
    		startActivity(intent);
        	
            break;
        }
        case  R.id.historia: {
            
        	Intent intent = new Intent(this, ZamHistoria.class);
    		
    		startActivity(intent);
        	
            break;
        }
        case  R.id.wyjdz: {
            
        	this.finish();
        	System.exit(0);
            break;
        }
		
		}}

	public String getIdKlient() {
		return idKlient;
	}

	public String getIdAdres() {
		return idAdres;
	}
	public Zamowienie getNoweZamowienie() {
		return noweZamowienie;
	}
	public void setNoweZamowienie(Zamowienie noweZamowienie) {
		this.noweZamowienie = noweZamowienie;
	}


}
