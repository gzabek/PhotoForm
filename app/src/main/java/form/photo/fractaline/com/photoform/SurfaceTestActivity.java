package form.photo.fractaline.com.photoform;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class SurfaceTestActivity extends Activity {

    private Context mContext;
    private CameraView cameraView;
    private CameraPreview cameraPreview;
    private Camera mCamera;
    private Handler mHandler = new Handler();
    private final Runnable mLoadCamera = new Runnable()
    {
        public void run()
        {
            startCamera();
        }
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent();
        mContext = getApplicationContext();
    }

    private void startCamera()
    {
        //RelativeLayout rl = (RelativeLayout)findViewById(R.id.surface_camera);
        LinearLayout rl = (LinearLayout)findViewById(R.id.camera_preview);
        SurfaceView surfaceView = new SurfaceView(mContext);
        final SurfaceHolder mSurfaceHolder = surfaceView.getHolder();

        mCamera = getCameraInstance();

        try
        {
            //cameraView = new CameraView();
            //mSurfaceHolder.addCallback(cameraView);

            cameraPreview = new CameraPreview(mContext, mCamera);
            mSurfaceHolder.addCallback(cameraPreview);

            mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        } catch(Exception e)
        {
            Log.d("debug", "Another exception");
            e.printStackTrace();
        }

        if(rl != null && surfaceView != null)
            //rl.addView(surfaceView);
            rl.addView(cameraPreview);
    }

    private void setContent()
    {
        //setContentView(R.layout.activity_surface_test);
        setContentView(R.layout.camera_preview);
        // Post the Runnable with a Slight delay -> than your layout will be
        // shown. Without the delay -> your UI will feel inresponsive
        mHandler.postDelayed(mLoadCamera, 100);
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
}