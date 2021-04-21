package com.example.a711;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This program will show a editText and the user can enter a text, the user can then press the copy button to copy the text in the editText
 * The user can then press the paste button to print the copied text.
 *
 * @author  Viggo Lagerstedt Ekholm
 * @date   2021-02-03
 */
public class MainActivity extends AppCompatActivity {
    private Button paste;
    private TextView textView;

    private ClipboardManager clipboardManager;

    /**
     * This method is called when we first start the activity.
     * We get all the UI components by id.
     * We add onClickListeners on our buttons.
     * We create the ClipboardManager to handle copy and paste.
     *
     *      * Copy:
     *      * If we clicked the copy button we check if the text is empty, if it's empty tell the user to put some text in.
     *      * If we have text in the field we can then copy it to our ClipData and add it to our ClipboardManager.
     *      *
     *      * Paste:
     *      * If we clicked the paste button we first see if there are any text that has been copied. If nothing is copied nothing will print.
     *      * If we have copied text from the field we can get this text and display it.
     *
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button copy = this.findViewById(R.id.buttonCopy);
        paste =  this.findViewById(R.id.buttonPaste);
        EditText textToCopy = this.findViewById(R.id.txtCopyText);
        textView = this.findViewById(R.id.textViewShowCopy);

        copy.setOnClickListener(v -> {
            String copiedText = textToCopy.getText().toString();

            if(!copiedText.equals("")){
                ClipData clipDataInout = ClipData.newPlainText("text", copiedText);
                clipboardManager.setPrimaryClip(clipDataInout);
                Toast.makeText(MainActivity.this, "Copied!", Toast.LENGTH_SHORT).show();
                paste.setEnabled(true);
            }else{
                Toast.makeText(MainActivity.this, "Put some text in!", Toast.LENGTH_SHORT).show();
            }
        });
        paste.setOnClickListener(v -> {
            String copiedText = textToCopy.getText().toString();

            if(!copiedText.equals("")){
                ClipData clipDataOutput = clipboardManager.getPrimaryClip();
                ClipData.Item item = clipDataOutput.getItemAt(0);
                textView.setText(item.getText().toString());
                Toast.makeText(MainActivity.this, "Pasted!", Toast.LENGTH_SHORT).show();
            }
        });

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if(!clipboardManager.hasPrimaryClip()){
            paste.setEnabled(false);
        }
    }
}