package com.example.akshaypall.mycontacts;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ContactListActivity extends ActionBarActivity implements ContactListFragment.Contract {

    private ContactListFragment mListFragment;
    private ContactViewFragment mViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        mListFragment = (ContactListFragment)getFragmentManager().findFragmentById(R.id.fragment_contact_list_container);

        if(mListFragment == null){
            mListFragment = new ContactListFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_contact_list_container, mListFragment)
                    .commit();
        }

        mViewFragment = (ContactViewFragment)getFragmentManager().findFragmentById(R.id.fragment_contact_view_container);

        if(mViewFragment == null && findViewById(R.id.fragment_contact_view_container) != null){
            mViewFragment = new ContactViewFragment();
            mViewFragment.setmPosition(0);
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_contact_view_container, mViewFragment)
                    .commit();
        }
    }

    @Override
    public void selectedPosition(int position) {
        if (mViewFragment == null) {
            Intent i = new Intent(this, ContactViewActivity.class);
            i.putExtra(ContactListFragment.EXTRA, position);
            startActivity(i);
        } else {
            mViewFragment.setmPosition(position);
        }
    }
}
