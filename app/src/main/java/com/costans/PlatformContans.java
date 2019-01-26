package com.costans;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yyy on 2017/7/6.
 */
public class PlatformContans {
    public static String root = "http://www.yunchebao.com:8899";//测试地址
    public static String rootUrl = "http://192.168.3.5:8899";
    //public static String rootUrl = root + ":8899/" + rootTag;//真实部署地址
    //http://api.cn.ronghub.com/user/getToken.[format] portraitUri,name,userId.post
    public static int ViewPagerTag = 0;//主页标签
    public static Map<String, Object> OBJECT_MAP = new HashMap<String, Object>();// 所有数据静态

    public static String DataSetId = "";
    public static String SignToken = "";
    public static boolean isLogin = false;
    public static class User{
        public static final String updateUser=root+"/user/updateUser";
        public static final String getVeriCode=root+"/common/getVerificationCode";
        public static final String isExitsAccount=root+"/user/getUserByUsername";
        public static final String userRegister=root+"/user/addUser";
        public static final String loginByPhone=root+"/user/loginByUsernameAndCode";
        public static final String loginByPwd=root+"/user/userLogin";
        public static final String loginByWechat=root+"/user/loginByWxId";
        public static final String loginByQQ=root+"/user/loginByQqId";
        public static final String updatePwd=root+"/user/updatePassword";
        public static final String bindQQ=root+"/user/bindByQQ";
        public static final String bindWechat=root+"/user/bindByWx";

        public static final String getIdentityVerification=root+"/user/getIdentityVerification";
        public static final String addIdentityVerification=root+"/user/addIdentityVerification";
    }
    public static class WiKi{
        public static final String getWikiClassifyByType=root+ "/wikiClassify/getWikiClassifyByType" ;
        public static final String getBabyWikiByclassifyId=root+ "/babyWiki/getBabyWikiByclassifyId";
    }
    public static class Shop{
        public static final String getMerchantById=root+ "/merchant/getMerchantById" ;


    }
    public static class Gift{
        public static final String getGiftCommodityListToAPP=root+ "/giftCommodity/getGiftCommodityListToAPP" ;
        public static final String getGiftCommodity=root+ "/giftCommodity/getGiftCommodity";
        public static final String addGiftOrder=root+ "/giftCommodityOrder/addGiftOrder";
        public static final String getGiftOrder=root+ "/giftCommodityOrder/getGiftOrder" ;
        public static final String getGiftOrderListByUserId=root+  "/giftCommodityOrder/getGiftOrderListByUserId";
        public static final String getCoinRecordByUserId=root+ "/coinRecord/getCoinRecordByUserId" ;
    }
    public static class GoodMenu{
        public static final String getBabyMerchantCategoryList=root+ "/babycategory/getBabyMerchantCategoryList";
        public static final String getGoodMenu=root+ "/babymerchantIndex/getBabyMerchantIndexList";
        public static final String getGoodList=root+ "/babymercommodity/getBabyMerchantCommodityBySecondId";

        public static final String getHotCommodity=root+ "/babymercommodity/getHotCommodity";
        public static final String getUserCommodity=root+ "/babymercommodity/getUserCommodity";
    }
    public static class GoodInfo{

        public static final String getGoodDetail=root+ "/babymercommodity/getBabyMerchantCommodity";
        public static final String getGoodParams=root+ "/babymercommodity/getBabyMerComParam";
        public static final String getBabyMerComFirstSpecifications=root+ "/babymercommodity/getBabyMerComFirstSpecifications";
    }
    public static class AddressManage{
        public static final String addUserAddress=root+ "/userAddress/addUserAddress";
        public static final String deleteUserAddress=root+  "/userAddress/deleteUserAddress";
        public static final String getUserAddress=root+ "/userAddress/getUserAddress";
        public static final String updateUserAddress=root+ "/userAddress/updateUserAddress";
    }
    public static class GoodsOrder{

        public static final String getGoodsComment=root+ "/babyMerchantOrder/getBabyMerchantCommentListByCommodityId";
        public static final String addOrder=root+ "/babyMerchantOrder/addOrder";
        public static final String addOrderByShoppingCar=root+ "/babyMerchantOrder/addOrderByShoppingCar";
        public static final String addShoppingCar=root+ "/babyMerchantOrder/addShoppingCar";

        public static final String addBabyMerchantComment=root+ "/babyMerchantOrder/addBabyMerchantComment";
        public static final String applyRefund=root+ "/babyMerchantOrder/applyRefund";
        public static final String deleteOrder=root+ "/babyMerchantOrder/delete";

