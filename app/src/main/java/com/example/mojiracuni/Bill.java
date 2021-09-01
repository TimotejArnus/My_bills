package com.example.mojiracuni;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mojiracuni.ui.main.Autentication;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bill {

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    public static List<Bill> ListsBills = new ArrayList<Bill>();


    static void GetBillsFromDB(){

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("bills").child(uid);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                if (Bill.ListsBills.size() != 0 )
                    Bill.ListsBills.clear();    // FOR NOT WRITING SAME DATA MORE THEN ONE TIME !

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String email = ds .child("email").getValue(String.class);
                    String market = ds .child("market").getValue(String.class);
                    Double price = ds .child("price").getValue(Double.class);
                    Date date = ds .child("date").getValue(Date.class);
                   // Log.d("TAG", email + "/" + market + "/" + price);


                   Bill.ListsBills.add(new Bill(email,market,price,date));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TAG", databaseError.getMessage()); //Don't ignore potential errors!
            }
        };
        uidRef.addListenerForSingleValueEvent(valueEventListener);
    }

    Double GetDataForDate(String ChousenDate){

        GetBillsFromDB();
        Double countPrice = 0.0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = null;

        if (ListsBills.size() == 0)
            return 0.0;

        try {
            date1 = sdf.parse("19/12/2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        Date date2= null;
        try {
            date2 = sdf.parse("19/7/2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf3 = new SimpleDateFormat("dd/MM/yyyy");
        Date date3= null;
        try {
            date3 = sdf.parse("19/1/2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (ChousenDate.toString() == "1 Month"){

            for (Bill bill: ListsBills ) {
                if (bill.date.after(date1)){
                    countPrice = countPrice + bill.Price;
                }
            }

        }else if(ChousenDate.toString() == "6 Months"){

            for (Bill bill: ListsBills ) {
                if (bill.date.after(date2)){
                    countPrice = countPrice + bill.Price;
                }
            }

        }else {

            for (Bill bill: ListsBills ) {
                if (bill.date.after(date3)){
                    countPrice = countPrice + bill.Price;
                }
            }

        }

        return countPrice;
    }

    void AddBillToDB(){
        //String Market, Double Price, Date date
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("bills");

        Bill bill = new Bill(this.email,this.Market,this.Price,this.date);
        bill.email = Autentication.GetUser();

        try {

            reference.child(Autentication.GetIdUser()).push().setValue(bill);

            //reference.push().setValue(bill);
           // reference.setValue(bill);
        }
        catch (Exception e){
            e.printStackTrace();
            return;
        }

    }

    String email;
    String Market;
    Double Price;
    Date date;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getMarket() {
        return Market;
    }

    public void setMarket(String market) {
        Market = market;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Bill(){

    }

    public Bill(String email, String Market, Double Price, Date date){
        this.email = email;
        this.Market = Market;
        this.Price = Price;
        this.date = date;
    }
}
