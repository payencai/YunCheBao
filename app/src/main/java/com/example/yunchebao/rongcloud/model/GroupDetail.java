package com.example.yunchebao.rongcloud.model;

import java.util.List;

/**
 * 作者：凌涛 on 2019/5/23 17:43
 * 邮箱：771548229@qq..com
 */
public class GroupDetail {

    /**
     * id : 1123257410
     * crowdName : 我的群1
     * crowdUserId : 90039d05-4b5e-4381-92a0-8346c6233afc
     * hxCrowdId : kqGfRUT92H4efWWgnmum
     * image : https://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019052010144852
     * description :
     * qrCodeCard :
     * createTime : 2019-05-10 11:53:38
     * indexUser : {"id":196,"userId":"117a39b4-bb6c-4b01-9306-197767461027","headPortrait":"https://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019052015410720","hxAccount":"RY-6089","nickName":"荆轲","isNotice":0}
     * indexList : [{"id":129,"userId":"90039d05-4b5e-4381-92a0-8346c6233afc","headPortrait":"https://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019051115103651","hxAccount":"RY-8144","nickName":"看看","isNotice":0},{"id":133,"userId":"18e6004f-c680-4fdf-a6ad-b02e0b4ea0f5","headPortrait":"http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019030410301927","hxAccount":"RY-3950","nickName":"核武器","isNotice":0},{"id":155,"userId":"7087b41a-99f5-4da2-ac59-04ccab8835c8","headPortrait":"https://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019051310003847","hxAccount":"RY-7788","nickName":"你是个好人","isNotice":0},{"id":156,"userId":"46a466a7-9535-470e-a59c-8b77f1073bd6","headPortrait":"https://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019051511001360","hxAccount":"RY-4992","nickName":"聪明人","isNotice":0},{"id":196,"userId":"117a39b4-bb6c-4b01-9306-197767461027","headPortrait":"https://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019052015410720","hxAccount":"RY-6089","nickName":"荆轲","isNotice":0}]
     * crowdUserVoId : 90039d05-4b5e-4381-92a0-8346c6233afc
     */

    private int id;
    private String crowdName;
    private String crowdUserId;
    private String hxCrowdId;
    private String image;
    private String description;
    private String qrCodeCard;
    private String createTime;
    private IndexUserBean indexUser;
    private String crowdUserVoId;
    private List<IndexListBean> indexList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCrowdName() {
        return crowdName;
    }

    public void setCrowdName(String crowdName) {
        this.crowdName = crowdName;
    }

    public String getCrowdUserId() {
        return crowdUserId;
    }

    public void setCrowdUserId(String crowdUserId) {
        this.crowdUserId = crowdUserId;
    }

    public String getHxCrowdId() {
        return hxCrowdId;
    }

    public void setHxCrowdId(String hxCrowdId) {
        this.hxCrowdId = hxCrowdId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQrCodeCard() {
        return qrCodeCard;
    }

    public void setQrCodeCard(String qrCodeCard) {
        this.qrCodeCard = qrCodeCard;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public IndexUserBean getIndexUser() {
        return indexUser;
    }

    public void setIndexUser(IndexUserBean indexUser) {
        this.indexUser = indexUser;
    }

    public String getCrowdUserVoId() {
        return crowdUserVoId;
    }

    public void setCrowdUserVoId(String crowdUserVoId) {
        this.crowdUserVoId = crowdUserVoId;
    }

    public List<IndexListBean> getIndexList() {
        return indexList;
    }

    public void setIndexList(List<IndexListBean> indexList) {
        this.indexList = indexList;
    }

    public static class IndexUserBean {
        /**
         * id : 196
         * userId : 117a39b4-bb6c-4b01-9306-197767461027
         * headPortrait : https://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019052015410720
         * hxAccount : RY-6089
         * nickName : 荆轲
         * isNotice : 0
         */

        private int id;
        private String userId;
        private String headPortrait;
        private String hxAccount;
        private String nickName;
        private int isNotice;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getHeadPortrait() {
            return headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public String getHxAccount() {
            return hxAccount;
        }

        public void setHxAccount(String hxAccount) {
            this.hxAccount = hxAccount;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getIsNotice() {
            return isNotice;
        }

        public void setIsNotice(int isNotice) {
            this.isNotice = isNotice;
        }
    }

    public static class IndexListBean {
        /**
         * id : 129
         * userId : 90039d05-4b5e-4381-92a0-8346c6233afc
         * headPortrait : https://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019051115103651
         * hxAccount : RY-8144
         * nickName : 看看
         * isNotice : 0
         */

        private int id;
        private String userId;
        private String headPortrait;
        private String hxAccount;
        private String nickName;
        private int isNotice;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getHeadPortrait() {
            return headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public String getHxAccount() {
            return hxAccount;
        }

        public void setHxAccount(String hxAccount) {
            this.hxAccount = hxAccount;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getIsNotice() {
            return isNotice;
        }

        public void setIsNotice(int isNotice) {
            this.isNotice = isNotice;
        }
    }
}
