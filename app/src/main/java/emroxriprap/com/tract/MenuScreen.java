package emroxriprap.com.tract;

import android.app.Activity;
import android.app.ActivityOptions;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MenuScreen extends ActionBarActivity implements View.OnClickListener{

RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
        getWindow().setExitTransition(new Explode());
        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView)findViewById(R.id.rv_list);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        List<Entry> entries = Entry.initTestData();
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

    @Override
    public void onClick(View v) {

//        switch (v.getId()){
//
////                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MenuScreen.this,new Pair(newTract,"cardBG"),new Pair(newText,"cardText"));
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MenuScreen.this,newTract,"cardBG");
//                Intent intent = new Intent(MenuScreen.this,NewTractScreen.class);
//                startActivity(intent, options.toBundle());
//        }
    }
    public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.EntryViewHolder> implements View.OnClickListener{
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
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            Entry entry = entries.get(itemPosition);
            String item = entry.getAddress();
            Toast.makeText(getApplicationContext(), item, Toast.LENGTH_LONG).show();
        }

    }
}
