package com.example.supermarket.modelViews;

import android.util.Log;

import com.example.supermarket.models.pojo.User;
import com.example.supermarket.utils.DataBaseHelper;

import java.util.List;

public class UsersModelView {
    private static final String TAG = "UsersModelView";

    private DataBaseHelper dataBaseHelper;

    public UsersModelView() {
        Log.i(TAG, "UsersModelView: Instance created...");

    }

    public void setDataBaseHelper(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }

    public User isUserRegsted(String userEmail) {

        Log.i(TAG, "isUserRegsted: email: " + userEmail);
        User u = dataBaseHelper.getUserByEmail(userEmail);
        if (u == null)
            return null;

        Log.i(TAG, "isUserRegsted: user ->" + u.toString());
        return u;
    }

    public long registerUser(User user) {
        Log.i(TAG, "registerUser: Starting ...");
        long userId = dataBaseHelper.createUser(user);
        Log.i(TAG, "registerUser: userId -> " + userId);

        if (userId == -1)
            return -1;

        return userId;
    }

    public User getUserById(int userId) {
        Log.i(TAG, "getUserById: getting userId= " + userId);

        User u = dataBaseHelper.getUser(userId);

        Log.i(TAG, "getUserById: user: -> " + u.toString());

        return u;
    }

    public void getAllUsers(){

        List<User> users = dataBaseHelper.getAllUsers();
    }

    public void updateUser(User user) {
        Log.i(TAG, "updateUser: ...");
        dataBaseHelper.updateUser(user);
    }

    public List<User> getAllCustomers(){
        return dataBaseHelper.getAllUsers();
    }

    public void deleteCustomer(int userId){
        dataBaseHelper.removeUser(userId);
    }
}
