package net.stechschulte.justaflashlight;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ToggleButton;

import java.io.IOException;

public class Flashlight extends Activity implements SurfaceHolder.Callback {

    private Camera camera;
    private static final String TAG = "just-a-flashlight";
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashlight);

        surfaceView = (SurfaceView) this.findViewById(R.id.surfaceview);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        boolean hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!hasFlash) {
            AlertDialog alert = new AlertDialog.Builder(Flashlight.this).create();
            alert.setTitle("Error");
            alert.setMessage("Your device does not have a flash.");
            alert.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
            return;
        }

        camera = Camera.open();
        if (camera == null) {
            Log.e(TAG, "can't open camera");
        }

    }

    public void toggleButtonClick(View view) {
        Camera.Parameters p;

        Log.v(TAG, "HERE");
        ToggleButton powerButton = (ToggleButton) findViewById(R.id.power);
        if (powerButton.isChecked()) {
            p = camera.getParameters();
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(p);
            camera.startPreview();
            Log.v(TAG, "flash_mode_torch");
        } else {
            p = camera.getParameters();
            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(p);
            camera.stopPreview();
            Log.v(TAG, "flash_mode_off");
        }
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

    public void onDestroy() {
        super.onDestroy();
        if (camera != null) {
            camera.release();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int I, int J, int K) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}
