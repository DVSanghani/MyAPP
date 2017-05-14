package com.example.peacock.myapp;

/**
 * Created by peacock on 8/4/17.
 */

public class Contact {

    //private variables
    int _id;
    String _name;
    String _dob;
    String _gender;
    byte[] _photo;
    String _country;
    String _hobbies;

    public Contact() {
    }

    public Contact(int _id, String _name, String _dob, String _gender, String _country, String _hobbies) {
        this._id = _id;
        this._name = _name;
        this._dob = _dob;
        this._gender = _gender;
        this._country = _country;
        this._hobbies = _hobbies;
    }

    public Contact(String _name, String _dob, String _gender, String _country, String _hobbies) {
        this._name = _name;
        this._dob = _dob;
        this._gender = _gender;
        this._country = _country;
        this._hobbies = _hobbies;
    }

    public Contact(String _name, String _dob, String _gender, byte[] _photo, String _country, String _hobbies) {
        this._name = _name;
        this._dob = _dob;
        this._gender = _gender;
        this._photo = _photo;
        this._country = _country;
        this._hobbies = _hobbies;
    }

    public Contact(int _id, String _name, String _dob, String _gender, byte[] _photo, String _country, String _hobbies) {
        this._id = _id;
        this._name = _name;
        this._dob = _dob;
        this._photo = _photo;
        this._gender = _gender;
        this._country = _country;
        this._hobbies = _hobbies;
    }

    public Contact(int _id, String _name, String _dob) {
        this._id = _id;
        this._name = _name;
        this._dob = _dob;
    }

    public byte[] get_photo() {
        return _photo;
    }

    public void set_photo(byte[] _photo) {
        this._photo = _photo;
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String getDob() {
        return _dob;
    }

    public void set_dob(String _dob) {
        this._dob = _dob;
    }

    public String get_gender() {
        return _gender;
    }

    public void set_gender(String _gender) {
        this._gender = _gender;
    }

    public String get_country() {
        return _country;
    }

    public void set_country(String _country) {
        this._country = _country;
    }

    public String get_hobbies() {
        return _hobbies;
    }

    public void set_hobbies(String _hobbies) {
        this._hobbies = _hobbies;
    }
}
