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
    public static Map<String, Object> OBJECT_MAP = new HashMap<String, Object>();// 所有数据静态
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
        public static final String updatePayPassword=root+"/user/updatePayPassword";
        public static final String bindQQ=root+"/user/bindByQQ";
        public static final String bindWechat=root+"/user/bindByWx";
        public static final String getIdentityVerification=root+"/user/getIdentityVerification";
        public static final String addIdentityVerification=root+"/user/addIdentityVerification";
        public static final String getMyWallet=root+"/user/getMyWallet";
        public static final String bindTelephone=root+"/user/bindTelephone";
        public static final String relieveTelephone=root+"/user/relieveTelephone";
        public static final String getUserFocusNumber=root+"/user/getUserFocusNumber";
        public static final String getOtherFocusList=root+ "/user/getOtherFocusList";
        public static final String getSpendRecord=root+ "/user/getSpendRecord";
        public static final String getUserResultById=root+ "/user/getUserResultById";
        public static final String setCarShowState=root+"/user/setCarShowState";
        public static final String getDynamic=root+"/user/getDynamic";
        public static final String getNotice=root+"/user/getNotice";
        public static final String addUserFocus=root+ "/user/addUserFocus";
        public static final String deleteUserFocus=root+ "/user/deleteUserFocus";
        public static final String getUserFocusList=root+ "/user/getUserFocusList";
        public static final String isFocus=root+ "/user/isFocus";
        public static final String getUserResult=root+ "/user/getUserResult";
    }
    public static class WiKi{
        public static final String getWikiClassifyByType=root+ "/wikiClassify/getWikiClassifyByType" ;
        public static final String getBabyWikiByclassifyId=root+ "/babyWiki/getBabyWikiByclassifyId";
    }
    public static class Shop{
        public static final String getMerchantById=root+ "/merchant/getMerchantById" ;
    }
    public static class MerchAdmin{
        public static final String getMerchInformationByShopId=root+ "/merchAdmin/getMerchInformationByShopId" ;
    }
    public static class MemberCard{
        public static final String getMemberCardRuleList=root+ "/memberCard/getMemberCardRuleList" ;
        public static final String addMemberCardOrder=root+ "/memberCard/addMemberCardOrder";
        public static final String memberCardPay=root+ "/alipay/memberCardPay";
    }
    public static class Label{
        public static final String addLabel=root+ "/label/addLabel" ;
        public static final String getLabelList=root+ "/label/getLabelList" ;
        public static final String deleteLabel=root+ "/label/deleteLabel" ;
        public static final String update=root+ "/label/update" ;
        public static final String updateLabel=root+ "/label/updateLabel" ;

    }
    public static class Gift{
        public static final String getGiftCommodityListToAPP=root+ "/giftCommodity/getGiftCommodityListToAPP" ;
        public static final String getGiftCommodity=root+ "/giftCommodity/getGiftCommodity";
        public static final String addGiftOrder=root+ "/giftCommodityOrder/addGiftOrder";
        public static final String getGiftOrder=root+ "/giftCommodityOrder/getGiftOrder" ;
        public static final String getGiftOrderListByUserId=root+  "/giftCommodityOrder/getGiftOrderListByUserId";
        public static final String getCoinRecordByUserId=root+ "/coinRecord/getCoinRecordByUserId" ;
        public static final String getCoinRuleList=root+ "/coinRule/getCoinRuleList" ;
    }
    public static class GoodMenu{
        public static final String getBabyMerchantCategoryList=root+ "/babycategory/getBabyMerchantCategoryList";
        public static final String getGoodMenu=root+ "/babymerchantIndex/getBabyMerchantIndexList";
        public static final String getGoodList=root+ "/babymercommodity/getBabyMerchantCommodityBySecondId";

        public static final String getHotCommodity=root+ "/babymercommodity/getHotCommodity";
        public static final String getUserCommodity=root+ "/babymercommodity/getUserCommodity";
    }
    public static class GoodCollect{
        public static final String getBabyMerchantCollection=root+ "/babyMerchantCollection/getBabyMerchantCollection";
        public static final String addBabyMerchantCollection=root+ "/babyMerchantCollection/addBabyMerchantCollection";
        public static final String deleteCommodityCollection=root+ "/babyMerchantCollection/deleteCommodityCollection";

    }
    public static class GoodInfo{
        public static final String getBabyMerchantShopResultById=root+ "/babymerchant/getBabyMerchantShopResultById";
        public static final String getMerchantCommodityList=root+ "/babymercommodity/getMerchantCommodityList";
        public static final String getCommodityByDistince=root+ "/babymercommodity/getCommodityByDistince";
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
        public static final String deleteShoppingCar=root+ "/babyMerchantOrder/deleteShoppingCar";
        public static final String getShoppingCarList=root+ "/babyMerchantOrder/getShoppingCarList";
        public static final String updateShoppingCarNumber=root+ "/babyMerchantOrder/updateShoppingCarNumber";
        public static final String getMyOrderList=root+ "/babyMerchantOrder/getMyOrderList";
        public static final String getBabyMerchantOrderItemByOrderId=root+ "/babyMerchantOrder/getBabyMerchantOrderItemByOrderId";
        public static final String deliveryOrder=root+ "/babyMerchantOrder/delivery";
        public static final String addBabyMerchantComment=root+ "/babyMerchantOrder/addBabyMerchantComment";
        public static final String applyRefund=root+ "/babyMerchantOrder/applyRefund";
        public static final String deleteOrder=root+ "/babyMerchantOrder/delete";
        public static final String finishOrder=root+ "/babyMerchantOrder/finish";
        public static final String cancelOrder=root+ "/babyMerchantOrder/cancel";
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

        public static final String addBabyCircleComment=root+ "/babyCircle/addBabyCircleComment";
        public static final String replyBabyCircleComment=root + "/babyCircle/replyBabyCircleComment";
        public static final String getBabyCircleCommentDetailsById=root+"/babyCircle/getBabyCircleCommentDetailsById";


        public static final String deleteCarCommunicationCircle=root+ "/babyCircle/deleteCarCommunicationCircle";
        public static final String deleteCarShowCircle=root + "/babyCircle/deleteCarShowCircle";
        public static final String deleteSelfDrivingCircle=root+ "/babyCircle/deleteSelfDrivingCircle";
    }
    public static class Collect{

        public static final String getBabyMerchantCollectionList=root + "/babyMerchantCollection/getBabyMerchantCollectionList";

        public static final String addNewCarCollection=root + "/carCollection/addNewCarCollection";
        public static final String getNewCarCollection=root + "/carCollection/getNewCarCollection";
        public static final String getNewCarCollectionList=root + "/carCollection/getNewCarCollectionList";

        public static final String addOldCarCollection=root + "/carCollection/addOldCarCollection";
        public static final String getOldCarCollection=root + "/carCollection/getOldCarCollection";
        public static final String getOldCarCollectionList=root + "/carCollection/getOldCarCollectionList";

        public static final String addCarCollection=root + "/washCollection/addWashCollection";
        public static final String isCollectionByShopId=root + "/washCollection/isCollectionByShopId";
        public static final String getWashCollectionList=root + "/washCollection/getWashCollectionList";
        public static final String deleteWashCollection=root + "/washCollection/deleteWashCollection";

        public static final String addBabyCollection=root + "/babyCircleCollection/addBabyCollection";
        public static final String getBabyCollection=root + "/babyCircleCollection/getBabyCollection";
        public static final String getCollectionById=root + "/babyCircleCollection/getCollectionById";

        public static final String addCommodityCollection=root+ "/commodityCollection/addCommodityCollection";
        public static final String deleteCommodityCollection=root+ "/commodityCollection/deleteCommodityCollection";
        public static final String getCommodityCollectionList=root+ "/commodityCollection/getCommodityCollectionList" ;
        public static final String isCollectionByCommodityId=root+ "/commodityCollection/isCollectionByCommodityId" ;

        public static final String addDrivingSchoolCollection=root + "/drivingSchoolCollection/addDrivingSchoolCollection";
        public static final String getDrivingSchoolCollection=root + "/drivingSchoolCollection/getDrivingSchoolCollection";
        public static final String getDrivingSchoolCollectionList=root + "/drivingSchoolCollection/getDrivingSchoolCollectionList";
    }
    public static class Commom{
        public static final String findCarWashRepairShopList=root+ "/carWashRepairShop/findCarWashRepairShopList";
        public static final String uploadImg=root + "/image/uploadImage";
        public static final String uploadImgs=root + "/image/uploadImages";
        public static final String uploadVideo=root + "/image/uploadVideo";
        public static final String searchAll=root+"/common/searchAll";
        public static final String getAppointmentCategoryListByApp=root + "/functionManager/getAppointmentCategoryListByApp";
        public static final String getSkipUrl=root+ "/common/getSkipUrl";
        public static final String getBannerList=root+"/banner/getBannerList";
        public static final String getSkipUrlResult=root+"/common/getSkipUrlResult";
        public static final String getTodayTemperatureByCity=root+ "/common/getTodayTemperatureByCity";
        public static final String getExpressResult=root+ "/common/getExpressResult";
        public static final String getSplash=root+ "/common/getPicture";
        public static final String adddrivingLicense=root+ "/drivingLicense/adddrivingLicense";
        public static final String addShop=root+ "/common/addShop";
    }
    public static class DrivingLicense{
        public static final String deleteMyCar=root + "/drivingLicense/deleteMyCar";
        public static final String editByUser=root +  "/drivingLicense/editByUser";
        public static final String getApplicationByUserId=root + "/drivingLicense/getApplicationByUserId";
        public static final String updateCarToIsDefault=root +  "/drivingLicense/updateCarToIsDefault";
    }
    public static class FourShop{
        public static final String getFourShopListByApp=root + "/fourShop/getFourShopListByApp";
        public static final String addFourShopOrder=root +  "/fourShop/addFourShopOrder";
    }
    public static class RoadRescue{
        public static final String getRoadRescueShopListByApp=root+ "/roadRescue/getRoadRescueShopListByApp";
        public static final String getRoadRescueServeListForApp=root+ "/roadRescue/getRoadRescueServeListForApp";
    }
    public static class CarWashRepairShop{
        public static final String getWashRepairShopById=root+ "/carWashRepairShop/getWashRepairShopById";
        public static final String getCarWashRepairShopListByApp=root+ "/carWashRepairShop/getCarWashRepairShopListByApp";
        public static final String getWashRepairServeResultByShopId=root+ "/carWashRepairShop/getWashRepairServeListByShopId";
        public static final String addWashRepairOrder=root+ "/carWashRepairShop/addWashRepairOrder";
        public static final String addWashRepairOrderComment=root+ "/carWashRepairShop/addWashRepairOrderComment";
        public static final String cancelWashRepairOrder=root+ "/carWashRepairShop/cancelWashRepairOrder";
        public static final String getWashRepairCommentDetailsList=root+ "/carWashRepairShop/getWashRepairCommentDetailsList";
        public static final String getWashRepairOrderCommentByOrderId=root+ "/carWashRepairShop/getWashRepairOrderCommentByOrderId";
    }
    public static class CarRent{
        public static final String getRentCarPhoto=root+ "/rentcar/getRentCarPhoto";
        public static final String getRentCar=root+ "/rentcar/getRentCar";
        public static final String getRentCarCarList=root+ "/rentcar/getRentCarCarList";
        public static final String getRentCarShop=root+ "/rentCar/getRentCarShop";
        public static final String getRentCarCarListByShopId=root+ "/rentCar/getRentCarCarListByShopId";
        public static final String getRentCarCommentDetailsList=root+ "/rentCar/getRentCarCommentDetailsList";
        public static final String addRentCarOrder=root+ "/rentCar/addRentCarOrder";
    }
    public static class OldCar{

        public static final String addOldCarUserCar=root+ "/oldcar/addOldCarUserCar";
        public static final String getOldCarMerchantCarByApp=root+ "/oldcar/getOldCarMerchantCarByApp";
        public static final String getOldCarMerchantCarByUser=root+ "/oldcar/getOldCarMerchantCarByUser";
        public static final String getOldCarMerchantCarById=root+ "/oldcar/getOldCarMerchantCarById";
    }
    public static class CarOrder{
        public static final String addCarOrder=root+ "/carOrder/addCarOrder";
        public static final String cancelCarOrder=root+ "/carOrder/cancelCarOrder";
    }
    public static class WechatPay{
        public static final String appointmentPay=root+ "/wechatPay/appointmentPay";
        public static final String babyMerchantOrderPay=root+ "/wechatPay/babyMerchantOrderPay";
        public static final String carOrderPay=root+ "/wechatPay/carOrderPay";
        public static final String memberCardPay=root+ "/wechatPay/memberCardPay";
        public static final String washRepairPay=root+ "/wechatPay/washRepairPay";
    }
    public static class Pay{
        public static final String babyMerchantOrderPay=root+ "/alipay/babyMerchantOrderPay";
        public static final String memberCardPay=root+ "/alipay/memberCardPay";
        public static final String carOrderPay=root+ "/alipay/carOrderPay";
        public static final String washRepairShopPay=root+ "/alipay/washRepairShopPay";
        public static final String appointmentPay=root+ "/alipay/appointmentPay";
        public static final String substituteDrivingPay=root+ "/alipay/substituteDrivingPay";
    }
    public static class Chat{
        public static final String updateFriendsById=root+"/huanxin/updateFriendsById";
        public static final String searchFriendByKeyWord=root+"/huanxin/searchFriendByKeyWord";
        public static final String searchCrowdsByKeyWord=root+ "/huanxin/searchCrowdsByKeyWord";
        public static final String quitCrowdByCrowdId=root+"/huanxin/quitCrowdByCrowdId";
        public static final String joinCrowdByUserIds=root+"/huanxin/joinCrowdByUserIds";
        public static final String getMyFriendList=root+"/huanxin/getMyFriendList";
        public static final String getMyFriendListForLabel=root+"/huanxin/getMyFriendListForLabel";
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
        public static final String getNewCarListByApp=root+ "/newcar/getNewCarListByApp";
        public static final String getNewCarMerchantMessage=root+ "/newcar/getNewCarMerchantMessage";
        public static final String getDetailParams=root+ "/newcar/getCarCategoryDetailParamById";
        public static final String getNewCarMerchantMessageById=root+"/newcar/getNewCarMerchantMessageById";
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
    public static class SubstituteDriving{
        public static final String getSubstituteDrivingCommentListByShopId=root+"/substituteDriving/getSubstituteDrivingCommentListByShopId";
        public static final String getSubstituteDrivingShopResultById=root+"/substituteDriving/getSubstituteDrivingShopResultById";
        public static final String isCollectionByShopId=root+"/substituteDriving/isCollectionByShopId";
        public static final String addShopCollection=root+"/substituteDriving/addShopCollection";
        public static final String deleteShopCollection=root+"/substituteDriving/deleteShopCollection";
        public static final String getSubstituteDrivingOrderListByUser=root+"/substituteDriving/getSubstituteDrivingOrderListByUser";
        public static final String getSubstituteDrivingShopListByApp=root+"/substituteDriving/getSubstituteDrivingShopListByApp";
        public static final String getSubstituteDrivingCommentListByDriverId=root+"/substituteDriving/getSubstituteDrivingCommentListByDriverId";
        public static final String getSubstituteDrivingCommentByOrderId=root+"/substituteDriving/getSubstituteDrivingCommentByOrderId";
        public static final String getSubstituteDriverListForApp=root+"/substituteDriving/getSubstituteDriverListForApp";
        public static final String getSubstituteDriverList=root+"/substituteDriving/getSubstituteDriverList";
        public static final String getShopCollectionList=root+"/substituteDriving/getShopCollectionList";
        public static final String addSubstituteDrivingOrder=root+"/substituteDriving/addSubstituteDrivingOrder";
        public static final String addSubstituteDrivingComment=root+"/substituteDriving/addSubstituteDrivingComment";
        public static final String deleteSubstituteDrivingOrderByUser=root+"/substituteDriving/deleteSubstituteDrivingOrderByUser";
    }
    public static  class Appointment{

        public static final String addNewCarAppointment=root+ "/appointment/addNewCarAppointment";
        public static final String addOldCarAppointment=root+ "/appointment/addOldCarAppointment";
        public static final String addRoadRescueAppointment=root+ "/appointment/addRoadRescueAppointment";
        public static final String getMyAppointmentList=root+ "/appointment/getMyAppointmentList";
        public static final String addWashRepairAppointment=root + "/appointment/addWashRepairAppointment";
        public static final String deleteAppointmentById=root + "/appointment/deleteAppointmentById";
    }
    public static  class Evaluation{
        public static final String addDrivingSchoolCoachEva=root+ "/drivingschool/addDrivingSchoolCoachEva";
        public static final String addEvaluation=root+ "/evaluation/addEvaluation";
        public static final String addOrderEvaluation=root+ "/evaluation/addOrderEvaluation";
        public static final String getMerchantEvaluationByUser=root+ "/evaluation/getMerchantEvaluationByUser";
    }
    public static class LoginContacts {
        public static final String FILENAME = "fckg";
        public static String IS_AUTO_LOGIN = "isAutoLogin";
        public static String IS_REM_PASSWORD = "ispassword";
        public static String IS_REM_USERNAME = "isuserinfo";
        public static String PASSWORD = "password";
        public static String USERNAME = "username";
        public static String BOOK_TYPE_ID = "BOOK_TYPE_ID";

    }
    public static  class Order{
        public static final String getUserOrderList=root+ "/carWashRepairShop/getUserOrderList";
        public static final String getUserCarOrder=root+ "/carOrder/getUserCarOrder";
        public static final String getEvaluationByOrderId=root+ "/evaluation/getEvaluationByOrderId";
    }
    public static  class Agency{
        public static final String getAgencyList =root+ "/agency/getAgencyUserListByApp";
    }

    public static class CommunicationCircle{
        /**app分页获取我的好友圈列表*/
        public static final String getMyCommunicationCircleList =root+ "/communicationcircle/getMyCommunicationCircleList";
        /**app分页获取他人好友圈列表*/
        public static final String getCommunicationCircleListByUserId =root+ "/communicationcircle/getCommunicationCircleListByUserId";
        /**app分页获取所有人的好友圈列表*/
        public static final String getCommunicationCircleListForApp =root+ "/communicationcircle/getCommunicationCircleListForApp";
        /**点赞朋友圈*/
        public static final String clickCommunicationCircleById =root+ "/communicationcircle/clickCommunicationCircleById";
        /**取消点赞*/
        public static final String cancelClickById =root+ "/communicationcircle/cancelClickById";
        /**删除好友圈*/
        public static final String deleteCommunicationCircleById =root+ "/communicationcircle/deleteCommunicationCircleById";
        /**发布好友圈*/
        public static final String addCommunicationCircle =root+ "/communicationcircle/addCommunicationCircle";
        /**添加好友圈评论*/
        public static final String addCommunicationCircleCommentById =root+ "/communicationcircle/addCommunicationCircleCommentById";
        /**回复好友圈评论*/
        public static final String replyCommunicationCircleComment =root+ "/communicationcircle/replyCommunicationCircleComment";
        /**删除评论或回复*/
        public static final String deleteCommunicationCircleComment =root+ "/communicationcircle/deleteCommunicationCircleComment";

    }
    public static String data = "{\"code\":0,\"msg\":\"\\u83b7\\u53d6\\u5546\\u54c1\\u6210\\u529f\",\"data\":[{\"cat\":\"汽车服务\",\"goods\":[{\"id\":\"1001\",\"name\":\"标准洗车-轿车\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"100\",\"sold_num\":\"2\"},{\"id\":\"1003\",\"name\":\"标准洗车-五座轿车\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"3\"}]},{\"cat\":\"汽车美容\",\"goods\":[{\"id\":\"1004\",\"name\":\"全车抛光\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"3\"},{\"id\":\"1005\",\"name\":\"内饰清洗\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"3\"},{\"id\":\"1006\",\"name\":\"全车打蜡\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"0\"},{\"id\":\"1007\",\"name\":\"空调除臭\",\"detail\":\"\",\"pic\":\"\\/Uploads\\/Goods\\/2017-11-06\\/5a000f6569db4.png\",\"price\":\"1\",\"sold_num\":\"1\"}]}]}";

}
