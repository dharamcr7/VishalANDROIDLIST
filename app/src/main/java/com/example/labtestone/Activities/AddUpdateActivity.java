package com.example.labtestone.Activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.labtestone.Models.UserData;
import com.example.labtestone.R;
import com.example.labtestone.database.AppDatabase;
import com.example.labtestone.database.AppExecutors;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class AddUpdateActivity extends AppCompatActivity {

    public static final String UPDATE_PERSON_ID="update_task";
    private EditText etName, etAge, etTuitionFee, etStartDate;
    private Button button;
    private AppDatabase mDb;
    private int mPersonId;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update);
        initViews();
        initViewsWithData();
    }

    private void initDatabase() {
        mDb = AppDatabase.getInstance(getApplicationContext());
    }

    private void initTitleBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void initViews() {
        etName = findViewById(R.id.edit_name);
        etAge = findViewById(R.id.edit_age);
        etTuitionFee = findViewById(R.id.edit_tuitionfee);
        etStartDate = findViewById(R.id.edit_startdate);
        button = findViewById(R.id.button);

    }

    private void initViewsWithData() {
        initTitleBar();
        initDatabase();
        getIntentData();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
    }

    private void populateUI(UserData person) {

        if (person == null) {
            return;
        }

        etName.setText(person.getName());
        etAge.setText(String.valueOf(person.getAge()));
        etTuitionFee.setText(String.valueOf(person.getTuitionFee()));
        etStartDate.setText(person.getStartDate());

    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(UPDATE_PERSON_ID)) {
            button.setText(getResources().getString(R.string.update));
            mPersonId = intent.getIntExtra(UPDATE_PERSON_ID, -1);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    UserData person = mDb.userDao().loadPersonById(mPersonId);
                    populateUI(person);
                }
            });
        }
    }

    public void onSaveButtonClicked() {
        final UserData person = new UserData(etName.getText().toString(), Integer.valueOf(etAge.getText().toString()), Double.valueOf(etTuitionFee.getText().toString()), etStartDate.getText().toString());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mPersonId > -1) {
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
        // Respond to the action bar's Up/Home button
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
