package com.video.upload.app.adapter;

import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.video.upload.app.CountResponse;
import com.video.upload.app.PlaybackActivity;
import com.video.upload.app.R;
import com.video.upload.app.db.SqlDatabase;
import com.video.upload.app.db.Videofile;
import com.video.upload.app.interfaces.OnClickEvent;
import com.video.upload.app.rest.API_Client;
import com.video.upload.app.rest.ApiInterface;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterClass extends BaseAdapter {

    List<Videofile> getAllContacts;
    PlaybackActivity context;
    OnClickEvent onClickEvent;
    Button button;
    VideoView videoView;
    SqlDatabase sqlDatabase;
    TextView status;

    public AdapterClass(List<Videofile> getAllContacts, PlaybackActivity context,OnClickEvent onClickEvent,SqlDatabase sqlDatabase) {
        this.getAllContacts = getAllContacts;
        this.context = context;
        this.onClickEvent=onClickEvent;
        this.sqlDatabase=sqlDatabase;
    }

    @Override
    public int getCount() {
        return getAllContacts.size();
    }

    @Override
    public Object getItem(int position) {
        return getAllContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(R.layout.adapterview,parent,false);
        TextView textView=convertView.findViewById(R.id.tv_video_path);
        textView.setText(getAllContacts.get(position).getFilepath());
        status=convertView.findViewById(R.id.status);
        videoView=convertView.findViewById(R.id.vv_playback);
        videoView.setVideoPath(getAllContacts.get(position).getFilepath());
        videoView.setKeepScreenOn(true);
        videoView.setMediaController(new MediaController(context));
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
            }
        });
        videoView.start();

        button=convertView.findViewById(R.id.upload);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileUpload(getAllContacts.get(position).getFilepath());
            }
        });

        if(getAllContacts.get(position).getStatus().equals("YES"))
        {
            button.setVisibility(View.GONE);
            status.setVisibility(View.VISIBLE);
        }
        else
        {
            button.setVisibility(View.VISIBLE);
            status.setVisibility(View.GONE);
        }
        return convertView;
    }

    private void fileUpload(final String files)
    {
        ApiInterface apiInterface= API_Client.getClient().create(ApiInterface.class);

        File file = new File(files);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Call<CountResponse> call=apiInterface.getAgentOfficeList(body); //
        call.enqueue(new Callback<CountResponse>() {
            @Override
            public void onResponse(Call<CountResponse> call, Response<CountResponse> response) {
                try{
                    if(response.code()==200)
                    {
                        if(response.body()!=null)
                        {

                            int i=sqlDatabase.update(files,"YES");
                            if(i>0)
                            {
                                context.startActivity(new Intent(context,PlaybackActivity.class));
                                context.finish();
                            }
                        }
                        else
                        {
                            Toast.makeText(context, "Upload error..", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(context, "Upload error..", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(context, "Upload error..", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<CountResponse> call, Throwable t) {
                Toast.makeText(context, "Upload error..", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
