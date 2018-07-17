package com.example.amhso.darmangar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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

public class ReportlabActivity extends AppCompatActivity {


    private ListView listView_report;
    private List<lab> list_report;
    private ReportAdapter reportAdapter;
    private String id_sick;
    private Boolean danger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportlab);

        G.activity=this;

        listView_report=(ListView) findViewById(R.id.listview_report);
        list_report = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){

            id_sick=bundle.getString("id_sick");
            danger=bundle.getBoolean("danger");
        }
        else{
            finish();
        }


        Button result_b = (Button) findViewById(R.id.result_lab);
        result_b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent i = new Intent(G.activity,MessageActivity.class);
                i.putExtra("id_sick",id_sick);
                i.putExtra("danger",danger);
                startActivity(i);
                G.activity.overridePendingTransition(R.anim.animation_activity_start,R.anim.animation_activity_end);

            }
        });


        HttpPostAsyncTask task = new HttpPostAsyncTask();
        task.execute(G.urlserver+"fetch_lab");
    }



















    public class HttpPostAsyncTask extends AsyncTask<String, String, String> {


        HttpPost httppost;
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        public ProgressDialog progressDialog;






        @Override
        protected void onPostExecute(String result) {

            Log.i("22222222222222222", "22222222222222222222222222" + result);



            try {
                progressDialog.dismiss();

            }
            catch (Exception e){

            }


            try {




                JSONArray contacts;
                JSONObject jsonObj = new JSONObject(result);
                contacts = jsonObj.getJSONArray("lab");



                for (int i = 0; i < contacts.length(); i++) {

                    JSONObject c = contacts.getJSONObject(i);
                    String id = c.getString("id");
                    String description = c.getString("description");
                    String date=c.getString("date");
                    String img=c.getString("img");
                    String category=c.getString("category");


                    lab l=new lab(description,date,img,category);
                    list_report.add(l);


                }

                reportAdapter = new ReportAdapter(G.activity,list_report);
                listView_report.setAdapter(reportAdapter);

            }
            catch (Exception e){


                Log.i("eeeeee", "errrrrrrrror: "+e.toString());
            }

        }






        @Override
        protected void onPreExecute() {

            try {
                progressDialog = new ProgressDialog(G.activity);
                progressDialog.setMessage("چند لحظه صبر کنید...."); // Setting Message
                progressDialog.setTitle("در حال جستجو ..."); // Setting Title
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
