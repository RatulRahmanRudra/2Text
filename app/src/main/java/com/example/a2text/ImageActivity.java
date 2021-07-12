package com.example.a2text;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
//import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static android.content.ContentValues.TAG;

public class ImageActivity extends AppCompatActivity {
    private Button captureImageBtn, detectTextBtn;
    private ImageView imageView;
    private EditText editText;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bundle extras;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);


        captureImageBtn = findViewById(R.id.capture_image);
        detectTextBtn = findViewById(R.id.detect_text_image);
        imageView = findViewById(R.id.imageView);
        editText = findViewById(R.id.file_content_et);

        captureImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        detectTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detectTextFromImage();
                editText.setText("");
            }
        });
    }




    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
//        try {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        } catch (ActivityNotFoundException e) {
//            // display error state to the user
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE ){
            extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }else{
            Toast.makeText(this, "error" + resultCode, Toast.LENGTH_SHORT).show();
        }
    }

    private void detectTextFromImage() {
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextDetector firebaseVisionTextDetector = FirebaseVision.getInstance().getVisionTextDetector();
        firebaseVisionTextDetector.detectInImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                displayTextFromImage(firebaseVisionText);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NotNull Exception e) {
                Toast.makeText(ImageActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Error : ", e.getMessage());
            }
        });
    }

    private void displayTextFromImage(FirebaseVisionText firebaseVisionText) {
        editText.setText("");
        List<FirebaseVisionText.Block> blockList = firebaseVisionText.getBlocks();
        if(blockList.size() == 0){
            Toast.makeText(this, "No text found in image", Toast.LENGTH_SHORT).show();
        } else {
            for (FirebaseVisionText.Block block : firebaseVisionText.getBlocks()){
                String text = block.getText();
                editText.append(text);
            }
        }
    }
}