        public static final String deleteShoppingCar=root+ "/babyMerchantOrder/deleteShoppingCar";
        public static final String finishOrder=root+ "/babyMerchantOrder/finish";
        public static final String getShoppingCarList=root+ "/babyMerchantOrder/getShoppingCarList";

        public static final String updateShoppingCarNumber=root+ "/babyMerchantOrder/updateShoppingCarNumber";
        public static final String getBabyMerchantOrderItemByOrderId=root+ "/babyMerchantOrder/getBabyMerchantOrderItemByOrderId";
        public static final String deliveryOrder=root+ "/babyMerchantOrder/delivery";


        public static final String getMyOrderList=root+ "/babyMerchantOrder/getMyOrderList";
    }
    public static class BabyCircle{
        public static final String getSelfDrivingCircleList=root+ "/babyCircle/getSelfDrivingCircleList" ;
        public static final String getMatchCircleList=root +"/babyCircle/getMatchCircleList";
        public static final String getCarShowCircleList=root+ "/babyCircle/getCarShowCircleList" ;
        public static final String getCarCommunicationCircleList=root +"/babyCircle/getCarCommunicationCircleList";

        public static final String getSelfDrivingCircleDetailsById=root+ "/babyCircle/getSelfDrivingCircleDetailsById" ;
        public static final String getMatchCircleById=root +"/babyCircle/getMatchCircleById";
        public static final String getCarShowCircleDetailsById=root+ "/babyCircle/getCarShowCircleDetailsById" ;
        public static final String getCarCommunicationCircleById=root + "/babyCircle/getCarCommunicationCircleById";

        public static final String getHistoryList=root + "/browsingHistory/getHistoryList";
        public static final String addSelfDrivingEnter=root + "/babyCircle/addSelfDrivingEnter";
        public static final String addSelfDrivingCircle=root +"/babyCircle/addSelfDrivingCircle";
        public static final String addCarCommunicationCircle=root+ "/babyCircle/addCarCommunicationCircle" ;
        public static final String addCarShowCircle=root + "/babyCircle/addCarShowCircle";
        public static final String getMyCircle=root+"/babyCircle/getMyCircle";
    }

    public static class Collect{
        public static final String addCarCollection=root + "/washCollection/addWashCollection";
        public static final String isCollectionByShopId=root + "/washCollection/isCollectionByShopId";
        public static final String getWashCollectionList=root + "/washCollection/getWashCollectionList";
        public static final String deleteWashCollection=root + "/washCollection/deleteWashCollection";

        public static final String addBabyCollection=root + "/babyCircleCollection/addBabyCollection";
        public static final String getBabyCollection=root + "/babyCircleCollection/getBabyCollection";
        public static final String getCollectionById=root + "/babyCircleCollection/getCollectionById";
    }
    public static class Commom{
        public static final String findCarWashRepairShopList=root+ "/carWashRepairShop/findCarWashRepairShopList";
        public static final String uploadImg=root + "/image/uploadImage";
        public static final String uploadVideo=root + "/image/uploadVideo";
        public static final String addWashRepairAppointment=root + "/appointment/addWashRepairAppointment";
        public static final String addRoadRescueAppointment=root + "/appointment/addRoadRescueAppointment";
        public static final String getAppointmentCategoryListByApp=root + "/functionManager/getAppointmentCategoryListByApp";
        public static final String getSkipUrl=root+ "/common/getSkipUrl" ;
        public static final String getBannerList=root+"/banner/getBannerList";
    }
    public static class FourShop{
        public static final String getFourShopListByApp=root + "/fourShop/getFourShopListByApp";
        public static final String addFourShopOrder=root +  "/fourShop/addFourShopOrder";
    }
    public static class RoadRescue{
        public static final String getRoadRescueShopListByApp=root+ "/roadRescue/getRoadRescueShopListByApp";
    }

