package com.example.amhso.darmangar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.amhso.darmangar.adapter.SickAdapter;
import com.example.amhso.darmangar.otherclass.G;
import com.example.amhso.darmangar.otherclass.sick;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailsickActivity extends AppCompatActivity {


    String id_sick;
    String phoneCall="-1";
    Boolean danger;
    public String subject="";
    public String text="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsick);


        G.activity=this;


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){

            id_sick=bundle.getString("id_sick");

        }
        else{
            finish();
        }


        Log.i("sick", "idididididididididiidid: "+id_sick);


        HttpPostAsyncTask conn=new HttpPostAsyncTask();
        conn.execute(G.urlserver+"detail_sick");




        Button call = (Button) findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {




                if (!phoneCall.equals("-1")){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(phoneCall)));

//                if (ActivityCompat.checkSelfPermission(DriverActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                        // TODO: Consider calling
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//                        return;
//                    }
                    startActivity(intent);
                }

            }
        });




        Button send_message = (Button) findViewById(R.id.send);
        send_message.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                EditText subject_et=(EditText)findViewById(R.id.title_message);
                String subjectS=subject_et.getText().toString();



                EditText text_et=(EditText)findViewById(R.id.text);
                String textS=text_et.getText().toString();


                if(subjectS.isEmpty()){
                    subject_et.setError("عنوان نباید خالی باشد.");
                }
                else if(textS.isEmpty()){
                    text_et.setError("متن پیام نباید خالی باشد.");
                }

                else {
                    subject=subjectS;
                    text=textS;

                    HttpPostAsyncTask conn=new HttpPostAsyncTask();
                    conn.execute(G.urlserver+"message_send");
                }


            }
        });




        Button report_b = (Button) findViewById(R.id.report_lab);
        report_b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent i = new Intent(G.activity,ReportlabActivity.class);
                i.putExtra("id_sick",id_sick);
                i.putExtra("danger",danger);
                startActivity(i);
                G.activity.overridePendingTransition(R.anim.animation_activity_start,R.anim.animation_activity_end);

            }
        });






        Button lab = (Button) findViewById(R.id.lab);
        lab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent i = new Intent(G.activity,LabActivity.class);
                i.putExtra("id_sick",id_sick);
                i.putExtra("danger",danger);
                startActivity(i);
                G.activity.overridePendingTransition(R.anim.animation_activity_start,R.anim.animation_activity_end);

            }
        });






        Button remove = (Button) findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(G.activity);
                // Setting Alert Dialog Title
//                alertDialogBuilder.setTitle("اطمینان");
                // Icon Of Alert Dialog
