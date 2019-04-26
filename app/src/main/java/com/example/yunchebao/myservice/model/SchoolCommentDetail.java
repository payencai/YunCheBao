package com.example.yunchebao.myservice.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：凌涛 on 2019/4/26 12:01
 * 邮箱：771548229@qq..com
 */
public class SchoolCommentDetail {

    /**
     * coachEvaluation : {"coachHead":"string","coachId":"string","coachName":"string","content":"string","createTime":"2019-04-26T02:54:08.375Z","headPortrait":"string","id":"string","isAnonymous":0,"isDel":0,"merchantContent":"string","merchantId":"string","name":"string","photo":"string","photos":["string"],"replyTime":"2019-04-26T02:54:08.375Z","score":0,"state":0,"userId":"string"}
     * merchantEvaluation : {"content":"string","createTime":"2019-04-26T02:54:08.375Z","headPortrait":"string","id":"string","isAnonymous":0,"isDel":0,"merchantContent":"string","merchantId":"string","name":"string","orderId":"string","photo":"string","photos":["string"],"replyTime":"2019-04-26T02:54:08.375Z","score":0,"state":0,"type":0,"userId":"string"}
     */

    private CoachEvaluationBean coachEvaluation;
    private MerchantEvaluationBean merchantEvaluation;

    public CoachEvaluationBean getCoachEvaluation() {
        return coachEvaluation;
    }

    public void setCoachEvaluation(CoachEvaluationBean coachEvaluation) {
        this.coachEvaluation = coachEvaluation;
    }

    public MerchantEvaluationBean getMerchantEvaluation() {
        return merchantEvaluation;
    }

    public void setMerchantEvaluation(MerchantEvaluationBean merchantEvaluation) {
        this.merchantEvaluation = merchantEvaluation;
    }

    public static class CoachEvaluationBean {
        /**
         * coachHead : string
         * coachId : string
         * coachName : string
         * content : string
         * createTime : 2019-04-26T02:54:08.375Z
         * headPortrait : string
         * id : string
         * isAnonymous : 0
         * isDel : 0
         * merchantContent : string
         * merchantId : string
         * name : string
         * photo : string
         * photos : ["string"]
         * replyTime : 2019-04-26T02:54:08.375Z
         * score : 0
         * state : 0
         * userId : string
         */

        private String coachHead;
        private String coachId;
        private String coachName;
        private String content;
        private String createTime;
        private String headPortrait;
        private String id;
        private int isAnonymous;
        private int isDel;
        private String merchantContent;
        private String merchantId;
        private String name;
        private String photo;
        private String replyTime;
        private float score;
        private int state;
        private String userId;
        private List<String> photos=new ArrayList<>();

        public String getCoachHead() {
            return coachHead;
        }

        public void setCoachHead(String coachHead) {
            this.coachHead = coachHead;
        }

        public String getCoachId() {
            return coachId;
        }

        public void setCoachId(String coachId) {
            this.coachId = coachId;
        }

        public String getCoachName() {
            return coachName;
        }

        public void setCoachName(String coachName) {
            this.coachName = coachName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getHeadPortrait() {
            return headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIsAnonymous() {
            return isAnonymous;
        }

        public void setIsAnonymous(int isAnonymous) {
            this.isAnonymous = isAnonymous;
        }

        public int getIsDel() {
            return isDel;
        }

        public void setIsDel(int isDel) {
            this.isDel = isDel;
        }

        public String getMerchantContent() {
            return merchantContent;
        }

        public void setMerchantContent(String merchantContent) {
            this.merchantContent = merchantContent;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getReplyTime() {
            return replyTime;
        }

        public void setReplyTime(String replyTime) {
            this.replyTime = replyTime;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public List<String> getPhotos() {
            return photos;
        }

        public void setPhotos(List<String> photos) {
            this.photos = photos;
        }
    }

    public static class MerchantEvaluationBean {
        /**
         * content : string
         * createTime : 2019-04-26T02:54:08.375Z
         * headPortrait : string
         * id : string
         * isAnonymous : 0
         * isDel : 0
         * merchantContent : string
         * merchantId : string
         * name : string
         * orderId : string
         * photo : string
         * photos : ["string"]
         * replyTime : 2019-04-26T02:54:08.375Z
         * score : 0
         * state : 0
         * type : 0
         * userId : string
         */

        private String content;
        private String createTime;
        private String headPortrait;
        private String id;
        private int isAnonymous;
        private int isDel;
        private String merchantContent;
        private String merchantId;
        private String name;
        private String orderId;
        private String photo;
        private String replyTime;
        private float score;
        private int state;
        private int type;
        private String userId;
        private List<String> photos=new ArrayList<>();

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getHeadPortrait() {
            return headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIsAnonymous() {
            return isAnonymous;
        }

        public void setIsAnonymous(int isAnonymous) {
            this.isAnonymous = isAnonymous;
        }

        public int getIsDel() {
            return isDel;
        }

        public void setIsDel(int isDel) {
            this.isDel = isDel;
        }

        public String getMerchantContent() {
            return merchantContent;
        }

        public void setMerchantContent(String merchantContent) {
            this.merchantContent = merchantContent;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getReplyTime() {
            return replyTime;
        }

        public void setReplyTime(String replyTime) {
            this.replyTime = replyTime;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public List<String> getPhotos() {
            return photos;
        }

        public void setPhotos(List<String> photos) {
            this.photos = photos;
        }
    }
}
