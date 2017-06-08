package io.github.rafikfarhad.ssamultitask;

/**
 * Created by root on 6/8/17.
 */

public class Contact {
    int _id;
    String _name;
    String _phone_number;
    String _post;
    public Contact(){   }
    public Contact(int id, String name, String _phone_number, String _post){
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;
        this._post = _post;
    }

    public Contact(String name, String _phone_number, String _post){
        this._name = name;
        this._phone_number = _phone_number;
        this._post = _post;
    }
    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

    public String getPhoneNumber(){
        return this._phone_number;
    }

    public void setPhoneNumber(String phone_number){
        this._phone_number = phone_number;
    }

    public String getPost(){
        return this._post;
    }

    public void setPost(String post){
        this._post = post;
    }
}