package com.video.upload.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.VideoView;

import com.video.upload.app.adapter.AdapterClass;
import com.video.upload.app.db.SqlDatabase;
import com.video.upload.app.db.Videofile;
import com.video.upload.app.interfaces.OnClickEvent;

import java.util.ArrayList;
import java.util.List;

public class PlaybackActivity extends AppCompatActivity implements OnClickEvent {


    List<Videofile> getAllContacts=new ArrayList<>();
    AdapterClass adapterClass;
    ListView listView;
    SqlDatabase sqlDatabase;
    LinearLayout gone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);
        listView=findViewById(R.id.list);
        sqlDatabase=new SqlDatabase(this);
        gone=findViewById(R.id.gone);
        getAllContacts=sqlDatabase.getAllContacts();
        if(getAllContacts.size()>0) {
            adapterClass = new AdapterClass(getAllContacts, this, this, sqlDatabase);
            listView.setAdapter(adapterClass);
        }
        else
        {
            listView.setVisibility(View.GONE);
            gone.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getClick(String path) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.recording:
                startActivity(new Intent(this,FFmpegRecordActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
