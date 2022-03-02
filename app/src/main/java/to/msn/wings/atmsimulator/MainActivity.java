package to.msn.wings.atmsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.media.AudioManager;
import android.media.MediaPlayer;



public class MainActivity extends AppCompatActivity {

    AudioManager am;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void registration_onClick(View view){
        Intent i = new Intent(this,to.msn.wings.atmsimulator.Registration.class);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC,5,0);
        mp = MediaPlayer.create(this,R.raw.sound1);
        mp.start();
        startActivity(i);

    }

    public void deposit_onClick(View view){
        Intent i = new Intent(this,to.msn.wings.atmsimulator.Deposit.class);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC,5,0);
        mp = MediaPlayer.create(this,R.raw.sound1);
        mp.start();
        startActivity(i);
    }

    public void drawer_onClick(View view){
        Intent i = new Intent(this,to.msn.wings.atmsimulator.Drawer.class);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC,5,0);
        mp = MediaPlayer.create(this,R.raw.sound1);
        mp.start();
        startActivity(i);
    }

    public void Inquery_onClick(View view){
        Intent i = new Intent(this,to.msn.wings.atmsimulator.Inquery.class);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC,5,0);
        mp = MediaPlayer.create(this,R.raw.sound1);
        mp.start();
        startActivity(i);
    }

    public void transer_onClick(View view){
        Intent i = new Intent(this, to.msn.wings.atmsimulator.Transer_Select.class);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC,5,0);
        mp = MediaPlayer.create(this,R.raw.sound1);
        mp.start();
        startActivity(i);
    }
}