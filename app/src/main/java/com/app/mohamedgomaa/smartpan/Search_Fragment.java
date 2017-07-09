package com.app.mohamedgomaa.smartpan;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Search_Fragment extends Fragment {
    Toolbar toolbar;
    EditText searchET;
    ListView listView;
    List<Search_item> myList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        searchET = (EditText) view.findViewById(R.id.ET_search_toolbar);
        listView = (ListView) view.findViewById(R.id.list_search);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (new CheckConnection_Internet(getActivity()).IsConnection()) {
            Singleton singleton = new Singleton(getContext());
            JsonArrayRequest jArry = new JsonArrayRequest(Request.Method.GET, "https://restcountries.eu/rest/v1/all", new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            myList.add(new Search_item(response.getJSONObject(i).getString("name"), response.getJSONObject(i).getString("capital")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            singleton.addToRequestQueue(jArry);
        } else {
            Toast.makeText(getActivity(), "check connect with Internet", Toast.LENGTH_SHORT).show();
        }
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Search_item> myList_search = new ArrayList<>();
                String result = searchET.getText().toString();
                for (int i = 0; i < myList.size(); i++) {
                    if (myList.get(i)._name.contains(result) || myList.get(i)._captial.contains(result)) {
                        myList_search.add(myList.get(i));
                    }
                }
                AdapterList adapter = new AdapterList(myList_search);
                listView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    class AdapterList extends BaseAdapter {
        private List<Search_item> _my_list = new ArrayList<>();

        public AdapterList(List<Search_item> _my_list) {
            this._my_list = _my_list;
        }

        @Override
        public int getCount() {
            return _my_list.size();
        }

        @Override
        public Object getItem(int position) {
            return _my_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.contact_item, null);
            TextView txt_name = (TextView) view.findViewById(R.id.txt_name);
            TextView txt_capital = (TextView) view.findViewById(R.id.txt_number);
            txt_name.setText(_my_list.get(position)._name);
            txt_capital.setText(_my_list.get(position)._captial);
            return view;
        }
    }
}


