package com.example.amhso.darmangar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.amhso.darmangar.adapter.SickAdapter;
import com.example.amhso.darmangar.otherclass.G;
import com.example.amhso.darmangar.otherclass.sick;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.takusemba.spotlight.SimpleTarget;
import com.takusemba.spotlight.Spotlight;

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

public class MainActivity extends AppCompatActivity {

    CarouselView carouselView;

//    int[] sampleImages = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};

    public String imgs[];
    public String url;




    private DrawerLayout drawer;
    private NavigationView navigat;
    private ListView listView;


    private List<sick> listnamesick;
    private SickAdapter sickAdapter;
    private PopupWindow pw;
    private TextView empty_sick_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        G.activity=this;
//        carouselView = (CarouselView) findViewById(R.id.carouselView);
//        carouselView.setPageCount(sampleImages.length);
//        carouselView.setImageListener(imageListener);



        listView=(ListView) findViewById(R.id.list_sick);
        listnamesick = new ArrayList<>();


        empty_sick_text =(TextView) findViewById(R.id.empty_sick);

        Typeface type3 = Typeface.createFromAsset(getAssets(),"fonts/IRANSansWeb(FaNum)_Bold.ttf");
        empty_sick_text.setTypeface(type3);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Log.i("ppppp", "ppppppppppppppppppppppp"+position);
                Intent i = new Intent(G.activity, DetailsickActivity.class);
                i.putExtra("id_sick", listnamesick.get(position).getId());
                G.activity.startActivity(i);
                G.activity.overridePendingTransition(R.anim.animation_activity_start,R.anim.animation_activity_end);
            }
        });



//        sickAdapter = new SickAdapter(this,listnamesick);
//        listView.setAdapter(sickAdapter);

        //ser drawer
        this.drawer = (DrawerLayout) findViewById(R.id.DL);
        //set click item drawer
        this.navigat = (NavigationView) findViewById(R.id.NA);






        this.navigat.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return itemClickNavigat(item);
            }
        });


        this.navigat.setItemIconTintList(null);



        ImageView send=(ImageView) findViewById(R.id.icon_menu);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                drawer.openDrawer(Gravity.RIGHT);

            }
        });





        ImageView search=(ImageView) findViewById(R.id.icon_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopup();


            }
        });





        HttpPostAsyncTask task = new HttpPostAsyncTask();
        task.execute(G.urlserver+"fetch_sick_relation_doctor");

    }







