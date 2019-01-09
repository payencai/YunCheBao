package com.costans;

import android.os.Environment;


/**
 * <b>参数定义</b>
 * <br>
 * <p/>
 * <br>
 * 一个保存所有可配信息的类，把所有可保存的信息放到一个类里面，便于保存和读取
 * <br>
 *
 * @version 1.0 BY 2014-06-10	定义常用参数<br>
 *          1.1 BY 2014-06-13	定时器、文件、用户、服务器代码<br>
 */
public class Constans {
    public final static String DIR_PACKAGE = "com.yichan.libpro";

    /**
     * 文件
     *
     */
    public class FilePath {
        public final static String DIR_ROOT = "yunchebao/";        // 根目录
        public final static String DIR_LOG = "a_bug_logs/";		// 日志目录
        public final static String DIR_PIC_CACHE = "ycbpiccache/"; 	//图片缓存
        public final static String DIR_IMG_PART="partimg/";// 电子图册文件夹路径

        public final static String DIR_RES = "res/";		// 资源根目录
        public final static String DIR_IMG = "img/";		// 图片目录
        public final static String DIR_NOTICE = "notice/";	// 公告目录
        public final static String DIR_SUPPORT = "support/";	// 销售支持
        public final static String DIR_XML = "xml/";		// 配置文件目录
        public final static String TEMP_RES = "temp/";		// 临时文件目录
        public final static String DIR_PIC_PICKER = "picpicker/";		// 临时文件目录
        public final static String DIR_JRCAMERA = "jrcamera/"; 	//手机端拍照存放地址

        public final static String DIR_BOOK_FILE="book/";//教材文件保存路径根路径
        public final static String DIR_BBS_FILE="bbs/";//论坛文件保存路径根路径
        public final static String DIR_OTHER_FILE="other/";//其他文件保存路径根路径

        public final static String CLIENT_ID = "client_id/";		// 设备ID
    }

    //下载地址
    public final static String rootUrl = PlatformContans.root;
    public final static String BASE_ADDRESS_SERVLET = "upload/";// 下载servlet前缀

    /**
     * 动画效果
     *
     */
    public class Animation {
        public final static int PUSH_DOWN = 1; //下推
        public final static int PUSH_UP = 2; //上推
        public final static int ROTATION = 3; //翻转
        public final static int FADE = 4; //淡入淡出
        public final static int SLIDE_IN = 5; //左右切入
        public final static int SLIDE_LEFT = 6; //左推
        public final static int SLIDE_RIGHT = 7; //右推
    }
    /**
     * 文件位置
     *
     * @ClassName: FileLocation
     * @Description:
     */
    public final static class FileLocation {
        public final static String FILE_SAVE_PATH = "/data/data/" + DIR_PACKAGE + "/filedr/image";// 内存中图片存放的位置
        public final static String ESD = Environment.getExternalStorageDirectory().toString();// 根目录
    }

    //缓存文件名
    public static final String CONFIGFILE = "SP_CONFIGFILE";

    /**
     * 文件存储
     * @author JerehSoft
     */
    public class Preference {
        public final static String PREFERENCE_VERSION = "app_version";
        public final static String PREFERENCE_ROOT = "root";
        public final static String PREFERENCE_RES = "res";
        public final static String PREFERENCE_DIR = "dir";
        public final static String PREFERENCE_LOCATION = "app_location";
    }
}
