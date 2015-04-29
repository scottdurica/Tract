package emroxriprap.com.tract;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import emroxriprap.com.tract.db.DbHandler;


public class EditTractScreen extends ActionBarActivity implements View.OnClickListener {

    EditText address,description,rate,materials,markup,hours;
    int id;
    CheckBox billed;
    TextView total;
    String callingClass;
    boolean isNewEntry;
    Button delete,update,addToDb;
    String date;
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
        address = (EditText)findViewById(R.id.et_address);
        description = (EditText)findViewById(R.id.et_description);
        rate = (EditText)findViewById(R.id.et_rate);
        hours = (EditText)findViewById(R.id.et_hours);
        materials = (EditText)findViewById(R.id.et_materials);
        markup = (EditText)findViewById(R.id.et_markup);
        billed = (CheckBox)findViewById(R.id.cb_is_entered);
        total = (TextView)findViewById(R.id.tv_total);


        delete = (Button)findViewById(R.id.b_delete);
        update = (Button)findViewById(R.id.b_update);
        addToDb = (Button)findViewById(R.id.b_add_to_db);
        delete.setOnClickListener(this);
        update.setOnClickListener(this);
        addToDb.setOnClickListener(this);
        showOrHideButtons(isNewEntry);
        if (isNewEntry == false) {

            id =Integer.valueOf(this.getIntent().getIntExtra("id",-1));
            address.setText(this.getIntent().getStringExtra("address"));
            hours.setText(Double.toString(this.getIntent().getIntExtra("hours", -1)/100));
            rate.setText(Double.toString(this.getIntent().getIntExtra("rate", -1)/100));
            materials.setText(Double.toString(this.getIntent().getIntExtra("materials", -1)/100));
            markup.setText(Double.toString(this.getIntent().getIntExtra("markup", -1)/100));
            description.setText(this.getIntent().getStringExtra("description"));
            int billedVal = this.getIntent().getIntExtra("billed",0);
            if (billedVal == 1){
                billed.setChecked(true);
            }


        }

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
        if (id == R.id.action_settings) {
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
                    Entry entry = new Entry(date, address.getText().toString(), Double.valueOf(rate.getText().toString()),
                    Double.valueOf(materials.getText().toString()),Double.valueOf(markup.getText().toString()),
                    Double.valueOf(hours.getText().toString()), description.getText().toString(),
                    billedVal);
                    if (db.addEntry(entry)){
                        Toast.makeText(this,"Data Saved",Toast.LENGTH_LONG).show();

                    }else
                        Toast.makeText(this,"Error! Data not saved.",Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(this,"Fill out all fields",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditTractScreen.this,MenuScreen.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(EditTractScreen.this).toBundle());
                break;

            case R.id.b_delete:
                break;

            case R.id.b_update:
                if (validateFields()){
                    DbHandler db = new DbHandler(this);
                    int billedVal =0;
                    if (billed.isChecked())billedVal = 1;
                    Entry entry = new Entry(date, address.getText().toString(), Double.valueOf(rate.getText().toString()),
                            Double.valueOf(materials.getText().toString()),Double.valueOf(markup.getText().toString()),
                            Double.valueOf(hours.getText().toString()), description.getText().toString(),
                            billedVal,id);

                    if (db.updateEntry(entry)){
                        Toast.makeText(this,"Data updated",Toast.LENGTH_LONG).show();

                    }else
                        Toast.makeText(this,"Error! Data not updated.",Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(this,"Fill out all fields",Toast.LENGTH_SHORT).show();
                intent = new Intent(EditTractScreen.this,MenuScreen.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(EditTractScreen.this).toBundle());
                break;
        }
    }

    private boolean validateFields() {

        if (address.getText() == null || address.getText().toString().equalsIgnoreCase("")||
                address.getText().toString().trim().length()== 0 ||
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
