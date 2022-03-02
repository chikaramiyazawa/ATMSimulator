package to.msn.wings.atmsimulator;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import android.media.AudioManager;
import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

public class DrawerActivity extends AppCompatActivity {
    private SimpleDatabaseHelper helper = null;
    private EditText txtnumber = null;
    private EditText txtpass = null;
    private EditText txtmoney = null;
    private EditText txtdrawer = null;
    AudioManager am;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity);
        helper = new SimpleDatabaseHelper(this);
        Intent intent = getIntent();
        CharSequence getString1 = intent.getCharSequenceExtra("String Value1");
        CharSequence getString2 = intent.getCharSequenceExtra("String Value2");
        CharSequence getString3 = intent.getCharSequenceExtra("String Value3");
        txtnumber = findViewById(R.id.txtnumber);
        txtpass = findViewById(R.id.txtpass);
        txtmoney = findViewById(R.id.txtmoney);
        txtdrawer = findViewById(R.id.txtdrawer);
        txtnumber.setVisibility(View.INVISIBLE);
        txtpass.setVisibility(View.INVISIBLE);
        txtnumber.setText(getString1);
        txtpass.setText(getString2);
        txtmoney.setText(getString3);
    }

    public void onDrawer(View view) {
        int money = Integer.parseInt(txtmoney.getText().toString());
        try{
            int drawer = Integer.parseInt(txtdrawer.getText().toString());
            if (money <= drawer) {
                am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.setStreamVolume(AudioManager.STREAM_MUSIC,5,0);
                mp = MediaPlayer.create(this,R.raw.back);
                mp.start();
                Toast.makeText(this, "残高が不足しているためお引き出しできません",
                        Toast.LENGTH_SHORT).show();
            } else {
                int result = money - drawer;
                try (SQLiteDatabase db = helper.getWritableDatabase()) {
                    ContentValues cv = new ContentValues();
                    cv.put("number", txtnumber.getText().toString());
                    cv.put("pass", txtpass.getText().toString());
                    cv.put("money", result);
                    db.insertWithOnConflict("ATMAccount", null, cv, SQLiteDatabase.CONFLICT_REPLACE);
                    Toast.makeText(this, "ありがとうございました",
                            Toast.LENGTH_SHORT).show();
                    am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    am.setStreamVolume(AudioManager.STREAM_MUSIC,5,0);
                    mp = MediaPlayer.create(this,R.raw.sound2);
                    mp.start();
                    Intent i = new Intent(this, to.msn.wings.atmsimulator.MainActivity.class);
                    startActivity(i);
                }
            }

        }catch(NumberFormatException e){
            am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            am.setStreamVolume(AudioManager.STREAM_MUSIC,5,0);
            mp = MediaPlayer.create(this,R.raw.back);
            mp.start();
            Toast.makeText(this, "お引き出し金額が入力してください。",
                    Toast.LENGTH_SHORT).show();
        }

    }
    public void onBack(View view){
        Intent i = new Intent(this, to.msn.wings.atmsimulator.MainActivity.class);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC,5,0);
        mp = MediaPlayer.create(this,R.raw.back);
        mp.start();
        startActivity(i);
    }
}
