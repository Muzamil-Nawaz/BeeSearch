package com.example.madproject;


import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.common.data.DataHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static DatabaseReference userReference =null;
    private static DatabaseReference professionsReference = null;
    private static DatabaseReference citiesReference = null;
    static List<User> usersList=null;
    static User u= new User();


    public static DatabaseReference getUserReference(){
        if(userReference==null){
            userReference = FirebaseDatabase.getInstance().getReference("users");
            return userReference;
        }
        return userReference;
    }
    public static DatabaseReference getProfessionReference(){
        if(professionsReference==null){
            professionsReference = FirebaseDatabase.getInstance().getReference("Professions");
            return professionsReference;
        }
        return professionsReference;
    }
    public static DatabaseReference getCitiesReference(){
        if(citiesReference==null){
            citiesReference = FirebaseDatabase.getInstance().getReference("Cities");
            return citiesReference;
        }
        return citiesReference;
    }

    public static List<String> getAllProfessions(){
        final List<String> professions = new ArrayList<>();
        DatabaseReference reference = DatabaseHandler.getProfessionReference();
        Query checkUser = reference.orderByValue();
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {
                    for (DataSnapshot childSnapShot : snapshot.getChildren()){
                        professions.add(childSnapShot.getValue().toString());
                    }

                }
                else{
                   // Toast.makeText(getApplicationContext(),"No data Available",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        return professions;

    }
    public static List<User> getAllUsers(String city, String profession){
        System.out.println(city+" "+profession);
        List<User> userList = new ArrayList<>();
        try {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            Query users = reference.child("users");
            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for(DataSnapshot postSnapshot : snapshot.getChildren()){
                            User user =  postSnapshot.getValue(User.class);
                            System.out.println(user.getEmail()+" "+user.getCity());
                            if(user.getProfession().equals(profession) && user.getCity().equals(city))
                                userList.add(user);

                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            return userList;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return usersList;
    }
    public static List<String> getAllCities(){
        final List<String> cities = new ArrayList<>();
        DatabaseReference reference = DatabaseHandler.getCitiesReference();
        Query checkUser = reference.orderByValue();
        try {

            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        for (DataSnapshot childSnapShot : snapshot.getChildren()) {
                            cities.add(childSnapShot.getValue().toString());
                        }

                    } else {
                        // Toast.makeText(getApplicationContext(),"No data Available",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
            return cities;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static List<User> getSearchResults(String city, String profession){

            List<User> users = DatabaseHandler.getAllUsers(city,profession);
            return users;
//            getUsers.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                    if (snapshot.exists()) {
//                        for (User u : users) {
//                            if (snapshot.child(u.getName()).exists()) {
//                                if (snapshot.child(u.getName()).child("profession").equals(profession) && snapshot.child(u.getName()).child("city").equals(city)) {
//                                    displayUsers.add((User) snapshot.child(u.getName()).getValue());
//                                    System.out.println(snapshot.child(u.getName()).child("email"));
//                                }
//
//                            }
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//            return displayUsers;

    }
    public static User getUserByName(String name) {
        List<User> users = DatabaseHandler.getAllUsers();
        System.out.println("Name:"+name);
        for(User us :users){
            if(us.getName().equals(name)){
                return us;
            }

        }
        return u;
    }
    public static List<User> getAllUsers(){
        List<User> userList = new ArrayList<>();
        try {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            Query users = reference.child("users");
            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for(DataSnapshot postSnapshot : snapshot.getChildren()){
                            User user =  postSnapshot.getValue(User.class);
                            System.out.println("card data :"+postSnapshot.getValue(User.class));
                            userList.add(postSnapshot.getValue(User.class));

                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            return userList;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return usersList;
    }
}
