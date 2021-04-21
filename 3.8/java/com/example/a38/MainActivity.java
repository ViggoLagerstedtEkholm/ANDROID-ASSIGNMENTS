package com.example.a38;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * This program draws a circle after the user put in a width and height value into the EditBoxes.
 * We use both Drawables and Canvas to draw 2D graphics.
 *
 * @author  Viggo Lagerstedt Ekholm
 * @date   2021-01-27
 */
public class MainActivity extends Activity implements View.OnClickListener {
    private EditText editTextRadius;
    private MyShapeDrawable myShapeDrawable;

    /**
     * OnCreate is called once the Activity is first initiated.
     * We get all the UI components from the View by id.
     * We add EventListeners to our buttons.
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonUpdate = this.findViewById(R.id.buttonUpdate);

        editTextRadius = this.findViewById(R.id.editTextRadius);

        myShapeDrawable = new MyShapeDrawable(this);
        ImageView imageViewDraw = this.findViewById(R.id.imageViewDraw);
        imageViewDraw.setImageDrawable(myShapeDrawable);

        buttonUpdate.setOnClickListener(this);
    }

    /**
     * We first check if the user has put any value into the EditText box.
     * If there are value change the radius of our circle model.
     * Invalidate calls the draw method to redraw the screen.
     * @param view This is the view object passed from the event.
     */
    @Override
    public void onClick(View view) {
        if(editTextRadius.length() > 0){
            int radius = Integer.parseInt(editTextRadius.getText().toString());

            myShapeDrawable.getCircle().setRadius(radius);

            myShapeDrawable.invalidateSelf();
        }
        else{
            Toast.makeText(this, "Enter all values!", Toast.LENGTH_SHORT).show();
        }
    }
}