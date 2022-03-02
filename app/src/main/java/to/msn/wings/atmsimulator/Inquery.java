package to.msn.wings.atmsimulator;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.media.AudioManager;
import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

public class Inquery extends AppCompatActivity {
    private SimpleDatabaseHelper helper = null;
    private EditText txtnumber = null;
    private EditText txtpass = null;
    private EditText txtmoney = null;
    AudioManager am;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inquery_screen);
        helper = new SimpleDatabaseHelper(this);
        txtnumber = findViewById(R.id.txtnumber);
        txtpass = findViewById(R.id.txtpass);
        txtmoney = findViewById(R.id.txtmoney);
    }

    public void onAuthentication(View view) {
        String[] cols = {"number", "pass", "money"};
        String[] params = {txtnumber.getText().toString(),txtpass.getText().toString()};
        try (SQLiteDatabase db = helper.getReadableDatabase();
             Cursor cs = db.query("ATMAccount", cols, "number = ? AND pass = ?" ,params,  "pass = ?", null,null,null)) {
            if (cs.moveToFirst()) {
                txtmoney.setText(cs.getString(2));
                am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
                mp = MediaPlayer.create(this, R.raw.sound1);
                mp.start();
            } else {
                Toast.makeText(this, "データがありません。", Toast.LENGTH_SHORT).show();
                am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
                mp = MediaPlayer.create(this, R.raw.back);
                mp.start();
            }
        }
    }
    public void onBack(View view){
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC,5,0);
        mp = MediaPlayer.create(this,R.raw.back);
        mp.start();
        Intent i = new Intent(this, to.msn.wings.atmsimulator.MainActivity.class);
        startActivity(i);
    }

    public  void onForgetPass(View view){
        Intent i = new Intent(this,to.msn.wings.atmsimulator.ForgetPassword.class);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC,5,0);
        mp = MediaPlayer.create(this,R.raw.sound1);
        mp.start();
        startActivity(i);
    }

    public void onForgetNumber(View view){
        Intent i = new Intent(this,to.msn.wings.atmsimulator.ForgetNumber.class);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC,5,0);
        mp = MediaPlayer.create(this,R.raw.sound1);
        mp.start();
        startActivity(i);
    }
}
