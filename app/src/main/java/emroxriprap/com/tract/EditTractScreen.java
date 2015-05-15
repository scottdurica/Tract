package emroxriprap.com.tract;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import emroxriprap.com.tract.db.DbHandler;


public class EditTractScreen extends ActionBarActivity implements View.OnClickListener {
    Spinner address;
    EditText description,rate,materials,markup,hours;
    int id;
    CheckBox billed;
    TextView total;
    String callingClass;
    boolean isNewEntry;
    Button delete,update,addToDb;
    String date;

    String addressArray;
    private String dialogAddress;
    final  Context mContext = EditTractScreen.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date = getIntent().getStringExtra("date");
        setTitle(date);

        setContentView(R.layout.activity_edit_tract_screen);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());
        callingClass = null;
        callingClass = getIntent().getStringExtra("callingActivity");
        isNewEntry = getIntent().getBooleanExtra("newEntry",false);
        initViews();
    }

    private void initViews(){
        address = (Spinner)findViewById(R.id.s_address);
        description = (EditText)findViewById(R.id.et_description);
        rate = (EditText)findViewById(R.id.et_rate);
        hours = (EditText)findViewById(R.id.et_hours);
        materials = (EditText)findViewById(R.id.et_materials);
        markup = (EditText)findViewById(R.id.et_markup);
        billed = (CheckBox)findViewById(R.id.cb_is_entered);
        total = (TextView)findViewById(R.id.tv_total);


        delete = (Button)findViewById(R.id.b_delete);
        update = (Button)findViewById(R.id.b_property_update);
        addToDb = (Button)findViewById(R.id.b_add_to_db);
        delete.setOnClickListener(this);
        update.setOnClickListener(this);
        addToDb.setOnClickListener(this);
        showOrHideButtons(isNewEntry);
        if (isNewEntry == false) {

            id =Integer.valueOf(this.getIntent().getIntExtra("id",-1));
//            address.setText(this.getIntent().getStringExtra("address"));
            hours.setText(Double.toString(this.getIntent().getDoubleExtra("hours", -1)));
            rate.setText(Double.toString(this.getIntent().getDoubleExtra("rate", -1)));
            materials.setText(Double.toString(this.getIntent().getDoubleExtra("materials", -1) ));
            markup.setText(Double.toString(this.getIntent().getDoubleExtra("markup", -1)));
            description.setText(this.getIntent().getStringExtra("description"));
            total.setText(Double.toString(this.getIntent().getDoubleExtra("total", -1)));
            int billedVal = this.getIntent().getIntExtra("billed",0);
            if (billedVal == 1) billed.setChecked(true);

        }
        addItemsToAddressSpinner(addressArray,this);
//        tempLoadArray();
        setListenerOnMoneyFields();
    }
