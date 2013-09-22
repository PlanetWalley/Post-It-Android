package com.planetwalley.postit;

import java.io.IOException;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/** A basic Camera preview class */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;

    @SuppressWarnings("deprecation")
	public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
//        	setCameraSize();
        } catch (IOException e) {
            //Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }
    
    public void setCameraSize() {
    	Camera.Parameters params = mCamera.getParameters();
    	Rect rect = mHolder.getSurfaceFrame();
    	params.setPreviewSize(rect.width(), rect.height());
    }
    
//    public void updateScreenRatio(FrameLayout preview) {
//        Camera.Size mSize = mCamera.getParameters().getPictureSize();
//        int mHeight = mSize.height;
//        int mWidth = mSize.width;
//        double mRatio = (double) mWidth / mHeight;
//		LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) preview.getLayoutParams();
//		int cHeight = lp.height;
//		int cWidth = lp.width;
//		double cRatio = (double) cWidth / cHeight;
//		if (cRatio < mRatio) // Width is smaller
//			preview.setLayoutParams(new LayoutParams(cWidth, (int) (cWidth / mRatio)));
//		else
//			preview.setLayoutParams(new LayoutParams((int) (cHeight * mRatio), cHeight));
////        Rect rect = mHolder.getSurfaceFrame();
////        int cHeight = rect.height();
////        int cWidth = rect.width();
////        double cRatio = (double) cWidth / cHeight;
////        if (cRatio < mRatio) // Width is smaller
////        	mHolder.setFixedSize(cWidth, (int) (cWidth / mRatio));
////        else
////        	mHolder.setFixedSize((int) (cHeight * mRatio), cHeight);
//    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
          // preview surface does not exist
          return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
          // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            //Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
}
