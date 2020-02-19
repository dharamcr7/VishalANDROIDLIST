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
import com.example.labtestone.constants.Constants;
import com.example.labtestone.database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

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

            holder.name.setText(mUserList.get(position).getName());
        holder.age.setText(String.valueOf(mUserList.get(position).getAge()));
        holder.tutionfee.setText(String.valueOf(mUserList.get(position).getTuitionFee()));
        holder.startdate.setText(mUserList.get(position).getStartDate());

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

            editImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int elementId = mUserList.get(getAdapterPosition()).getId();
                    Intent i = new Intent(mContext, AddUpdateActivity.class);
                    i.putExtra(Constants.UPDATE_Person_Id, elementId);
                    mContext.startActivity(i);
                }
            });
        }

    }


}