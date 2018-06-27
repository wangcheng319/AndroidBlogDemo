package com.example.wangc.myapplication;

import android.hardware.Camera;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by wangc on 2018/6/27
 * E-MAIL:274281610@QQ.COM
 */
public class CameraUtil {
    /**
     * 根据比例得到合适的尺寸的最大尺寸
     */
    public static Camera.Size getProperSize4Ratio(List<Camera.Size> sizeList, float displayRatio) {
        Collections.sort(sizeList, new SizeL2hComparator());
        Camera.Size result = null;
        for (Camera.Size size : sizeList) {
            float curRatio = ((float) size.width) / size.height;
            if (curRatio == displayRatio) {
                result = size;
            }
        }

        if (null == result) {
            for (Camera.Size size : sizeList) {
                float curRatio = ((float) size.width) / size.height;
                if (curRatio == 3f / 4) {
                    result = size;
                }
            }
        }
        return result;
    }

    /**
     * 根据宽度得到最大合适的尺寸
     * @param sizeList
     * @param Width
     * @return
     */
    public static Camera.Size getMaxSize4Width(List<Camera.Size> sizeList, int Width) {
        // 先对传进来的size列表进行排序
        Collections.sort(sizeList, new SizeL2hComparator());
        Camera.Size result = null;
        for (Camera.Size size : sizeList) {
            if (size.height == Width) {
                result = size;
            }
        }
        return result;
    }

    /**
     * 获取支持的最大尺寸
     */
    public static Camera.Size getMaxSize(List<Camera.Size> sizeList) {
        // 先对传进来的size列表进行排序
        Collections.sort(sizeList, new SizeL2hComparator());
        Camera.Size result = null;
        if(sizeList != null && !sizeList.isEmpty()){
            result = sizeList.get(sizeList.size() - 1);
        }
        return result;
    }

    /**
     * 从小到大排序
     */
    private static class SizeL2hComparator implements Comparator<Camera.Size> {
        @Override
        public int compare(Camera.Size size1, Camera.Size size2) {
            if (size1.width < size2.width) {
                return -1;
            }else if (size1.width > size2.width) {
                return 1;
            }
            return 0;
        }
    }

    public static int getRecorderRotation(int cameraId){
        android.hardware.Camera.CameraInfo info = new                 android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        return info.orientation;
    }
}
