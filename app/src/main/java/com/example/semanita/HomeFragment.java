package com.example.semanita;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ViewGroup calendarLayout = view.findViewById(R.id.calendar_layout);

        for (int i = 0; i < 5; i++) {
            View taskView = inflater.inflate(R.layout.task, calendarLayout, false);

            TextView timeText = taskView.findViewById(R.id.time_text);
            TextView taskTitle = taskView.findViewById(R.id.task1);
            TextView taskTime = taskView.findViewById(R.id.task_time);
            TextView taskDescription = taskView.findViewById(R.id.card_description);
            TextView taskCategory = taskView.findViewById(R.id.card_category);

            timeText.setText((i + 1) * 10 + "m");
            taskTitle.setText("Tarea " + (i + 1));
            taskTime.setText((i + 1) * 15 + " Minutos");
            taskDescription.setText("Descripción de la tarea " + (i + 1));
            taskCategory.setText("Categoría " + (i + 1));

            calendarLayout.addView(taskView);
        }

        return view;
    }
}