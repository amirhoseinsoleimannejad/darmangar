package com.example.amhso.darmangar.adapter;



import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.amhso.darmangar.DetailsickActivity;
import com.example.amhso.darmangar.R;
import com.example.amhso.darmangar.otherclass.G;
import com.example.amhso.darmangar.otherclass.lab;
import com.example.amhso.darmangar.otherclass.sick;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ReportAdapter extends ArrayAdapter<lab> {

    private final Activity context;
    private final List<lab> itemname;

    public ReportAdapter(Activity context, List<lab> itemname) {
        super(context, R.layout.listsick, itemname);
        this.context=context;
        this.itemname=itemname;
    }




    public View getView(int position,View view,ViewGroup parent) {



        View listItem = view;
        MyWrapper wrapper = null;


        try {


            if (listItem == null) {

                listItem = LayoutInflater.from(context).inflate(R.layout.listlab, parent, false);
                wrapper = new MyWrapper(listItem);
                listItem.setTag(wrapper);

            }
            else {
                wrapper = (MyWrapper) listItem.getTag();
            }


            String date = itemname.get(position).getDate();
            wrapper.getDate().setText(date);




            String description = itemname.get(position).getDescription();
            wrapper.getDescription().setText(description);



            String category = itemname.get(position).getCategory();
            wrapper.getCategory().setText(category);



            String img = itemname.get(position).getImg();


            Picasso.with(G.activity)
                    .load(G.urlimage+img)
                    .resize(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels / 3 )
                    .into(wrapper.getImg());



        }
        catch (Exception e){
            Log.i("eeeeee", "eeeeeeeeeeeeeeee"+e.toString());
        }



        return listItem;


    };







    class MyWrapper
    {
        private View base;
        private TextView date;
        private TextView description;
        private ImageView img;
        private TextView category;


        public MyWrapper(View base)
        {
            this.base = base;
        }




        public TextView getDate(){
            if(date == null){
                date = (TextView) base.findViewById(R.id.date);
            }
            return date;
        }



        public TextView getDescription(){
            if(description == null){
                description = (TextView) base.findViewById(R.id.description);
            }
            return description;
        }





        public ImageView getImg(){
            if(img == null){
                img = (ImageView) base.findViewById(R.id.image_lab);
            }
            return img;
        }




        public TextView getCategory(){
            if(category == null){
                category = (TextView) base.findViewById(R.id.category);
            }
            return category;
        }



    }
}
