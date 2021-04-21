package com.example.a721;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * This class is our DialogFragment and will be custom made for our fields.
 */
public class ExampleDialogFragment extends DialogFragment {
    private EditText field1;
    private EditText field2;
    private MyDialogListener dialogListener;

    /**
     * This method is called when we create our dialog.
     * We will use our own layout_dialog.XML and inflate this to our view.
     * We add OnclickListener to both the OK button and the CANCEL button in the dialog.
     * If we clicked "OK" we will send the result with our dialogListener.
     * Else don't do anything, we canceled.
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     * @return Dialog our dialog.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        field1 = view.findViewById(R.id.Field1);
        field2 = view.findViewById(R.id.Field2);

        builder.setView(view).setTitle("My dialog!");
        builder.setMessage("dialog!");
        builder.setPositiveButton("OK", (dialog, which) -> {
            String input1 = field1.getText().toString();
            String input2 = field2.getText().toString();
            dialogListener.inputData(input1, input2);
        });
        builder.setNegativeButton("CANCEL", (dialog, which) -> {
            //Do whatever
        });

        return builder.create();
    }

    /**
     * This method is called once the fragment is associated with its activity.
     * We try to assign the class dialogListener to the context dialogListener, if we can't do this the exception of "The dialog listener is not implemented" will be printed.
     * @param context the activity context.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            dialogListener = (MyDialogListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "The dialog listener is not implemented");
        }
    }
}
