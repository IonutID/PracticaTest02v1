package ro.pub.cs.systems.eim.practicaltest02v1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PracticalTest02v1MainActivity extends AppCompatActivity {

    private EditText serverPortEditText = null;
    private Button startServerButton = null;
    private EditText clientAddressEditText = null;
    private EditText clientPortEditText = null;
    private EditText clientWordEditText = null;

    private TextView responseTextView = null;

    private Button connectButton = null;
    private Button getWordButton = null;
    private ServerThread serverThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serverPortEditText = (EditText)findViewById(R.id.server_port);
        clientAddressEditText = (EditText)findViewById(R.id.server);
        clientPortEditText = (EditText)findViewById(R.id.port);
        clientWordEditText = (EditText)findViewById(R.id.word);
        responseTextView = (TextView)findViewById(R.id.response);

        connectButton = (Button)findViewById(R.id.btn_start_server);
        getWordButton = (Button)findViewById(R.id.search);

        connectButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serverPort = serverPortEditText.getText().toString();
                if (serverPort == null || serverPort.isEmpty()) {
                    return;
                }
                serverThread = new ServerThread(Integer.parseInt(serverPort));
                if (serverThread.getServerSocket() != null) {
                    serverThread.start();
                }
            }
        });


        getWordButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clientAddress = clientAddressEditText.getText().toString();
                String clientPort = clientPortEditText.getText().toString();
                String clientWord = clientWordEditText.getText().toString();
                if (clientAddress == null || clientAddress.isEmpty() ||
                        clientPort == null || clientPort.isEmpty() ||
                        clientWord == null || clientWord.isEmpty()) {
                    return;
                }

                ClientThread clientThread = new ClientThread(
                        clientAddress, Integer.parseInt(clientPort), clientWord, responseTextView);
                clientThread.start();
            }
        });
    }
}