package com.example.mojiracuni.ui.main;

import com.google.firebase.auth.FirebaseAuth;

public class Autentication {
    public static FirebaseAuth fAuth = FirebaseAuth.getInstance();

    public static  String GetUser(){
        return fAuth.getCurrentUser().getEmail().toString();
    }
    public static  String GetIdUser(){
        return fAuth.getCurrentUser().getUid();
    }
}
