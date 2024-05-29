package ro.pub.cs.systems.eim.practicaltest02v1;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CommunicationThread extends Thread{
    private ServerThread serverThread;
    private Socket socket;

    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    @Override
    public void run() {
        if (socket == null) {
            return;
        }
        try {
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);

            String word = bufferedReader.readLine();

            if (word == null || word.isEmpty()) {
                return;
            }

            HttpClient httpClient = new DefaultHttpClient();
            String PageSourceCode = "";
            HttpGet httpGet = new HttpGet(Constants.WEB_SERVICE_ADDRESS + word);
            HttpResponse httpGetResponse = httpClient.execute(httpGet);
            HttpEntity httpGetEntity = httpGetResponse.getEntity();
            if (httpGetEntity != null) {
                PageSourceCode = EntityUtils.toString(httpGetEntity);
            }

            String result = "";
            JSONArray jsonArray = new JSONArray(PageSourceCode);

            for (int i = 0; i < jsonArray.length(); i++) {
                Object element = jsonArray.get(i);
                if (element instanceof JSONArray) {
                    result = element.toString();
                    break;
                }
            }

            printWriter.println(result);
            printWriter.flush();


        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}


