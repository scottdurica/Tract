package emroxriprap.com.tract;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
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
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import emroxriprap.com.tract.db.DbHandler;


public class MenuScreen extends ActionBarActivity{

    RecyclerView recyclerView;
    Context context;
    ImageButton addNewEntry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
        getWindow().setExitTransition(new Explode());
        getWindow().setEnterTransition(new Slide());

        initViews();
        this.context = getApplicationContext();
    }

    private void initViews() {
        recyclerView = (RecyclerView)findViewById(R.id.rv_list);
        recyclerView.setHasFixedSize(true);

        addNewEntry = (ImageButton)findViewById(R.id.b_newItem);
        addNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MenuScreen.this,addNewEntry,"tf_addressss");

                Intent intent = new Intent(MenuScreen.this,DateChooserScreen.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MenuScreen.this).toBundle());
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
//        List<Entry> entries = Entry.initTestData();
        DbHandler db = new DbHandler(this);
        List<Entry> entries = db.getAllEntries();
        MyCustomAdapter adapter = new MyCustomAdapter(entries);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_screen, menu);
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

    public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.EntryViewHolder> implements View.OnClickListener, View.OnLongClickListener{
        List<Entry> entries;

        MyCustomAdapter(List<Entry> entries){
            this.entries = entries;
        }


        public class EntryViewHolder extends RecyclerView.ViewHolder {

            CardView cardView;

            TextView address;

            TextView date;
            TextView description;
            EntryViewHolder(View itemView){
                super(itemView);

                cardView = (CardView)itemView.findViewById(R.id.cv_entry);
                address = (TextView)itemView.findViewById(R.id.tv_cv_address);
                date = (TextView)itemView.findViewById(R.id.tv_cv_date);
                description = (TextView)itemView.findViewById(R.id.tv_cv_description);
            }


        }
        @Override
        public int getItemCount() {
            return entries.size();
        }
        @Override
        public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_card_view,parent,false);
view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            EntryViewHolder entryViewHolder = new EntryViewHolder(view);
            return entryViewHolder;
        }

        @Override
        public void onBindViewHolder(EntryViewHolder holder, int position) {
            Entry e = entries.get(position);
            holder.description.setText(e.getDescription());
            holder.date.setText(e.getDate());
            holder.address.setText(e.getAddress());


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
            tv.setTransitionName("tf_address");
            TextView desc = (TextView)v.findViewById(R.id.tv_cv_description);
            desc.setTransitionName("tf_description");
            int itemPosition = recyclerView.getChildPosition(v);
            Entry entry = entries.get(itemPosition);
            Intent intent = new Intent(MenuScreen.this,EditTractScreen.class);
            intent.putExtra("address",entry.getAddress());
            intent.putExtra("date",entry.getDate());
            intent.putExtra("description",entry.getDescription());
            intent.putExtra("id",entry.getId());
            intent.putExtra("rate",entry.getRate());
            intent.putExtra("materials",entry.getMaterials());
            intent.putExtra("markup",entry.getMarkup());
            intent.putExtra("hours",entry.getHours());
            intent.putExtra("billed",entry.getBilled());

//            Toast.makeText(getApplicationContext(),intent.getStringExtra("address"),Toast.LENGTH_LONG).show();
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MenuScreen.this,tv,"tf_addressss");
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MenuScreen.this,tv,"tf_addressss");
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MenuScreen.this,new Pair<View, String>(tv,"tf_address"),new Pair<View, String>(desc,"tf_description"));
//            Toast.makeText(getApplicationContext(), tv.getText(), Toast.LENGTH_LONG).show();
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MenuScreen.this).toBundle());
//            startActivity(intent);
        }

    }
}
