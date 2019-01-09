package com.bbcircle.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Data
 *
 * @author lvfq
 * @Github: https://github.com/lvfaqiang
 * @Blog: http://blog.csdn.net/lv_fq
 * @date 2017/9/2 上午12:14
 * @desc :
 */

public class Data {


//    http://opgirl-tmp.adbxb.cn/images2/mi/2202/148_ce2b24f5ea4fadaf6c5591416e7990dc_75.jpg
//
//    http://opgirl-tmp.adbxb.cn/images3/mi/20/144_dc7b83c64f60363a8212f01eef43f513_75.jpg
//    http://opgirl-tmp.adbxb.cn/images2/mi/1447/148_62de05945c28dbccaeb92f131f26f972_75.jpg
//    http://opgirl-tmp.adbxb.cn/images2/mi/1240/142_b2f099d4f8576543cb0b0c3878cae26e_75.jpg
//    http://i1.go2yd.com/corpimage.php?url=http://si1.go2yd.com/get-image/0CZZQOCHE3M&source=mb&type=_896x504
//
//    http://i1.go2yd.com/corpimage.php?url=http://si1.go2yd.com/get-image/09yg7aATwp6&source=mb&type=_896x504
//
//    http://opgirl-tmp.adbxb.cn/images2/mi/1307/206_773d003844d1966a7ce0cee1d245155a_75.jpg
//
//    http://opgirl-tmp.adbxb.cn/images2/mi/2847/206_da5207eb0798f155572dca227ddf0411_75.jpg
//
//    http://opgirl-tmp.adbxb.cn/images1/mi/2566/208_cfcb6e2cd501aadf61fc572119ad8a29_75.jpg
//
//    http://opgirl-tmp.adbxb.cn/images2/mi/1392/206_7c9ab9991319e284c8ce781481a866a4_75.jpeg

    private static String[] imgs = {
            "http://opgirl-tmp.adbxb.cn/images2/mi/2202/148_ce2b24f5ea4fadaf6c5591416e7990dc_75.jpg",
            "http://opgirl-tmp.adbxb.cn/images3/mi/20/144_dc7b83c64f60363a8212f01eef43f513_75.jpg",
            "http://i1.go2yd.com/corpimage.php?url=http://si1.go2yd.com/get-image/0CZZQOCHE3M&source=mb&type=_896x504",
            "http://opgirl-tmp.adbxb.cn/images2/mi/1447/148_62de05945c28dbccaeb92f131f26f972_75.jpg",
            "http://opgirl-tmp.adbxb.cn/images2/mi/1240/142_b2f099d4f8576543cb0b0c3878cae26e_75.jpg",
            "http://i1.go2yd.com/corpimage.php?url=http://si1.go2yd.com/get-image/09yg7aATwp6&source=mb&type=_896x504",
            "http://opgirl-tmp.adbxb.cn/images2/mi/1307/206_773d003844d1966a7ce0cee1d245155a_75.jpg",
            "http://opgirl-tmp.adbxb.cn/images2/mi/2847/206_da5207eb0798f155572dca227ddf0411_75.jpg",
//            "http://opgirl-tmp.adbxb.cn/images1/mi/2566/208_cfcb6e2cd501aadf61fc572119ad8a29_75.jpg",
            "http://opgirl-tmp.adbxb.cn/images2/mi/1392/206_7c9ab9991319e284c8ce781481a866a4_75.jpeg"
    };

    /**
     * 获取图片数量
     *
     * @param count
     * @return
     */
    public static List<String> getImgs(int count) {
        if (count <= 0) {
            return null;
        }
        List<String> list = new ArrayList<>();
        if (count >= 9) {
            return Arrays.asList(imgs);
        } else {
            for (int i = 0; i < count; i++) {
                list.add(imgs[i]);
            }
        }
        return list;
    }

    /**
     * 生成评论列表
     *
     * @return
     */
    public static List<CommentsBean> getComments() {
        List<CommentsBean> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            CommentsBean commentsBean = new CommentsBean();
            commentsBean.setCommentsId(i);
            commentsBean.setContent("为你打Call ");
            if (i % 2 == 0) {
                UserBean replyUser = new UserBean();
                replyUser.setUserId(i + 30);
                replyUser.setUserName("用户" + replyUser.getUserId());
                commentsBean.setReplyUser(replyUser);
            }
            UserBean comUser = new UserBean();
            comUser.setUserId(i);
            comUser.setUserName("彭于晏" + i);
            commentsBean.setCommentsUser(comUser);
            list.add(commentsBean);
        }

        return list;
    }


    /**
     * 生成点赞列表
     *
     * @return
     */
    public static List<UserBean> getLikes() {
        List<UserBean> list = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            UserBean userBean = new UserBean();
//            userBean.setUserName("用户" + i);
//            userBean.setUserId(i + 1);
//            list.add(userBean);
//        }
        UserBean userBean = new UserBean();
        userBean.setUserName("彭于晏");
        userBean.setUserId(1);
        list.add(userBean);
        UserBean userBean1 = new UserBean();
        userBean1.setUserName("你是个好人");
        userBean1.setUserId(2);
        list.add(userBean1);
        UserBean userBean3 = new UserBean();
        userBean3.setUserName("给个槌子");
        userBean3.setUserId(3);
        list.add(userBean3);

        return list;
    }

}