    public static class CarWashRepairShop{
        public static final String getWashRepairCommentDetailsList=root+ "/carWashRepairShop/getWashRepairCommentDetailsList";
        public static final String getCarWashRepairShopListByApp=root+ "/carWashRepairShop/getCarWashRepairShopListByApp";
        public static final String getWashRepairServeResultByShopId=root+ "/carWashRepairShop/getWashRepairServeResultByShopId";
    }
    public static class CarRent{
        public static final String getRentCarPhoto=root+ "/rentcar/getRentCarPhoto";
        public static final String getRentCar=root+ "/rentcar/getRentCar";
        public static final String getRentCarCarList=root+ "/rentcar/getRentCarCarList";
    }
    public static class OldCar{
        public static final String addOldCarUserCar=root+ "/oldcar/addOldCarUserCar";
        public static final String getOldCarMerchantCarByApp=root+ "/oldcar/getOldCarMerchantCarByApp";
        public static final String getOldCarMerchantCarByUser=root+ "/oldcar/getOldCarMerchantCarByUser";
      //  public static final String getWashRepairServeResultByShopId=root+ "/carWashRepairShop/getWashRepairServeResultByShopId";
    }
    public static class CarOrder{
        public static final String addCarOrder=root+ "/carOrder/addCarOrder";
        public static final String cancelCarOrder=root+ "/carOrder/cancelCarOrder";
        public static final String getUserCarOrder=root+ "/carOrder/getUserCarOrder";

    }
    public static class Pay{
        public static final String babyMerchantOrderPay=root+ "/alipay/babyMerchantOrderPay";
        public static final String memberCardPay=root+ "/alipay/memberCardPay";
        public static final String carOrderPay=root+ "/alipay/carOrderPay";
        public static final String washRepairShopPay=root+ "/alipay/washRepairShopPay";
    }
    public static class Chat{
        public static final String searchFriendByKeyWord=root+"/huanxin/searchFriendByKeyWord";
        public static final String searchCrowdsByKeyWord=root+ "/huanxin/searchCrowdsByKeyWord";
        public static final String quitCrowdByCrowdId=root+"/huanxin/quitCrowdByCrowdId";
        public static final String joinCrowdByUserIds=root+"/huanxin/joinCrowdByUserIds";
        public static final String getMyFriendList=root+"/huanxin/getMyFriendList";
        public static final String updateCrowdApply=root+"/huanxin/updateCrowdApply";
        public static final String updateCrowds=root+  "/huanxin/updateCrowds";
        public static final String updateFriendApply=root+"/huanxin/updateFriendApply";
        public static final String updateMyCrowdData=root+"/huanxin/updateMyCrowdData";
        public static final String searchFriends=root+"/huanxin/searchFriends";
        public static final String getFriendApplyList=root+  "/huanxin/getFriendApplyList";
        public static final String getCrowdsList=root+"/huanxin/getCrowdsList";
        public static final String getCrowdDetailsByCrowdId=root+"/huanxin/getCrowdDetailsByCrowdId";
        public static final String getCrowdApplyList=root+ "/huanxin/getCrowdApplyList";
        public static final String addCrowdApply=root+"/huanxin/addCrowdApply";
        public static final String addCrowds=root+  "/huanxin/addCrowds";
        public static final String addFriendApply=root+"/huanxin/addFriendApply";
        public static final String deleteMyFriend=root+"/huanxin/deleteMyFriend";
        public static final String dismissCrowdByCrowdId=root+ "/huanxin/dismissCrowdByCrowdId";
        public static final String deleteCrowdByUserIds=root+"/huanxin/deleteCrowdByUserIds";
        public static final String kickCrowdByCrowdId=root+"/huanxin/kickCrowdByCrowdId";
        public static final String transferCrowdByCrowdId=root+ "/huanxin/transferCrowdByCrowdId";
    }
    public static  class NewCar{
        public static final String getMerchantList=root+"/newcar/getMerchantList";
        public static final String getComment=root+ "/evaluation/getMerchantEvaluationByUser";
        public static final String getNewCarMerchantMessage=root+ "/newcar/getNewCarMerchantMessage";
        public static final String getDetailParams=root+ "/newcar/getCarCategoryDetailParamById";
    }
    public static  class CarCategory{
        public static final String getFirstCategory=root+ "/carcategory/getFirstCategory";
        public static final String getSubclass=root+ "/carcategory/getSubclass";
        public static final String getNewOldIndex=root+ "/carcategory/getNewOldIndex";
    }
    public static class DrivingSchool{
        public static final String getDrivingSchool=root+ "/drivingschool/getDrivingSchool";
        public static final String getDrivingSchoolClass=root+ "/drivingschool/getDrivingSchoolClass";
        public static final String getDrivingSchoolCoach=root+ "/drivingschool/getDrivingSchoolCoach";
        public static final String getCoashComment=root+ "/drivingschool/getMerchantEvaluationByUser";
        public static final String getUserComment=root+ "/evaluation/getMerchantEvaluationByUser";
        public static final String getDrivingSchoolPhoto=root+"/drivingschool/getDrivingSchoolPhoto";
    }
    public static class LoginContacts {
        public static final String FILENAME = "fckg";
        public static String NOT_FIRST_ENTER = "NOT_FIRST_ENTER";
        public static String IS_AUTO_LOGIN = "isAutoLogin";
        public static String IS_REM_PASSWORD = "ispassword";
        public static String IS_REM_USERNAME = "isuserinfo";
        public static String PASSWORD = "password";
        public static String USERNAME = "username";
        public static Double lat = null;
        public static Double lng = null;
        public static String BOOK_TYPE_ID = "BOOK_TYPE_ID";
        public static int BOOK_TYPE_WASH_ID = 1;
        public static int BOOK_TYPE_REPAIR_ID = 2;
        public static int BOOK_TYPE_NEW_ID = 3;
        public static int BOOK_TYPE_OLD_ID = 4;


    }



