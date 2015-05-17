package com.example.administrator.myclient_for_xxd;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.SynchronousQueue;


public class MainActivity extends ActionBarActivity {

    TextView tv = null;
    EditText ev = null;
    EditText ip = null;
    Button send = null;
    Button link = null;
    private String content = "";
    Socket socket = null;
    Thread printServer = null;
    Thread receive = null;
    PrintWriter pw = null;
    BufferedReader br = null;
    Vector <String> waitMessage=null;
    ScrollView sv=null;
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String z=tv.getText().toString();

            while(!waitMessage.isEmpty()) {
                z = z + "\n" + waitMessage.elementAt(0);
                waitMessage.remove(0);
            }
            tv.setText(z);

            sv.fullScroll(ScrollView.FOCUS_DOWN);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.outputTV);
        ev = (EditText) findViewById(R.id.editText);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(click);
        sv=(ScrollView)findViewById(R.id.scrollView);
        ip = (EditText) findViewById(R.id.IP);
        link = (Button) findViewById(R.id.Link);
        link.setOnClickListener(linkClick);
        waitMessage= new Vector <String>();
    }

    View.OnClickListener linkClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Thread t = new Thread(new linkThread());
            t.start();
        }
    };

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (socket == null) {
                Toast.makeText(getApplicationContext(),"you need to link before send",Toast.LENGTH_LONG).show();
                return;
            }
            printServer = new Thread(new printThread());
            printServer.start();
        }
    };

    public class linkThread implements Runnable {
        @Override
        public void run() {
            try {

                if (socket != null) {
                    socket.close();
                }
                socket = new Socket(ip.getText().toString(), 8080);
                pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                        socket.getOutputStream(), "Unicode")), true);
                br = new BufferedReader(new InputStreamReader(
                        socket.getInputStream(), "Unicode"));
                receive = new Thread(new receiveThread());
                receive.start();
                Log.e("mylog", "socket make success");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class receiveThread implements Runnable {

        @Override
        public void run() {
            try {

                Log.e("mylog", "receive start loop");
                while (true) {
                    String get;
                    get = br.readLine();
                    if (get == null)
                        continue;
                    waitMessage.add(get);
                    mHandler.sendMessage(mHandler.obtainMessage());
                    Log.e("mylog", "get message" + content);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class printThread implements Runnable {
        @Override
        public void run() {
            pw.println(ev.getText().toString());
            pw.flush();
        }
    }
}
