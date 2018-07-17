package com.example.amhso.darmangar.adapter;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.example.amhso.darmangar.MessageActivity;
import com.example.amhso.darmangar.R;
import com.example.amhso.darmangar.otherclass.G;
import com.example.amhso.darmangar.otherclass.sick;

import java.util.List;


public class SickAdapter extends ArrayAdapter<sick> {

    private final Activity context;
    private final List<sick>  itemname;

    public SickAdapter(Activity context, List<sick> itemname) {
        super(context, R.layout.listsick, itemname);
        this.context=context;
        this.itemname=itemname;
    }




    public View getView(int position,View view,ViewGroup parent) {



//        LayoutInflater inflater=context.getLayoutInflater();
//        View rowView=inflater.inflate(R.layout.listsick, null,true);
//
//        TextView txtTitle = (TextView) rowView.findViewById(R.id.name);
//
//        txtTitle.setText(itemname.get(position).getName());
//
//
//        return rowView;

        View listItem = view;
        MyWrapper wrapper = null;


        try {




            if (listItem == null) {

                listItem = LayoutInflater.from(context).inflate(R.layout.listsick, parent, false);
                wrapper = new MyWrapper(listItem);
                listItem.setTag(wrapper);

            } else {
                wrapper = (MyWrapper) listItem.getTag();
            }


            String getnameseller = itemname.get(position).getName();
            wrapper.getName().setText(getnameseller);




            String getphoneseller = itemname.get(position).getPhone();
            wrapper.getPhone().setText(getphoneseller);



            Boolean danger = itemname.get(position).getDanger();

            if(danger){
                wrapper.getRelative().setBackgroundColor(Color.parseColor("#f4c2c3"));
                wrapper.getDanger().setText("پر خطر");

            }
            else{
                wrapper.getRelative().setBackgroundColor(Color.parseColor("#cbffe1"));

                wrapper.getDanger().setText("کم خطر");

            }



//            wrapper.getMessage().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    View parentRow = (View) v.getParent();
//                    ListView listView = (ListView) parentRow.getParent();
//                    final int position = listView.getPositionForView(parentRow);
//
//
//                    Log.i("ppppp", "ppppppppppppppppppppppp" + position);
//                    Intent i = new Intent(G.activity, MessageActivity.class);
//                    i.putExtra("id_sick", itemname.get(position).getId());
//                    G.activity.startActivity(i);
//                    G.activity.overridePendingTransition(R.anim.animation_activity_start, R.anim.animation_activity_end);
//                }
//            });


            wrapper.getEye().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View parentRow = (View) v.getParent();
                    ListView listView = (ListView) parentRow.getParent();
                    final int position = listView.getPositionForView(parentRow);


                    Log.i("ppppp", "ppppppppppppppppppppppp" + position);
                    Intent i = new Intent(G.activity, DetailsickActivity.class);
                    i.putExtra("id_sick", itemname.get(position).getId());
                    G.activity.startActivity(i);
                    G.activity.overridePendingTransition(R.anim.animation_activity_start, R.anim.animation_activity_end);
                }
            });


        }
        catch (Exception e){
            Log.i("eeeeee", "eeeeeeeeeeeeeeee"+e.toString());
        }



        return listItem;


    };







    class MyWrapper
    {
        private View base;
        private TextView name;
        private TextView phone;
        private ImageView eye;
        private TextView danger;
        private RelativeLayout relative;


        public MyWrapper(View base)
        {
            this.base = base;
        }




        public TextView getName(){
            if(name == null){
                name = (TextView) base.findViewById(R.id.name);
            }
            return name;
        }



        public TextView getPhone(){
            if(phone == null){
                phone = (TextView) base.findViewById(R.id.phone);
            }
            return phone;
        }


        public TextView getDanger(){

            if(danger == null){
                danger = (TextView) base.findViewById(R.id.danger);
            }
            return danger;

        }

        public ImageView getEye(){
            if(eye == null){
                eye = (ImageView) base.findViewById(R.id.eye);
            }
            return eye;
        }


        public RelativeLayout getRelative(){
            if(relative == null){
                relative = (RelativeLayout) base.findViewById(R.id.relative);
            }
            return relative;
        }

//
//        public ImageView getMessage(){
//            if(message == null){
//                message = (ImageView) base.findViewById(R.id.message);
//            }
//            return message;
//        }






    }
}
