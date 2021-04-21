package com.example.a310;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

/**
 * A simple {@link Fragment} subclass.
 * This fragment will draw our Selection picker
 */
public class SelectorFragment extends Fragment{

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SelectorFragment() {
        // Required empty public constructor
    }

    /**
     * OnCreate is called once the Fragment is first created.
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * This method return the inflated view of our fragment.
     * @param inflater The layoutinflated used to inflate any view in a fragment.
     * @param container The parent view the fragment UI should be attached to if not null.
     * @param savedInstanceState The most recent saved instance.
     * @return View , the inflated view with our XML.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selectior, container, false);
    }

    /**
     * This method is called once the view is created.
     * We get all the UI components from the View by ID.
     * We add our EventListeners to our buttons.
     * @param view This is the view reference passed from the event.
     * @param savedInstanceState The most recent saved instance.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonBarChart = view.findViewById(R.id.buttonBarChart);
        Button buttonLineChart = view.findViewById(R.id.buttonLineChart);
        Button buttonPieChart = view.findViewById(R.id.buttonPieChart);

        //Open bar chart
        buttonBarChart.setOnClickListener(v -> Navigation.findNavController(getView()).navigate(R.id.action_selectiorFragment_to_barChartFragment));

        //Open pie chart
        buttonPieChart.setOnClickListener(v -> Navigation.findNavController(getView()).navigate(R.id.action_selectiorFragment_to_piechartFragment));

        //Open line chart
        buttonLineChart.setOnClickListener(v -> Navigation.findNavController(getView()).navigate(R.id.action_selectiorFragment_to_LineChartFragment));
    }
}