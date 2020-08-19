package com.example.weather;
// Created By Callum Macleod Murdoch - S1828149
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class FeedFragment extends androidx.fragment.app.ListFragment {
    //Declaring variables
    private int feedID = 0;
    private Selected activity;
    private ArrayList<RSSFeed> weatherFeeds;
    private String URL = null;
    private ArrayAdapter adapter;


    public interface Selected
    {
        void onSelected(RSSFeed wFeed, int index);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Selected) context;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        URL = getArguments().getString("url");
        new ProcessInBackground().execute();

    }

    //Method that activates on click
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

            RSSFeed wFeed = weatherFeeds.get((int)id);
            activity.onSelected(wFeed, position);
    }


    public InputStream getInputStream(java.net.URL url)
    {
        try
        {
            return url.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            return null;
        }
    }


    //Method that runs thread in background
    public class ProcessInBackground extends AsyncTask<Void, Void, ArrayList<RSSFeed>>
    {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weatherFeeds = new ArrayList<RSSFeed>();
        }

        @Override
        protected void onPostExecute(ArrayList<RSSFeed> s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            ArrayList<String> weatherList=new ArrayList<String>();
            weatherList.add(weatherFeeds.get(0).getTitle());
            weatherList.add(weatherFeeds.get(1).getTitle());
            weatherList.add(weatherFeeds.get(2).getTitle());
            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1 , weatherList);
            //Sets the feed list which is displayed
            setListAdapter(adapter);
        }
        //Method that runs thread in background that parses data from RSS
        @Override
        protected ArrayList<RSSFeed> doInBackground(Void... params) {

            try
            {
                java.net.URL url = new URL(URL);

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(getInputStream(url), "UTF_8");
                boolean inTag = false;
                RSSFeed feedToAdd = null;
                int event = xpp.getEventType();

                while(event != XmlPullParser.END_DOCUMENT)
                {
                    if(event == XmlPullParser.START_TAG)
                    {
                        if(xpp.getName().equalsIgnoreCase("item"))
                        {
                            feedToAdd = new RSSFeed();
                            inTag = true;
                            feedToAdd.setUniqueID(feedID);
                            feedID = feedID + 1;
                        }
                        else if(xpp.getName().equalsIgnoreCase("title"))
                        {
                            if(inTag)
                            {
                                feedToAdd.setTitle(xpp.nextText());
                            }
                        }else if(xpp.getName().equalsIgnoreCase("description"))
                        {
                            if(inTag)
                            {
                                feedToAdd.setDescription(xpp.nextText());
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("link"))
                        {
                            if(inTag) {
                                feedToAdd.setLink(xpp.nextText());
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("pubDate"))
                        {
                            if(inTag) {
                                feedToAdd.setDatePublished(xpp.nextText());
                            }
                        }
                    }
                    else if(event == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    {
                        inTag = false;
                        weatherFeeds.add(feedToAdd);
                    }

                    event = xpp.next();
                }
            }
            catch (XmlPullParserException e)
            {
                exception = e;
            }
            catch (IOException e)
            {
                exception = e;
            }
            //Returns parsed RSS data
            return weatherFeeds;

        }
    }
}