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

/**
 * Created by Scott Durica on 4/26/2015.
 */
public class DbHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tract.db";

    /* table names */
    private static final String TABLE_ENTRIES = "entries";



    /* entries table fields */
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_HOURS = "hours";
    public static final String COLUMN_RATE = "rate";
    public static final String COLUMN_MATERIALS= "materials";
    public static final String COLUMN_MARKUP= "markup";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_BILLED= "billed";


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
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_DATE + " VARCHAR,"
                + COLUMN_ADDRESS + " VARCHAR,"
                + COLUMN_HOURS + " INTEGER,"
                + COLUMN_RATE + " INTEGER,"
                + COLUMN_MATERIALS + " INTEGER,"
                + COLUMN_MARKUP + " INTEGER,"
                + COLUMN_DESCRIPTION + " VARCHAR,"
                + COLUMN_BILLED + " INTEGER"
                +")";
        Log.d("String is ", CREATE_ENTRIES_TABLE);
        db.execSQL(CREATE_ENTRIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRIES);
        onCreate(db);
    }

    public boolean addEntry(Entry entry){
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE,entry.getDate());
        values.put(COLUMN_ADDRESS,entry.getAddress());
        values.put(COLUMN_HOURS,entry.getHours());
        values.put(COLUMN_RATE,entry.getRate());
        values.put(COLUMN_MATERIALS, entry.getMaterials());
        values.put(COLUMN_MARKUP, entry.getMarkup());
        values.put(COLUMN_DESCRIPTION, entry.getDescription());
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
        values.put(COLUMN_HOURS,entry.getHours());
        values.put(COLUMN_RATE,entry.getRate());
        values.put(COLUMN_MATERIALS, entry.getMaterials());
        values.put(COLUMN_MARKUP, entry.getMarkup());
        values.put(COLUMN_DESCRIPTION, entry.getDescription());
        values.put(COLUMN_BILLED, entry.getBilled());
        SQLiteDatabase db = this.getWritableDatabase();
        long successful = db.update(TABLE_ENTRIES,values,COLUMN_ID + "=" + entry.getId(),null);
        return successful != -1 ? true : false;
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
                e.setHours(Integer.valueOf(cursor.getString(3)));
                e.setRate(Integer.valueOf(cursor.getString(4)));
                e.setMaterials(Integer.valueOf(cursor.getString(5)));
                e.setMarkup(Integer.valueOf(cursor.getString(6)));
                e.setDescription(cursor.getString(7));
                e.setBilled(Integer.valueOf(cursor.getString(8)));


                list.add(e);
            }while(cursor.moveToNext());
        }
        return list;
    }

}
