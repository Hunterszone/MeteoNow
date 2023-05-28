package drenski.com.simpleweather;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class WeatherActivity extends AppCompatActivity {

    ArrayList<String> homeCity = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new WeatherFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.change_city){
            showInputDialog();
        }

        if(item.getItemId() == R.id.refresh_app){
            if(homeCity.size() > 0){

                changeCity(homeCity.get(homeCity.size() - 1));
                locationGetAlert();

            }

            if(homeCity.size() == 0){

                changeCity("Sofia, BG");
                locationDefaultAlert();
            }

        }

        if(item.getItemId() == R.id.setHome_app){

                setHomeEngine();
        }

        if(item.getItemId() == R.id.clear_list_app){
            homeCity.clear();
            locationResetAlert();
        }

        return false;

    }

    public void locationDefaultAlert(){
        makeText(this, R.string.location_def, LENGTH_SHORT).show();
    }

    public void locationSetAlert(){
        makeText(this, R.string.location_set, LENGTH_SHORT).show();
    }

    public void locationGetAlert(){
        makeText(this, R.string.location_get, LENGTH_SHORT).show();
    }

    public void locationResetAlert(){
        makeText(this, R.string.location_res, LENGTH_SHORT).show();
    }

    private void setHomeEngine(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("E.g.: London, GB");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String a = input.getText().toString();
                homeCity.add(a);
                locationSetAlert();

            }
        });
        builder.show();
    }


    private void showInputDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("E.g.: London, GB");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeCity(input.getText().toString());
            }
        });
        builder.show();
    }

    public void changeCity(String city){
        WeatherFragment wf = (WeatherFragment)getSupportFragmentManager()
                .findFragmentById(R.id.container);
        wf.changeCity(city);
        new CityPreference(this).setCity(city);
    }

}
