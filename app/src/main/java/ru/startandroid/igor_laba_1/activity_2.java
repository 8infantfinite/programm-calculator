package ru.startandroid.igor_laba_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_2 extends AppCompatActivity {
 Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_2);

        button2 = (Button)findViewById(R.id. button2);
         button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_2.this, MainActivity.class);
                startActivity(intent);
            }
        }   );
    }
}
