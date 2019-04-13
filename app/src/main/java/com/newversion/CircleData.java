package com.newversion;

import java.util.List;

public class CircleData {
   /**
    * address : string
    * clickList : [{"headPortrait":"string","name":"string","userId":"string"}]
    * commentList : [{"circleId":0,"content":"string","id":"string","name":"string","replyName":"string","replyUserId":"string","type":0,"userId":"string"}]
    * content : string
    * createTime : 2019-04-04T10:07:51.082Z
    * headPortrait : string
    * id : 0
    * imgs : string
    * isClick : string
    * kind : 0
    * latitude : string
    * longitude : string
    * name : string
    * type : 0
    * url : string
    * userId : string
    * video : string
    * vimg : string
    */

   private String address;
   private String content;
   private String createTime;
   private String headPortrait;
   private String id;
   private String imgs;
   private String isClick;
   private int kind;
   private String latitude;
   private String longitude;
   private String name;
   private int type;
   private String url;
   private String userId;
   private String video;
   private String vimg;
   private List<ClickListBean> clickList;
   private List<CommentListBean> commentList;

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
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

   public String getIsClick() {
      return isClick;
   }

   public void setIsClick(String isClick) {
      this.isClick = isClick;
   }

   public int getKind() {
      return kind;
   }

   public void setKind(int kind) {
      this.kind = kind;
   }

   public String getLatitude() {
      return latitude;
   }

   public void setLatitude(String latitude) {
      this.latitude = latitude;
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

   public int getType() {
      return type;
   }

   public void setType(int type) {
      this.type = type;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public String getVideo() {
      return video;
   }

   public void setVideo(String video) {
      this.video = video;
   }

   public String getVimg() {
      return vimg;
   }

   public void setVimg(String vimg) {
      this.vimg = vimg;
   }

   public List<ClickListBean> getClickList() {
      return clickList;
   }

   public void setClickList(List<ClickListBean> clickList) {
      this.clickList = clickList;
   }

   public List<CommentListBean> getCommentList() {
      return commentList;
   }

   public void setCommentList(List<CommentListBean> commentList) {
      this.commentList = commentList;
   }

   public static class ClickListBean {
      /**
       * headPortrait : string
       * name : string
       * userId : string
       */

      private String headPortrait;
      private String name;
      private String userId;

      public String getHeadPortrait() {
         return headPortrait;
      }

      public void setHeadPortrait(String headPortrait) {
         this.headPortrait = headPortrait;
      }

      public String getName() {
         return name;
      }

      public void setName(String name) {
         this.name = name;
      }

      public String getUserId() {
         return userId;
      }

      public void setUserId(String userId) {
         this.userId = userId;
      }
   }

   public static class CommentListBean {
      /**
       * circleId : 0
       * content : string
       * id : string
       * name : string
       * replyName : string
       * replyUserId : string
       * type : 0
       * userId : string
       */

      private String circleId;
      private String content;
      private String id;
      private String name;
      private String replyName;
      private String replyUserId;
      private int type;
      private String userId;

      public String  getCircleId() {
         return circleId;
      }

      public void setCircleId(String circleId) {
         this.circleId = circleId;
      }

      public String getContent() {
         return content;
      }

      public void setContent(String content) {
         this.content = content;
      }

      public String getId() {
         return id;
      }

      public void setId(String id) {
         this.id = id;
      }

      public String getName() {
         return name;
      }

      public void setName(String name) {
         this.name = name;
      }

      public String getReplyName() {
         return replyName;
      }

      public void setReplyName(String replyName) {
         this.replyName = replyName;
      }

      public String getReplyUserId() {
         return replyUserId;
      }

      public void setReplyUserId(String replyUserId) {
         this.replyUserId = replyUserId;
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
   }
}
