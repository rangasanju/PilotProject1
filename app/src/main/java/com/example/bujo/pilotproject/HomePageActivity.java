package com.example.bujo.pilotproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#e8eaf6'>"+"Pilot Book"+"</font>"));

        NavigationView navigationView = (NavigationView) findViewById(R.id.left_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu_black_24dp, null);
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Toast.makeText(this," Selected!",Toast.LENGTH_SHORT).show();
        String title = (String) item.getTitle();
        Toast.makeText(HomePageActivity.this,title+" Selected!",Toast.LENGTH_SHORT).show();
        if(title.equals("Fill Biodata"))
        {
            Toast.makeText(HomePageActivity.this,title,Toast.LENGTH_SHORT).show();

            Intent toy = new Intent(HomePageActivity.this,FillBiodata.class);
            startActivity(toy);
        }
        else if(title.equals("View Biodata"))
        {

            Intent toy = new Intent(HomePageActivity.this,ViewBiodata.class);
            startActivity(toy);
        }

        return true;
    }




    //function for click of drawer layout opening and closing
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            // findViewById(R.id.fillyourbiodata).setOnClickListener(new View);

            return true;}
        return super.onOptionsItemSelected(item);
    }


    public boolean onMenuItemClick(MenuItem item)
    {
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
