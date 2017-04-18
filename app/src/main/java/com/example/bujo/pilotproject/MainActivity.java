package com.example.bujo.pilotproject;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
//import org.jetbrains.annotations.Contract;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.params.BasicHttpParams;

import static com.example.bujo.pilotproject.R.styleable.View;


public class MainActivity extends AppCompatActivity {
    private Button signInButton;
    private EditText emailId, password;
    private TextInputLayout inputLayoutPassword, inputLayoutEmailid;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeWidgets();
       // emailId.setText("");
        //password.setText("");
        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                myFancyMethod();
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //  @Override
    private void initializeWidgets() {
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.inputLayoutPassword);
        inputLayoutEmailid = (TextInputLayout) findViewById(R.id.inputLayoutPassword);
        password = (EditText) findViewById(R.id.password);
        emailId = (EditText) findViewById(R.id.email);
        signInButton = (Button) findViewById(R.id.button);
    }
    //@Contract("null -> false");
   // public static boolean isCharSequence(@Nullable PsiType type) {
     //   return type != null && InheritanceUtil.isInheritor(type, JAVA_LANG_CHAR_SEQUENCE);
    //}

    private void myFancyMethod() {
        boolean isValid = true;

        //for email chk
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailId.getText().toString());

        //for password chk
        Pattern letter = Pattern.compile("[A-Z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasLetter = letter.matcher(password.getText().toString());
        Matcher hasDigit = digit.matcher(password.getText().toString());
        Matcher hasSpecial = special.matcher(password.getText().toString());

        if (emailId.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            inputLayoutEmailid.setError(getString(R.string.blank_emailOrpassword));
            isValid = false;
        }
        else if(!matcher.matches())
        {
            inputLayoutEmailid.setError(getString(R.string.email_incorrect));
            isValid = false;

        }
       //else  if (!Patterns.EMAIL_ADDRESS.matcher(emailId.getText().toString()).matches())‌​
        //{
          //  inputLayoutEmailid.setError(getString(R.string.email_incorrect));
          //  isValid = false;
        //}
        else if (password.getText().toString().trim().length() < 8) {
            inputLayoutPassword.setError(getString(R.string.password_length));
            isValid = false;
        }
        else if (!(hasLetter.find() && hasDigit.find() && hasSpecial.find())) {
            inputLayoutPassword.setError(getString(R.string.password_uniqueCharlength));
            isValid = false;
        }
        else {
            isValid = true;
        }


        if (isValid) {
            inputLayoutPassword.setErrorEnabled(false);
            inputLayoutEmailid.setErrorEnabled(false);
            Toast.makeText(getApplicationContext(), R.string.signIn_Success, Toast.LENGTH_LONG).show();
            Intent toy = new Intent(MainActivity.this, HomePageActivity.class);
            startActivity(toy);
        }

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
    //})


    class GetUserInfo extends AsyncTask<Void, Void, String> {


        String jsonString = "";

        @Override
        protected String doInBackground(Void... params) {

            DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            InputStream is = null;
            String result = "";

            HttpResponse response = null;
            HttpEntity entity = null;

            try {


                // FIRING THE URL
                System.out.println(" Check innnn   1: ");
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpget = new HttpGet("http://192.168.1.102:8080/passws/biodata/P-101");
                System.out.println(" Check innnn   2: ");


                // RECEIVING THE OUTPUT


                System.out.println(" Check innnn   3: ");
                response = httpclient.execute(httpget);
                System.out.println(" Check innnn   4: ");
                entity = response.getEntity();
                is = entity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                System.out.println(" Result : " + result);
                // PARSING THE JSON DATA


            } catch (ClientProtocolException e) {

                e.printStackTrace();
            } catch (IOException ex) {
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


            try {
                jArray = new JSONArray(res);

                for (int i = 0; i < jArray.length(); i++) {
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


            if (res.equals("Y")) {

            } else
                Toast.makeText(getApplicationContext(),
                        "String retrived:" + res, Toast.LENGTH_SHORT).show();
        }
    }


}
