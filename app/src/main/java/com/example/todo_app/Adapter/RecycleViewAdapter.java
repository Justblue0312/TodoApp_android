package com.example.todo_app.Adapter;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo_app.Model.Task;
import com.example.todo_app.Notification.ReminderBroadcast;
import com.example.todo_app.R;
import com.example.todo_app.Utils.Sharepref;
import com.example.todo_app.Utils.Utils;
import com.google.android.material.chip.Chip;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private final List<Task> taskList;
    private final OnTodoClickListener todoClickListener;
    Context mContext;

    public RecycleViewAdapter(List<Task> taskList, OnTodoClickListener onTodoClickListener, Context context) {
        this.taskList = taskList;
        this.todoClickListener = onTodoClickListener;
        this.mContext = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = taskList.get(position);
        String formatted = Utils.formatDate(task.getDueDate());

        ColorStateList colorStateList = new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{android.R.attr.state_enabled}
        },
                new int[]{
                        Color.LTGRAY,
                        Utils.priorityColor(task)
                }
        );

        holder.label.setText(task.getTaskLabel());
        holder.task.setText(task.getTask());

        Sharepref.setDefaults("label", task.getTaskLabel(), mContext.getApplicationContext());
        Sharepref.setDefaults("task", task.getTask(), mContext.getApplicationContext());

        holder.todayChip.setText(formatted);
        holder.todayChip.setTextColor(Utils.priorityColor(task));
        holder.todayChip.setChipIconTint(colorStateList);
        holder.radioButton.setButtonTintList(colorStateList);

        holder.itemView.setOnLongClickListener(v -> {
            Toast.makeText(v.getContext(), task.getTaskLabel(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(mContext.getApplicationContext(), ReminderBroadcast.class);
            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext.getApplicationContext(), 0, intent, 0);

            AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

            long timeAtButtonClick = System.currentTimeMillis();

            long tenSecondInMillis = 1000 * 10;

            alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick + tenSecondInMillis, pendingIntent);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public AppCompatRadioButton radioButton;
        public AppCompatTextView  label,task;
        public Chip todayChip;

        OnTodoClickListener onTodoClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.todo_radio_button);
            label = itemView.findViewById(R.id.todo_row_label);
            task = itemView.findViewById(R.id.todo_row_todo);
            todayChip = itemView.findViewById(R.id.todo_row_chip);
            this.onTodoClickListener = todoClickListener;

            itemView.setOnClickListener(this);
            radioButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            Task currTask = taskList.get(getAdapterPosition());
            if(id == R.id.todo_row_layout){
                onTodoClickListener.onTodoClick(currTask);
            }else if(id == R.id.todo_radio_button){
                currTask = taskList.get(getAdapterPosition());
                onTodoClickListener.onTodoRadioButtonClick(currTask);
            }
        }
    }

}
