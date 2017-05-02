package com.example.bujo.pilotproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.kosalgeek.android.photoutil.PhotoLoader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.example.bujo.pilotproject.R.id.imageView;

/**
 * Created by Bujo on 4/18/2017.
 */

public class UploadImage extends AppCompatActivity {

    ImageView ivCamera,imgSelected,ivGallery,ivUpload;
    private final int IMG_REQUEST = 1;
    private Bitmap bitmap;
    static final int CAM_REQUEST=2;
    //to specify the ath to the php cript available on the localhost
    private String UploadUrl = "http://10.0.2.2/PilotProject/updateInfo.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_gallery_upload);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.upload_layout);

        ivCamera = (ImageView) findViewById(R.id.ivCamera);
        imgSelected = (ImageView) findViewById(R.id.imgSelected);
        ivGallery = (ImageView) findViewById(R.id.ivGallery);
        ivUpload = (ImageView) findViewById(R.id.ivUpload);
        ivGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "gallery pressed", Toast.LENGTH_LONG).show();
                selectImage();
            }

        });
        ivCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "camera pressed", Toast.LENGTH_LONG).show();
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               // File file  = getFile();
                camera_intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
               // camera_intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(file));
                startActivityForResult(camera_intent,CAM_REQUEST);
            }

        });
    }

    private File getFile()
    {
        File folder = new File("sdcard/PilotProject");
        if(!folder.exists() || folder == null )
        {
            folder.mkdir();
        }
        File imageFile = new File(folder,"cam_image.jpg");
        return imageFile;
    }
    private void selectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case IMG_REQUEST:
                // handle the contact result
                if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null)
                {
                    Uri path = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                        imgSelected.setImageBitmap(bitmap);
                        imgSelected.setVisibility(View.VISIBLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case CAM_REQUEST:
                // handle the camera result
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgSelected.setImageBitmap(bitmap);
                imgSelected.setVisibility(View.VISIBLE);

                // String path = "sdcard/PilotProject/cam_image.jpg";
                //imgSelected.setImageDrawable(Drawable.createFromPath(path));
                break;

    }}
   // private void uploadImage()
   // {
       // StringRequest stringRequest = new StringRequest(Request.Method.POST,UploadUrl,new Response.Listener<String>()){
       //     public void onResponse//to b continued23:48
      //  }
   // }
    //method to return Bitmap in the form of string value
    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }
}
