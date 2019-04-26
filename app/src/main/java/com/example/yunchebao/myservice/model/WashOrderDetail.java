package com.example.yunchebao.myservice.model;

/**
 * 作者：凌涛 on 2019/4/25 11:07
 * 邮箱：771548229@qq..com
 */
public class WashOrderDetail {

    /**
     * address : string
     * agencyId : string
     * area : string
     * city : string
     * createTime : 2019-04-25T03:09:41.431Z
     * grade : 0
     * handleTime : 2019-04-25T03:09:41.431Z
     * id : string
     * isComment : 0
     * latitude : string
     * logo : string
     * longitude : string
     * name : string
     * orderNo : string
     * payMethod : 0
     * payTime : 2019-04-25T03:09:41.431Z
     * price : 0.0
     * province : string
     * serveCategory : string
     * serveTitle : string
     * shopId : string
     * shopName : string
     * shopTelephone : string
     * state : 0
     * telephone : string
     * userId : string
     * washRepairOrderComment : {"answer":"string","content":"string","createTime":"2019-04-25T03:09:41.431Z","headPortrait":"string","id":"string","imgs":"string","nickName":"string","score":0,"userId":"string"}
     */

    private String address;
    private String agencyId;
    private String area;
    private String city;
    private String createTime;
    private int grade;
    private String handleTime;
    private String id;
    private int isComment;
    private String latitude;
    private String logo;
    private String longitude;
    private String name;
    private String orderNo;
    private int payMethod;
    private String payTime;
    private double price;
    private String province;
    private String serveCategory;
    private String serveTitle;
    private String shopId;
    private String shopName;
    private String shopTelephone;
    private int state;
    private String telephone;
    private String userId;
    private WashRepairOrderCommentBean washRepairOrderComment;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsComment() {
        return isComment;
    }

    public void setIsComment(int isComment) {
        this.isComment = isComment;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getServeCategory() {
        return serveCategory;
    }

    public void setServeCategory(String serveCategory) {
        this.serveCategory = serveCategory;
    }

    public String getServeTitle() {
        return serveTitle;
    }

    public void setServeTitle(String serveTitle) {
        this.serveTitle = serveTitle;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopTelephone() {
        return shopTelephone;
    }

    public void setShopTelephone(String shopTelephone) {
        this.shopTelephone = shopTelephone;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public WashRepairOrderCommentBean getWashRepairOrderComment() {
        return washRepairOrderComment;
    }

    public void setWashRepairOrderComment(WashRepairOrderCommentBean washRepairOrderComment) {
        this.washRepairOrderComment = washRepairOrderComment;
    }

    public static class WashRepairOrderCommentBean {
        /**
         * answer : string
         * content : string
         * createTime : 2019-04-25T03:09:41.431Z
         * headPortrait : string
         * id : string
         * imgs : string
         * nickName : string
         * score : 0.0
         * userId : string
         */

        private String answer;
        private String content;
        private String createTime;
        private String headPortrait;
        private String id;
        private String imgs;
        private String nickName;
        private double score;
        private String userId;

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
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

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
