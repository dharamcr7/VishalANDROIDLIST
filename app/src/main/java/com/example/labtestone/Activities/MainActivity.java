package com.example.labtestone.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.labtestone.R;
import com.example.labtestone.Adapters.RecyclerViewAdapter;
import com.example.labtestone.Models.UserData;
import com.example.labtestone.database.AppDatabase;
import com.example.labtestone.database.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // private List<UserData> userList = new List<>();
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private AppDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initViewsWithData();
        initializeAdapter();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            case R.id.item_add:
                startActivity(new Intent(MainActivity.this, AddUpdateActivity.class));
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.rv_user_list);

    }

    private void initViewsWithData() {
        //UserData userData = addUserData("Vishal", "", "");
        //userList.add(userData);
    }

//    private UserData addUserData(String name, String  age, String  tutionFees) {
//        UserData userData = new UserData();
//        userData.setName(name);
//        userData.setAge(age);
//        userData.setTuitionFee(tutionFees);
//        return userData;
//    }

    private void initializeAdapter() {
        // set up the RecyclerView

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new RecyclerViewAdapter(getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        mDb = AppDatabase.getInstance(getApplicationContext());

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<UserData> tasks = mAdapter.getTasks();
                        mDb.userDao().delete(tasks.get(position));
                        retrieveTasks();
                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);

    }


    @Override
    protected void onResume() {
        super.onResume();
        retrieveTasks();
    }

    private void retrieveTasks() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                try {
                    final List<UserData> users = mDb.userDao().loadAllPersons();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            mAdapter.setTasks(users);
                        }
                    });

                } catch (Exception e) {
                    // Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("myTag", e + "This is my message");

                }

            }
        });

//
    }
}