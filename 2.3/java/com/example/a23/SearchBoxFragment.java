package com.example.a23;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 * @author Viggo Lagerstedt Ekholm
 */
public class SearchBoxFragment extends Fragment implements View.OnClickListener {
    private FilterViewModel filterViewModel;
    private EditText searchWord;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchBoxFragment() { }

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
     * @return View , the inflated view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_box, container, false);
    }

    /**
     * This is our event handler which is called once the user clicks a registered button.
     * We call our ViewModel to set the value of our searchWord.
     * @param view This is the view object passed from the event.
     */
    @Override
    public void onClick(View view) {
        filterViewModel.select(searchWord.getText().toString());
    }

    /**
     * This method is called once the view is created.
     * We get all of our elements from the view and add eventlisteners on the buttons.
     * We also create a ViewModel instance for this class.
     * @param view This is the view reference passed from the event.
     * @param savedInstanceState The most recent saved instance.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button buttonSearch = view.findViewById(R.id.buttonSearch);
        searchWord = view.findViewById(R.id.txfSearchWord);
        buttonSearch.setOnClickListener(this);
        filterViewModel = new ViewModelProvider(requireActivity()).get(FilterViewModel.class);
    }
}