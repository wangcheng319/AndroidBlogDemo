package com.example.wangc.myapplication;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;


public class Utils {

	public static int getWindowScreenHeight(Activity activity) {
		DisplayMetrics outMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	public static int getWindowScreenWidth(Activity activity) {
		DisplayMetrics outMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	public static String getVersionName(Context context) {
		try {
			String pkName = context.getPackageName();
			String versionName = context.getPackageManager().getPackageInfo(
					pkName, 0).versionName;

			return versionName;
		} catch (Exception e) {

		}
		return null;
	}


	// 获得独一无二的Psuedo ID
	/*public static String getUniquePsuedoID() {
		String serial = null;
		String m_szDevIDShort = "35" + Build.BOARD.length() % 10
				+ Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10
				+ Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10
				+ Build.HOST.length() % 10 + Build.ID.length() % 10
				+ Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10
				+ Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10
				+ Build.TYPE.length() % 10 + Build.USER.length() % 10; // 13 位

		try {
			serial = android.os.Build.class.getField("SERIAL").get(null)
					.toString();
			// API>=9 使用serial号
			return new UUID(m_szDevIDShort.hashCode(), serial.hashCode())
					.toString();
		} catch (Exception exception) {
			// serial需要一个初始化
			serial = "serial"; // 随便一个初始化
		}
		// 使用硬件信息拼凑出来的15位号码
		return new UUID(m_szDevIDShort.hashCode(), serial.hashCode())
				.toString();

	}*/



	/**
	 * 将从Message中获取的，表示图片的字符串解析为Bitmap对象
	 * 
	 * @param picStrInMsg
	 * @return
	 */
	public static Bitmap decodeImg(String picStrInMsg) {
		Bitmap bitmap = null;

		byte[] imgByte = null;
		InputStream input = null;
		try {
			imgByte = Base64.decode(picStrInMsg, Base64.DEFAULT);
			BitmapFactory.Options options = new BitmapFactory.Options();
//			 options.inSampleSize = 1;
			input = new ByteArrayInputStream(imgByte);
			SoftReference softRef = new SoftReference(
					BitmapFactory.decodeStream(input, null, options));
			bitmap = (Bitmap) softRef.get();
			;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (imgByte != null) {
				imgByte = null;
			}

			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return bitmap;
	}
	
	public static void closeBoard(Activity mActivity) {
			InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
	        View view = mActivity.getWindow().getDecorView();
	        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	
	/** 
     * 程序是否在前台运行 
     *  
     * @return 
     */  
    public static boolean isAppOnForeground(Activity activity) {
            // Returns a list of application processes that are running on the  
            // device  
               
            ActivityManager activityManager = (ActivityManager) activity.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            String packageName = activity.getApplicationContext().getPackageName();

            List<RunningAppProcessInfo> appProcesses = activityManager
                            .getRunningAppProcesses();  
            if (appProcesses == null)  
                    return false;  

            for (RunningAppProcessInfo appProcess : appProcesses) {
                    // The name of the process that this object is associated with.  
                    if (appProcess.processName.equals(packageName)  
                                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                            return true;  
                    }  
            }  

            return false;  
    }
    
    /** 
     *  
     * @throws IOException
     */  
    public static void deleteAllFilesOfDir(File path) {
        if (!path.exists())  
            return;  
        if (path.isFile()) {  
            path.delete();  
            return;  
        }  
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {  
            deleteAllFilesOfDir(files[i]);  
        }  
        path.delete(); 
    }  
    
    /*public static ArrayList<AllAppInfo> getAllAppList(Context context){
        ArrayList<AllAppInfo> appList = new ArrayList<AllAppInfo>();  
        List<PackageInfo> packageInfos = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packageInfos.size(); i++) {
            PackageInfo pInfo=packageInfos.get(i);
            AllAppInfo allAppInfo=new AllAppInfo();
            allAppInfo.setAppname(pInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());//应用程序的名称
            allAppInfo.setPackagename(pInfo.packageName);//应用程序的包
            allAppInfo.setVersionCode(pInfo.versionCode);//版本号
            allAppInfo.setLastInstal(pInfo.firstInstallTime);
            //allAppInfo.setProvider(pInfo.providers);
            allAppInfo.setInstalPath(pInfo.applicationInfo.sourceDir);
            allAppInfo.setAppicon(pInfo.applicationInfo.loadIcon(context.getPackageManager()));
            appList.add(allAppInfo);
        }
        return appList;
    }*/
    
    public static ArrayList<String> getAllAppList(Context context){
        ArrayList<String> appList = new ArrayList<String>();
        List<PackageInfo> packageInfos = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packageInfos.size(); i++) {
            PackageInfo pInfo=packageInfos.get(i);
            appList.add(pInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());//应用程序的名称
        }
        return appList;
    }
	
    /**
     * 判断应用是否已经启动
     * @param context 一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    public static boolean isAppAlive(Context context, String packageName){
        ActivityManager activityManager =
                (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for(int i = 0; i < processInfos.size(); i++){
            if(processInfos.get(i).processName.equals(packageName)){
                Log.i("NotificationLaunch",
                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        Log.i("NotificationLaunch",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }
    
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	
	/**
	 * 10000 return 10,000
	 * 
	 * @param amount
	 * @return
	 */
	public static String amountConversionFormat(long amount) {
		return NumberFormat.getInstance().format(amount);
	}

	/**
	 * 10000 return 10,000
	 * 
	 * @param amount
	 * @return
	 */
	public static String amountConversionFormat(double amount) {
		return NumberFormat.getInstance().format(amount);
	}

	public static boolean isHttpUrl(String url) {
		
	        return (null != url) &&
	               (url.length() > 6) &&
	               url.substring(0, 7).equalsIgnoreCase("vico://");
	    }
	
	/** 
	 * 读取图片属性：旋转的角度 
	 * @param path 图片绝对路径 
	 * @return degree旋转的角度 
	 */  
	public static int readPictureDegree(String path) {
	    int degree = 0;  
	    try {  
	        ExifInterface exifInterface = new ExifInterface(path);
	        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
	        switch (orientation) {  
	        case ExifInterface.ORIENTATION_ROTATE_90:
	            degree = 90;  
	            break;  
	        case ExifInterface.ORIENTATION_ROTATE_180:
	            degree = 180;  
	            break;  
	        case ExifInterface.ORIENTATION_ROTATE_270:
	            degree = 270;  
	            break;  
	        }  
	    } catch (IOException e) {
	        e.printStackTrace();  
	        return degree;  
	    }  
	    return degree;  
	}  	
	
	/** 
	 * 旋转图片，使图片保持正确的方向。 
	 * @param bitmap 原始图片 
	 * @param degrees 原始图片的角度 
	 * @return Bitmap 旋转后的图片 
	 */  
	public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
	    if (degrees == 0 || null == bitmap) {  
	        return bitmap;  
	    }  
	    Matrix matrix = new Matrix();
	    matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);  
	    Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	    if (null != bitmap) {  
	        bitmap.recycle();  
	    }  
	    return bmp;  
	}



	/**
	 * 判断一个字符串中是否包含有Emoji表情
	 * @param input
	 * @return true 有Emoji
	 */
	public static  boolean isEmojiCharacter(CharSequence input)
	{
		for (int i = 0; i < input.length(); i++)
		{
			if (isEmojiCharacter(input.charAt(i)))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否是Emoji 表情
	 *
	 * @param codePoint
	 * @return true 是Emoji表情
	 */
	public static boolean isEmojiCharacter(char codePoint)
	{
		// Emoji 范围
		boolean isScopeOf = (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF) && (codePoint != 0x263a))
				|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));

		return !isScopeOf;
	}


