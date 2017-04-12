package com.example.bujo.pilotproject;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import static java.lang.Thread.sleep;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



public class ViewBiodata extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_biodata);

        EditText etfirstname = (EditText) findViewById(R.id.etfirstname);
        new GetBARequest().execute();
    }





    class GetBARequest   extends AsyncTask<Void,Void,String> {


        String jsonString="";

        @Override
        protected String doInBackground(Void... params) {

            DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            InputStream is = null;
            String result = "";

            HttpResponse response = null;
            HttpEntity entity = null;

            try{


                // FIRING THE URL
                System.out.println(" Check innnn   1: " );
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpget = new HttpGet("http://192.168.1.102:8080/passws/biodata/P-101");
                System.out.println(" Check innnn   2: " );


                // RECEIVING THE OUTPUT


                    System.out.println(" Check innnn   3: " );
                    response = httpclient.execute(httpget);
                    System.out.println(" Check innnn   4: " );
                    entity = response.getEntity();
                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result=sb.toString();
                System.out.println(" Result : " + result);
                    // PARSING THE JSON DATA




            }catch(ClientProtocolException e)
            {

                e.printStackTrace();
            }catch(IOException ex)
            {
                ex.printStackTrace();
            }

            return result;
        }


        @Override
        protected void onPostExecute(String res) {

            JSONArray jArray = null;
            EditText etfirstname = (EditText) findViewById(R.id.etfirstname);
            EditText etlastname = (EditText) findViewById(R.id.etlastname);
            EditText etfathersname = (EditText) findViewById(R.id.etfathersname);
            EditText etmothersname = (EditText) findViewById(R.id.etmothersname);
            EditText etdob = (EditText) findViewById(R.id.etdob);
            EditText etpob = (EditText) findViewById(R.id.etpob);
            EditText etnationality = (EditText) findViewById(R.id.etnationality);
            EditText etoccupation = (EditText) findViewById(R.id.etoccupation);


            try{
            jArray = new JSONArray(res);

            for(int i=0; i<jArray.length();i++)
            {
                JSONObject jo = jArray.getJSONObject(i);
                System.out.println(" Res : " + jo.getString("firstname"));
                etfirstname.setText(jo.getString("firstname"));
                etlastname.setText(jo.getString("lastname"));
                etfathersname.setText(jo.getString("fathersname"));
                etmothersname.setText(jo.getString("mothersname"));
                etdob.setText(jo.getString("dob"));
                etpob.setText(jo.getString("pob"));
                etnationality.setText(jo.getString("nationality"));
                etoccupation.setText(jo.getString("occupation"));


            }
            } catch (JSONException e) {
                e.printStackTrace();
            }



            if(res.equals("Y"))
            {

            }
            else
                Toast.makeText(getApplicationContext(),
                        "String retrived:" + res, Toast.LENGTH_SHORT).show();
        }
    }



}
