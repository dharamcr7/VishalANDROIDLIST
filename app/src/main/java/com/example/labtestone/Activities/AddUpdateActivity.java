package com.example.labtestone.Activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.labtestone.Models.UserData;
import com.example.labtestone.R;
import com.example.labtestone.constants.Constants;
import com.example.labtestone.database.AppDatabase;
import com.example.labtestone.database.AppExecutors;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class AddUpdateActivity extends AppCompatActivity {
    EditText name, age, tutionfee, startdate;
    Button button;
    int mPersonId;
    Intent intent;
    private AppDatabase mDb;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initViews();
        mDb = AppDatabase.getInstance(getApplicationContext());
        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.UPDATE_Person_Id)) {
            button.setText("Update");

            mPersonId = intent.getIntExtra(Constants.UPDATE_Person_Id, -1);

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    UserData person = mDb.userDao().loadPersonById(mPersonId);
                    populateUI(person);
                }
            });


        }

    }

    private void populateUI(UserData person) {

        if (person == null) {
            return;
        }

        name.setText(person.getName());
        age.setText(String.valueOf(person.getAge()));
        tutionfee.setText(person.getTuitionFee().toString());
        startdate.setText(person.getStartDate());

    }

    private void initViews() {
        name = findViewById(R.id.edit_name);
        age = findViewById(R.id.edit_age);
        tutionfee = findViewById(R.id.edit_tuitionfee);
        startdate = findViewById(R.id.edit_startdate);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
    }

    public void onSaveButtonClicked() {
        final UserData person = new UserData(name.getText().toString(),Integer.valueOf( age.getText().toString()),Double.valueOf(tutionfee.getText().toString()),startdate.getText().toString());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!intent.hasExtra(Constants.UPDATE_Person_Id)) {
                    mDb.userDao().insertPerson(person);
                } else {
                    person.setId(mPersonId);
                    mDb.userDao().updatePerson(person);
                }
                finish();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