//                alertDialogBuilder.setIcon(R.drawable.question);
                // Setting Alert Dialog Message
                alertDialogBuilder.setMessage("آیا می خواهید این بیمار از لیست شما حذف شود؟");
                alertDialogBuilder.setCancelable(false);

                alertDialogBuilder.setPositiveButton("بله", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        HttpPostAsyncTask conn=new HttpPostAsyncTask();
                        conn.execute(G.urlserver+"enable_sick");

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
        });



    }
















    public class HttpPostAsyncTask extends AsyncTask<String, String, String> {


        HttpPost httppost;
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        ProgressDialog progressDialog;






        @Override
        protected void onPostExecute(String result) {

            Log.i("22222222222222222", "22222222222222222222222222" + result);

            try {
                progressDialog.dismiss();

            }
            catch (Exception e){

            }



            String id;
            String name="";
            String fe_male="";
            String job="" ;
            String address="" ;
            String age="" ;
            String single_marriage="";
            String phone="";
            String meli="";



            try {


                JSONArray contacts;
                JSONObject jsonObj = new JSONObject(result);
                contacts = jsonObj.getJSONArray("sick");



                for (int i = 0; i < contacts.length(); i++) {

                     JSONObject c = contacts.getJSONObject(i);
                     id = c.getString("id");
                     name = c.getString("name");
                     fe_male = c.getString("male_female");
                     job = c.getString("job");
                     address = c.getString("address");
                     age = c.getString("age");
                     single_marriage = c.getString("single_marriage");
                     phone = c.getString("phone");
                     meli = c.getString("meli");

                     String danger_string=c.getString("danger_sick");
                     if (danger_string.equals("1")){
                         danger=true;
                     }
                     else if (danger_string.equals("0")){
                         danger=false;
                     }

                }


                    TextView nameT=(TextView) G.activity.findViewById(R.id.name);
                    nameT.setText("نام و نام خانوادگی: " + name);
                    Typeface type2 = Typeface.createFromAsset(getAssets(),"fonts/IRANSansWeb(FaNum).ttf");
                    nameT.setTypeface(type2);



                    TextView female_male=(TextView) G.activity.findViewById(R.id.male_female);
                    female_male.setText("جنسیت: " + fe_male);
                    female_male.setTypeface(type2);



                    TextView jobT=(TextView) G.activity.findViewById(R.id.job);
                    jobT.setText("شغل: " + job);
                    jobT.setTypeface(type2);



                    TextView addressT=(TextView) G.activity.findViewById(R.id.address);
                    addressT.setText("آدرس: " + address);
                addressT.setTypeface(type2);


                    TextView ageT=(TextView) G.activity.findViewById(R.id.age);
                    ageT.setText("سن:" + age);
                ageT.setTypeface(type2);



                    TextView single_marriageT=(TextView) G.activity.findViewById(R.id.single_mariage);
                    single_marriageT.setText("وضعبت تاهل:" + single_marriage);
                single_marriageT.setTypeface(type2);




                    TextView phoneT=(TextView) G.activity.findViewById(R.id.phone);
                    phoneT.setText("تلفن:" + phone);
                phoneT.setTypeface(type2);

                phoneCall=phone;



                TextView meliT=(TextView) G.activity.findViewById(R.id.meli);
                meliT.setText("کد ملی:" + meli);
                meliT.setTypeface(type2);


                String
                        q1="بدون پاسخ",
                        q2="بدون پاسخ",
                        q3="بدون پاسخ";
//                        q4="بدون پاسخ",
//                        q5="بدون پاسخ",
//                        q6="بدون پاسخ",
//                        q7="بدون پاسخ",
//                        q8="بدون پاسخ",
//                        q9="بدون پاسخ";

                try {


                    JSONArray question;
                    JSONObject jsonObj_q = new JSONObject(result);
                    question = jsonObj_q.getJSONArray("question");



                    for (int i = 0; i < question.length(); i++) {

                        JSONObject c = question.getJSONObject(i);
                        q1 = c.getString("q1");
                        q2 = c.getString("q2");
                        q3 = c.getString("q3");
//                        q4 = c.getString("q4");
//                        q5 = c.getString("q5");
//                        q6 = c.getString("q6");
//                        q7 = c.getString("q7");
//                        q8 = c.getString("q8");
//                        q9 = c.getString("q9");



                    }


                    TextView why=(TextView) G.activity.findViewById(R.id.why);
                    why.setTypeface(type2);

                    if(danger){
                        why.setText("بیمار پر خطر هستند");

                    }
                    else {
                        why.setText("بیمار کم خطر هستند");

                    }

                    TextView q1_tv=(TextView) G.activity.findViewById(R.id.q1);
                    q1_tv.setText("سرفه بیش از دو هفته " + q1);
                    q1_tv.setTypeface(type2);


                    TextView q2_tv=(TextView) G.activity.findViewById(R.id.q2);
                    q2_tv.setText("خلط سینه " + q2);
                    q2_tv.setTypeface(type2);


                    TextView q3_tv=(TextView) G.activity.findViewById(R.id.q3);
                    q3_tv.setText("خلط خونی " + q3);
                    q3_tv.setTypeface(type2);

                }
                catch (Exception e){

                    Log.i("eeeeee", "errrrrrrrror: "+e.toString());
                }

            }
            catch (Exception e){


                if(result.equals("1")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(G.activity);
                    // Setting Alert Dialog Title
                    alertDialogBuilder.setTitle("نتیجه عملیات");
                    // Icon Of Alert Dialog
//                alertDialogBuilder.setIcon(R.drawable.question);
                    // Setting Alert Dialog Message
                    alertDialogBuilder.setMessage("عملیات به درستی انجام شد.");
                    alertDialogBuilder.setCancelable(false);

                    alertDialogBuilder.setPositiveButton("باشه", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {


                        }
                    });



                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                Log.i("eeeeee", "errrrrrrrror: "+e.toString());
            }




        }






        @Override
        protected void onPreExecute() {



            try {
                progressDialog = new ProgressDialog(G.activity);
                progressDialog.setMessage("چند لحظه صبر کنید...."); // Setting Message
                progressDialog.setTitle("گرفتن اطلاعات ..."); // Setting Title
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
            catch (Exception e){

            }

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
                nameValuePairs.add(new BasicNameValuePair("id_sick",id_sick.trim()));
                nameValuePairs.add(new BasicNameValuePair("subject",subject.trim()));
                nameValuePairs.add(new BasicNameValuePair("text",text.trim()));




                SharedPreferences shpref = G.activity.getSharedPreferences("salamat", Context.MODE_PRIVATE);
//
                nameValuePairs.add(new BasicNameValuePair("id_user",shpref.getString("id_user","-1").trim()));


                Log.i("dddddddddd", "doInBackground: "+shpref.getString("id_user","-1").trim());
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
