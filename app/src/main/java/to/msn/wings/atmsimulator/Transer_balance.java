package to.msn.wings.atmsimulator;

import android.content.ContentValues;
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

public class Transer_balance extends AppCompatActivity {
    private SimpleDatabaseHelper helper = null;
    private EditText txtnumber_my = null;
    private EditText txtpass_my = null;
    private EditText txtmoney_my = null;
    private EditText txttranser = null;
    private EditText txtnumber = null;
    private EditText txtpass = null;
    private EditText txtmoney = null;
    AudioManager am;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transer_balance);
        helper = new SimpleDatabaseHelper(this);
        Intent intent = getIntent();
        CharSequence getString1 = intent.getCharSequenceExtra("String Value1");
        CharSequence getString2 = intent.getCharSequenceExtra("String Value2");
        CharSequence getString3 = intent.getCharSequenceExtra("String Value3");
        txtnumber_my = findViewById(R.id.txtnumber_my);
        txtpass_my = findViewById(R.id.txtpass_my);
        txtmoney_my = findViewById(R.id.txtmoney_my);
        txtnumber = findViewById(R.id.txtnumber);
        txtpass = findViewById(R.id.txtpass);
        txtmoney = findViewById(R.id.txtmoney);
        txttranser = findViewById(R.id.txttranser);

        txtnumber_my.setVisibility(View.VISIBLE);
        txtpass_my.setVisibility(View.VISIBLE);

        txtpass.setVisibility(View.VISIBLE);
        txtmoney.setVisibility(View.VISIBLE);

        txtnumber_my.setText(getString1);
        txtpass_my.setText(getString2);
        txtmoney_my.setText(getString3);

    }

    public void onTranser(View view) {
        int number = Integer.parseInt(txtnumber.getText().toString());
        int number_my = Integer.parseInt(txtnumber_my.getText().toString());
        if (number == number_my) {
            am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            am.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
            mp = MediaPlayer.create(this, R.raw.back);
            mp.start();
            Toast.makeText(this, "お振込み元として使用されている口座番号です。",
                    Toast.LENGTH_SHORT).show();
        } else {

            int money = Integer.parseInt(txtmoney_my.getText().toString());
            try {
                int transer = Integer.parseInt(txttranser.getText().toString());
                if (money <= transer) {
                    am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    am.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
                    mp = MediaPlayer.create(this, R.raw.back);
                    mp.start();
                    Toast.makeText(this, "残高が不足しているためお振込みできません。",
                            Toast.LENGTH_SHORT).show();
                } else {
                    String[] cols = {"number", "pass", "money"};
                    String[] params = {txtnumber.getText().toString()};
                    try (SQLiteDatabase db = helper.getReadableDatabase();
                         Cursor cs = db.query("ATMAccount", cols, "number = ?", params, null, null, null)
                    ) {
                        if (cs.moveToFirst()) {
                            txtpass.setText(cs.getString(1));
                            txtmoney.setText(cs.getString(2));
                            int money_transer = Integer.parseInt(txtmoney.getText().toString());
                            try (SQLiteDatabase db_transer = helper.getWritableDatabase()) {
                                int getmoney = money_transer + transer;
                                ContentValues tr = new ContentValues();
                                tr.put("number", txtnumber.getText().toString());
                                tr.put("pass", txtpass.getText().toString());
                                tr.put("money", getmoney);
                                am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                                am.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
                                mp = MediaPlayer.create(this, R.raw.sound2);
                                mp.start();
                                db_transer.insertWithOnConflict("ATMAccount", null, tr, SQLiteDatabase.CONFLICT_REPLACE);
                                Toast.makeText(this, "お振込完了しました。",
                                        Toast.LENGTH_SHORT).show();
                                try (SQLiteDatabase db_my = helper.getWritableDatabase()) {
                                    int result = money - transer;
                                    ContentValues my = new ContentValues();
                                    my.put("number", txtnumber_my.getText().toString());
                                    my.put("pass", txtpass_my.getText().toString());
                                    my.put("money", result);
                                    db_my.insertWithOnConflict("ATMAccount", null, my, SQLiteDatabase.CONFLICT_REPLACE);

                                    Intent i = new Intent(this, to.msn.wings.atmsimulator.MainActivity.class);
                                    startActivity(i);
                                }
                            }
                        } else {
                            Toast.makeText(this, "データがありません。", Toast.LENGTH_SHORT).show();
                            am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                            am.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
                            mp = MediaPlayer.create(this, R.raw.back);
                            mp.start();
                        }
                    }
                }

            } catch (NumberFormatException e) {
                am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
                mp = MediaPlayer.create(this, R.raw.back);
                mp.start();
                Toast.makeText(this, "お振込み金額を入力してください。",
                        Toast.LENGTH_SHORT).show();
            }
        }

        }
    public void onBack (View view){
        Intent i = new Intent(this, to.msn.wings.atmsimulator.MainActivity.class);
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);
        mp = MediaPlayer.create(this, R.raw.back);
        mp.start();
        startActivity(i);
    }
}







