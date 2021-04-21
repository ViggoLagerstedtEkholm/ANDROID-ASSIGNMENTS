package com.example.a411;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

/**
 * This program will show the user 3 buttons. Each button leads to 3 different youtube videos.
 *
 * @author  Viggo Lagerstedt Ekholm
 * @date   2021-01-29
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This method gets called once we start the activity.
     * We get all of our buttons from the UI by id.
     * We add OnClickListeners to all of them.
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = this.findViewById(R.id.button1);
        Button button2 = this.findViewById(R.id.button2);
        Button button3 = this.findViewById(R.id.button3);

        button1.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ&ab_channel=RickAstleyVEVO"))));
        button2.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=jNQXAC9IVRw&ab_channel=jawed"))));
        button3.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=0rdntnhMFrs&ab_channel=AjStuff1010"))));
    }
}