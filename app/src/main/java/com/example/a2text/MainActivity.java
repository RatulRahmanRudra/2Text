package com.example.a2text;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

//    private final int CHOOSE_PDF_FROM_DEVICE = 1001;
    private static final String tag = "MainActivity";

    private Button openPdf;
//    private EditText content_et;
//    private TextView path_tv;

    private Button choose_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // pdf

        openPdf = (Button) findViewById(R.id.btn_get_pdf);
//        content_et = findViewById(R.id.file_content_et);
//        path_tv = findViewById(R.id.path_tv);
        openPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdfActivity();
            }
        });


        // image
        choose_image = (Button) findViewById(R.id.btn_choose_image);
        choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageActivity();
            }
        });



    }

    private void openPdfActivity() {
        Intent intent  = new Intent(this, pdfActivity.class);
        startActivity(intent);
    }

    private void openImageActivity() {
        Intent intent  = new Intent(this, ImageActivity.class);
        startActivity(intent);
    }

    
//
//    private void callChoosePdfFile(){
//        System.out.println("clicked to open");
//
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("application/pdf");
//        startActivityForResult(intent, CHOOSE_PDF_FROM_DEVICE);
////
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
//        super.onActivityResult(requestCode, resultCode, resultData);
//        System.out.println("res-data -> "+resultData.getData());
//        path_tv.setText("fp://" + resultData.getData());
////        char[] ch = {'h','e','l','p'};
////        path_tv.setText("hello");
//        if(requestCode == CHOOSE_PDF_FROM_DEVICE && resultCode == RESULT_OK){
//            if(resultData != null){
//                Log.d(TAG, "onActivityResult: " + resultData.getData());
//                path_tv.setText("File path : " + resultData.getData());
//                try {
//                    extractTextPdfFile(resultData.getData());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//
//    InputStream inputStream;
//    public void extractTextPdfFile(final Uri uri) throws IOException{
//
//        try{
//            inputStream = MainActivity.this.getContentResolver().openInputStream(uri);
//        }catch (FileNotFoundException e){
//            e.printStackTrace();
//        }
//
//        new Thread(() -> {
//            String fileContent = "";
//            StringBuilder builder = new StringBuilder();
//            PdfReader reader = null;
//            try {
//                reader = new PdfReader(inputStream);
//                int n = reader.getNumberOfPages();
//                for (int i=1; i<=n; i++){
//                    try {
//                        fileContent = PdfTextExtractor.getTextFromPage(reader, i);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    builder.append(fileContent);
//                }
//
//                reader.close();
//                runOnUiThread(() -> {
//                    System.out.println(builder.toString());
//                    content_et.setText(builder.toString());
//                });
//
//            }catch (IOException e) {
//                e.printStackTrace();
////                Log.d(TAG, "run: " + e.getMessage());
//            }
//        }).start();
//    }

}