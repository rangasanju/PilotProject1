package com.example.bujo.pilotproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.FileNotFoundException;
import java.io.IOException;

import static com.example.bujo.pilotproject.R.id.imageView;

/**
 * Created by Bujo on 4/18/2017.
 */

public class UploadImage extends AppCompatActivity {


    ImageView ivCamera,ivGallery,ivUpload;
    CameraPhoto cameraPhoto;
    final int CAMERA_REQUEST = 12233;
    final int CAMERA_PERMISSION_REQUEST_CODE = 9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#e8eaf6'>" + "Upload files" + "</font>"));

        setContentView(R.layout.camera_gallery_upload);
        ivCamera = (ImageView) findViewById(R.id.ivCamera);
        ivGallery = (ImageView) findViewById(R.id.ivGallery);
        ivUpload = (ImageView) findViewById(R.id.ivUpload);
        cameraPhoto = new CameraPhoto(getApplicationContext());
        try {
            startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
        }
        cameraPhoto.addToGallery();
        ivCamera.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
               // Intent in = new Intent(CAMERA_SERVICE);
                try {
                    if(checkPermissionForCamera()){
                    startActivityForResult(cameraPhoto.takePhotoIntent(),CAMERA_REQUEST);}
                    else
                    {            Toast.makeText(getApplicationContext(),"fffff", Toast.LENGTH_SHORT).show();
                       // public v
                        // id requestPermissionForCamera(){
                        if (ActivityCompat.shouldShowRequestPermissionRationale(UploadImage.this, Manifest.permission.CAMERA)){
                            Toast.makeText(UploadImage.this, "Camera permission required. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
                        } else {
                            ActivityCompat.requestPermissions(UploadImage.this,new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_REQUEST_CODE);
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public boolean checkPermissionForCamera(){
        int result = ContextCompat.checkSelfPermission(UploadImage.this, Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(getApplicationContext(),"inside onActivityResult", Toast.LENGTH_SHORT).show();
        if(resultCode == RESULT_OK){
            Toast.makeText(getApplicationContext(),"result ok ", Toast.LENGTH_SHORT).show();

            if(requestCode == CAMERA_REQUEST){
                String photoPath = cameraPhoto.getPhotoPath();
                Toast.makeText(getApplicationContext(),"result :: "+photoPath, Toast.LENGTH_SHORT).show();

                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                    ivCamera.setImageBitmap(bitmap); //imageView is your ImageView
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }//end if resultCode    }
    }

}
