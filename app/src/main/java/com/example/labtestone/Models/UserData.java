package com.example.labtestone.Models;

import java.util.Date;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "person")

public class UserData {

    @PrimaryKey(autoGenerate = true)
    int id;
    private String name = "";
    private int age ;
    private Double tuitionFee ;
    private String startDate = "";


    @Ignore
   public UserData(String name,int age,Double tuitionFee,String startDate){
        this.name = name;
        this.age = age;
        this.tuitionFee = tuitionFee;
        this.startDate = startDate;
    }

    public UserData(int id,String name,int age,Double tuitionFee,String startDate){
        this.id = id;
        this.name = name;
        this.age = age;
        this.tuitionFee = tuitionFee;
        this.startDate = startDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Double getTuitionFee() {
        return tuitionFee;
    }

    public void setTuitionFee(Double tuitionFee) {
        this.tuitionFee = tuitionFee;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
