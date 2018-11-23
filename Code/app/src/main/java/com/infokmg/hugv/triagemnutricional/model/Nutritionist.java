package com.infokmg.hugv.triagemnutricional.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.infokmg.hugv.triagemnutricional.DAO.FirebaseConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by uealuduslab on 31/07/2018.
 */

public class Nutritionist {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String CRN;
    private String genre;
    private String id;


    public void save(){
        DatabaseReference databaseReference = FirebaseConfig.getDatabaseReference();
        databaseReference.child("nutricionist").child(String.valueOf(getId())).setValue(this);
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapNutricionist = new HashMap<>();

        hashMapNutricionist.put("id", getId());
        hashMapNutricionist.put("email", getEmail());
        hashMapNutricionist.put("senha", getPassword());
        hashMapNutricionist.put("firstName", getFirstName());
        hashMapNutricionist.put("lastName", getLastName());
        hashMapNutricionist.put("genre", getGenre());
        hashMapNutricionist.put("CRN", getCRN());

        return hashMapNutricionist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCRN() {
        return CRN;
    }

    public void setCRN(String CRN) {
        this.CRN = CRN;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
