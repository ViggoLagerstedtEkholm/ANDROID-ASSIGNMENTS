package com.example.a21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The class shows the most recent prime number the program has generated and serializes/deserializes this value in internal storage.
 * The prime is tored in "value.txt".
 * @author  Viggo Lagerstedt Ekholm
 * @date   2021-01-20
 */

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private long value;

    /**
     * OnCreate is called once the Activity is first initiated.
     * Get the latest stored prime number using the ReadFromStorage to deserialize our value.txt.
     * We set the text to the prime number received.
     * If we don't have the value.txt file this would cast an exception.
     * Parse the storedValue from String to long.
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = this.findViewById(R.id.button);
        button.setOnClickListener(this);

        try {
            String storedValue = ReadFromStorage("value.txt");
            String is_prime = getString(R.string.is_prime, storedValue);
            TextView textView = this.findViewById(R.id.textView);
            textView.setText(is_prime); //Set the biggest prime to the stored one.
            value = Long.parseLong(storedValue);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This is our event handler which is called once the user clicks the button.
     * Check if the value we're currently on is prime. If so save this in value.txt.
     * Use the WriteToStore method and pass the file name and the current value to be stored.
     * If it's prime or not the textView will make sure the user knows by displaying the correct response.
     * @param view This is the view object passed from the event.
     */
    @Override
    public void onClick(View view) {
        value++;

        TextView textView = this.findViewById(R.id.textView);

        //Remove decimals.
        int intVal = (int) value;

        //Check if value is prime, if so write that to storage.
        if(isPrime(value)){
            String is_prime = getString(R.string.is_prime, Integer.toString(intVal));
            textView.setText(is_prime);
            try {
                WriteToStorage("value.txt", value);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            String is_not_prime = getString(R.string.is_not_prime, Integer.toString(intVal));
            textView.setText(is_not_prime);
        }
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

    /**
     * This method uses FileOutputStream to write the value to specified path.
     * We write to this output stream with our parsed value.
     * We make sure to close the stream.
     * @param path This is the path of the file
     * @param value This is a long value
     * @exception IOException casts on file error.
     */
    public void WriteToStorage(String path, long value) throws IOException {
        FileOutputStream outputStream = openFileOutput(path, Context.MODE_PRIVATE);
        String str = String.valueOf(value);
        outputStream.write(str.getBytes());
        outputStream.close();
    }

    /**
     * Read content of specified file.
     * The FileInputStream goes through the whole file and sum all characters inside.
     * We make sure to close the stream.
     * @param file This is the path for the file
     * @exception IOException casts on file error.
     * @return String
     */
    public String ReadFromStorage(String file) throws IOException {
        FileInputStream fileInputStream = openFileInput(file);
        int character;
        StringBuilder temp= new StringBuilder();
        while( (character = fileInputStream.read()) != -1){
            temp.append((char) character);
        }

        fileInputStream.close();

        return temp.toString();
    }
}