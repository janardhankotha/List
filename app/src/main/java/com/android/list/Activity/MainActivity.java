package com.android.list.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.list.Utilities.ConnectionDetector;
import com.android.list.Utilities.EndUrl;
import com.android.list.Utilities.JSONParser;
import com.android.list.Adapter.Adapter_List;
import com.android.list.Pojo.Pojo_List;
import com.android.list.R;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    Adapter_List mAdapter;
    private ArrayList<Pojo_List> allItems1 = new ArrayList<Pojo_List>();
    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh); // Initialising Swipe Refresh
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent); // Set color to Swipe REfresh

        mRecyclerView=(RecyclerView)findViewById(R.id.recycler_view); // Initialising ReclerView
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager); // Set as Linear Layout

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // Swipe Refresh Method
            @Override
            public void onRefresh() {
                ConnectionDetector cd = new ConnectionDetector(MainActivity.this);// checking Internet connection
                if (cd.isConnectingToInternet()) {
                    new LoadProductList().execute();// Calling API
                } else {
                    Toast.makeText(MainActivity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        ConnectionDetector cd = new ConnectionDetector(MainActivity.this);// checking Internet connection
        if (cd.isConnectingToInternet()) {
            new LoadProductList().execute();// Calling API
        } else {
            Toast.makeText(MainActivity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }
    }

    class LoadProductList extends AsyncTask<String, String, String> {

        String response;

        private ProgressDialog pDialog;
        String jsontitle;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        public String doInBackground(String... args) {

            //  product_details_lists = new ArrayList<Product_list>();

            allItems1 =new ArrayList<Pojo_List>();
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();

            JSONObject json = jsonParser.makeHttpRequest(EndUrl.APIURL, "GET", userpramas);

            Log.e("testing", "userpramas result = " + userpramas);
            Log.e("testing", "json result = " + json);

            if (json == null) {

            } else {
                Log.e("testing", "jon2222222222222");
                try {

                    jsontitle = json.getString("title");


                        String arrayresponse = json.getString("rows");
                        Log.e("testing", "adapter value=" + arrayresponse);

                        JSONArray responcearray = new JSONArray(arrayresponse);
                        Log.e("testing", "responcearray value=" + responcearray);

                        for (int i = 0; i < responcearray.length(); i++) {

                            JSONObject post = responcearray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<String, String>();
                            Pojo_List item = new Pojo_List();

                            item.setTitle(post.optString("title"));
                            item.setDesc(post.optString("description"));
                            item.setImage(post.optString("imageHref"));

                            allItems1.add(item);


                        }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            return response;


        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);

            pDialog.dismiss();
            mAdapter = new Adapter_List(MainActivity.this, allItems1);
            mRecyclerView.setAdapter(mAdapter);

            getSupportActionBar().setTitle(jsontitle);


        }

    }

}
