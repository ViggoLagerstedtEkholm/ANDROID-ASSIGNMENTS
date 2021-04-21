package com.example.a22;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

/**
 *  This class will show the button to add a number to our counter. This fragment is the start view destination of our program.
 */

public class HomeFragment extends Fragment {
    private long value;
    private DatabaseHelper databaseHelper;
    private TextView textView;

    private static final String ARG_COLUMN_COUNT = "column-count";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeFragment() {
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
    }

    /**
     * We return the inflated view of our fragment and pass the container and don't attach it to root.
     * @Override We override to add specific behavior.
     * @param inflater The layoutinflated used to inflate any view in a fragment.
     * @param container The parent view the fragment UI should be attached to if not null.
     * @param savedInstanceState The most recent saved instance.
     * @return View , the inflated view with our item XML.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    /**
     * This method is called once the view is created.
     * We get all the elements from the view by id.
     * We add eventlisteners to our buttons.
     * We load the most recent prime number from our database into the value variable.
     * We show the user the most recent prime number.
     *
     * If we click the button "buttonAdd" we increment the counter.
     * We check if the new incremented number is prime, if it's prime get the date and save this into our database.
     * If the incremented value is not prime we don't add it to our database and instead let the user know it's not prime.
     * The other case "buttonHistory" is used if we want to see the history fragment.
     *
     * @Override We override to add specific behavior.
     * @param view This is the view reference passed from the event.
     * @param savedInstanceState The most recent saved instance.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonHistory = getView().findViewById(R.id.buttonHistory);
        Button buttonAdd = view.findViewById(R.id.buttonAdd);

        textView = view.findViewById(R.id.textView);

        buttonAdd.setOnClickListener(v -> {
            value++;
            databaseHelper.insertValue(value);
            if (isPrime(value)){
                String is_prime = getString(R.string.is_prime, convertFromLongToInt(value));
                textView.setText(is_prime);
                PrimeModel model = new PrimeModel(value, new Date().toString());
                boolean success = databaseHelper.insertPrime(model);
                Toast.makeText(getContext(), "Added: " + success, Toast.LENGTH_SHORT).show();
            } else {
                String is_not_prime = getString(R.string.is_not_prime, convertFromLongToInt(value));
                textView.setText(is_not_prime);
            }
        });

        buttonHistory.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_itemFragment));

        databaseHelper = MainActivity.databaseHelper;

        try {
            value = databaseHelper.getMostRecentValue();
            Log.d("REACHED", "value: " + value);
            if(value == 0){
                String is_not_prime_or_prime = getString(R.string.is_prime_and_not_prime, "0");
                textView.setText(is_not_prime_or_prime);
            }
            else if(isPrime(value)){
                String is_prime = getString(R.string.is_prime, convertFromLongToInt(value));
                textView.setText(is_prime);
            }
            else{
                String is_not_prime = getString(R.string.is_not_prime, convertFromLongToInt(value));
                textView.setText(is_not_prime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Convert long to string for insertion to database.
     * @param val value to be converted.
     */
    private String convertFromLongToInt(long val){
        int intVal = (int) val;
        return String.valueOf(intVal);
    }

    /**
     * This method takes a long value and calculates the square-root of this number.
     * The for-loop checks if our candidate value is prime.
     * If the values is prime we return true, else false.
     * @param candidate long value
     * @return boolean
     */
    private boolean isPrime(long candidate) {
        long sqrt = (long)Math.sqrt(candidate) + 1;
        for(long i = 2; i <= sqrt; i++)
            if(candidate % i == 0) return false;
        return true;
    }
}