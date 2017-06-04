package fr.unice.polytech.ihmandroid.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.ihmandroid.model.Event;
import fr.unice.polytech.ihmandroid.model.Product;
import fr.unice.polytech.ihmandroid.model.Store;

/**
 * Created by MSI on 16/05/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {



    private static String DB_NAME = "tobeortohave_database.db";

    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void openDataBase() throws SQLException, IOException {
        //Open the database
        String myPath = myContext.getDatabasePath(DB_NAME).getAbsolutePath();
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if(!dbExist){
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                // Copy the database in assets to the application database.
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database", e);
            }
        }
    }

    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = myContext.getDatabasePath(DB_NAME).getAbsolutePath();
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch(SQLiteException e){
            //database doesn't exist yet.
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null;
    }

    private void copyDataBase() throws IOException{
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = myContext.getDatabasePath(DB_NAME).getAbsolutePath();
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }



    public List<Store> buildStores(){



        Cursor cursor =myDataBase.rawQuery("SELECT * FROM store ORDER BY id ", null);
        cursor.moveToFirst();

        List<Store> stores = new ArrayList<>();

        while (!cursor.isAfterLast()){
            int id;
            String name;
            String city;
            String adress;
            String cityNumber;
            String imageURL;
            String description;
            float lat;
            float lng;


            id = cursor.getInt(0);
            name = cursor.getString(1);
            city = cursor.getString(2);
            adress = cursor.getString(3);
            cityNumber = cursor.getString(4);
            imageURL = cursor.getString(5);
            description = cursor.getString(6);
            lat = cursor.getFloat(7);
            lng = cursor.getFloat(8);


            Store store = new Store(id, name, city, adress, cityNumber, imageURL, description, lat, lng);
            stores.add(store);


            cursor.moveToNext();
        }

        return stores;

    }

    public ArrayList<Product> buildProducts(){
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM product ORDER BY id", null);
        cursor.moveToFirst();

        ArrayList<Product> products = new ArrayList<>();

        while (!cursor.isAfterLast()){

            int id;
            String name;
            String category;
            String image;
            double price;
            String description;


            id = cursor.getInt(0);
            name = cursor.getString(1);
            category = cursor.getString(2);
            image = cursor.getString(3);
            price = cursor.getDouble(4);
            description = cursor.getString(5);


            Product product = new Product(id, name, category, image, price, description);

            if (cursor.getInt(6)==1){
                product.setPromoted();
            }
            if (cursor.getInt(7)==1){
                product.setReduction(cursor.getDouble(8));
            }

            products.add(product);


            cursor.moveToNext();
        }

        return products;
    }


    public List<Store> buildInventories(List<Store> stores, List<Product> products){

        Cursor cursor = myDataBase.rawQuery("SELECT * FROM inventory", null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){

            int storeid;
            int productid;

            productid  = cursor.getInt(0);
            storeid = cursor.getInt(1);


            for (Store store : stores){
                if (store.getId()==storeid){
                    for (Product product : products){
                        if (product.getId()==productid){
                            store.addProduct(product);
                            break;
                        }
                    }
                    break;
                }
            }

            cursor.moveToNext();

        }

        return stores;


    }

    public List<Event> buildEvents(){

        Cursor cursor = myDataBase.rawQuery("SELECT * FROM event ORDER BY name", null);
        cursor.moveToFirst();

        List<Event> events = new ArrayList<>();

        while (!cursor.isAfterLast()){

            String name,description, date;
            float lat, lng;

            name = cursor.getString(0);
            description = cursor.getString(1);
            lat = cursor.getFloat(2);
            lng = cursor.getFloat(3);

            Event event = new Event(name, description, lat, lng);
            events.add(event);

        }

        return events;

    }

}
