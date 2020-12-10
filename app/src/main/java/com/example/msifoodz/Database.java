package com.example.msifoodz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 04-Jul-18.
 */

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "MsiFoodzDB.db";
    private static final String TABLE_NAME = "OrderDetails";
    private static final int DB_VERSION = 1;
    private static final String TAG = "Basel";

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (Food_name TEXT, Food_Price INTEGER, Food_Quantity INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public List<Cart_food_item_list> getCarts() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Food_name", "Food_Price", "Food_Quantity"};
        String sqlTable = "OrderDetails";

        qb.setTables(sqlTable);
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);

        final List<Cart_food_item_list> results = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {

                do {

                    results.add(new Cart_food_item_list(
                            cursor.getString(cursor.getColumnIndex("Food_name")),
                            cursor.getInt(cursor.getColumnIndex("Food_price")),
                            cursor.getInt(cursor.getColumnIndex("Food_quantity"))
                    ));

                } while (cursor.moveToNext());
                cursor.close();
            }
        }
            return results;
        }

    public void addToCarts(Cart_food_item_list order){

        SQLiteDatabase db = getWritableDatabase();
        try {

            String query = String.format("INSERT INTO OrderDetails(Food_name,Food_price,Food_quantity) VALUES('%s','%s','%s');",
                    order.getFood_name(),
                    order.getFood_price(),
                    order.getQuantity()
            );

            db.execSQL(query);
        }
        catch(Exception e){
            onCreate(db);
        }
    }

    public void cleanCarts(){

        SQLiteDatabase db = getReadableDatabase();
        String query = "DELETE FROM OrderDetails";
        db.execSQL(query);
    }

    public void addToFavourites(String foodId){

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO Favourites(foodId) VALUES('%s');",foodId);
        db.execSQL(query);
    }

    public void removeFromFavourites(String foodId){

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM Favourites WHERE foodId = '%s';",foodId);
        db.execSQL(query);
    }

    public boolean isFavourites(String foodId){

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT * FROM Favourites WHERE foodId = '%s';",foodId);
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount() <=0){

            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public int getCountCarts() {
        int count = 0;
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT COUNT(*) FROM OrderDetails");
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                count = cursor.getInt(0);
            }while(cursor.moveToNext());
        }
        return count;
    }

    public void updateCart(Cart_food_item_list order) {

        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("DefaultLocale") String query = String.format("UPDATE OrderDetails SET quantity = '%s' WHERE Food_name = '%d';",order.getQuantity(),order.getFood_name());
        db.execSQL(query);
    }
}
