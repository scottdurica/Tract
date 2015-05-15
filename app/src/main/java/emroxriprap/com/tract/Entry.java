package emroxriprap.com.tract;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 4/25/15.
 */
public class Entry {
    private String address,description,date;
    private double rate,materials,markup,hours,total;
    private int id, billed;

    public Entry(){

    };

    public Entry(String date, String address, double rate, double materials, double markup, double hours, String description,double total,int billed){
        this.date = date;
        this.address = address;
        this.rate = rate;
        this.materials = materials;
        this.markup = markup;
        this.hours = hours;
        this.description = description;
        this.total = total;
        this.billed = billed;


    }
    public Entry(String date, String address, double rate, double materials, double markup, double hours, String description,double total, int billed, int id){
        this.date = date;
        this.address = address;
        this.rate = rate;
        this.materials = materials;
        this.markup = markup;
        this.hours = hours;
        this.description = description;
        this.total =total;
        this.billed = billed;
        this.id = id;

    }

    public double convertToCents(double amount){
        return (double)(amount * 100);
    }
    public static List<Entry> initTestData(){
        List<Entry> list = new ArrayList<Entry>();

        for (int i=0;i<20; i++){
            Entry e =  new Entry("" + i + "/0/2015","" + i + " Main Street",35, 50,12,20,"Whatever",0,i);
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getMaterials() {
        return materials;
    }

    public void setMaterials(double materials) {
        this.materials = materials;
    }

    public double getMarkup() {
        return markup;
    }

    public void setMarkup(double markup) {
        this.markup = markup;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public int getBilled() {
        return billed;
    }

    public void setBilled(int billed) {
        this.billed = billed;
    }

    public double getTotal() { return total; }

    public void setTotal(double total) {this.total = total;}
}
