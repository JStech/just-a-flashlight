package net.stechschulte.justaflashlight;

import android.app.Activity;
import android.hardware.camera2.CaptureRequest;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Flashlight extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashlight);

        Button powerButton = (Button) findViewById(R.id.power);

        int flashMode = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getInt("Preference_FlashModeValue", -1);

        if (flashMode == CaptureRequest.FLASH_MODE_OFF) {
            powerButton.setText(R.string.poweron);
        } else if (flashMode == CaptureRequest.FLASH_MODE_TORCH) {
            powerButton.setText(R.string.poweroff);
        }

        powerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button powerButton = (Button) findViewById(R.id.power);
                int flashMode = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                        .getInt("Preference_FlashModeValue", -1);

                if (flashMode == CaptureRequest.FLASH_MODE_OFF) {

                    powerButton.setText(R.string.poweroff);
                } else if (flashMode == CaptureRequest.FLASH_MODE_TORCH) {
                    powerButton.setText(R.string.poweron);
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_flashlight, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
