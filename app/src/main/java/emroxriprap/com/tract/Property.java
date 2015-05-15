package emroxriprap.com.tract;

/**
 * Created by Scott Durica on 5/5/2015.
 */
public class Property {
    private String address, city;
    private int id;

    public Property(){

    }
    public Property(String address, String city){
        this.address = address;
        this.city = city;
    }
    public Property(String address, String city,int id){
        this.address = address;
        this.city = city;
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
}