//    private void tempLoadArray(){
//        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putInt(arrayName +"_size", array.length);
//        for(int i=0;i<array.length;i++)
//            editor.putString(arrayName + "_" + i, array[i]);
//        editor.commit();
//    }
    private void addItemsToAddressSpinner(String array, Context context){
        DbHandler db = new DbHandler(this);
        List<String> list = db.getAllPropertyAddresses();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        address.setAdapter(dataAdapter);

    }
    private void showOrHideButtons(boolean isNewEntry) {
        if (isNewEntry){
            delete.setVisibility(View.INVISIBLE);
            update.setVisibility(View.INVISIBLE);
            addToDb.setVisibility(View.VISIBLE);
        }else{
            delete.setVisibility(View.VISIBLE);
            update.setVisibility(View.VISIBLE);
            addToDb.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_tract_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings_property_list) {
            Intent intent = new Intent(EditTractScreen.this,PropertyListScreen.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(EditTractScreen.this).toBundle());

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.b_add_to_db:
                if (validateFields()){
                    DbHandler db = new DbHandler(this);
                    int billedVal =0;
                    if (billed.isChecked())billedVal = 1;
                    Entry entry = new Entry(date, String.valueOf(address.getSelectedItem()), Double.valueOf(rate.getText().toString()),
                    Double.valueOf(materials.getText().toString()),Double.valueOf(markup.getText().toString()),
                    Double.valueOf(hours.getText().toString()), description.getText().toString(),
                    Double.valueOf(total.getText().toString()),
                    billedVal);
                    if (db.addEntry(entry)){
                        Toast.makeText(this,"Data Saved",Toast.LENGTH_LONG).show();

                    }else
                        Toast.makeText(this,"Error! Data not saved.",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EditTractScreen.this,MenuScreen.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(EditTractScreen.this).toBundle());
                }else
                    Toast.makeText(this,"Fill out all fields",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(EditTractScreen.this,MenuScreen.class);
//                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(EditTractScreen.this).toBundle());
                break;

            case R.id.b_delete:
                DbHandler db1 = new DbHandler(this);
                if(db1.deleteEntry(id))
                    Toast.makeText(this,"Entry Deleted",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditTractScreen.this,MenuScreen.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(EditTractScreen.this).toBundle());
                break;

            case R.id.b_property_update:
                if (validateFields()){
                    DbHandler db = new DbHandler(this);
                    int billedVal =0;
                    if (billed.isChecked())billedVal = 1;
                    Entry entry = new Entry(date, String.valueOf(address.getSelectedItem()), Double.valueOf(rate.getText().toString()),
                            Double.valueOf(materials.getText().toString()),Double.valueOf(markup.getText().toString()),
                            Double.valueOf(hours.getText().toString()), description.getText().toString(),
                            Double.valueOf(total.getText().toString()),
                            billedVal,id);

                    if (db.updateEntry(entry)){
                        Toast.makeText(this,"Data updated",Toast.LENGTH_LONG).show();
                        intent = new Intent(EditTractScreen.this,MenuScreen.class);
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(EditTractScreen.this).toBundle());
                    }else
                        Toast.makeText(this,"Error! Data not updated.",Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(this,"Fill out all fields",Toast.LENGTH_SHORT).show();
//                intent = new Intent(EditTractScreen.this,MenuScreen.class);
//                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(EditTractScreen.this).toBundle());
                break;
        }
    }
    private void setListenerOnMoneyFields(){

        hours.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final double hoursVal,rateVal,materialVal,markupVal;
                if(hours.getText().length() > 0 && hours.getText().toString().equalsIgnoreCase(".")==false){
                    hoursVal = Double.parseDouble(hours.getText().toString());
                    rateVal = (rate.getText().toString().equalsIgnoreCase(""))? 0:Double.parseDouble(rate.getText().toString());
                    materialVal = (materials.getText().toString().equalsIgnoreCase(""))? 0 :Double.parseDouble(materials.getText().toString());
                    markupVal = (markup.getText().toString().equalsIgnoreCase(""))? 0 : Double.parseDouble(markup.getText().toString());
                    double totalVal = (hoursVal * rateVal) + (materialVal +(materialVal * markupVal));
                    total.setText(String.valueOf(totalVal));
                }
            }
        });
        rate.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final double hoursVal,rateVal,materialVal,markupVal;
                if(rate.getText().length() > 0 && rate.getText().toString().equalsIgnoreCase(".")==false){
                    hoursVal = (hours.getText().toString().equalsIgnoreCase(""))? 0:Double.parseDouble(hours.getText().toString());
                    rateVal = Double.parseDouble(rate.getText().toString());
                    materialVal = (materials.getText().toString().equalsIgnoreCase(""))? 0 :Double.parseDouble(materials.getText().toString());
                    markupVal = (markup.getText().toString().equalsIgnoreCase(""))? 0 : Double.parseDouble(markup.getText().toString());
                    double totalVal = (hoursVal * rateVal) + (materialVal +(materialVal * markupVal));
                    total.setText(String.valueOf(totalVal));
                }
            }
        });
        materials.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final double hoursVal,rateVal,materialVal,markupVal;
                if(materials.getText().length() > 0 && materials.getText().toString().equalsIgnoreCase(".")==false){
                    hoursVal = (hours.getText().toString().equalsIgnoreCase(""))? 0 :Double.parseDouble(hours.getText().toString());
                    rateVal = (rate.getText().toString().equalsIgnoreCase(""))? 0:Double.parseDouble(rate.getText().toString());
                    materialVal = Double.parseDouble(materials.getText().toString());
                    markupVal = (markup.getText().toString().equalsIgnoreCase(""))? 0 : Double.parseDouble(markup.getText().toString());
                    double totalVal = (hoursVal * rateVal) + (materialVal +(materialVal * markupVal));
                    total.setText(String.valueOf(totalVal));
                }
            }
        });
        markup.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final double hoursVal,rateVal,materialVal,markupVal;
                if(markup.getText().length() > 0 && markup.getText().toString().equalsIgnoreCase(".")==false){
                    hoursVal = (hours.getText().toString().equalsIgnoreCase(""))? 0 : Double.parseDouble(hours.getText().toString());
                    rateVal = (rate.getText().toString().equalsIgnoreCase(""))? 0:Double.parseDouble(rate.getText().toString());
                    materialVal = (materials.getText().toString().equalsIgnoreCase(""))? 0 :Double.parseDouble(materials.getText().toString());
                    markupVal = Double.parseDouble(markup.getText().toString());
                    double totalVal = (hoursVal * rateVal) + (materialVal +(materialVal * markupVal));
                    total.setText(String.valueOf(totalVal));
                }
            }
        });

    }
    private boolean validateFields() {

        if (String.valueOf(address.getSelectedItem()) == null || String.valueOf(address.getSelectedItem()).equalsIgnoreCase("")||
                String.valueOf(address.getSelectedItem()).trim().length()== 0 ||
                hours.getText() == null || hours.getText().toString().equalsIgnoreCase("")||
                rate.getText() == null || rate.getText().toString().equalsIgnoreCase("")||
                materials.getText() == null || materials.getText().toString().equalsIgnoreCase("")||
                markup.getText() == null || markup.getText().toString().equalsIgnoreCase("")||
                description.getText() == null || description.getText().toString().equalsIgnoreCase("")||
                description.getText().toString().trim().length()== 0){
            return false;
        }
        return true;
    }
}
