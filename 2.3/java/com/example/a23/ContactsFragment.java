package com.example.a23;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * @author Viggo Lagerstedt Ekholm
 */
public class ContactsFragment extends Fragment {
    private String searchWord;
    private List<Contact> contacts;
    private MyItemRecyclerViewAdapter adapter;
    private static final int REQUEST_RUNTIME_PERMISSION = 100;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactsFragment() { }

    /**
     * OnCreate is called once the Fragment is first created.
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contacts = new ArrayList<>();
    }

    /**
     * This method is called once the view is created.
     * We add our observer here to get the value passed from our ViewModel.
     * @param view This is the view reference passed from the event.
     * @param savedInstanceState The most recent saved instance.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FilterViewModel filterViewModel = new ViewModelProvider(requireActivity()).get(FilterViewModel.class);
        LiveData<String> searchFilter = filterViewModel.getSelected();
        searchFilter.observe(getViewLifecycleOwner(), item -> {
            searchWord = item;
            contacts = getContacts();
            adapter.addItems(contacts);
            adapter.notifyDataSetChanged();
        });
    }

    /**
     * This method return the inflated view of our fragment.
     * Our ItemRecyclerView is created here. We also use our getContacts to fill this adapter with items.
     * @param inflater The layoutinflated used to inflate any view in a fragment.
     * @param container The parent view the fragment UI should be attached to if not null.
     * @param savedInstanceState The most recent saved instance.
     * @return View , the inflated view with our item XML.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);

        adapter = new MyItemRecyclerViewAdapter();
        searchWord = "";
        contacts = getContacts();
        adapter.addItems(contacts);
        adapter.notifyDataSetChanged();

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);
        }

        return view;
    }

    /**
     * This method checks if we have permission, and we query the user for permission before doing anything else.
     * If we get permission we get all the contacts from the user phone.
     * The contact data contains of a name and a phone number. If a given contact doesn't have a number we fill this field with "NO NUMBER".
     * @return List<Contact> All the contacts from the user phone.
     */
    private List<Contact> getContacts() {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_CONTACTS); //granted = 0
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[] {Manifest.permission.READ_CONTACTS}, REQUEST_RUNTIME_PERMISSION);
            return new ArrayList<>();
        }
        contacts.clear();
        List<Contact> contacts = new ArrayList<>();
        String phone = "";

        //Filter by using a selection
        String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" like'%" + searchWord +"%'";
        Cursor cur = requireContext().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, selection, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                // get the phone number
                Cursor pCur = requireContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                        new String[]{id}, null);
                while (pCur.moveToNext()) {
                    phone = pCur.getString(
                            pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    System.out.println("phone" + phone);
                }
                Contact contact;
                if(phone.equals("")){
                    contact = new Contact(name, "NO NUMBER");
                }else{
                    contact = new Contact(name, phone);
                }
                contacts.add(contact);
                Log.d("STATUS", "FETCHING DONE: " + contacts.size());

                phone = "";
                pCur.close();
            }
        }

        if(contacts.size() == 0){
            Toast.makeText(getActivity(), "No contacts found!", Toast.LENGTH_SHORT).show();
        }
        cur.close();
        return contacts;
    }

    /**
     * This method does the right logic whenever the user clicks accept. We can therefore fill our adapter with items if the user clicked "Allow this app to use Contacts".
     * @param grantResults The permission we are asking for.
     * @param permissions The permissions requested.
     * @param requestCode The code we sent when requesting permission.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_RUNTIME_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                adapter.addItems(getContacts());
                adapter.notifyDataSetChanged();
            } else {
                // Permission Denied
                Toast.makeText(getActivity(), "READ_CONTACTS Denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}