package ro.pub.cs.systems.eim.practicaltest02v1;

import android.util.Log;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{

    private ServerSocket serverSocket = null;

    public ServerThread(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Log.v(Constants.TAG, "Waiting for a client invocation...");
                Socket socket = serverSocket.accept();
                Log.v(Constants.TAG, "Client connected...");
                CommunicationThread communicationThread = new CommunicationThread(this, socket);
                communicationThread.start();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
