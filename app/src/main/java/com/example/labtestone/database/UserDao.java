package com.example.labtestone.database;

import com.example.labtestone.Models.UserData;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM PERSON ORDER BY ID")
    List<UserData> loadAllPersons();

    @Insert
    void insertPerson(UserData user);

    @Update
    void updatePerson(UserData user);

    @Delete
    void delete(UserData user);

    @Query("SELECT * FROM PERSON WHERE id = :id")
    UserData loadPersonById(int id);
}
