package com.example.amhso.darmangar.otherclass;

/**
 * Created by amhso on 21/04/2018.
 */

public class sick {

    private String id;
    private String name;
    private Boolean danger;
    private String phone;


    public sick(String id,String name,String phone, Boolean danger){
        this.id=id;
        this.name=name;
        this.phone=phone;
        this.danger=danger;
    }




    public String getId(){
        return this.id;
    }


    public String getPhone(){
        return this.phone;
    }

    public Boolean getDanger(){
        return this.danger;
    }

    public String getName(){
        return this.name;
    }
}
