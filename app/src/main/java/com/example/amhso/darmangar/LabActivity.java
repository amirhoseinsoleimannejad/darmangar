package com.example.amhso.darmangar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.amhso.darmangar.otherclass.G;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class LabActivity extends AppCompatActivity {

    public Bitmap image_lab_bitmap;
    private static final int CAMERA_PIC_REQUEST = 2500;
    private ImageView imageview;
    private ProgressDialog progressDialog ;
    public Boolean check=true;
    public String ServerUploadPath=G.urlserver + "lab";
    private RadioGroup category_group;
    private String category;
    private RadioButton category_button_radio;
    private String id_sick;
    private Boolean danger;
    private String decription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab);



        G.activity=this;




        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){

            id_sick=bundle.getString("id_sick");
            danger=bundle.getBoolean("danger");
        }
        else{
            finish();
        }


        if (danger){
            category_group = (RadioGroup) findViewById(R.id.category_group_danger);
            category_group.setVisibility(View.GONE);
        }
        else{
            category_group = (RadioGroup) findViewById(R.id.category_group);
            category_group.setVisibility(View.GONE);
        }



        
        Button b = (Button)findViewById(R.id.take_img);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });


        Button send = (Button)findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//                category_group = (RadioGroup) findViewById(R.id.category_group);
                int selectedId = category_group.getCheckedRadioButtonId();

                    category_button_radio = (RadioButton) findViewById(selectedId);
                    category=category_button_radio.getText().toString();


                EditText des_et=(EditText)findViewById(R.id.description);
                decription=des_et.getText().toString();



                if (image_lab_bitmap!=null){
                    ImageUploadToServerFunction();
                }
                else{
                    Toast.makeText(G.activity,"از آزمایش باید عکس بگیرید." ,Toast.LENGTH_LONG).show();
                }

            }
        });



    }





    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            if(data!=null){
                image_lab_bitmap = (Bitmap) data.getExtras().get("data");
                imageview = (ImageView) findViewById(R.id.photo_image);
                imageview.setImageBitmap(image_lab_bitmap);
            }

        }
    }
























    public void ImageUploadToServerFunction(){


        ByteArrayOutputStream byteArrayOutputStreamObjectSing ;
        byteArrayOutputStreamObjectSing = new ByteArrayOutputStream();

        image_lab_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObjectSing);


        byte[] byteArrayVarSing = byteArrayOutputStreamObjectSing.toByteArray();

        final String CovertImageSing= Base64.encodeToString(byteArrayVarSing, Base64.DEFAULT);


        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(G.activity,"در حال ارسال اطلاعات","لطفا منتظر بمانید",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                // Dismiss the progress dialog after done uploading.
                progressDialog.dismiss();


                Log.i("uuuuuuuuuu", "uuuuuuuuuuuuuuuu"+string1);

                if(string1.equals("1")){


                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(G.activity);
                    // Setting Alert Dialog Title
                  alertDialogBuilder.setTitle("اطلاعات به درستی ارسال شد");
                    // Icon Of Alert Dialog
//                alertDialogBuilder.setIcon(R.drawable.question);
                    // Setting Alert Dialog Message
                    alertDialogBuilder.setMessage("اطلاعات آزمایش به درستی در سیستم ذخیره شد");
                    alertDialogBuilder.setCancelable(false);

                    alertDialogBuilder.setPositiveButton("باشه", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            image_lab_bitmap=null;
                            imageview.setImageBitmap(image_lab_bitmap);
                        }
                    });



                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }
                else{
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(G.activity);
                    // Setting Alert Dialog Title
                    alertDialogBuilder.setTitle("خطا");
                    // Icon Of Alert Dialog
//                alertDialogBuilder.setIcon(R.drawable.question);
                    // Setting Alert Dialog Message
                    alertDialogBuilder.setMessage("خطایی در ارسال اطلاعات پیش آمده دوباره تلاش کنید");
                    alertDialogBuilder.setCancelable(false);

                    alertDialogBuilder.setPositiveButton("باشه", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {


                        }
                    });


                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }



            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();


                HashMapParams.put("category", category);


                HashMapParams.put("image_lab", CovertImageSing);

                HashMapParams.put("id_sick", id_sick);
                HashMapParams.put("description", decription);

                SharedPreferences shpref = G.activity.getSharedPreferences("salamat", Context.MODE_PRIVATE);

                HashMapParams.put("id_user", shpref.getString("id_user","-1").trim());



                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }




    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject ;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject ;
                BufferedReader bufferedReaderObject ;
                int RC ;

                url = new URL(requestURL);

                Log.i("uuuuuuuuuuuuuuuuuuuu", "uuuuuuuuuuuuuuuuuuuu: "+url.toString());
                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }



        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            StringBuilder stringBuilderObject;

            stringBuilderObject = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

                if (check)

                    check = false;
                else
                    stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilderObject.append("=");

                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilderObject.toString();
        }

    }
}
