package com.example.todo_app.Adapter;

import com.example.todo_app.Model.Task;

public interface OnTodoClickListener {
    void onTodoClick(Task task);
    void onTodoRadioButtonClick(Task task);
}