    public static String data = "{\"code\":0,\"msg\":\"\\u83b7\\u53d6\\u5546\\u54c1\\u6210\\u529f\",\"data\":[{\"cat\":\"汽车服务\",\"goods\":[{\"id\":\"1001\",\"name\":\"标准洗车-轿车\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"100\",\"sold_num\":\"2\"},{\"id\":\"1003\",\"name\":\"标准洗车-五座轿车\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"3\"}]},{\"cat\":\"汽车美容\",\"goods\":[{\"id\":\"1004\",\"name\":\"全车抛光\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"3\"},{\"id\":\"1005\",\"name\":\"内饰清洗\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"3\"},{\"id\":\"1006\",\"name\":\"全车打蜡\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"0\"},{\"id\":\"1007\",\"name\":\"空调除臭\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"1\"}]}]}";
    public static String data2 = "{\"code\":0,\"msg\":\"\\u83b7\\u53d6\\u5546\\u54c1\\u6210\\u529f\",\"data\":[{\"cat\":\"附近门店\",\"goods\":[{\"id\":\"1001\",\"name\":\"爱琴海购物公园送车点\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"100\",\"sold_num\":\"2\"},{\"id\":\"1003\",\"name\":\"荷泰花园酒店送车点\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"3\"}]},{\"cat\":\"机场/火车站\",\"goods\":[{\"id\":\"1004\",\"name\":\"昆明站\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"3\"},{\"id\":\"1005\",\"name\":\"长水机场\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"3\"},{\"id\":\"1006\",\"name\":\"昆明南站\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"0\"},{\"id\":\"1007\",\"name\":\"昆明北站\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"1\"}]}]}";
    public static String data3 = "{\"code\":0,\"msg\":\"\\u83b7\\u53d6\\u5546\\u54c1\\u6210\\u529f\",\"data\":[{\"cat\":\"购车常识\",\"goods\":[{\"id\":\"1001\",\"name\":\"金九银十是买车的最好时机吗？\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"100\",\"sold_num\":\"2\"},{\"id\":\"1003\",\"name\":\"买德系车和日系车那个更保值？\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"3\"}]},{\"cat\":\"需求选车\",\"goods\":[{\"id\":\"1004\",\"name\":\"排量大的车比排量小的车更省油？汽车谣言你只多少\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"3\"},{\"id\":\"1005\",\"name\":\"买新车还是买二手\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"3\"},{\"id\":\"1006\",\"name\":\"买新车还是买二手\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"0\"},{\"id\":\"1007\",\"name\":\"买新车还是买二手\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"1\"}]}]}";
    public static String data4 = "{\"code\":0,\"msg\":\"\\u83b7\\u53d6\\u5546\\u54c1\\u6210\\u529f\",\"data\":[{\"cat\":\"初次购车\",\"goods\":[{\"id\":\"1001\",\"name\":\"汽车故障灯说明大全？\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"100\",\"sold_num\":\"2\"},{\"id\":\"1003\",\"name\":\"排量大的车比排量小的车更省油？汽车谣言你知多少\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"3\"}]},{\"cat\":\"家庭用车\",\"goods\":[{\"id\":\"1004\",\"name\":\"排量大的车比排量小的车更省油\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"3\"},{\"id\":\"1005\",\"name\":\"买新车还是买二手\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"3\"},{\"id\":\"1006\",\"name\":\"买新车还是买二手\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"0\"},{\"id\":\"1007\",\"name\":\"买新车还是买二手\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"1\"}]}]}";
}
