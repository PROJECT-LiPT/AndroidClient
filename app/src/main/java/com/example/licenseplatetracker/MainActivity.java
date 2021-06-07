package com.example.licenseplatetracker;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_PICK = 2;
    private Handler hMessage = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnCapture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "btnCapture", Toast.LENGTH_SHORT).show();
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(MainActivity.this,
                                "com.example.android.fileprovider",
                               photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });
        findViewById(R.id.btnSelect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "btnSelect", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_PICK);
            }
        });
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                "temp_",  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageView = (ImageView) findViewById(R.id.imgReview);
        TextView tv = (TextView)findViewById(R.id.tvMessage);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);
            imageView.setImageBitmap(imageBitmap);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try  {
                        updateMessage("Uploading...");
                        String result = ImageUtil.CallAPI(imageBitmap);
                        updateMessage(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();

            (new File(currentPhotoPath)).delete();
        } else if (requestCode == REQUEST_PICK && resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(imageBitmap);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try  {
                            updateMessage("Uploading...");
                            String result = ImageUtil.CallAPI(imageBitmap);
                            updateMessage(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateMessage(String str){
        TextView tv = (TextView)findViewById(R.id.tvMessage);
        hMessage.post(new Runnable() {
            @Override
            public void run() {
                tv.setText(str);
            }
        });
    }
}