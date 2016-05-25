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
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;



public class PolaczZserwerem {
	
	WebServiceTask wst;
	JSONObject jso;
	private Activity activity;
	AsyncResponse async;
	public PolaczZserwerem(Activity ac){
		activity = ac;
		jso = null;
	}
	
	
	
	public void odbierz(String response) {
        
		   
        try {
             
             jso = new JSONObject(response);
            
           
             
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	
	


	public WebServiceTask getWst() {
		return wst;
	}



	public JSONObject getJso() {
		return jso;
	}

    public void newWst(Activity ac) {
    	 wst = new WebServiceTask(ac);
    }

	public void wyslijZapytanie(int idZapytan,String url) {
		
		
	      
	     
	        wst.execute(new String[] { url }); 
			
	      
        
         
		}
    
    
	
	public class WebServiceTask extends AsyncTask<String, Integer, String> {
		 
		  private Activity activity;
          private ProgressDialog dialog;
          private Context context;
          
		 public WebServiceTask (Activity activity) {
			    
			    this.activity = activity;
	            this.context = activity;
	            this.dialog = new ProgressDialog(activity);
	            this.dialog.setTitle("Czekaj");
	            this.dialog.setMessage("2：");
	            if(!this.dialog.isShowing()){
	                this.dialog.show();
	            }
	        
		 }
		 private ArrayList<NameValuePair> parametry = new ArrayList<NameValuePair>();
	       
		 		public void dodajParametryDanych(String name, String value) {
			 
	            parametry.add(new BasicNameValuePair(name, value));
	        }

		 		protected String doInBackground(String... urls) {
	        	 String url = urls[0];
	             String result = "";
	  
	             HttpResponse response = doResponse(url);
	  
	             if (response == null) {
	            	 
	                 return result;
	             } else {
	  
	                 try {
	                	
	                     result = inputStreamToString(response.getEntity().getContent());
	  
	                 } catch (IllegalStateException e) {
	                	 e.printStackTrace();
	  
	                 } catch (IOException e) {
	                	 e.printStackTrace();
	                 }
	  
	             }
	             
	             return result;
	        }
	 
	 			private HttpResponse doResponse(String url) {
        
	 				HttpClient httpclient = new DefaultHttpClient(getHttpParams());
		
	 				HttpResponse response = null;

        
                HttpPost httppost = new HttpPost(url);
                try {
               	 httppost.setEntity(new UrlEncodedFormEntity(parametry));
               	 response = httpclient.execute(httppost);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                return response;
          }
     
	 			@Override
	 	        protected void onPostExecute(String response) {
	 	             
	 	          odbierz(response);
	 	        
	 	          this.dialog.dismiss();
	 	          ((AsyncResponse) activity).setJso(getJso());  
	 	        }
	 			
	 			private HttpParams getHttpParams() {
        
        HttpParams htpp = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(htpp, 5000);
        HttpConnectionParams.setSoTimeout(htpp, 5000);
         
       
        return htpp;
    }
	 			
	 			//metoda pobieraj�ca strumie� znakow
	 	private String inputStreamToString(InputStream is) {
		 
        String line = "";
        StringBuilder total = new StringBuilder();

        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        try {
            // Read response until the end
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
       	 e.printStackTrace();
        }

       
        
        
        return total.toString();
    }
          }


	
	
	
	

}
