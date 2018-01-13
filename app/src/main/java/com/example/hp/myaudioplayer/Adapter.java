package com.example.hp.myaudioplayer;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.SeekBar;

import java.util.ArrayList;

/**
 * Created by HP on 1/9/2018.
 */

public class Adapter  extends ArrayAdapter<Item> {

    private ArrayList<Item> items;
    private int resource;
    private Context context;

    public Adapter(@NonNull Context context, int resource, @NonNull ArrayList<Item> items) {
        super(context, resource, items);
        this.items=items;
        this.resource=resource;
        this.context=context;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public Item getItem(int position) {
        return items.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null || convertView.getTag()==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(resource,null);
            holder.mPlayButton = (Button) convertView.findViewById(R.id.button_play);
            holder.mPauseButton = (Button) convertView.findViewById(R.id.button_pause);
            holder.mResetButton = (Button) convertView.findViewById(R.id.button_reset);
            holder.mSeekbarAudio = (SeekBar) convertView.findViewById(R.id.seekbar_audio);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.mediaPlayerHolder=new MediaPlayerHolder(context,getItem(position).uri,holder.mSeekbarAudio);
        holder.playbackListener=new PlaybackListener();
        holder.mediaPlayerHolder.setPlaybackInfoListener(holder.playbackListener);
        holder.mUserIsSeeking=false;
        holder.mediaPlayerHolder.loadMedia(getItem(position).uri);


        final ViewHolder finalHolder = holder;
        holder.mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalHolder.mediaPlayerHolder.play();
            }
        });

        holder.mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalHolder.mediaPlayerHolder.pause();
            }
        });
        holder.mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalHolder.mediaPlayerHolder.reset();
            }
        });

        holder.mSeekbarAudio.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int userSelectedPosition = 0;

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        finalHolder.mUserIsSeeking = true;
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            userSelectedPosition = progress;
                        }
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        finalHolder.mUserIsSeeking = false;
                        finalHolder.mediaPlayerHolder.seekTo(userSelectedPosition);
                    }
                });


        return convertView;
    }



    private class ViewHolder{
        public Button mPlayButton;
        public Button mPauseButton;
        public Button mResetButton;
        public SeekBar mSeekbarAudio;
        public MediaPlayerHolder mediaPlayerHolder;
        public PlaybackListener playbackListener;
        public boolean mUserIsSeeking= false;

    }



    public class PlaybackListener extends PlaybackInfoListener {

        @Override
        public void onDurationChanged(int duration,SeekBar seekBar) {
            seekBar.setMax(duration);
            Log.d("Playing with audio", String.format("setPlaybackDuration: setMax(%d)", duration));
        }

        @Override
        public void onPositionChanged(int position,boolean mUserIsSeeking,SeekBar mSeekbarAudio) {
            if (!mUserIsSeeking) {
                mSeekbarAudio.setProgress(position);
                Log.d("On positionchanged", String.format("setPlaybackPosition: setProgress(%d)", position));
            }
        }

        @Override
        public void onStateChanged(@State int state) {
            String stateToString = PlaybackInfoListener.convertStateToString(state);
        }

        @Override
        public void onPlaybackCompleted(SeekBar mSeekbarAudio) {
            mSeekbarAudio.setProgress(0);

        }



    }
}
