package emroxriprap.com.tract;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 4/25/15.
 */
public class Entry {
    private String address,description,date;
    private int rate,materials,markup,hours;
    private int id, billed;

    public Entry(){

    };

    public Entry(String date, String address, double rate, double materials, double markup, double hours, String description,int billed){
        this.date = date;
        this.address = address;
        this.rate = convertToCents(rate);
        this.materials = convertToCents(materials);
        this.markup = convertToCents(markup);
        this.hours = convertToCents(hours);
        this.description = description;
        this.billed = billed;


    }
    public Entry(String date, String address, double rate, double materials, double markup, double hours, String description,int billed, int id){
        this.date = date;
        this.address = address;
        this.rate = convertToCents(rate);
        this.materials = convertToCents(materials);
        this.markup = convertToCents(markup);
        this.hours = convertToCents(hours);
        this.description = description;
        this.billed = billed;
        this.id = id;

    }

    public int convertToCents(double amount){
        return (int)(amount * 100);
    }
    public static List<Entry> initTestData(){
        List<Entry> list = new ArrayList<Entry>();

        for (int i=0;i<20; i++){
            Entry e =  new Entry("" + i + "/0/2015","" + i + " Main Street",35, 50,12,20,"Whatever",i);
            list.add(e);
        }

        return list;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getMaterials() {
        return materials;
    }

    public void setMaterials(int materials) {
        this.materials = materials;
    }

    public int getMarkup() {
        return markup;
    }

    public void setMarkup(int markup) {
        this.markup = markup;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBilled() {
        return billed;
    }

    public void setBilled(int billed) {
        this.billed = billed;
    }
}
