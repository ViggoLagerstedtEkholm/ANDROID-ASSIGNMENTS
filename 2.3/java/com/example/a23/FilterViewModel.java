package com.example.a23;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 *  This class is a is a subclass of @ViewModel.
 *  SearchBoxFragment will pass a String item to the select method once we want to search for a contact.
 *  ContactsFragment will observe this and will access this search word with getSelected returning the LiveData.
 *  This class makes it possible to isolate both SearchBoxFragment and ContactsFragment so they don't establish a direct connection.
 *  Both ContactsFragment and SearchBoxFragment will have a instance of this class.
 *  @author Viggo Lagerstedt Ekholm
 */
public class FilterViewModel extends ViewModel {
    private final MutableLiveData<String> selected = new MutableLiveData<>();

    /**
     * This method is a setter for the selected value.
     * @param item item we want to pass.
     */
    public void select(String item) {
        selected.setValue(item);
    }

    /**
     * This method is a returns the live data.
     * @return LiveData<String>
     */
    public LiveData<String> getSelected() {
        return selected;
    }
}
