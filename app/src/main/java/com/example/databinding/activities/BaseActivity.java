package com.example.databinding.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.example.databinding.App;
import com.example.databinding.room.dao.DishDao;
import com.example.databinding.room.database.AppDatabase;
import com.example.databinding.util.Constants;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

public abstract class BaseActivity extends AppCompatActivity {

    protected AppDatabase database = App.getInstance()
                                        .getDatabase();
    protected DishDao dishDao = database.dishDao();

    protected String mCurrentPhotoPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActionBar();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }

    private void initActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    protected void openCamera() {
        dispatchTakePictureIntent();
    }

    protected void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                                   MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Constants.GALLERY_REQUEST_CODE);
    }

    private File generateFileUri() throws IOException {
        File image = File.createTempFile(String.valueOf(System.currentTimeMillis()),
                                         Constants.JPG_EXTENSION, getDirectory());
        /*File file = new File(
                getDirectory().getPath() + Constants.SYMBOL_SLASH + Constants.PART_OF_PHOTO_NAME
                + System.currentTimeMillis() + Constants.JPG_EXTENSION);*/

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private File getDirectory() {
        File directory = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Constants.NAME_SUBFOLDER_PHOTOS);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        return directory;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,  /* prefix */
                                         ".jpg",         /* suffix */
                                         storageDir      /* directory */);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
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
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider",
                                                          photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, Constants.CAMERA_REQUEST_CODE);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
}
