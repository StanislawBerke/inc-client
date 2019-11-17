package stanislaw.inc_client;

import androidx.appcompat.app.AppCompatActivity;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.widget.Button;
import android.widget.EditText;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnMaxTemp = findViewById(R.id.btnAktualizujMaxTemp);
        EditText txtMaxTemp = findViewById(R.id.txtMaxTemp);
        EditText txtWynik = findViewById(R.id.txtWynik);

        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        String ipAddress = Formatter.formatIpAddress(ip);

        txtWynik.setText("Adres ip aplikacji: " + ipAddress);
        btnMaxTemp.setOnClickListener(view -> {

            String maxTemp = txtMaxTemp.getText().toString();
            txtWynik.setText("Ustawiam temperatruę na: " + maxTemp);
            try {
                Socket clientSocket = new Socket("192.168.1.105", 5432);
                PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                printWriter.println("zmien temp max");
                printWriter.println(maxTemp);
                clientSocket.close();
            } catch (IOException e) {

            }
        });
    }
}