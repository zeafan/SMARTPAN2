package com.app.mohamedgomaa.smartpan;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Contact_Fragment extends Fragment {
    Toolbar toolbar;
    EditText searchET;
    ListView listView;
    List<Contact_item> myList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        searchET = (EditText) view.findViewById(R.id.ET_search_toolbar);
        searchET.setVisibility(View.GONE);
        listView = (ListView) view.findViewById(R.id.list_contect);
        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            myList.add(new Contact_item(name, phoneNumber));
        }
        AdapterList adapterList = new AdapterList(myList);
        listView.setAdapter(adapterList);
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Contact_item> myList_search = new ArrayList<>();
                String result=searchET.getText().toString();
                for(int i=0;i<myList.size();i++)
                {
                    if(myList.get(i).name.contains(result))
                    {
                        myList_search.add(myList.get(i));
                    }
                }
                AdapterList adapterList2 = new AdapterList(myList_search);
                listView.setAdapter(adapterList2);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    boolean check_anim = true;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.profile) {

            View view = getActivity().getLayoutInflater().inflate(R.layout.user_info, null);
            TextView t_name = (TextView) view.findViewById(R.id.id_user_info_name);
            TextView t_mail = (TextView) view.findViewById(R.id.id_user_info_mail);
            TextView t_mobile = (TextView) view.findViewById(R.id.id_user_info_mobile);
            SharedPreferences user_store=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
            String name=user_store.getString("name","");
            String mail=user_store.getString("mail","");
            String mobile=user_store.getString("mobile","");
            t_name.setText(name);
            t_mail.setText(mail);
            t_mobile.setText(mobile);
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setView(view);
            alert.setNeutralButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();
        } else if (item.getItemId() == R.id.search) {

            Animation Anim = AnimationUtils.loadAnimation(getContext(), R.anim.scale1);
            Animation Anim2 = AnimationUtils.loadAnimation(getContext(), R.anim.scale2);
            if (check_anim) {
                searchET.setVisibility(View.VISIBLE);
                searchET.setAnimation(Anim);
                check_anim = false;
            } else if (!check_anim) {
                check_anim = true;
                searchET.setAnimation(Anim2);
                searchET.setVisibility(View.INVISIBLE);
            }
        } else if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            SharedPreferences user_store=getActivity().getSharedPreferences("user",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=user_store.edit();
            editor.clear();
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

class AdapterList extends BaseAdapter {
    private List<Contact_item> _my_list=new ArrayList<>();

    public AdapterList(List<Contact_item> _my_list) {
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
        LinearLayout linear=(LinearLayout)view.findViewById(R.id.linear);
        TextView txt_name = (TextView) view.findViewById(R.id.txt_name);
        TextView txt_number = (TextView) view.findViewById(R.id.txt_number);
        txt_name.setText(_my_list.get(position).name);
        txt_number.setText(_my_list.get(position).NumberPhone);
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("tel:"+_my_list.get(position).NumberPhone));
                startActivity(i);

            }
        });
        return view;
    }
}
    }
