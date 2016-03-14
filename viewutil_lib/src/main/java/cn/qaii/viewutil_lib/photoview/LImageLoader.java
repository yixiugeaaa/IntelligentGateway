package cn.qaii.viewutil_lib.photoview;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import cn.qaii.viewutil_lib.R;
import cn.qaii.viewutil_lib.constant.LConstants;


/**
 * 图片加载控件
 * LImageLoader
 * 
 * @author larry 2015-5-18 下午1:23:41 
 * @version 1.0.0
 *
 */
public class LImageLoader {
	
	public static DisplayImageOptions mOptions;
	
	/**
	 * 初始化图片加载缓存工具
	 * @param context
	 */
	public static void initConfig(Context context){
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, LConstants.TEMP_PATH); //缓存路径
		@SuppressWarnings("deprecation")
		ImageLoaderConfiguration config = new ImageLoaderConfiguration  
			    .Builder(context)  
//			    .memoryCacheExtraOptions(250, 375) // max width, max height，即保存的每个缓存文件的最大长宽  
			    .threadPoolSize(3)//线程池内加载的数量  
			    .threadPriority(Thread.NORM_PRIORITY - 2)
			    .denyCacheImageMultipleSizesInMemory()  
			    .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现  
//			    .memoryCacheSize(2 * 1024 * 1024)    
//			    .discCacheSize(50 * 1024 * 1024)    
//			    .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密  
			    .tasksProcessingOrder(QueueProcessingType.LIFO)  
//			    .discCacheFileCount(100) //缓存的文件数量  
			    .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径  
			    .defaultDisplayImageOptions(DisplayImageOptions.createSimple())  
			    .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间  
			    .writeDebugLogs() // Remove for release app  
			    .build();//开始构建  
			    // Initialize ImageLoader with configuration. 
		ImageLoader.getInstance().init(config);
		initOptions();
	}
	
	/**
	 * 初始化图片配置参数
	 */
	private static void initOptions(){
		mOptions = new DisplayImageOptions.Builder()  
		.showImageOnLoading(R.drawable.iv_no_picture) //设置图片在下载期间显示的图片
		.showImageForEmptyUri(R.drawable.iv_no_picture)//设置图片Uri为空或是错误的时候显示的图片  
		.showImageOnFail(R.drawable.iv_no_picture)  //设置图片加载/解码过程中错误时候显示的图片  
		.cacheInMemory(true)//设置下载的图片是否缓存在内存中  
		.cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中  
		.considerExifParams(true)  
		.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示  
		.bitmapConfig(Bitmap.Config.ARGB_8888)//设置图片的解码类型//  
//		.decodingOptions(Options decodingOptions)//设置图片的解码配置  
		.considerExifParams(true)//设置图片下载前的延迟  
		//.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的延迟时间  
		//设置图片加入缓存前，对bitmap进行设置  
		//.preProcessor(BitmapProcessor preProcessor)  
//		.resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位  
		.displayer(new RoundedBitmapDisplayer(12))//是否设置为圆角，弧度为多少  
//		.displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间  
		.build();//构建完成  
	}

}
