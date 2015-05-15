package emroxriprap.com.tract.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import emroxriprap.com.tract.Entry;
import emroxriprap.com.tract.Property;

/**
 * Created by Scott Durica on 4/26/2015.
 */
public class DbHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tract.db";

    /* table names */
    private static final String TABLE_ENTRIES = "entries";
    private static final String TABLE_PROPERTIES = "properties";



    /* entries table fields */
    public static final String COLUMN_ENTRY_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_HOURS = "hours";
    public static final String COLUMN_RATE = "rate";
    public static final String COLUMN_MATERIALS= "materials";
    public static final String COLUMN_MARKUP= "markup";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TOTAL = "total";
    public static final String COLUMN_BILLED= "billed";

    /* properties table fields */
    public static final String COLUMN_PROPERTY_ID= "id";
    public static final String COLUMN_PROPERTY_ADDRESS= "address";
    public static final String COLUMN_PROPERTY_CITY= "city";


    /* Constructors */
    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);

    }
    public DbHandler(Context context,String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ENTRIES_TABLE = "CREATE TABLE " + TABLE_ENTRIES + "("
                + COLUMN_ENTRY_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_DATE + " VARCHAR,"
                + COLUMN_ADDRESS + " VARCHAR,"
                + COLUMN_HOURS + " INTEGER,"
                + COLUMN_RATE + " INTEGER,"
                + COLUMN_MATERIALS + " INTEGER,"
                + COLUMN_MARKUP + " INTEGER,"
                + COLUMN_DESCRIPTION + " VARCHAR,"
                + COLUMN_TOTAL + " INTEGER,"
                + COLUMN_BILLED + " INTEGER"
                +")";
        String CREATE_TABLE_PROPERTIES = "CREATE TABLE " + TABLE_PROPERTIES + "("
                + COLUMN_PROPERTY_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_PROPERTY_ADDRESS + " VARCHAR,"
                + COLUMN_PROPERTY_CITY + " VARCHAR"
                +")";
//        Log.d("String is ", CREATE_ENTRIES_TABLE);
        db.execSQL(CREATE_ENTRIES_TABLE);
        db.execSQL(CREATE_TABLE_PROPERTIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTIES);
        onCreate(db);
    }

    public boolean addEntry(Entry entry){
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE,entry.getDate());
        values.put(COLUMN_ADDRESS,entry.getAddress());
        values.put(COLUMN_HOURS,entry.getHours()*100);
        values.put(COLUMN_RATE,entry.getRate()*100);
        values.put(COLUMN_MATERIALS, entry.getMaterials()*100);
        values.put(COLUMN_MARKUP, entry.getMarkup()*100);
        values.put(COLUMN_DESCRIPTION, entry.getDescription());
        values.put(COLUMN_TOTAL, entry.getTotal()*100);
        values.put(COLUMN_BILLED, entry.getBilled());
        SQLiteDatabase db = this.getWritableDatabase();
        long finished = db.insert(TABLE_ENTRIES,null,values);
        if (finished != -1){
            return true;
        }
        return false;
    }
    public boolean updateEntry(Entry entry){
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE,entry.getDate());
        values.put(COLUMN_ADDRESS,entry.getAddress());
        values.put(COLUMN_HOURS,entry.getHours()*100);
        values.put(COLUMN_RATE,entry.getRate()*100);
        values.put(COLUMN_MATERIALS, entry.getMaterials()*100);
        values.put(COLUMN_MARKUP, entry.getMarkup()*100);
        values.put(COLUMN_DESCRIPTION, entry.getDescription());
        values.put(COLUMN_TOTAL, entry.getTotal()*100);
        values.put(COLUMN_BILLED, entry.getBilled());
        SQLiteDatabase db = this.getWritableDatabase();
        long successful = db.update(TABLE_ENTRIES,values,COLUMN_ENTRY_ID + "=" + entry.getId(),null);
        return successful != -1 ? true : false;
    }
    public boolean deleteEntry(int entryId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ENTRIES,COLUMN_ENTRY_ID + "=" + entryId,null)>0;
    }
    public List<Entry> getAllEntries(){
        List<Entry> list = new ArrayList<Entry>();
        String query = "Select * from " + TABLE_ENTRIES + "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                Entry e = new Entry();
                e.setId((Integer.parseInt(cursor.getString(0))));
                e.setDate(cursor.getString(1));
                e.setAddress(cursor.getString(2));
                e.setHours(Double.valueOf(cursor.getString(3))/100);
                e.setRate(Double.valueOf(cursor.getString(4))/100);
                e.setMaterials(Double.valueOf(cursor.getString(5))/100);
                e.setMarkup(Double.valueOf(cursor.getString(6))/100);
                e.setDescription(cursor.getString(7));
                e.setTotal(Double.valueOf(cursor.getString(8))/100);
                e.setBilled(Integer.valueOf(cursor.getString(9)));


                list.add(e);
            }while(cursor.moveToNext());
        }
        return list;
    }
    public boolean entriesExistForProperty(String address){
        String query = "Select * from " + TABLE_ENTRIES + " where " +
                COLUMN_ADDRESS + " =?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{address});
        Log.d("Cursor count: ", "" + cursor.getCount());
        if (cursor.getCount()>0){
            return true;
        }

        return false;
    }
    public List<Property>getAllProperties(){
        List<Property> list = new ArrayList<Property>();
        String query = "Select * from " + TABLE_PROPERTIES + "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                Property p = new Property();
                p.setId((Integer.parseInt(cursor.getString(0))));
                p.setAddress(cursor.getString(1));
                p.setCity(cursor.getString(2));
                list.add(p);
            }while(cursor.moveToNext());
        }
        return list;
    }
    public List<String>getAllPropertyAddresses(){
        List<String> list = new ArrayList<String>();
        String query = "Select * from " + TABLE_PROPERTIES + "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                String s = cursor.getString(1);
                list.add(s);
            }while(cursor.moveToNext());
        }
        return list;
    }
    public boolean addProperty(Property property){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROPERTY_ADDRESS,property.getAddress());
        values.put(COLUMN_PROPERTY_CITY,property.getCity());
        SQLiteDatabase db = this.getWritableDatabase();
        long finished = db.insert(TABLE_PROPERTIES,null,values);
        if (finished != -1){
            return true;
        }
        return false;
    }
    public boolean updateProperty(Property property){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROPERTY_ADDRESS,property.getAddress());
        values.put(COLUMN_PROPERTY_CITY,property.getCity());

        SQLiteDatabase db = this.getWritableDatabase();
        long successful = db.update(TABLE_PROPERTIES,values,COLUMN_PROPERTY_ID + "=" + property.getId(),null);
        return successful != -1 ? true : false;
    }
    public boolean deleteProperty(int propertyId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PROPERTIES,COLUMN_PROPERTY_ID + "=" + propertyId,null)>0;
    }
}
