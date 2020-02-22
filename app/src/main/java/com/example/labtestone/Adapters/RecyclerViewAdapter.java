package com.example.labtestone.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labtestone.Activities.AddUpdateActivity;
import com.example.labtestone.Models.UserData;
import com.example.labtestone.R;
import com.example.labtestone.database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.labtestone.Activities.AddUpdateActivity.UPDATE_PERSON_ID;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<UserData> mUserList;
    private Context mContext;

    // data is passed into the constructor
    public RecyclerViewAdapter(Context context) {
        mContext = context;

    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final UserData user = mUserList.get(position);

        holder.name.setText(user.getName());
        holder.age.setText(String.valueOf(user.getAge()));
        holder.tutionfee.setText(String.valueOf(user.getTuitionFee()));
        holder.startdate.setText(user.getStartDate());

        holder.editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int elementId = user.getId();
                Intent intent = new Intent(mContext, AddUpdateActivity.class);
                intent.putExtra(UPDATE_PERSON_ID, elementId);
                mContext.startActivity(intent);
            }
        });
        //  holder.myTextView.setText(user.getAge());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        if (mUserList == null) {
            return 0;
        }
        return mUserList.size();
    }

    public void setTasks(List<UserData> usersList) {
        mUserList = usersList;
        notifyDataSetChanged();
    }

    public List<UserData> getTasks() {

        return mUserList;
    }

    // stores and recycles views as they are scrolled off screen
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, age, tutionfee, startdate;
        ImageView editImage;

        AppDatabase mdb;

        ViewHolder(View itemView) {
            super(itemView);
            mdb = AppDatabase.getInstance(mContext);
            name = itemView.findViewById(R.id.person_name);
            age = itemView.findViewById(R.id.person_age);
            tutionfee = itemView.findViewById(R.id.person_tutionfee);
            startdate = itemView.findViewById(R.id.person_startdate);
            editImage = itemView.findViewById(R.id.edit_Image);

        }

    }


}