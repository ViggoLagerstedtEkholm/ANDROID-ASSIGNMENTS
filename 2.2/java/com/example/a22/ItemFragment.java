package com.example.a22;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment representing a list of Items.
 */
public class ItemFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    /**
     * Helper method if we want to save something into the bundle on recreation.
     * @Override We override to add specific behavior.
     * @param columnCount column count.
     * @return void
     */
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called when we create this fragment.
     * @Override We override to add specific behavior.
     * @param savedInstanceState The most recent saved instance.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    /**
     * This method, we create a recycler view that will hold all of our items.
     * @Override We override to add specific behavior.
     * @param inflater The layoutinflated used to inflate any view in a fragment.
     * @param container The parent view the fragment UI should be attached to if not null.
     * @param savedInstanceState The most recent saved instance.
     * @return View , the inflated view with our item XML.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        DatabaseHelper databaseHelper = MainActivity.databaseHelper;
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(databaseHelper.getAllPrimes()));
        }
        return view;
    }

    /**
     * This method will be called when the view is created.
     * We add a OnBackPressedCallback to the main activity so we can press the "back" button on our screen
     * to navigate to the activity.
     * @Override We override to add specific behavior.
     * @param view This is the view object passed from the event.
     * @param savedInstanceState The most recent saved instance.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.action_itemFragment_to_homeFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }
}