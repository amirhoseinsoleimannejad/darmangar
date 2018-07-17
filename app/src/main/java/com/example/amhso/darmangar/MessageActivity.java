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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.amhso.darmangar.adapter.ReportAdapter;
import com.example.amhso.darmangar.adapter.SickAdapter;
import com.example.amhso.darmangar.otherclass.G;
import com.example.amhso.darmangar.otherclass.lab;
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

public class MessageActivity extends AppCompatActivity {

    private String id_sick;
    private Boolean danger;

    private String ppd="-1",sonography_shekam="-1",aks_sineh="-1",sitiscan_riyeh="-1",pcr="-1";
    private String khelt_sineh_one="-1",khelt_sineh_two="-1",aks_riyeh="-1",bronkoscopy="-1",shireh_medeh="-1",sonography_riyeh="-1";

    private String exam1="-1",need="-1",exam2="-1",child="-1",ophony="-1",result="-1";
    private String descriptionS="-1";
    private RadioGroup Radio_group;
    private RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        G.activity=this;

//
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){

            id_sick=bundle.getString("id_sick");
            danger=bundle.getBoolean("danger");
        }
        else{
            finish();
        }


        Log.i("danger", "dddddddddddddddddddddddd: "+danger.toString());



        if (danger) {

            LinearLayout linearLayout_danger = (LinearLayout) findViewById(R.id.nodanger);
            linearLayout_danger.setVisibility(View.GONE);

        }

        else {


            LinearLayout linearLayout_danger = (LinearLayout) findViewById(R.id.danger);
            linearLayout_danger.setVisibility(View.GONE);
        }


        Button search=(Button) findViewById(R.id.send);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {


                    if (danger) {



                        Log.i("danger", "ddddddddddddddddddddddddanger: "+danger.toString());

                        Radio_group = (RadioGroup) findViewById(R.id.khelt_one);
                        int selectedId = Radio_group.getCheckedRadioButtonId();
                        radioButton = (RadioButton) findViewById(selectedId);
                        khelt_sineh_one = radioButton.getText().toString();


                        Radio_group = (RadioGroup) findViewById(R.id.khelt_two);
                        selectedId = Radio_group.getCheckedRadioButtonId();
                        radioButton = (RadioButton) findViewById(selectedId);
                        khelt_sineh_two = radioButton.getText().toString();


                        Radio_group = (RadioGroup) findViewById(R.id.aks_riyeh);
                        selectedId = Radio_group.getCheckedRadioButtonId();
                        radioButton = (RadioButton) findViewById(selectedId);
                        aks_riyeh = radioButton.getText().toString();


                        Radio_group = (RadioGroup) findViewById(R.id.bronscopy);
                        selectedId = Radio_group.getCheckedRadioButtonId();
                        radioButton = (RadioButton) findViewById(selectedId);
                        bronkoscopy = radioButton.getText().toString();


                        Radio_group = (RadioGroup) findViewById(R.id.shire_mede);
                        selectedId = Radio_group.getCheckedRadioButtonId();
                        radioButton = (RadioButton) findViewById(selectedId);
                        shireh_medeh = radioButton.getText().toString();


                        Radio_group = (RadioGroup) findViewById(R.id.pcr_danger);
                        selectedId = Radio_group.getCheckedRadioButtonId();
                        radioButton = (RadioButton) findViewById(selectedId);
                        pcr = radioButton.getText().toString();


                        Radio_group = (RadioGroup) findViewById(R.id.conography_riyeh);
                        selectedId = Radio_group.getCheckedRadioButtonId();
                        radioButton = (RadioButton) findViewById(selectedId);
                        sonography_riyeh = radioButton.getText().toString();


                    } else {



                        Log.i("danger", "dddddddddddddddddddddddnnnndanger: "+danger.toString());

                        Radio_group = (RadioGroup) findViewById(R.id.ppd);
                        int selectedId = Radio_group.getCheckedRadioButtonId();
                        radioButton = (RadioButton) findViewById(selectedId);
                        ppd = radioButton.getText().toString();


                        Radio_group = (RadioGroup) findViewById(R.id.aks_cineh);
                        selectedId = Radio_group.getCheckedRadioButtonId();
                        radioButton = (RadioButton) findViewById(selectedId);
                        aks_sineh = radioButton.getText().toString();


                        Radio_group = (RadioGroup) findViewById(R.id.city_scan);
                        selectedId = Radio_group.getCheckedRadioButtonId();
                        radioButton = (RadioButton) findViewById(selectedId);
                        sitiscan_riyeh = radioButton.getText().toString();


                        Radio_group = (RadioGroup) findViewById(R.id.conography_shikam);
                        selectedId = Radio_group.getCheckedRadioButtonId();
                        radioButton = (RadioButton) findViewById(selectedId);
                        sonography_shekam = radioButton.getText().toString();


                        Radio_group = (RadioGroup) findViewById(R.id.pcr_nodanger);
                        selectedId = Radio_group.getCheckedRadioButtonId();
                        radioButton = (RadioButton) findViewById(selectedId);
                        pcr = radioButton.getText().toString();
                    }


                    Radio_group = (RadioGroup) findViewById(R.id.exam_one);
                    int selectedId = Radio_group.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(selectedId);
                    exam1 = radioButton.getText().toString();


                    Radio_group = (RadioGroup) findViewById(R.id.need);
                    selectedId = Radio_group.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(selectedId);
                    need = radioButton.getText().toString();


                    Radio_group = (RadioGroup) findViewById(R.id.exam2);
                    selectedId = Radio_group.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(selectedId);
                    exam2 = radioButton.getText().toString();


                    Radio_group = (RadioGroup) findViewById(R.id.child);
                    selectedId = Radio_group.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(selectedId);
                    child = radioButton.getText().toString();


                    Radio_group = (RadioGroup) findViewById(R.id.ophony);
                    selectedId = Radio_group.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(selectedId);
                    ophony = radioButton.getText().toString();


                    Radio_group = (RadioGroup) findViewById(R.id.result);
                    selectedId = Radio_group.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(selectedId);
                    result = radioButton.getText().toString();


                    EditText description = (EditText) findViewById(R.id.des);
                    descriptionS = description.getText().toString();


                    HttpPostAsyncTask task = new HttpPostAsyncTask();
                    task.execute(G.urlserver + "insert_request");

                }
                catch (Exception e){
                    Log.i("eeeeeeeeeeeeeee", "onClick: "+e.toString());

                    Toast.makeText(G.activity,"تمام اطلاعات را به درستی وارد کنید با تشکر" ,Toast.LENGTH_LONG).show();

                }


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



            if(result.equals("1")){

                AlertDialog.Builder builder = new AlertDialog.Builder(G.activity);
                builder.setTitle("تایید اطلاعات");
                builder.setMessage("اطلاعات به درستی ثبت شد با تشکر ")
                        .setCancelable(false)
                        .setPositiveButton("بازگشت به صفحه اصلی", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                               Intent i = new Intent(G.activity,MainActivity.class);
                               G.activity.startActivity(i);
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();

            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(G.activity);
                builder.setTitle("خطا");
                builder.setMessage("مشکل از ارتباط با سرور اینترنت خود را چک کنید")
                        .setCancelable(false)
                        .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(G.activity,MainActivity.class);
                                G.activity.startActivity(i);
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }

        }






        @Override
        protected void onPreExecute() {

            try {
                progressDialog = new ProgressDialog(G.activity);
                progressDialog.setMessage("چند لحظه صبر کنید...."); // Setting Message
                progressDialog.setTitle("در حال ثبت اطلاعات ..."); // Setting Title
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

                Log.i("eeeeeeee", "onPreExecute: "+e.toString());
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
                nameValuePairs = new ArrayList<NameValuePair>(30);
                // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,


                nameValuePairs.add(new BasicNameValuePair("ppd",ppd));
                nameValuePairs.add(new BasicNameValuePair("pcr",pcr));
                nameValuePairs.add(new BasicNameValuePair("sonography_riyeh",sonography_riyeh));
                nameValuePairs.add(new BasicNameValuePair("sonography_shekam",sonography_shekam));
                nameValuePairs.add(new BasicNameValuePair("khelt_sineh_one",khelt_sineh_one));
                nameValuePairs.add(new BasicNameValuePair("khelt_sineh_two",khelt_sineh_two));
                nameValuePairs.add(new BasicNameValuePair("ophony",ophony));
                nameValuePairs.add(new BasicNameValuePair("exam1",exam1));
                nameValuePairs.add(new BasicNameValuePair("exam2",exam2));
                nameValuePairs.add(new BasicNameValuePair("child",child));
                nameValuePairs.add(new BasicNameValuePair("result",result));
                nameValuePairs.add(new BasicNameValuePair("description",descriptionS));
                nameValuePairs.add(new BasicNameValuePair("aks_riyeh",aks_riyeh));
                nameValuePairs.add(new BasicNameValuePair("aks_sineh",aks_sineh));
                nameValuePairs.add(new BasicNameValuePair("need",need));
                nameValuePairs.add(new BasicNameValuePair("bronkoscopy",bronkoscopy));
                nameValuePairs.add(new BasicNameValuePair("shireh_medeh",shireh_medeh));
                nameValuePairs.add(new BasicNameValuePair("sitiscan_riyeh",sitiscan_riyeh));



                SharedPreferences shpref = G.activity.getSharedPreferences("salamat", Context.MODE_PRIVATE);
//
                nameValuePairs.add(new BasicNameValuePair("id_user",shpref.getString("id_user","-1").trim()));
                nameValuePairs.add(new BasicNameValuePair("id_sick",id_sick));


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
