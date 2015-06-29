package com.example.akshaypall.mycontacts;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactViewFragment extends Fragment {

    public static final String TAG = "Click complete";
    private int mMainColour;
    private Contact mContact;
    private int mPosition;
    private TextView mContactName;
    private FieldsAdapter mAdapter;

    public ContactViewFragment() {
        // Required empty public constructor
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
        if (mAdapter != null) {
            mContact = ContactList.getInstance().get(mPosition);
            updateUI();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_view, container, false);

        mContact = ContactList.getInstance().get(mPosition);

        mContactName = (TextView)v.findViewById(R.id.contact_view_name);

        Toolbar toolbar = (Toolbar)v.findViewById(R.id.contact_view_toolbar);
        //setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_contact_view);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.contact_view_edit) {
                    Log.d(TAG, "Edit button clicked");

                    Intent i = new Intent(getActivity(), ContactEditActivity.class);
                    i.putExtra(ContactEditActivity.EXTRA, mPosition);
                    startActivity(i);
                    return true;
                }
                return false;
            }
        });

        ListView listView = (ListView)v.findViewById(R.id.contact_view_fields);
        mAdapter = new FieldsAdapter(mContact);
        listView.setAdapter(mAdapter);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wonderland);
        Palette palette = Palette.generate(bitmap);
        mMainColour = palette.getDarkVibrantSwatch().getRgb();

        updateUI();
        return v;
    }


    private void updateUI() {
        mContactName.setText(mContact.getName());
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
        //when contact has been edited and the user returns to the view activity
    }

    private class FieldsAdapter extends BaseAdapter {
        ArrayList<String> emails, phoneNumbers;
        FieldsAdapter(Contact contact){
            this.setContact(contact);
        }
        public void setContact (Contact contact) {
            emails = contact.emails;
            phoneNumbers = contact.phoneNumbers;
        }
        @Override
        public int getCount() {
            return phoneNumbers.size()+emails.size(); //phone numbers array comes first! All of the code is structured this way
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.contact_view_field_row, parent, false);
            }

            String value = (String)getItem(position);
            TextView contactValue = (TextView)convertView.findViewById(R.id.contact_view_row_value);
            contactValue.setText(value);

            //for conditional info icons, see below
            ImageView infoIcon = (ImageView)convertView.findViewById(R.id.contact_view_info_icon);
            if (isFirst(position)){
                if (isEmail(position)){
                    infoIcon.setImageResource(R.drawable.ic_email);
                } else {
                    infoIcon.setImageResource(R.drawable.ic_call);
                }

                infoIcon.setColorFilter(mMainColour);
            }

            return convertView;
        }

        private boolean isFirst (int position) {
            return (position == 0 || position == phoneNumbers.size());
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (isEmail(position)){
                return emails.get(position-phoneNumbers.size()); //since phone #s come first
            } else {
                return phoneNumbers.get(position);
            }
        }
        private boolean isEmail (int position) {
            return (position > phoneNumbers.size()-1); //returns if that statement is true or not; boolean
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_contact_view, menu);
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
