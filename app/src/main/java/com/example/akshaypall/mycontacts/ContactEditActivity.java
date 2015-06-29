package com.example.akshaypall.mycontacts;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactEditActivity extends ActionBarActivity {

    public static final String EXTRA = "CEA_EXTRA";
    private Contact mContact;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.contact_edit_toolbar);
        toolbar.setTitle(getResources().getString(R.string.edit_contact));
        toolbar.setNavigationIcon(R.drawable.ic_done);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editName = (EditText) findViewById(R.id.contact_edit_name);
                mContact.setName(editName.getText().toString());
                mContact.phoneNumbers = getSectionValues(R.id.phonenumbers_sections);
                mContact.emails = getSectionValues(R.id.emails_sections);

                Toast.makeText(ContactEditActivity.this, "Contact Saved", Toast.LENGTH_LONG)
                        .show();

                finish();
            }
        });


        mPosition = getIntent().getIntExtra(EXTRA, 0);
        mContact = ContactList.getInstance().get(mPosition);

        EditText editName = (EditText) findViewById(R.id.contact_edit_name);
        editName.setText(mContact.getName());

        addToSection(mContact.phoneNumbers, R.id.phonenumbers_sections);
        addToSection(mContact.emails, R.id.emails_sections);

        TextView addNewPhoneNumber = (TextView) findViewById(R.id.addnew_phonenumber);
        addNewPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToSection("", R.id.phonenumbers_sections);
            }
        });
        TextView addNewEmailAddress = (TextView) findViewById(R.id.addnew_email);
        addNewEmailAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToSection("", R.id.emails_sections);
            }
        });
    }

    private ArrayList<String> getSectionValues (int sectionID) {
        LinearLayout section = (LinearLayout)findViewById(sectionID);
        ArrayList<String> newSection = new ArrayList<String>();
        for (int i = 0; i < section.getChildCount(); i++) {
            EditText editValue = (EditText)section.getChildAt(i);
            newSection.add(editValue.getText().toString());
        }
        return newSection;
    }

    private void addToSection(ArrayList<String> valueArray, int layoutID) {
        LinearLayout section = (LinearLayout) findViewById(layoutID);
        for (int i = 0; i < valueArray.size(); i++) {
            EditText et = new EditText(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            et.setLayoutParams(lp);
            et.setText(valueArray.get(i));
            section.addView(et);
        }
    }

    private void addToSection(String value, int layoutID) {
        LinearLayout section = (LinearLayout) findViewById(layoutID);
        EditText et = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        et.setLayoutParams(lp);
        et.setText(value);
        section.addView(et);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_edit, menu);
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
