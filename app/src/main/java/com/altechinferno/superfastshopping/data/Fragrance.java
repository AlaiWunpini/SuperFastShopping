package com.altechinferno.superfastshopping.data;

import android.database.Cursor;

/**
 * Created by delaroy on 9/3/17.
 */
public class Fragrance {

    public int id;

    public String name;
    public String description;
    public String imageUrl;
    public Double price;
    public int userRating;


    public Fragrance(Cursor cursor) {
        this.name = cursor.getString(cursor.getColumnIndex(com.altechinferno.superfastshopping.data.FragranceContract.FragranceEntry.COLUMN_NAME));
        this.description = cursor.getString(cursor.getColumnIndex(com.altechinferno.superfastshopping.data.FragranceContract.FragranceEntry.COLUMN_DESCRIPTION));
        this.imageUrl = cursor.getString(cursor.getColumnIndex(com.altechinferno.superfastshopping.data.FragranceContract.FragranceEntry.COLUMN_IMAGE));
        this.price = cursor.getDouble(cursor.getColumnIndex(com.altechinferno.superfastshopping.data.FragranceContract.FragranceEntry.COLUMN_PRICE));
        this.userRating = cursor.getInt(cursor.getColumnIndex(com.altechinferno.superfastshopping.data.FragranceContract.FragranceEntry.COLUMN_USERRATING));
    }

}
