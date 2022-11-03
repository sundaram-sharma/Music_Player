package en.sundaram.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity {

    TextView titleTv, currentTimeTv, totalTimeTv;
    SeekBar seekBar;
    ImageView pausePlay, nextBtn, previousBtn, musicIcon;
    ArrayList<AudioModel> songsList;
    AudioModel currentSong;
    MediaPlayer mediaPlayer = MyMediaPlayer.getInstace();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        titleTv = findViewById(R.id.song_title);
        currentTimeTv = findViewById(R.id.current_Time);
        totalTimeTv = findViewById(R.id.total_time);
        seekBar = findViewById(R.id.seek_bar);
        pausePlay = findViewById(R.id.pause_play);
        nextBtn = findViewById(R.id.next);
        previousBtn = findViewById(R.id.previous);
        musicIcon = findViewById(R.id.music_icon_big);

        titleTv.setSelected(true);

        songsList = (ArrayList<AudioModel>) getIntent().getSerializableExtra("LIST"); //this way we got the songs list

        setResourcesWithMusic();
    }
    void setResourcesWithMusic(){

        currentSong = songsList.get(MyMediaPlayer.currentIndex); //whenever the song is played, that song wil get inside the currentSong.

        titleTv.setText(currentSong.getTitle()); //currentSongs title will get set

        totalTimeTv.setText(convertToMMSS(currentSong.getDuration()));

        pausePlay.setOnClickListener(v -> pausePlay());
        nextBtn.setOnClickListener(v -> playNextSong());
        previousBtn.setOnClickListener(v -> playPreviousSong());

        playMusic();

    }

    private void playMusic(){
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(currentSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0); //set progress bar to 0 when song start
            seekBar.setMax(mediaPlayer.getDuration()); //set progress bar at maximum duration depending
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    private void playNextSong(){

        if(MyMediaPlayer.currentIndex == songsList.size()-1){ //if the song is last
            return;
        }

        MyMediaPlayer.currentIndex +=1; //when music is changed to next the index will be updated

        mediaPlayer.reset();
        setResourcesWithMusic();
    }
    private void playPreviousSong(){

        if(MyMediaPlayer.currentIndex == 0){ //if the song is first
            return;
        }

        MyMediaPlayer.currentIndex -=1; //when music is changed to previous, the index will be updated

        mediaPlayer.reset();
        setResourcesWithMusic();

    }

    private void pausePlay(){

        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
        else{
            mediaPlayer.start();
        }

    }

    public static String convertToMMSS(String duration){ //method to get time in millisecond
        Long millis  = Long.parseLong(duration);
        return String.format("%02:%02",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));


    }
}