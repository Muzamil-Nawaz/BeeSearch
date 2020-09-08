package com.example.madproject;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String name;
    public String profession;
    public String description;
    public String email;
    public String password;
    public String nic;
    public String experience;
    public String phone;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String city;
    public User(){

    }

    public User(String name, String profession, String description, String email, String password, String nic, String experience, String phone) {
        this.name = name;
        this.profession = profession;
        this.description = description;
        this.email = email;
        this.password = password;
        this.nic = nic;
        this.experience = experience;
        this.phone = phone;
    }

    public String getName() {
        return this.name;
    }

    public String getProfession() {
        return this.profession;
    }

    public String getDescription() {
        return this.description;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return password;
    }

    public String getNic() {
        return this.nic;
    }

    public String getExperience() {
        return this.experience;
    }

    public String getPhone() {
        return this.phone;
    }

    public String toString(){
        return getName()+" "+getProfession()+",\n"+getDescription();
    }


}
