package com.example.amhso.darmangar.otherclass;

/**
 * Created by amhso on 03/05/2018.
 */

public class lab {

    private String description;
    private String date;
    private String img;
    private String category;

    public lab(String description,String date,String img,String category){
        this.description=description;
        this.category=category;
        this.date=date;
        this.img=img;
    }
    public String getDescription(){
        return this.description;
    }
    public String getDate(){
        return this.date;
    }
    public String getImg(){
        return this.img;
    }
    public String getCategory(){
        return this.category;
    }
}
