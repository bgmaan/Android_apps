package com.example.pizza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,AsyncResponse {

	Button zaloguj;
	Button rejestracja;
    PolaczZserwerem polaczenie;
	EditText haslo;
	EditText login;
	private static final String TAG = "AndroidRESTClientActivity";
	String log;
	String loginS;
	String hasloS;
	JSONObject jso;
	String idKlient;
	String idAdres;
	static MainActivity loginActivity ; 
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loginActivity = this;
		
		
		
		zaloguj = (Button) findViewById(R.id.zaloguj);
		rejestracja = (Button) findViewById(R.id.rejestracja);
		login = (EditText) findViewById(R.id.login);
		haslo = (EditText) findViewById(R.id.haslo);
                login.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        }
    });
                haslo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        }
    });
		zaloguj.setOnClickListener(this);
		rejestracja.setOnClickListener(this);
	}
	public static MainActivity getActivity() 
    {
       return   loginActivity ;  
     }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	public void wejdzRejestracja() {
		Intent intent = new Intent(this, Rejestracja.class);
		startActivity(intent);
	}
	
	public void wejdzMenu() {
		
		Intent intent = new Intent(this, GlowneMenu.class);
		intent.putExtra("idKlient",idKlient ) ;
		intent.putExtra("idAdres",idAdres ) ;
		startActivity(intent);
                this.finish();
	}
	  public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		
		switch (arg0.getId()) {
        case  R.id.zaloguj: {
       
        	loginS = login.getText().toString();
        	hasloS = haslo.getText().toString();
        	
        	sprawdzDane();
        	
            break;
        }
        case  R.id.rejestracja: {
        	
             wejdzRejestracja();
            break;
        }
        
		
		}}
	@Override	
	public void setJso(JSONObject jso) {
		
		this.jso = jso;
		try{  
			idKlient = jso.getString("id");
	        idAdres = jso.getString("idAdres");
	        if(Integer.parseInt(idKlient)>0) {
	        	Toast.makeText(this, "Prawidlowe dane",
	                    Toast.LENGTH_LONG).show();
	        	wejdzMenu();
	        }
	      
	        else
	        {
	        	Toast.makeText(this, "B��dny login lub has�o",
	                    Toast.LENGTH_LONG).show();
	        }
	        
	              
	         
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	
	}
	
	public void sprawdzDane() {
		
		if (loginS=="" || hasloS=="") 
		{
			 Toast.makeText(this, "Prosze wpisac dane",
	                    Toast.LENGTH_LONG).show();
		}
		else
		{
		polaczenie = new PolaczZserwerem(this);
		polaczenie.newWst(this);
        String sampleURL = "http://pizzaserwer-bgmaan.rhcloud.com/mavenproject1-1.0-SNAPSHOT/rest/person/zaloguj";
        polaczenie.wst.dodajParametryDanych("login", loginS);
        polaczenie.wst.dodajParametryDanych("haslo", hasloS);
      
    
        
       
        try {
        	
        	
        	
            
        	
        	polaczenie.wyslijZapytanie(1, sampleURL);
        	
        	
        	
        	
        	
             
        } catch (Exception e) {
        	e.printStackTrace();
        }
		}
		
    }
	
	
   
	

}



