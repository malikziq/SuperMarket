package com.example.supermarket.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.supermarket.models.pojo.Cart;
import com.example.supermarket.models.pojo.Favorite;
import com.example.supermarket.models.pojo.Item;
import com.example.supermarket.models.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DataBaseHelper";

    // DataBase Version
    public static final int DATABASE_VERSION = 1;

    // DataBase Name
    public static final String DATABASE_NAME = "supermarket";

    // Tables Names
    public static final String TABLE_USER = "users";
    public static final String TABLE_GOOD = "goods";
    public static final String TABLE_CART = "carts";
    public static final String TABLE_FAVORITE = "favorites";

    // Common column name
    public static final String KEY_ID = "id";

    // USERS TABLE - column name
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_CITY = "city";
    public static final String KEY_ROLE = "role";

    // GOOD TABLE - column name
    public static final String KEY_TITLE = "title";
    public static final String KEY_TYPE = "type";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGEURL = "imageurl";
    public static final String KEY_HEIGHT = "height";
    public static final String KEY_WIDTH = "width";
    public static final String KEY_PRICE = "price";
    public static final String KEY_RATING = "rating";

    // Common FAVORITES & CARTS TABLES - column name
    public static final String KEY_USER_ID = "uid";
    public static final String KEY_GOOD_ID = "gid";

    // CART TABLE - column name
    public static final String KEY_AMOUNT = "amount";

    // Table create statement
    // USER TABLE
    public static final String CREATE_TABLE_USER = "CREATE TABLE " +
            TABLE_USER + " (" + KEY_ID + " INTEGER PRIMARY KEY, " +
            KEY_FIRST_NAME + " TEXT, " +
            KEY_LAST_NAME + " TEXT, " +
            KEY_EMAIL + " TEXT NOT NULL UNIQUE, " +
            KEY_PASSWORD + " TEXT, " +
            KEY_PHONE + " TEXT, " +
            KEY_CITY + " TEXT, " +
            KEY_ROLE + " TEXT, " +
            KEY_GENDER + " TEXT)";

    // GOOD TABLE
    public static final String CREATE_TABLE_GOOD = "CREATE TABLE " +
            TABLE_GOOD + " (" + KEY_ID + " INTEGER PRIMARY KEY, " +
            KEY_TITLE + " TEXT NOT NULL UNIQUE, " +
            KEY_TYPE + " TEXT, " +
            KEY_DESCRIPTION + " TEXT, " +
            KEY_IMAGEURL + " TEXT, " +
            KEY_HEIGHT + " INTEGER, " +
            KEY_WIDTH + " INTEGER, " +
            KEY_PRICE + " DOUBLE, " +
            KEY_RATING + " INTEGER )";

    // FAVORITE TABLE
    public static final String CREATE_TABLE_FAVORITE = "CREATE TABLE " +
            TABLE_FAVORITE + " (" + KEY_ID + " INTEGER PRIMARY KEY, " +
            KEY_USER_ID + " INTEGER, " +
            KEY_GOOD_ID + " INTEGER )";

    // CART TABLE
    public static final String CREATE_TABLE_CART = "CREATE TABLE " +
            TABLE_CART + " (" + KEY_ID + " INTEGER PRIMARY KEY, " +
            KEY_USER_ID + " INTEGER, " +
            KEY_GOOD_ID + " INTEGER," +
            KEY_AMOUNT + " INTEGER )";


    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i(TAG, "DataBaseHelper: Getting instance...");
    }

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.i(TAG, "DataBaseHelper: Getting instance...");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Log.i(TAG, "onCreate: Creating db tables");
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(CREATE_TABLE_GOOD);
        sqLiteDatabase.execSQL(CREATE_TABLE_CART);
        sqLiteDatabase.execSQL(CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        // Drop old tables
        Log.i(TAG, "onUpgrade:  droping old tables ... ");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_GOOD);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);

        // creates new ones
        Log.i(TAG, "onUpgrade:  creating new tables ...");
        onCreate(sqLiteDatabase);
    }


    // USER TABLE CRUD
    public long createUser(User user) {
        Log.i(TAG, "createUser: create new user..." + user.toString());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, user.getFirstName());
        values.put(KEY_LAST_NAME, user.getLastName());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PHONE, user.getPhone());
        values.put(KEY_GENDER, user.getGender());
        values.put(KEY_CITY, user.getCity());
        values.put(KEY_ROLE, user.getRole());

        long userId = db.insert(TABLE_USER, null, values);

        Log.i(TAG, "createUser: userId - >" + userId);
        getUser((int) userId);
        return userId;
    }

    public User getUser(int userId) {
        Log.i(TAG, "getUser: get user with id=" + userId);
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * " +
                " FROM " + TABLE_USER +
                " WHERE " + KEY_ID + " = " + userId;

        Cursor c = db.rawQuery(selectQuery, null);

        User user = null;

        if (c.moveToFirst()) {
            user = new User();
            user.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            user.setFirstName(c.getString(c.getColumnIndex(KEY_FIRST_NAME)));
            user.setLastName(c.getString(c.getColumnIndex(KEY_LAST_NAME)));
            user.setCity(c.getString(c.getColumnIndex(KEY_CITY)));
            user.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
            user.setGender(c.getString(c.getColumnIndex(KEY_GENDER)));
            user.setPhone(c.getString(c.getColumnIndex(KEY_PHONE)));
            user.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
            user.setRole(c.getString(c.getColumnIndex(KEY_ROLE)));
            Log.i(TAG, "getUser: -> " + user.toString());
        }
        c.close();

        return user;
    }

    public User getUserByEmail(String email) {
        Log.i(TAG, "getUser: get user with email=" + email);
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * " +
                " FROM " + TABLE_USER +
                " WHERE " + KEY_EMAIL + " =?";

        Log.i(TAG, "getUserByEmail: " + selectQuery);

        Cursor c = db.rawQuery(selectQuery, new String[]{email});

        Log.i(TAG, "getUserByEmail: c: " + c.toString());
        User user = null;

        if (c.moveToFirst()) {

            user = new User();
            user.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            user.setFirstName(c.getString(c.getColumnIndex(KEY_FIRST_NAME)));
            user.setLastName(c.getString(c.getColumnIndex(KEY_LAST_NAME)));
            user.setCity(c.getString(c.getColumnIndex(KEY_CITY)));
            user.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
            user.setGender(c.getString(c.getColumnIndex(KEY_GENDER)));
            user.setPhone(c.getString(c.getColumnIndex(KEY_PHONE)));
            user.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
            user.setRole(c.getString(c.getColumnIndex(KEY_ROLE)));
        }

        return user;
    }

    public List<User> getAllUsers() {
        Log.i(TAG, "getAllUsers: ...");
        List<User> users = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * " +
                " FROM " + TABLE_USER + " WHERE " + KEY_ROLE + " != 'admin'";

        Cursor c = db.rawQuery(selectQuery, null);

        while (c.moveToNext()) {
            User user = new User();
            user.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            user.setFirstName(c.getString(c.getColumnIndex(KEY_FIRST_NAME)));
            user.setLastName(c.getString(c.getColumnIndex(KEY_LAST_NAME)));
            user.setCity(c.getString(c.getColumnIndex(KEY_CITY)));
            user.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
            user.setGender(c.getString(c.getColumnIndex(KEY_GENDER)));
            user.setPhone(c.getString(c.getColumnIndex(KEY_PHONE)));
            user.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
            user.setRole(c.getString(c.getColumnIndex(KEY_ROLE)));

            Log.i(TAG, "getAllUsers: user-> " + user.toString());
            users.add(user);
        }
        c.close();
        return users;

    }

    public void updateUser(User user) {
        Log.i(TAG, "updateUser: updating..." + user.toString());

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, user.getFirstName());
        values.put(KEY_LAST_NAME, user.getLastName());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PHONE, user.getPhone());
        values.put(KEY_GENDER, user.getGender());
        values.put(KEY_CITY, user.getCity());
        values.put(KEY_ROLE, user.getRole());

        db.update(TABLE_USER, values, KEY_ID + " = " + user.getId(), null);
        Log.i(TAG, "updateUser: done...");
    }


    public void removeUser(int userId) {
        Log.i(TAG, "removeUser: delete user:" + userId);

        // delete cart first
        deleteCartByUserId(userId);

        // delete favorites
        deleteFavoriteByUserId(userId);

        Log.i(TAG, "removeUser: deleting cart of user: " + userId);
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_USER, KEY_ID + " = " + userId, null);
        Log.i(TAG, "removeUser: result = " + result);


    }

    private void deleteFavoriteByUserId(int userId) {
        Log.i(TAG, "deleteFavoriteByUserId: deleting favorite of user: " + userId);
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_FAVORITE, KEY_USER_ID + " = " + userId, null);
        Log.i(TAG, "deleteFavoriteByUserId: result = " + result);
    }
    //------------------------------------------------------------------------------

    // GOOD TABLE CURD
    public void createGood(Item good) {

        Log.i(TAG, "createGood: insert new good...");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, good.getTitle());
        values.put(KEY_TYPE, good.getType());
        values.put(KEY_PRICE, good.getPrice());
        values.put(KEY_HEIGHT, good.getHeight());
        values.put(KEY_WIDTH, good.getWidth());
        values.put(KEY_DESCRIPTION, good.getDescription());
        values.put(KEY_IMAGEURL, good.getImageurl());
        values.put(KEY_RATING, good.getRating());

        db.insert(TABLE_GOOD, null, values);



    }

    public List<Item> getAllItems() {
        Log.i(TAG, "getAllItems: getting all items..");
        List<Item> items = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * " +
                " FROM " + TABLE_GOOD;

        Log.i(TAG, "getAllItems: executing query: " + selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        while (c.moveToNext()) {
            Item item = new Item();
            item.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            item.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
            item.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
            item.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
            item.setImageurl(c.getString(c.getColumnIndex(KEY_IMAGEURL)));
            item.setHeight(c.getInt(c.getColumnIndex(KEY_HEIGHT)));
            item.setWidth(c.getInt(c.getColumnIndex(KEY_WIDTH)));
            item.setPrice(c.getDouble(c.getColumnIndex(KEY_PRICE)));
            item.setRating(c.getInt(c.getColumnIndex(KEY_RATING)));

            items.add(item);
        }
        c.close();

        return items;
    }
    //------------------------------------------------------------------------------


    // FAVORITE TABLE CURD
    public long createFavorite(Favorite favorite) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, favorite.getUserId());
        values.put(KEY_GOOD_ID, favorite.getGoodId());

        return db.insert(TABLE_FAVORITE, null, values);
    }

    public void removeFavorite(Favorite favorite) {
        Log.i(TAG, "removeFavorite: deleting...");
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_FAVORITE
                , KEY_USER_ID + " = " + favorite.getUserId()
                        + " AND " + KEY_GOOD_ID + " = " + favorite.getGoodId()
                , null);
    }

    public List<Item> getAllFavoriteByUserId(int userId) {
        Log.i(TAG, "getAllFavoriteByUserId: adding ... ");
        List<Item> items = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT g.* " +
                " FROM " + TABLE_GOOD + " g,  " + TABLE_FAVORITE + " f " +
                " WHERE g." + KEY_ID + " = f." + KEY_GOOD_ID + " " +
                " AND f." + KEY_USER_ID + " = " + userId;

        Cursor c = db.rawQuery(selectQuery, null);

        while (c.moveToNext()) {
            Item item = new Item();
            item.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            item.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
            item.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
            item.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
            item.setImageurl(c.getString(c.getColumnIndex(KEY_IMAGEURL)));
            item.setHeight(c.getInt(c.getColumnIndex(KEY_HEIGHT)));
            item.setWidth(c.getInt(c.getColumnIndex(KEY_WIDTH)));
            item.setPrice(c.getDouble(c.getColumnIndex(KEY_PRICE)));
            item.setRating(c.getInt(c.getColumnIndex(KEY_RATING)));

            items.add(item);
        }
        c.close();

        return items;
    }

    //------------------------------------------------------------------------------

    // CART TABLE CURD
    public void createCart(Cart cart) {

        // TODO if exists add them up
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, cart.getUserId());
        values.put(KEY_GOOD_ID, cart.getGoodId());
        values.put(KEY_AMOUNT, cart.getAmount());

        db.insert(TABLE_CART, null, values);
        Log.i(TAG, "createCart: done..");
    }

    public List<Item> getAllCartItemByUserId(int userId) {
        List<Item> items = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT g.*, c. " + KEY_AMOUNT +
                " FROM " + TABLE_GOOD + " g,  " + TABLE_CART + " c " +
                " WHERE g." + KEY_ID + " = c." + KEY_GOOD_ID + " " +
                " AND c." + KEY_USER_ID + " = " + userId;

        Cursor c = db.rawQuery(selectQuery, null);

        while (c.moveToNext()) {
            Item item = new Item();
            item.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            item.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
            item.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
            item.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
            item.setImageurl(c.getString(c.getColumnIndex(KEY_IMAGEURL)));
            item.setHeight(c.getInt(c.getColumnIndex(KEY_HEIGHT)));
            item.setWidth(c.getInt(c.getColumnIndex(KEY_WIDTH)));
            item.setPrice(c.getDouble(c.getColumnIndex(KEY_PRICE)));
            item.setRating(c.getInt(c.getColumnIndex(KEY_RATING)));
            item.setAmount(c.getInt(c.getColumnIndex(KEY_AMOUNT)));

            items.add(item);
        }
        c.close();

        Log.i(TAG, "getAllCartItemByUserId: cart items: " + items.size());
        return items;
    }

    public void deleteCartByUserId(int userId) {

        Log.i(TAG, "deleteCartByUserId: deleting cart of user: " + userId);
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_CART, KEY_USER_ID + " = " + userId, null);
        Log.i(TAG, "deleteCartByUserId: result = " + result);
    }

    //------------------------------------------------------------------------------


}
