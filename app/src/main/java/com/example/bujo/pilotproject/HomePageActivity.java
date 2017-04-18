package com.example.bujo.pilotproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        NavigationView navigationView = (NavigationView) findViewById(R.id.left_drawer);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        //toolbar icon and color
        //      mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#e8eaf6'>" + "Pilot Book" + "</font>"));
        // Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_menu_black_24dp,  null);
        // drawable = DrawableCompat.wrap(drawable);
        // DrawableCompat.setTint(drawable, Color.WHITE);
        //  getSupportActionBar().setHomeAsUpIndicator(drawable);

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Toast.makeText(this," ff!",Toast.LENGTH_SHORT).show();
        String title = (String) item.getTitle();
        Toast.makeText(HomePageActivity.this, title + " Selected!", Toast.LENGTH_SHORT).show();
        if (title.equals("Biodata")) {
            Toast.makeText(HomePageActivity.this, title, Toast.LENGTH_SHORT).show();
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Intent toy = new Intent(HomePageActivity.this, FillBiodata.class);
            startActivity(toy);
        } else if (title.equals("View_Biodata")) {
            Intent toy = new Intent(HomePageActivity.this, ViewBiodata.class);
            startActivity(toy);
        } else if (title.equals("Logout")) {
            Intent toy = new Intent(HomePageActivity.this, MainActivity.class);
            startActivity(toy);
        } else if (title.equals("Write_Us")) {
           // Intent contactIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", getString(R.string.email_to), null));
            //contactIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
            //startActivity(Intent.createChooser(contactIntent, getString(R.string.email_chooser)));
        } else if (title.equals("Call_Us")) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:+918130994198"));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
               // return TODO;
                ActivityCompat.requestPermissions(HomePageActivity.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_REQUEST_CODE);

            }
            else {
                startActivity(callIntent);
                 }
           }
        return true;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeCall("123");
                }
                break;
        }
    }
    public void makeCall(String s)
    {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + s));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

            requestForCallPermission();

        } else {
            startActivity(intent);

        }
    }
    public void requestForCallPermission()
    {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE))
        {
        }
        else {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_REQUEST_CODE);
        }
    }


    //function for click of drawer layout opening and closing
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this," Selected1!",Toast.LENGTH_SHORT).show();

        if(mToggle.onOptionsItemSelected(item)){
            mDrawerLayout.requestLayout();
            // findViewById(R.id.fillyourbiodata).setOnClickListener(new View);
            return true;}
        return super.onOptionsItemSelected(item);
    }


    public boolean onMenuItemClick(MenuItem item)
    {
        Toast.makeText(this," Selected2!",Toast.LENGTH_SHORT).show();

        String title = (String) item.getTitle();
        Toast.makeText(HomePageActivity.this,title+" Selected!",Toast.LENGTH_SHORT).show();
        if(title.equals("fillyourbiodata"))
        {
            Toast.makeText(HomePageActivity.this,title,Toast.LENGTH_SHORT).show();
            Intent toy = new Intent(HomePageActivity.this,FillBiodata.class);
            startActivity(toy);
        }
        else if(title.equals("viewbiodata"))
        {
            Intent toy = new Intent(HomePageActivity.this,ViewBiodata.class);
            startActivity(toy);
        }
        return true;
    }
}
