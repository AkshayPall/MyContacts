package com.example.akshaypall.mycontacts;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactListFragment extends ContractFragment<ContactListFragment.Contract> {

    public static final String EXTRA = "CVA_Contact";
    private ContactList mContacts;
    private ContactAdapter mAdapter;

    public ContactListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_list, container, false);

        mContacts = ContactList.getInstance();

        for (int i = 0; i < 3; i++) {
            Contact contact1 = new Contact();
            contact1.setName("Akshay Pall");
            contact1.emails = new ArrayList<String>();
            contact1.emails.add("akshaypall@hotmail.com");
            contact1.emails.add("iosakshay@gmail.com");
            contact1.phoneNumbers= new ArrayList<String>();
            contact1.phoneNumbers.add("111-111-1111");
            contact1.phoneNumbers.add("111-111-1222");
            mContacts.add(contact1);
        }
        for (int i = 0; i < 6; i++) {
            Contact contact2 = new Contact();
            contact2.setName("Test Name");
            contact2.emails = new ArrayList<String>();
            contact2.emails.add("test@hotmail.com");
            contact2.emails.add("test@gmail.com");
            contact2.phoneNumbers= new ArrayList<String>();
            contact2.phoneNumbers.add("331-111-1111");
            contact2.phoneNumbers.add("311-111-1222");
            mContacts.add(contact2);
        }

        ListView contactListView = (ListView)v.findViewById(R.id.contact_list_view);
        mAdapter = new ContactAdapter(mContacts);
        contactListView.setAdapter(mAdapter);
        /*contactListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int previousFirstItemSeen = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                ActionBar ab = ((ActionBarActivity)getActivity()).getActionBar();
                if (firstVisibleItem > previousFirstItemSeen) {
                    ab.hide();
                } else if (firstVisibleItem < previousFirstItemSeen) {
                    ab.show();
                }
                previousFirstItemSeen = firstVisibleItem;
            }
        });*/

        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getmContract() != null){
                    getmContract().selectedPosition(position);
                }
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_contact_list, menu);
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

    private class ContactAdapter extends ArrayAdapter<Contact> {
        ContactAdapter(ArrayList<Contact> Contacts) {
            super (getActivity(), R.layout.contact_list_row, R.id.contact_row_name, Contacts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);

            Contact contactShown = getItem(position);

            TextView contactNameTextView = (TextView)convertView.findViewById(R.id.contact_row_name);
            contactNameTextView.setText(contactShown.getName());

            return convertView;
        }
    }

    public interface Contract {
        public void selectedPosition (int position);
    }

}
