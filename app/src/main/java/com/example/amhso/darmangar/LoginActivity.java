package com.example.amhso.darmangar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.amhso.darmangar.otherclass.G;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {


    public EditText user;
    public String userS;

    public EditText pass;
    public String passS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        G.activity=this;



        SharedPreferences shpref = G.activity.getSharedPreferences("salamat", Context.MODE_PRIVATE);


        if(!shpref.getString("id_user","-1").equals("-1")){

            Intent i=new Intent(LoginActivity.this  , MainActivity.class);
            startActivity(i);
            finish();
        }





        Button send=(Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                user=(EditText) findViewById(R.id.user);
                userS=user.getText().toString();



                pass=(EditText) findViewById(R.id.pass);
                passS=pass.getText().toString();





                if(userS.isEmpty()){
                    user.setError("نام کاربری نباید خالی باشد");
                }


               else if(passS.isEmpty()){
                    pass.setError("رمز عبور نباید خالی باشد");
                }


                else if(passS.length() <= 5){
                    pass.setError("رمز عبور باید 6 کاراکتری باشد");
                }


                else{
                    HttpPostAsyncTask task = new HttpPostAsyncTask();
                    task.execute(G.urlserver+"signup_doctor");
                }

            }
        });

    }









    public class HttpPostAsyncTask extends AsyncTask<String, String, String> {


        HttpPost httppost;
        public ProgressDialog progressDialog;
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        ProgressDialog dialog = null;





        @Override
        protected void onPostExecute(String result) {

            Log.i("22222222222222222", "22222222222222222222222222" + result);
//
            progressDialog.dismiss();

            String Result[]= result.split(":");
            if(Result[0].equals("1")){


                SharedPreferences shpref_login1 = G.activity.getSharedPreferences("salamat", Context.MODE_PRIVATE);
                SharedPreferences.Editor sh_edit = shpref_login1.edit();
                sh_edit.putString("username", userS);
                sh_edit.putString("password",passS);
                sh_edit.putString("id_user",Result[1]);
                sh_edit.apply();


                Intent i = new Intent(G.activity,MainActivity.class);
                startActivity(i);
                G.activity.overridePendingTransition(R.anim.animation_activity_start,R.anim.animation_activity_end);
                finish();

            }


//            else if(G.checknet()){
//
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(G.activity);
//                // Setting Alert Dialog Title
//                alertDialogBuilder.setTitle("اینترنت شما قطع می باشد");
//                // Icon Of Alert Dialog
////                alertDialogBuilder.setIcon(R.drawable.question);
//                // Setting Alert Dialog Message
//                alertDialogBuilder.setMessage("لطفا اینترنت خود را وصل کنید");
//                alertDialogBuilder.setCancelable(false);
//
//                alertDialogBuilder.setPositiveButton("باشه", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//
//
//                    }
//                });
//
//
//
//                AlertDialog alertDialog = alertDialogBuilder.create();
//                alertDialog.show();
//            }

            else{



                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(G.activity);
                // Setting Alert Dialog Title
                alertDialogBuilder.setTitle("اطلاعات وارد شده اشتباه می باشد");
                // Icon Of Alert Dialog
//                alertDialogBuilder.setIcon(R.drawable.question);
                // Setting Alert Dialog Message
                alertDialogBuilder.setMessage("آیا دوباره امتحان می کنید؟");
                alertDialogBuilder.setCancelable(false);

                alertDialogBuilder.setPositiveButton("بله", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                    }
                });

                alertDialogBuilder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();



            }

        }






        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(G.activity);
            progressDialog.setMessage("چند لحظه صبر کنید...."); // Setting Message
            progressDialog.setTitle("در حال تایید اطلاعات..."); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog


            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(20000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    progressDialog.dismiss();
                }
            }).start();

        }



        // This is a function that we are overriding from AsyncTask. It takes Strings as parameters because that is what we defined for the parameters of our async task
        @Override
        protected String doInBackground(String... params) {

            try {


                Log.i("urluuuuuuuuuuuuuuu", "doInBackground: "+params[0]);

                httpclient=new DefaultHttpClient();
                httppost= new HttpPost(params[0]); // make sure the url is correct.
                //add your data

                Log.i("uuuuuu", "urluuuuuuuuuuuu "+params[0]);
                nameValuePairs = new ArrayList<NameValuePair>(2);
                // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,

                nameValuePairs.add(new BasicNameValuePair("username", userS.trim()));
                nameValuePairs.add(new BasicNameValuePair("password", passS.trim()));


                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));


                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String response = httpclient.execute(httppost, responseHandler);
                System.out.println("Response : " + response);
                return response;



            } catch (Exception e) {
                Log.i("error rrrrrrr", e.toString());
            }

            return "0";
        }
    }



}
