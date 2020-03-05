package cpm.mdk.uarttest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import cpm.mdk.uarttest.SerialPortUtil;



public class MainActivity extends AppCompatActivity {

    protected SerialPortUtil serialPortUtil;
    protected Button btn_send;
    protected EditText etx_send;
    protected TextView textView;
    private Switch mswitch;
    private String xxxxx;
    private Button btn_clear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.sample_text);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());


        serialPortUtil = SerialPortUtil.getInstance();
        serialPortUtil.setOnDataReceiveListener(new SerialPortUtil.OnDataReceiveListener() {
            @Override
            public void onDataReceive(byte[] buffer, int size) {
                byte[] xx = new byte[size];
                System.arraycopy(buffer, 0, xx, 0, size);
                String s = new String(xx);
                Log.d("sss","text:"+ s +"size:" + size);
                //textView.setText(s);
                test(s);

            }
        });

        etx_send = findViewById(R.id.send_text);
        mswitch = findViewById(R.id.select);

        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etx_send.getText().length() != 0){
                    serialPortUtil.sendCmds(etx_send.getText().toString(), mswitch.isChecked());
                }

            }
        });


        btn_clear = findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xxxxx="";
                textView.setText(xxxxx);
            }
        });
    }

    public void test(String xx) {
        final String str = xx;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //更改UI；
                xxxxx = xxxxx + str;
                textView.setText(xxxxx);
            }
        });
    }




}
