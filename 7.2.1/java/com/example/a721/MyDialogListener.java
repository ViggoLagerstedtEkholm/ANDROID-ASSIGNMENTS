package com.example.a721;

/**
 * Our DialogListener that will pass 2 fields to the MainActivity when we press "OK" on the dialog.
 */
public interface MyDialogListener {
    void inputData(String field1, String field2);
}
