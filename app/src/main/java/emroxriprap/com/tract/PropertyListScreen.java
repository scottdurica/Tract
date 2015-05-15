package emroxriprap.com.tract;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import emroxriprap.com.tract.db.DbHandler;


public class PropertyListScreen extends ActionBarActivity {

    RecyclerView recyclerView;
    Context context;
    ImageButton addNewEntry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list_screen);
        getWindow().setExitTransition(new Explode());
        getWindow().setEnterTransition(new Slide());

        initViews();
        this.context = getApplicationContext();
    }

    private void initViews() {
        recyclerView = (RecyclerView)findViewById(R.id.rv_properties);
        recyclerView.setHasFixedSize(true);

        addNewEntry = (ImageButton)findViewById(R.id.b_properties_newItem);
        addNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MenuScreen.this,addNewEntry,"tf_addressss");

                Intent intent = new Intent(PropertyListScreen.this,EditPropertyScreen.class);
                intent.putExtra("newEntry",true);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(PropertyListScreen.this).toBundle());
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
//        List<Entry> entries = Entry.initTestData();
        DbHandler db = new DbHandler(this);
        List<Property> properties = db.getAllProperties();
        MyCustomAdapter adapter = new MyCustomAdapter(properties);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_property_list_screen, menu);
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

    public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.PropertyViewHolder> implements View.OnClickListener, View.OnLongClickListener{
        List<Property> properties;

        MyCustomAdapter(List<Property> properties){
            this.properties = properties;
        }


        public class PropertyViewHolder extends RecyclerView.ViewHolder {

            CardView cardView;
            TextView address;
            TextView city;

            PropertyViewHolder(View itemView){
                super(itemView);

                cardView = (CardView)itemView.findViewById(R.id.cv_property);
                address = (TextView)itemView.findViewById(R.id.tv_cv_property_address_one);
                city = (TextView)itemView.findViewById(R.id.tv_cv_property_city);
            }


        }
        @Override
        public int getItemCount() {

            return properties.size();

        }
        @Override
        public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_card_view,parent,false);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            PropertyViewHolder propertyViewHolder = new PropertyViewHolder(view);
            return propertyViewHolder;
        }

        @Override
        public void onBindViewHolder(PropertyViewHolder holder, int position) {
            Property p = properties.get(position);
            holder.address.setText(p.getAddress());
            holder.city.setText(p.getCity());


        }

        @Override
        public boolean onLongClick(View v) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setTitle("")
            return true;
        }
        @Override
        public void onClick(View v) {
            TextView tv = (TextView)v.findViewById(R.id.tv_cv_address);
            TextView desc = (TextView)v.findViewById(R.id.tv_cv_description);
            int itemPosition = recyclerView.getChildPosition(v);
            Property property = properties.get(itemPosition);
            Intent intent = new Intent(PropertyListScreen.this,EditPropertyScreen.class);
            intent.putExtra("address",property.getAddress());
            intent.putExtra("city",property.getCity());
            intent.putExtra("id",property.getId());

            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(PropertyListScreen.this).toBundle());

        }

    }
}
