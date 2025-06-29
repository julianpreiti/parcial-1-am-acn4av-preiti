package com.example.semanita;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private int selectedIndex = 3; // Hoy es el dia 3
    private View lastSelectedView = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayout daysContainer = view.findViewById(R.id.days_container);

        // Creamos los dias 3 antes, hoy y 7 despues
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -3);

        String[] dayLetters = {"D", "L", "M", "M", "J", "V", "S"};
        SimpleDateFormat dayNumberFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat dayNameFormat = new SimpleDateFormat("EEEE", new Locale("es"));

        for (int i = 0; i < 11; i++) {
            View dayView = inflater.inflate(R.layout.item_day, daysContainer, false);
            TextView letter = dayView.findViewById(R.id.day_letter);
            TextView number = dayView.findViewById(R.id.day_number);

            int weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            letter.setText(dayLetters[weekDay]);
            number.setText(dayNumberFormat.format(calendar.getTime()));

            // Cual seleccionó el usuario
            if (i == selectedIndex) {
                dayView.setBackgroundResource(R.drawable.button_rounded);
                lastSelectedView = dayView;
                letter.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
                number.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
                updateHeader(view, calendar);
            } else {
                dayView.setBackgroundResource(0);
                letter.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_inactive));
                number.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            }

            // Listener para seleccionar el día
            final int index = i;
            final Calendar selectedDate = (Calendar) calendar.clone();
            dayView.setOnClickListener(v -> {
                if (lastSelectedView != null) {
                    lastSelectedView.setBackgroundResource(0);
                    TextView l = lastSelectedView.findViewById(R.id.day_letter);
                    TextView n = lastSelectedView.findViewById(R.id.day_number);
                    l.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_inactive));
                    n.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
                }
                v.setBackgroundResource(R.drawable.button_rounded);
                letter.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
                number.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
                lastSelectedView = v;
                selectedIndex = index;
                updateHeader(view, selectedDate);
            });

            daysContainer.addView(dayView);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // aca desp ponemos la logica del resto

        return view;
    }

    private void updateHeader(View view, Calendar date) {
        TextView dayNumber = view.findViewById(R.id.dayNumber);
        TextView dayName = view.findViewById(R.id.dayName);
        TextView dayHour = view.findViewById(R.id.dayHour);

        SimpleDateFormat dayNumberFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat dayNameFormat = new SimpleDateFormat("EEEE", new Locale("es"));

        dayNumber.setText(dayNumberFormat.format(date.getTime()));
        String name = dayNameFormat.format(date.getTime());
        dayName.setText(name.substring(0, 1).toUpperCase(new Locale("es")) + name.substring(1));

        // Calcular diferencia de días
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        Calendar selected = (Calendar) date.clone();
        selected.set(Calendar.HOUR_OF_DAY, 0);
        selected.set(Calendar.MINUTE, 0);
        selected.set(Calendar.SECOND, 0);
        selected.set(Calendar.MILLISECOND, 0);

        long diffMillis = selected.getTimeInMillis() - today.getTimeInMillis();
        int diffDays = (int) (diffMillis / (24 * 60 * 60 * 1000));

        if (diffDays == 0) {
            // si es hoy mostramos la hora actual
            SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            dayHour.setText(hourFormat.format(Calendar.getInstance().getTime()));
        } else if (diffDays == -1) {
            dayHour.setText("Ayer");
        } else if (diffDays < -1) {
            dayHour.setText("Hace " + Math.abs(diffDays) + " días");
        } else if (diffDays == 1) {
            dayHour.setText("Mañana");
        } else {
            dayHour.setText("En " + diffDays + " días");
        }
    }
}