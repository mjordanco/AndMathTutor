package com.jorflekel.mathtutor;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EntryActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_entry, menu);
        return true;
    }
}