//    ImageListener imageListener = new ImageListener() {
//        @Override
//        public void setImageForPosition(int position, ImageView imageView) {
//            imageView.setImageResource(sampleImages[position]);
//        }
//    };





    private boolean itemClickNavigat(MenuItem item){
        switch (item.getItemId()){

            case R.id.search_one :

                this.drawer.closeDrawer(Gravity.RIGHT);
                HttpPostAsyncTask task1 = new HttpPostAsyncTask();
                task1.execute(G.urlserver+"fetch_sick_relation_doctor_all");

                return true;
            case R.id.search_two :

                this.drawer.closeDrawer(Gravity.RIGHT);

                HttpPostAsyncTask task2 = new HttpPostAsyncTask();
                task2.execute(G.urlserver+"fetch_sick_relation_doctor_danger");

                return true;

            case R.id.search_three :
                this.drawer.closeDrawer(Gravity.RIGHT);
                HttpPostAsyncTask task3 = new HttpPostAsyncTask();
                task3.execute(G.urlserver+"fetch_sick_relation_doctor_tell");
                return true;


            case R.id.search_four :
                this.drawer.closeDrawer(Gravity.RIGHT);
                HttpPostAsyncTask task4 = new HttpPostAsyncTask();
                task4.execute(G.urlserver+"fetch_sick_relation_doctor_danger_notell");

                return true;

        }
        return false;
    }





    private void showPopup() {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) MainActivity.this.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.search,
                    (ViewGroup) findViewById(R.id.popup_1));


            pw = new PopupWindow(layout, 600, 300, true);
            pw.showAtLocation(layout, Gravity.TOP, 0, 100);

           final EditText et=(EditText) layout.findViewById(R.id.search_text);

            Button Close;
            Close = (Button) layout.findViewById(R.id.button_search);
            Close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String name_search = et.getText().toString();
                    if(name_search.isEmpty()){

                        et.setError("نام نمی تواند خالی باشد.");
                    }
                    else{
                        pw.dismiss();
                        HttpPostAsyncTask task = new HttpPostAsyncTask();
                        task.execute(G.urlserver+"fetch_sick_relation_doctor_search_name?name="+name_search);
                    }

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    @Override
    public void onWindowFocusChanged (boolean hasFocus) {

        ImageView menu;
        ImageView search;

        SharedPreferences shpref = G.activity.getSharedPreferences("salamat", Context.MODE_PRIVATE);
        if(shpref.getBoolean("help",true)){

            SharedPreferences shpref_login1 = G.activity.getSharedPreferences("salamat", Context.MODE_PRIVATE);
            SharedPreferences.Editor sh_edit = shpref_login1.edit();
            sh_edit.putBoolean("help", false);
            sh_edit.apply();



            menu = (ImageView) G.activity.findViewById(R.id.icon_menu);
            int[] oneLocation = new int[2];
            menu.getLocationOnScreen(oneLocation);
            float oneX = oneLocation[0] + menu.getWidth() / 2f   ;
            float oneY = oneLocation[1] + menu.getHeight()  / 2f  ;


            // make an target
            SimpleTarget firstTarget = new SimpleTarget.Builder(G.activity).setPoint(oneX, oneY)
                    .setRadius(42f)
                    .setTitle("جستجو")
                    .setDescription("برای جستجو بر اساس دسته می توانید استفاده کنید.")
                    .build();




            search = (ImageView) G.activity.findViewById(R.id.icon_search);
            search.getLocationOnScreen(oneLocation);
            float oneX2 = oneLocation[0]+ search.getWidth()  / 2f   ;
            float oneY2 = oneLocation[1] + search.getHeight()  / 2f  ;



            // make an target
            SimpleTarget twoTarget = new SimpleTarget.Builder(G.activity).setPoint(oneX2, oneY2)
                    .setRadius(42f)
                    .setTitle("جستجو")
                    .setDescription("برای جستجو نام می توانید استفاده کنید")
                    .build();




            Spotlight.with(G.activity)
                    .setOverlayColor(ContextCompat.getColor(G.activity, R.color.background))
                    .setDuration(1000L)
                    .setAnimation(new DecelerateInterpolator(2f))
                    .setTargets(firstTarget,twoTarget)
                    .setClosedOnTouchedOutside(true)
                    .start();
        }

    }





    private class TestLoopAdapter extends LoopPagerAdapter {



        public TestLoopAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
//            view.setImageResource(imgs[position]);

            Picasso.with(G.activity)
                    .load(G.urlimage+imgs[position])
                    .resize(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels / 3 )
                    .into(view);



            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!url.startsWith("https://") && !url.startsWith("http://")){
                        url = "http://" + url;
                    }

                    Intent openUrlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    G.activity.startActivity(openUrlIntent);



                }
            });




            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getRealCount() {
            return imgs.length;
        }
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
                listnamesick.clear();
            }
            catch (Exception e){

            }




            try {


                JSONArray baner;
                JSONObject jsonObj = new JSONObject(result);
                baner = jsonObj.getJSONArray("baner");


                imgs = new String[baner.length()];
                for (int i = 0; i < baner.length(); i++) {

                    JSONObject c = baner.getJSONObject(i);
                    String img = c.getString("img");
                    String url2 = c.getString("url");

                    imgs[i] = img;
                    url=url2;

                }


                com.jude.rollviewpager.RollPagerView mRollViewPager = (com.jude.rollviewpager.RollPagerView) G.activity.findViewById(R.id.baner);
                mRollViewPager.setAdapter(new TestLoopAdapter(mRollViewPager));

            }
            catch (Exception e){
                Log.i("baner", "eeeeeeeeee: "+e.toString());
            }

            try {




                JSONArray contacts;
                JSONObject jsonObj = new JSONObject(result);
                contacts = jsonObj.getJSONArray("sick");



                for (int i = 0; i < contacts.length(); i++) {

                    JSONObject c = contacts.getJSONObject(i);
                    String id = c.getString("id");
                    String name = c.getString("name");
                    String phone=c.getString("phone");
                    String danger=c.getString("danger_sick");

                    if(danger.equals("1")){
                        sick s=new sick(id,name,phone,true);

                        listnamesick.add(s);
                    }
                    else{
                        sick s=new sick(id,name,phone,false);

                        listnamesick.add(s);
                    }

                }


                if (!listnamesick.isEmpty()){
                    empty_sick_text.setVisibility(View.GONE);
                }


                sickAdapter = new SickAdapter(G.activity,listnamesick);
                listView.setAdapter(sickAdapter);

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
