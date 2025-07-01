package com.example.semanita;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddTaskDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_task_dialog, null);

        Spinner spinnerDay = view.findViewById(R.id.spinner_day);
        EditText editTitle = view.findViewById(R.id.edit_title);
        EditText editDescription = view.findViewById(R.id.edit_description);
        EditText editMinutes = view.findViewById(R.id.edit_minutes);

        List<String> days = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd/MM", new Locale("es"));
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < 8; i++) {
            days.add(sdf.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, days);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapter);

        return new AlertDialog.Builder(requireContext())
                .setTitle("Añadir Tarea")
                .setView(view)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    // acá tenemos que poner la logica después para guardarlo en firebase
                })
                .setNegativeButton("Cancelar", null)
                .create();
    }
}