package en.sundaram.musicplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

import en.sundaram.musicplayer.R;
import en.sundaram.musicplayer.model.audioModel;
import en.sundaram.musicplayer.activities.musicPlayerActivity;
import en.sundaram.musicplayer.myMediaPlayer;

public class musicListAdapter extends RecyclerView.Adapter<musicListAdapter.ViewHolder>{

    ArrayList<audioModel> songsList;
    Context context;

    public musicListAdapter(ArrayList<audioModel> songsList, Context context) {
        this.songsList = songsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) { // to create a new ViewHolder and initializes some private fields to be used by RecyclerView.
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new musicListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(musicListAdapter.ViewHolder holder, int position) { // to update the ViewHolder contents with the item at the given position
        audioModel songData = songsList.get(position);
        holder.titleTextView.setText(songData.getTitle()); //update the title with the current song name

        if(myMediaPlayer.currentIndex==position){ //if the selected songs played
            holder.titleTextView.setTextColor(Color.parseColor("#FF0000")); //update the color of current song
        }else{
            holder.titleTextView.setTextColor(Color.parseColor("#000000")); //update the color of other songa
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to another activity

                myMediaPlayer.getInstance().reset();
                myMediaPlayer.currentIndex = position;
                Intent intent = new Intent(context, musicPlayerActivity.class);
                intent.putExtra("LIST",songsList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent); //start new activity ( music player activity ) when song is clicked

            }
        });

    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        ImageView iconImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.music_title_text); //
            iconImageView = itemView.findViewById(R.id.icon_view);
        }
    }
}
