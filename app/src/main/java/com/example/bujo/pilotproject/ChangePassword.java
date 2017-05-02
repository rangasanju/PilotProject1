package com.example.bujo.pilotproject;

import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Bujo on 5/2/2017.
 */

public class ChangePassword extends AppCompatActivity  {
    private Button savePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.chng_passwrd_layout);
        savePwd = (Button) findViewById(R.id.saveBtn);

        savePwd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                savePassword();
            }
        });

    }
    private void savePassword(){
        AlertDialog.Builder alert = new AlertDialog.Builder(ChangePassword.this);
       // alert.setTitle("Doctor");
        alert.setMessage("Password saved successfully!");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface alert, int which) {
                // TODO Auto-generated method stub
                  Intent toy = new Intent(ChangePassword.this, HomePageActivity.class);
                startActivity(toy);

                alert.dismiss();
            }
        });
        alert.show();
       //// Toast.makeText(getApplicationContext(), "Password Saved!", Toast.LENGTH_LONG).show();
       // Context c = ChangePassword.this;


       // AlertDialog alertDialog = new AlertDialog.Builder(c).create();

       // alertDialog.setMessage("Password saved successfully!");
       // alertDialog.setButton("OK",new DialogInterface.OnClickListener(){
        //    @Override
          //  public void onClick(DialogInterface dialog,int which)
            //{
              //  Intent toy = new Intent(ChangePassword.this, HomePageActivity.class);
                //startActivity(toy);
            //}
        //});
        //alertDialog.show();
    }

}