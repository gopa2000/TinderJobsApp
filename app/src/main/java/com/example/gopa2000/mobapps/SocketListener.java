package com.example.gopa2000.mobapps;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketListener extends Service {

    private static String TAG = "SocketListener";
    private Socket socket;

    private ServiceCallback servicecallback;
    private DispServiceCallback dispServiceCallback;

    public SocketListener() { }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return localBinder;
    }

    public void sendMessage(String msg, JSONObject obj){
        socket.emit(msg, obj);
    }

    public void IsBoundable(){
        Toast.makeText(this,"I bind like butter", Toast.LENGTH_LONG).show();
    }

    private final IBinder localBinder = new LocalBinder();
    public class LocalBinder extends Binder {
        public SocketListener getService(){
            Log.i(TAG, "getService: Sitting in local binder.");
            return SocketListener.this;
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

                socket.on("match", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {

                        SessionManager sessionManager = new SessionManager(getApplicationContext());
                        Map<String, ?> userDetails = sessionManager.getUserDetails();

                        SessionCache sessionCache = SessionCache.getInstance();

                        JSONObject obj      = (JSONObject) args[0];
                        String userEmail    = userDetails.get(DbHelper.KEY_EMAIL).toString();

                        dispServiceCallback.displayMatch();

                        try {
                            String employer = obj.getString("employer").toString();
                            String seeker = obj.getString("seeker").toString();
                            if (userEmail.equals(employer) || userEmail.equals(seeker)){
                                // alert user about match here
                            }

                            sessionCache.addToMatchTable(seeker, employer, userEmail);
                            Log.i(TAG, "call: " + obj.toString());
                        } catch (JSONException e){
                            Log.e(TAG, "call: ", e);
                        }
                    }
                });

                socket.on("message", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {

                            JSONObject obj = (JSONObject) args[0];

                        try {

                            if (servicecallback != null) {
                                servicecallback.receiveMessageRequest(obj);
                            } else {
                                Log.e(TAG, "call: service callback is null.");

                                SessionCache sessionCache = SessionCache.getInstance();
                                sessionCache.addMessagesToChat(obj.get("room").toString(), obj.get("sender").toString(), obj.get("message").toString());
                            }
                        }
                        catch(JSONException e){
                            Log.e(TAG, "call: ", e);
                        }
                    }
                });

                socket.on("like", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        JSONObject obj = (JSONObject)args[0];

                        SessionCache sessionCache = SessionCache.getInstance();

                        try {
                            sessionCache.addToLikeTable(new Like(obj.get("liker").toString(), obj.get("likee").toString()));
                            dispServiceCallback.addToLiketable(new Like(obj.get("liker").toString(), obj.get("likee").toString()));
                        }
                        catch(JSONException e){
                            Log.e(TAG, "call: ", e);
                        }
                    }
                });

                socket.connect();
            } catch (URISyntaxException e){
                Log.e(TAG, "run: ", e);
            }
        }
    }
    public void setCallbacks(ServiceCallback callbacks) {
        servicecallback = callbacks;
    }

    public void dispServicecb(DispServiceCallback dc){
        dispServiceCallback = dc;
    }

    public Socket getSocket(){
        return this.socket;
    }
}
