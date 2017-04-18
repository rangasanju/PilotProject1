package com.example.bujo.pilotproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import static java.lang.Thread.sleep;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class ViewBiodata extends AppCompatActivity {


    private ProgressBar pb;
    FloatingActionButton fab;
    Button bt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_biodata);
        //getSupportActionBar().setTitle(Html.fromHtml("<font color='#e8eaf6'>"+"Pilot Book"+"</font>"));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        // String myUrl="file://?android_asset/index.html";
        //WebView view = (WebView) this.findViewById(R.id.webView);
        //view.getSettings().setJavaScriptEnabled(true);
        //view.loadUrl(myUrl);
     //  View v = (View)findViewById(R.id.activity_view_biodata);
       //    v.getSettings.setJavaScriptEnabled(true);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"FAB pressed!:", Toast.LENGTH_SHORT).show();
            }
        }
        );

        bt = (Button) findViewById(R.id.uploadButton);
        bt.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Toast.makeText(getApplicationContext(), "upload pressed", Toast.LENGTH_LONG).show();
                // setContentView(R.layout.camera_gallery_upload);
                Intent toy = new Intent(ViewBiodata.this, UploadImage.class);
                startActivity(toy);            }
        }
        );

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



    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            postData(params[0]);
            return null;
        }

        protected void onPostExecute(Double result) {

            pb.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "command sent",
                    Toast.LENGTH_LONG).show();
        }

        protected void onProgressUpdate(Integer... progress) {
            pb.setProgress(progress[0]);
        }

        public void postData(String valueIWantToSend) {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(
                    "http://users.aber.ac.uk/bym1/group/androidto.php");

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("firstname",
                        valueIWantToSend));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        }

    }


}
