package form.photo.fractaline.com.photoform;

import android.hardware.Camera;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by Greg on 2015-06-17.
 */
public class CameraView  implements SurfaceHolder.Callback{

    // Variables
    private Camera mCamera = null;
    private boolean mPreviewRunning = false;
    private boolean mProcessing = false;
    private int mWidth = 0;
    private int mHeight = 0;

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height)
    {
        if(mPreviewRunning )
        {
            mCamera.stopPreview();
        }

        // Store width and height
        mWidth = width;
        mHeight = height;

        // Set camera parameters
        Camera.Parameters p = mCamera.getParameters();
        mCamera.setParameters(p);

        if(android.os.Build.VERSION.SDK_INT >= 8)
        {   // If API >= 8 -> rotate display...
            mCamera.setDisplayOrientation(90);
        }

        try
        {
            mCamera.setPreviewDisplay(holder);
        } catch(IOException e)
        {
            e.printStackTrace();
        }

        mCamera.startPreview();
        mPreviewRunning = true;

    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder)
    {
        try {
            mCamera = Camera.open();
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e)
        {
            mCamera.release();
            mCamera = null;
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        if(mCamera != null)
        {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mPreviewRunning = false;
            mCamera.release();
            mCamera = null;
        }
    }
}