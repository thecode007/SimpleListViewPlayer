

package com.example.hp.myaudioplayer;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Allows playback of a single MP3 file via the UI. It contains a {@link MediaPlayerHolder}
 * which implements the {@link PlayerAdapter} interface that the activity uses to control
 * audio playback.
 */
public final class MainActivity extends AppCompatActivity {
     private Adapter adapter;
     private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1);
        listView=(ListView)findViewById(R.id.list);
        ArrayList<Item> items=new ArrayList<>();

        items.add(new Item(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Voice0001.aac")));

        adapter=new Adapter(getApplicationContext(),R.layout.list_item,items);
        listView.setAdapter(adapter);
    }





}
