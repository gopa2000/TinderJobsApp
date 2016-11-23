package com.example.gopa2000.mobapps;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketListener extends Service {

    private static String TAG = "SocketListener";
    private Socket socket;

    public SocketListener() { }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return localBinder;
    }

    private final IBinder localBinder = new LocalBinder();
    public class LocalBinder extends Binder {
        public SocketListener getService(){
            Log.i(TAG, "getService: Sitting in local binder.");
            return SocketListener.this;
        }

        public void sendMessage(String message){
            socket.emit("match", message);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void isBoundable(){
        Log.i(TAG, "Bind like a baller.");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Runnable connect = new ConnectSocket();
        new Thread(connect).start();
        return START_STICKY;
    }

    class ConnectSocket implements Runnable {
        @Override
        public void run() {
            try {
                socket = IO.socket(RESTClient.getURL());

                socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Log.i(TAG, "call: Connected to backend, yo!");
                    }
                });

                socket.connect();
            } catch (URISyntaxException e){
                Log.e(TAG, "run: ", e);
            }
        }
    }

    public Socket getSocket(){
        return this.socket;
    }
}
