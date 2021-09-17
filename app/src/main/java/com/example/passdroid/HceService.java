package com.example.passdroid;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.cardemulation.HostApduService;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import android.util.Log;

public class HceService extends HostApduService {
    MainActivity main = new MainActivity();
    String credentials;
    byte sw1 = 90;
    byte sw2 = 00;
    byte[] status = "9000".getBytes();


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("APDU", "Connection established!.");
    }


    public interface MyCallback {
        public void updateMyText(String myString);

    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    NdefMessage message = new NdefMessage(new NdefRecord[] {NdefRecord.createTextRecord("en", "test")});
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public byte[] processCommandApdu(byte[] RawApdu, Bundle extras) {
        Log.e("Response", "Command sent");
        byte[] response = "username,password".getBytes();
        Log.e("Response", Arrays.toString(response));
        return response;

    }

    @Override
    public void onDeactivated(int reason) {
        Log.e("onDeactivated", "Disconnected...");
    }


}

