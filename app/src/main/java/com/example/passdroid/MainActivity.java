package com.example.passdroid;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity implements HceService.MyCallback {
    Button sendButton;
    TextView message;
    String username, pass;
    EditText usernameInput;
    EditText passwordInput;
    String new_creds;
    String final_creds;
    private NfcAdapter nfcAdapter;

    public void updateMyText(String myString) {
         message = (TextView) findViewById(R.id.textView);
         message.setText(myString);

        Log.e("Interface", "Text Updated");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("MainActivity", "Starting...");
        setContentView(R.layout.activity_main);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.isEnabled();
        usernameInput = (EditText) findViewById(R.id.usernameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);

        sendButton = (Button) findViewById(R.id.SendBtn);
        message = (TextView) findViewById(R.id.textView);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setText("Sending...");
                username = usernameInput.getText().toString();
                pass = passwordInput.getText().toString();
                new_creds = username + "," + pass;
                setData(new_creds);
                Log.e("ButtonPress", new_creds);

            }


        });

    }
    


    public String getData(){
        return final_creds;
    }
    public void setData(String final_msg){
        this.final_creds = final_msg;
    }

    public void onResume() {
        super.onResume();

        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(this, "com.example.passdroid.HceService"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }



}