	/**
	 * 去除字符串中的Emoji表情
	 * @param source
	 * @return
	 */
	public static String removeEmoji(CharSequence source)
	{
		String result = "";
		for (int i = 0; i < source.length(); i++)
		{
			char c = source.charAt(i);
			if (isEmojiCharacter(c))
			{
				continue;
			}
			result += c;
		}
		return result;
	}

	/**
	 * 只允许数字，字母和汉字
	 * @return
	 * @throws PatternSyntaxException
     */
//	public static boolean stringFilter(String str)throws PatternSyntaxException {
////		String   regEx  =  "[^a-zA-Z0-9\u4E00-\u9FA5]";
//		String regex="^[a-zA-Z0-9\u4E00-\u9FA5-\\.]+$";
//		Pattern p   =   Pattern.compile(regex);
//		Matcher m   =   p.matcher(str);
//		return  m.matches();
//	}

	public static String getPrintSize(long size) {
		//如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
		if (size < 1024) {
			return String.valueOf(size) + "B";
		} else {
			size = size / 1024;
		}
		//如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
		//因为还没有到达要使用另一个单位的时候
		//接下去以此类推
		if (size < 1024) {
			return String.valueOf(size) + "KB";
		} else {
			size = size / 1024;
		}
		if (size < 1024) {
			//因为如果以MB为单位的话，要保留最后1位小数，
			//因此，把此数乘以100之后再取余
			size = size * 100;
			return String.valueOf((size / 100)) + "."
					+ String.valueOf((size % 100)) + "MB";
		} else {
			//否则如果要以GB为单位的，先除于1024再作同样的处理
			size = size * 100 / 1024;
			return String.valueOf((size / 100)) + "."
					+ String.valueOf((size % 100)) + "GB";
		}
	}


}
