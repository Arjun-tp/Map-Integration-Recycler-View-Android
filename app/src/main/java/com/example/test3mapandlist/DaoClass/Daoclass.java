package com.example.test3mapandlist.DaoClass;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.test3mapandlist.EntityClass.UserModel;
import java.util.List;

@Dao
public interface Daoclass {

    // Query

    @Insert
    void insertAllData(UserModel model);

    @Query("select * from user")
    List<UserModel> getAllData();

    @Query("delete from user where `key`= :id")
    void deleteData(int id);

    @Query("update user set name= :name, country= :country, birthday= :birthday, gender= :gender, `userImage `=:image, lat=:lat, lng=:lng where `key`= :key")
    void updateData(String name, String country, String birthday, String gender, byte[] image, String lat, String lng, int key);

}
