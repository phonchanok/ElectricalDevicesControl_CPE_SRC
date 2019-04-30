package com.example.edc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import io.netpie.microgear.Microgear;
import io.netpie.microgear.MicrogearEventListener;
public class MainActivity extends AppCompatActivity {
    private Microgear microgear = new Microgear(this);
    private String appid = "EDC"; //APP_ID
    private String key = "atXbHGWUX9NWjH0"; //KEY
    private String secret = "LS3EzlrIeGAmElEw5bF1xIocC"; //SECRET
    private String alias = "android";
    private Button toggleButton2;
    private Button toggleButton3;
    private Button toggleButton4;
    private Button toggleButton5;
    private boolean toggleButton2State = false;
    private boolean toggleButton3State = false;
    private boolean toggleButton4State = false;
    private boolean toggleButton5State = false;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String string = bundle.getString("myKey");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MicrogearCallBack callback = new MicrogearCallBack();
        microgear.connect(appid,key,secret,alias);
        microgear.setCallback(callback);
        microgear.subscribe("Topictest");

        toggleButton2 = findViewById(R.id.toggleButton2);
        toggleButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toggleButton2State)
                {
                    microgear.chat("esp32_node1", "On1");
                }
                else
                {
                    microgear.chat("esp32_node1", "Off1");
                }

                toggleButton2State=!toggleButton2State;

            }

        });
        toggleButton3 = findViewById(R.id.toggleButton3);
        toggleButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toggleButton3State)
                {
                    microgear.chat("esp32_node1", "On2");
                }
                else
                {
                    microgear.chat("esp32_node1", "Off2");
                }

                toggleButton3State=!toggleButton3State;
            }
        });
        toggleButton4 = findViewById(R.id.toggleButton4);
        toggleButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toggleButton4State)
                {
                    microgear.chat("esp32_node1", "On3");
                }
                else
                {
                    microgear.chat("esp32_node1", "Off3");
                }

                toggleButton4State=!toggleButton4State;
            }
        });
        toggleButton5 = findViewById(R.id.toggleButton5);
        toggleButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toggleButton5State)
                {
                    microgear.chat("esp32_node1", "On4");
                }
                else
                {
                    microgear.chat("esp32_node1", "Off4");
                }

                toggleButton5State=!toggleButton5State;
            }
        });
    }
    class MicrogearCallBack implements MicrogearEventListener{
        @Override
        public void onConnect() {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", "Now I'm connected with netpie");
            msg.setData(bundle);
            handler.sendMessage(msg);
            Log.i("Connected","Now I'm connected with netpie");
        }

        @Override
        public void onMessage(String topic, String message) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", topic+" : "+message);
            msg.setData(bundle);
            handler.sendMessage(msg);
            Log.i("Message",topic+" : "+message);
        }

        @Override
        public void onPresent(String token) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", "New friend Connect :"+token);
            msg.setData(bundle);
            handler.sendMessage(msg);
            Log.i("present","New friend Connect :"+token);
        }

        @Override
        public void onAbsent(String token) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", "Friend lost :"+token);
            msg.setData(bundle);
            handler.sendMessage(msg);
            Log.i("absent","Friend lost :"+token);
        }

        @Override
        public void onDisconnect() {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", "Disconnected");
            msg.setData(bundle);
            handler.sendMessage(msg);
            Log.i("disconnect","Disconnected");
        }

        @Override
        public void onError(String error) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", "Exception : "+error);
            msg.setData(bundle);
            handler.sendMessage(msg);
            Log.i("exception","Exception : "+error);
        }

        @Override
        public void onInfo(String info) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", "Exception : "+info);
            msg.setData(bundle);
            handler.sendMessage(msg);
            Log.i("info","Info : "+info);
        }
    }
}

