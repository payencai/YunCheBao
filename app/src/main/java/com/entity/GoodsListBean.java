package com.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dalong on 2016/12/27.
 */

public class GoodsListBean implements Serializable{


//    private List<DataEntity> data;
    private List<DataEntity.GoodscatrgoryEntity> data;

    public List<DataEntity.GoodscatrgoryEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity.GoodscatrgoryEntity> data) {
        this.data = data;
    }

    //    public List<DataEntity> getData() {
//        return data;
//    }
//
//    public void setData(List<DataEntity> data) {
//        this.data = data;
//    }

    public static class DataEntity {
        /**
         * goodscatrgory : {"goodsitem":[{"name":"苹果","price":10,"introduce":"苹果好吃啊，很甜！","moreStandard":false},{"name":"香蕉","price":10,"introduce":"香蕉好吃啊，又大很甜！","moreStandard":false},{"name":"橘子","price":10,"introduce":"橘子非常好吃啊，很甜！","moreStandard":false},{"name":"榴莲","price":10,"introduce":"我对象喜欢吃榴莲！","moreStandard":false},{"name":"桃子","price":10,"introduce":"大龙家的桃子就是好吃！","moreStandard":false},{"name":"橘子","price":10,"introduce":"橘子非常好吃啊，很甜！","moreStandard":false},{"name":"梨","price":10,"introduce":"梨非常好吃啊，很甜！","moreStandard":false}],"name":"水果"}
         */
        private GoodscatrgoryEntity goodscatrgory;

        public GoodscatrgoryEntity getGoodscatrgory() {
            return goodscatrgory;
        }

        public void setGoodscatrgory(GoodscatrgoryEntity goodscatrgory) {
            this.goodscatrgory = goodscatrgory;
        }

        public static class GoodscatrgoryEntity {
            /**
             * goodsitem : [{"name":"苹果","price":10,"introduce":"苹果好吃啊，很甜！","moreStandard":false},{"name":"香蕉","price":10,"introduce":"香蕉好吃啊，又大很甜！","moreStandard":false},{"name":"橘子","price":10,"introduce":"橘子非常好吃啊，很甜！","moreStandard":false},{"name":"榴莲","price":10,"introduce":"我对象喜欢吃榴莲！","moreStandard":false},{"name":"桃子","price":10,"introduce":"大龙家的桃子就是好吃！","moreStandard":false},{"name":"橘子","price":10,"introduce":"橘子非常好吃啊，很甜！","moreStandard":false},{"name":"梨","price":10,"introduce":"梨非常好吃啊，很甜！","moreStandard":false}]
             * name : 水果
             */

            private String name;
            private String cat;
            private int bugNum;
            private int position;
            private String introduce;
            private String goodsImgUrl;
            private boolean moreStandard;
            private int buyNum;
            private String id;      // "id":"1001",
            private String pic;      //     "pic":"/Uploads/Goods/2017-11-06/5a000f6569db4.png",
            private String price;      //     "price":"100",//单位为分
            private String sold_num;      //      "sold_num":"0"//售出数量

            private double totalPrice;


            private List<GoodsitemEntity> goodsitem;
            private List<GoodscatrgoryEntity> goods;

            public List<GoodscatrgoryEntity> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodscatrgoryEntity> goods) {
                this.goods = goods;
            }

            public double getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(double totalPrice) {
                this.totalPrice = totalPrice;
            }

            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
            }

            public String getIntroduce() {
                return introduce;
            }

            public void setIntroduce(String introduce) {
                this.introduce = introduce;
            }

            public String getGoodsImgUrl() {
                return goodsImgUrl;
            }

            public void setGoodsImgUrl(String goodsImgUrl) {
                this.goodsImgUrl = goodsImgUrl;
            }

            public boolean isMoreStandard() {
                return moreStandard;
            }

            public void setMoreStandard(boolean moreStandard) {
                this.moreStandard = moreStandard;
            }

            public int getBuyNum() {
                return buyNum;
            }

            public void setBuyNum(int buyNum) {
                this.buyNum = buyNum;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getSold_num() {
                return sold_num;
            }

            public void setSold_num(String sold_num) {
                this.sold_num = sold_num;
            }

            public String getCat() {
                return cat;
            }

            public void setCat(String cat) {
                this.cat = cat;
            }

            public String getName() {
                return name;
            }

            public int getBugNum() {
                return bugNum;
            }

            public void setBugNum(int bugNum) {
                this.bugNum = bugNum;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<GoodsitemEntity> getGoodsitem() {
                return goodsitem;
            }

            public void setGoodsitem(List<GoodsitemEntity> goodsitem) {
                this.goodsitem = goodsitem;
            }

            public static class GoodsitemEntity {
                /**
                 * id：1
                 * name : 苹果
                 * price : 10
                 * introduce : 苹果好吃啊，很甜！
                 * goodsImgUrl : ""
                 * moreStandard : false
                 */
                private String introduce;
                private String goodsImgUrl;
                private boolean moreStandard;
                private int buyNum;
                private String id;      // "id":"1001",
                private String name;      //    "name":"测试",
                private String pic;      //     "pic":"/Uploads/Goods/2017-11-06/5a000f6569db4.png",
                private String price;      //     "price":"100",//单位为分
                private String sold_num;      //      "sold_num":"0"//售出数量

                public void setId(String id) {
                    this.id = id;
                }


                public String getId() {
                    return id;
                }

                public String getPrice() {
                    return price;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getSold_num() {
                    return sold_num;
                }

                public void setSold_num(String sold_num) {
                    this.sold_num = sold_num;
                }

                public int getBuyNum() {
                    return buyNum;
                }

                public void setBuyNum(int buyNum) {
                    this.buyNum = buyNum;
                }


                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }


                public String getIntroduce() {
                    return introduce;
                }

                public void setIntroduce(String introduce) {
                    this.introduce = introduce;
                }

                public String getGoodsImgUrl() {
                    return goodsImgUrl;
                }

                public void setGoodsImgUrl(String goodsImgUrl) {
                    this.goodsImgUrl = goodsImgUrl;
                }

                public boolean isMoreStandard() {
                    return moreStandard;
                }

                public void setMoreStandard(boolean moreStandard) {
                    this.moreStandard = moreStandard;
                }
            }
        }
    }
}
