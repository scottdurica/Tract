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

import emroxriprap.com.tract.db.DbHandler;


public class EditPropertyScreen extends ActionBarActivity implements View.OnClickListener{
    EditText address,city;
    int id;

    String callingClass;
    boolean isNewEntry;
    Button delete,update,addToDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Property");

        setContentView(R.layout.activity_edit_property_screen);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());
        callingClass = null;
        callingClass = getIntent().getStringExtra("callingActivity");
        isNewEntry = getIntent().getBooleanExtra("newEntry",false);
        initViews();
    }

    private void initViews(){

        address = (EditText)findViewById(R.id.et_property_address);
        city = (EditText)findViewById(R.id.et_property_city);



        delete = (Button)findViewById(R.id.b_property_delete);
        update = (Button)findViewById(R.id.b_property_update);
        addToDb = (Button)findViewById(R.id.b_property_add_to_db);
        delete.setOnClickListener(this);
        update.setOnClickListener(this);
        addToDb.setOnClickListener(this);
        showOrHideButtons(isNewEntry);
        if (isNewEntry == false) {

            id =Integer.valueOf(this.getIntent().getIntExtra("id",-1));
            address.setText(this.getIntent().getStringExtra("address"));
            city.setText(this.getIntent().getStringExtra("city"));

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
        getMenuInflater().inflate(R.menu.menu_edit_property_screen, menu);
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
           return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.b_property_add_to_db:
                if (validateFields()){
                    DbHandler db = new DbHandler(this);
                    Property property = new Property(address.getText().toString(),city.getText().toString());

                    if (db.addProperty(property)){
                        Toast.makeText(this, "Property Saved", Toast.LENGTH_LONG).show();

                    }else
                        Toast.makeText(this,"Error! Property not saved.",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EditPropertyScreen.this,PropertyListScreen.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(EditPropertyScreen.this).toBundle());
                }else
                    Toast.makeText(this,"Fill out all fields",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(EditTractScreen.this,MenuScreen.class);
//                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(EditTractScreen.this).toBundle());
                break;

            case R.id.b_property_delete:
                DbHandler db1 = new DbHandler(this);
                if(db1.entriesExistForProperty(address.getText().toString())){
                    Toast.makeText(this,"Existing Entries Found",Toast.LENGTH_SHORT).show();

                }
                else{
                if(db1.deleteProperty(id))
                    Toast.makeText(this,"Property Deleted",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditPropertyScreen.this,PropertyListScreen.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(EditPropertyScreen.this).toBundle());
                }
                break;

            case R.id.b_property_update:
                if (validateFields()){
                    DbHandler db = new DbHandler(this);
                    Property property = new Property(address.getText().toString(),city.getText().toString(),id);

                    if (db.updateProperty(property)){
                        Toast.makeText(this,"Property updated",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(EditPropertyScreen.this,PropertyListScreen.class);
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(EditPropertyScreen.this).toBundle());
                    }else
                        Toast.makeText(this,"Error! Property not updated.",Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(this,"Fill out all fields",Toast.LENGTH_SHORT).show();
//                intent = new Intent(EditTractScreen.this,MenuScreen.class);
//                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(EditTractScreen.this).toBundle());
                break;
        }
    }

    private boolean validateFields() {

        if (address.getText().toString() == null || address.getText().toString().equalsIgnoreCase("")||
                address.getText().toString().trim().length()== 0 ||
                city.getText() == null || city.getText().toString().equalsIgnoreCase("")||
                city.getText().toString().trim().length() == 0){
            return false;
        }
        return true;
    }
}
