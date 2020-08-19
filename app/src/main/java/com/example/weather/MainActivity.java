package com.example.weather;
// Created By Callum Macleod Murdoch - S1828149
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.res.Configuration;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements FeedFragment.Selected {

    //Declaring variables
    private boolean IsLandscape = false;
    private TextView extendTxt;
    private ScrollView extendScrollView;
    private String GlasgowURL = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579";
    private String LondonURL = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743";
    private String NewYorkURL = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581";
    private String OmanURL = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/287286";
    private String MauritiusURL = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154";
    private String BangladeshURL = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/1185241";
    private String JohannesburgURL = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/993800";

    //First method that is executed
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        extendTxt = findViewById(R.id.extendTxt);
        extendScrollView = findViewById(R.id.extendScrollView);

        //Creates the first fragment if no saved state
        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment newFragment = new FeedFragment();
            //Sets the base data sent to the FeedFragment
            Bundle info = new Bundle();
            if(findViewById(R.id.layout_land) != null)
            {
                IsLandscape = true;
            }
            info.putBoolean("landscape", IsLandscape);
            info.putString("url", GlasgowURL);
            newFragment.setArguments(info);
            ft.replace(R.id.fragment_feed, newFragment, "FeedFragment");
            ft.commit();
            getSupportFragmentManager().executePendingTransactions();
        }
        orientate(); //Calls the orientate method to set the fragments shown
    }

    public void orientate() {

        //If the phone is in portrait
        if(findViewById(R.id.layout_portrait) != null)
        {
            FragmentManager fm = this.getSupportFragmentManager();
            Fragment f = fm.findFragmentByTag("FeedFragment");
            if(f!=null) {
                fm.beginTransaction()
                        //Show the feed fragment and hide the extend fragment
                        .hide(fm.findFragmentById(R.id.fragment_extend))
                        .show(fm.findFragmentById(R.id.fragment_feed))
                        .commit();
            }
        }

        //If the phone is in landscape
        if(findViewById(R.id.layout_land) != null)
        {
            FragmentManager fm = this.getSupportFragmentManager();
            Fragment f = fm.findFragmentByTag("FeedFragment");
            if(f!=null) {
                fm.beginTransaction()
                        //Show both fragments
                        .show(fm.findFragmentById(R.id.fragment_extend))
                        .show(fm.findFragmentById(R.id.fragment_feed))
                        .commit();
            }
        }
    }
    //Inflates the dropdown menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dropdown_menu, menu);
        return true;
    }
    //Method for when an item is selected from the dropdown menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Creates the new fragment of the selected item
        Fragment selectedItem = new FeedFragment();
        Bundle info = new Bundle();
        if(findViewById(R.id.layout_land) != null)
        {
            IsLandscape = true;
        }
        info.putBoolean("landscape", IsLandscape);
        if(id == R.id.menu_Glasgow)
        {
            info.putString("url", GlasgowURL);
            selectedItem.setArguments(info);
            TextView textElement =  findViewById(R.id.textViewTitle);
            textElement.setText("Weather Forecast for Glasgow");
        }
        if(id == R.id.menu_London)
        {
            info.putString("url", LondonURL);
            selectedItem.setArguments(info);
            TextView textElement =  findViewById(R.id.textViewTitle);
            textElement.setText("Weather Forecast for London");
        }
        if(id == R.id.menu_NewYork)
        {
            info.putString("url", NewYorkURL);
            selectedItem.setArguments(info);
            TextView textElement = findViewById(R.id.textViewTitle);
            textElement.setText("Weather Forecast for New York");
        }
        if(id == R.id.menu_Oman)
        {
            info.putString("url", OmanURL);
            selectedItem.setArguments(info);
            TextView textElement =  findViewById(R.id.textViewTitle);
            textElement.setText("Weather Forecast for Oman");
        }
        if(id == R.id.menu_Mauritius)
        {
            info.putString("url", MauritiusURL);
            selectedItem.setArguments(info);
            TextView textElement = findViewById(R.id.textViewTitle);
            textElement.setText("Weather Forecast for Mauritius");
        }
        if(id == R.id.menu_Bangladesh)
        {
            info.putString("url", BangladeshURL);
            selectedItem.setArguments(info);
            TextView textElement =  findViewById(R.id.textViewTitle);
            textElement.setText("Weather Forecast for Bangladesh");
        }
        if(id == R.id.menu_Johannesburg)
        {
            info.putString("url", JohannesburgURL);
            selectedItem.setArguments(info);
            TextView textElement =  findViewById(R.id.textViewTitle);
            textElement.setText("Weather Forecast for Johannesburg");
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_feed, selectedItem, "FeedFragment")
                .commit();

        orientate();//Calls the orientate method
        extendTxt.setText(""); //Resets the text in the extend fragment
        return super.onOptionsItemSelected(item);
    }

    // Loads and saves changes to orientation
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(IsLandscape == true){
            IsLandscape = false;
        } else {
            IsLandscape = true;
        }
    }

    //Creates the extended description when the item clicked on
    @Override
    public void onSelected(RSSFeed response, int index) {

        StringBuilder extendOut = new StringBuilder();
        //Creates the title for the extend fragment
        String out = response.getTitle();
        String firstWord = out.substring(0, out.indexOf(" "));
        out =out.replaceFirst(firstWord, "");
        extendOut.append("<b>" + firstWord + "</b>" + out + "<br /><br /><br />");
        //Creates the extended details for the extend fragment
        extendOut.append("<b>Extended Details: </b>");
        out = response.getDescription();
        extendOut.append(out + "<br /><br /><br />");

        //Creates the date for the extend fragment
        SimpleDateFormat dFormatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
        Date weatherDate = null;
        try {
            weatherDate = dFormatter.parse(response.getDatePublished());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(weatherDate);
        if(index == 1){
            c.add(Calendar.DATE, 1);
            weatherDate = c.getTime();
        }
        if(index == 2){
            c.add(Calendar.DATE, 2);
            weatherDate = c.getTime();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(weatherDate);

        extendOut.append("<b>Date: </b>");
        out = strDate;
        extendOut.append(out+ "<br /><br /><br />");

        //Creates the link for the extend fragment
        extendOut.append("<b>Link: </b>");
        out = response.getLink();
        extendOut.append(out + "<br /><br /><br />");
        //Creates the published date for the extend fragment
        extendOut.append("<b>Date Published: </b>");
        out = response.getDatePublished();
        extendOut.append(out);

        extendTxt.setText(Html.fromHtml(extendOut.toString()));

        //Hides the feed fragment and brings up the extend fragment when in portrait
        if (findViewById(R.id.layout_portrait) != null) {
            FragmentManager fm = this.getSupportFragmentManager();
            Fragment f = fm.findFragmentByTag("FeedFragment");

            if (f != null) {
                fm.beginTransaction()
                        .show(fm.findFragmentById(R.id.fragment_extend))
                        .hide(fm.findFragmentById(R.id.fragment_feed))
                        .addToBackStack(null)
                        .commit();
            }
        }
        //Sets the ScrollView back to the top
        extendScrollView.smoothScrollTo(0, extendTxt.getTop());

    }

}