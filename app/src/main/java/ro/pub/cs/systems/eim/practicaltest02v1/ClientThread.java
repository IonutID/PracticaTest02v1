package ro.pub.cs.systems.eim.practicaltest02v1;

import android.widget.TextView;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread{
    private String address;
    private int port;
    private String word;
    private TextView responseTextView;

    private Socket socket;

    public ClientThread(String address, int port, String word, TextView responseTextView) {
        this.address = address;
        this.port = port;
        this.word = word;
        this.responseTextView = responseTextView;
    }

    @Override
    public void run() {
       try {
            socket = new Socket(address, port);
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            printWriter.println(word);
            printWriter.flush();
            String response;

            while ((response = bufferedReader.readLine()) != null) {
                final String finalizedResponse = response;
                responseTextView.post(() -> responseTextView.setText(finalizedResponse));
            }
       } catch (Exception exception) {
            exception.printStackTrace();
       }
    }


}
