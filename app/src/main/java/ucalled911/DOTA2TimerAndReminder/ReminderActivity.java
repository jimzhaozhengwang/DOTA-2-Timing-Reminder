package ucalled911.DOTA2TimerAndReminder;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ReminderActivity extends AppCompatActivity {

    MainActivity access = new MainActivity();
    public int second = access.getSecond();
    public int minute = access.getMinute();
    public String plus_minus_zero = access.getPlus_minus_zero();
    public String plus = "+";
    public String minus = "-";
    long startTime = 0;
    MediaPlayer the_battle_begins_sound;
    MediaPlayer stack_sound;
    MediaPlayer rune_sound;
    Intent main_activity;
    PowerManager power_manager;
    PowerManager.WakeLock wake_lock;


    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millisecond = System.currentTimeMillis() - startTime;
            int one_second = (int) (millisecond / 1000);
            if (one_second == 1){
                startTime = System.currentTimeMillis();
                forward_second_logic();
            }
            timerHandler.postDelayed(this, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        the_battle_begins_sound = MediaPlayer.create(this, R.raw.the_battle_begins);
        stack_sound = MediaPlayer.create(this, R.raw.stack);
        rune_sound = MediaPlayer.create(this, R.raw.rune);

        setup();

        power_manager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wake_lock = power_manager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Wake Lock Tag");
        wake_lock.acquire();

        main_activity = new Intent(ReminderActivity.this, MainActivity.class);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        the_battle_begins_sound.release();
        stack_sound.release();
        rune_sound.release();
        timerHandler.removeCallbacks(timerRunnable);
        wake_lock.release();
        finish();
        startActivity(main_activity);
    }

    public void setup(){
        display_time();

        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);

        minus_button_listener();
        plus_button_listener();
        play_pause_button_listener();
        reset_button_listener();
    }

    public void minus_button_listener(){
        Button minus_button = (Button) findViewById(R.id.minus_button);
        minus_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        backward_second_logic();
                    }
                }
        );
    }

    public void plus_button_listener(){
        Button plus_button = (Button) findViewById(R.id.plus_button);
        plus_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        forward_second_logic();
                    }
                }
        );
    }

    public void play_pause_button_listener(){
        final ToggleButton play_pause_button = (ToggleButton) findViewById(R.id.play_pause_button);
        play_pause_button.setText("Pause");
        play_pause_button.setTextOn("Play");
        play_pause_button.setTextOff("Pause");

        play_pause_button.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View V) {
                        if (play_pause_button.getText().equals("Play")) {
                            timerHandler.removeCallbacks(timerRunnable);
                        } else {
                            startTime = System.currentTimeMillis();
                            timerHandler.postDelayed(timerRunnable, 0);
                        }
                    }
                }
        );
    }

    public void reset_button_listener(){
        Button reset_button = (Button) findViewById(R.id.reset_button);
        reset_button.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        the_battle_begins_sound.release();
                        stack_sound.release();
                        rune_sound.release();
                        timerHandler.removeCallbacks(timerRunnable);
                        wake_lock.release();
                        finish();
                        startActivity(main_activity);
                    }
                }
        );
    }

    public void backward_second_logic(){
        if (plus_minus_zero.equals(plus)){
            if (minute == 0 && second == 0) {
                plus_minus_zero = minus;
                second = 1;
            }else if (second == 0){
                second = 59;
                minute--;
            }else{
                second--;
            }
        }else{ // plus_minus_zero.equals(minus)
            if (second == 59){
                second = 0;
                minute++;
            }
            else{
                second++;
            }
        }
        display_time();
    }

    public void forward_second_logic(){
        if (plus_minus_zero.equals(plus)) {
            if (second == 59) {
                second = 0;
                minute++;
            } else {
                second++;
            }
        }else { // plus_minus_zero.equals(minus)
            if (minute != 0 && second == 0) {
                second = 59;
                minute--;
            } else {
                second--;
            }
        }
        display_time();
    }

    public void display_time(){
        String time;
        if (minute == 0 && second == 0){
            time = String.format("%02d : %02d", minute, second);
            the_battle_begins_sound.start();
            plus_minus_zero = plus;
        }else{
            time = String.format(plus_minus_zero + " %02d : %02d", minute, second);
            if (plus_minus_zero.equals(minus) && minute == 0 && second == 20){
                rune_sound.start();
            }
            if (plus_minus_zero.equals(plus) && (minute % 2) != 0 && second == 40){
                rune_sound.start();
            }
            if (plus_minus_zero.equals(plus) && second == 33){
                stack_sound.start();
            }
        }

        TextView time_text = (TextView) findViewById(R.id.time_text);
        time_text.setText(time);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        the_battle_begins_sound.release();
        stack_sound.release();
        rune_sound.release();
        timerHandler.removeCallbacks(timerRunnable);
        wake_lock.release();
        finish();
    }
}

