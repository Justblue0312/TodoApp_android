package com.example.todo_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo_app.Adapter.OnTodoClickListener;
import com.example.todo_app.Adapter.RecycleViewAdapter;
import com.example.todo_app.Model.SharedViewModel;
import com.example.todo_app.Model.Task;
import com.example.todo_app.Model.TaskViewModel;
import com.example.todo_app.Utils.Const;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements OnTodoClickListener {

    private RecyclerView recyclerView;
    private RecycleViewAdapter recycleViewAdapter;
    BottomSheetFragment bottomSheetFragment;
    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initAction();
    }

    private void initAction() {
    }

    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomSheetFragment = new BottomSheetFragment();
        ConstraintLayout constraintLayout = findViewById(R.id.bottomSheet);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);
        
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TaskViewModel taskViewModel = new ViewModelProvider.AndroidViewModelFactory(
                MainActivity.this.getApplication())
                .create(TaskViewModel.class);

        sharedViewModel = new ViewModelProvider(this)
                .get(SharedViewModel.class);

        taskViewModel.getAllTasks().observe(this, tasks -> {
            recycleViewAdapter = new RecycleViewAdapter(tasks, this, getApplicationContext());
            recyclerView.setAdapter(recycleViewAdapter);
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showBottomSheetDialog());
    }

    private void showBottomSheetDialog() {
        bottomSheetFragment.show(getSupportFragmentManager(),
                bottomSheetFragment.getTag());
    }

    @Override
    public void onTodoClick(Task task) {
        sharedViewModel.selectedItem(task);
        sharedViewModel.setIsEdit(true);
        showBottomSheetDialog();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onTodoRadioButtonClick(Task task) {
        Log.d(Const.CLICK, "OnRadioButton: " + task.getTaskLabel());
        Log.d(Const.CLICK, "OnRadioButton: " + task.getTask());
        TaskViewModel.delete(task);
        recycleViewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_settings){
            startActivity(new Intent(this, AboutActivity.class));
        }else if(id == R.id.action_settings1){
            startActivity(new Intent(this, PasswordActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}