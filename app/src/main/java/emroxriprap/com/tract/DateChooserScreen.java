package emroxriprap.com.tract;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateChooserScreen extends ActionBarActivity {

    CalendarView calanderView;
    String selectedDate;
    Button nextScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_chooser_screen);
        getWindow().setExitTransition(new Explode());
        getWindow().setEnterTransition(new Slide(Gravity.TOP));
        setTitle("Select date");
        initViews();
    }
    private void initViews(){
        nextScreen = (Button)findViewById(R.id.b_nextScreen);
        nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DateChooserScreen.this,EditTractScreen.class);
                boolean flag = true;
                intent.putExtra("newEntry",flag);
                intent.putExtra("date",selectedDate);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(DateChooserScreen.this).toBundle());
            }
        });
        calanderView = (CalendarView)findViewById(R.id.cv_entry_date);
        selectedDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
        Log.d("Initial date value is: ", selectedDate);
        calanderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int d = dayOfMonth;
                selectedDate = String.valueOf(month + 1) + "/" + String.valueOf(dayOfMonth) +
                        "/" + String.valueOf(year);
                Log.d("Updated date value is: ", selectedDate);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_date_chooser_screen, menu);
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
}
