package emroxriprap.com.tract;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MenuScreen extends ActionBarActivity implements View.OnClickListener{

    CardView newTract;
    TextView newText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
        initViews();
    }

    private void initViews() {
        newTract = (CardView)findViewById(R.id.cv_new_tract);
        newTract.setOnClickListener(this);
        newText = (TextView)findViewById(R.id.tv_newTract);
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

        switch (v.getId()){
            case R.id.cv_new_tract:
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MenuScreen.this,new Pair(newTract,"cardBG"),new Pair(newText,"cardText"));
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MenuScreen.this,newTract,"cardBG");
                Intent intent = new Intent(MenuScreen.this,NewTractScreen.class);
                startActivity(intent, options.toBundle());
        }
    }
}
