package com.example.a310;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * This fragment will draw our BarChart
 */
public class BarChartFragment extends Fragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BarChartFragment() {
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

        return inflater.inflate(R.layout.fragment_bar_chart, container, false);
    }

    /**
     * This method is called once the view is created.
     *
     * We get the XYPlot from the View.
     *
     * We create a formatter for the bars in the graph. We change the FillColor and border color.
     *
     * We add a few numbers into an array representing our test data.
     *
     * We create a XYSeries and then add this to the plot displaying our graph.
     *
     * @param view This is the view reference passed from the event.
     * @param savedInstanceState The most recent saved instance.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        XYPlot plot = getView().findViewById(R.id.plot);

        BarFormatter formatter1 = new BarFormatter(Color.rgb(100, 150, 100), Color.WHITE);

        Number[] series1Numbers = {1, 4, 2, 8, 4, 16, 8, 32, 16, 64};
        XYSeries s1 = new SimpleXYSeries(Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "series1");
        plot.addSeries(s1, formatter1);

        BarRenderer renderer = plot.getRenderer(BarRenderer.class);
        renderer.setBarGroupWidth(BarRenderer.BarGroupWidthMode.FIXED_WIDTH, 50);
    }
}