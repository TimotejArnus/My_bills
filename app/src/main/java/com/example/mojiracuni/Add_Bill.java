package com.example.mojiracuni;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mojiracuni.ui.main.Autentication;
import com.google.android.gms.tasks.OnFailureListener;
/*
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

 */


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class Add_Bill extends AppCompatActivity {

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;

    Button confirmButton;
    Button cancleButton;

    TextView Market, Price, DateOfBuy;


    ImageView selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        confirmButton = (Button) findViewById(R.id.confirmButton); // ON CONFIRM
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Market = findViewById(R.id.editTextTextMarket);
                Price = findViewById(R.id.editTextPrice);
                DateOfBuy = findViewById(R.id.editTextDate);

                if (TextUtils.isEmpty(Market.getText())){
                    Market.setError("Market Required");
                    return;
                }

                if (TextUtils.isEmpty(Price.getText())){
                    Price.setError("Price Required");
                    return;
                }

                if (TextUtils.isEmpty(DateOfBuy.getText())){
                    DateOfBuy.setError("Date Required");
                    return;
                }

                Date d = null;
                try {
                    d = new SimpleDateFormat("dd/MM/yyyy").parse(DateOfBuy.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                    DateOfBuy.setError("Wrong date time ");
                    return;
                }

                Bill bill = new Bill(Autentication.GetUser(),Market.getText().toString(), Double.valueOf(Price.getText().toString()), d);
                bill.AddBillToDB();
                Toast.makeText(getApplicationContext(), "Success !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        cancleButton = (Button) findViewById(R.id.cancelButton); // ON CANCLE
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        selectedImage = findViewById(R.id.displayImageView);
        askCameraPermission();
    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERM_CODE){
            if (grantResults.length < 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                openCamera();
            }
            else {
                Toast.makeText(this, "Camera Perrmission is required ti use the camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (data != null){
                Bitmap image = (Bitmap) data.getExtras().get("data");       // TODO IF == NULL
                selectedImage.setImageBitmap(image);
                //runTextRecognition(image);  // FIREBASE TEXT RECOGNITION..
            }else {
                Toast.makeText(this, "Error when geting Image!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    // FIREBASE ML
    /*
    private void runTextRecognition(Bitmap selectedImage){
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(selectedImage);
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

        confirmButton.setEnabled(false);    // IMAGE IS BEING PROCESED, CANOT CLICK CONFIRM WHILE DOING IT

        detector.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() { // SUCESSFUL
            @Override
            public void onSuccess(FirebaseVisionText texts) {
                confirmButton.setEnabled(true);
                processTextRecognitionResult(texts);
            }
        }).addOnFailureListener(new OnFailureListener() {   // FAILED
            @Override
            public void onFailure(@NonNull Exception e) {
                confirmButton.setEnabled(true);
                e.printStackTrace();
            }
        });

    }

    private void processTextRecognitionResult(FirebaseVisionText texts){
        List<FirebaseVisionText.TextBlock> blocks = texts.getTextBlocks();
        if (blocks.size() == 0){    //IF NO TEXT IS FOUND
            Toast.makeText(this, "No text found", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < blocks.size(); i++ ){       // EACH BLOCK
            List<FirebaseVisionText.Line> Lines = blocks.get(i).getLines();
            for (int j = 0; j < Lines.size(); j++){     // EACH LINE
                List<FirebaseVisionText.Element> elements = Lines.get(j).getElements();
                for (int k = 0; k < elements.size(); k++) { // EACH ELEMENT
                        // TODO ...

                }
            }
        }
    }

    */




}