package com.example.paodao;

/**
 * Created by jingdongqi on 7/1/15.
 */
public class SlideModel {
    private long timestamp;
    private int amount;
    private int fromCityCode;
    private  int toCityCode;
    private String fromCity;
    private String toCity;
    private int fromGender;
    private int fromLevel;
    private int toGender;
    private int toLevel;
    private String fromUid;
    private String toUid;
    private String fromNickname;
    private String toNickname;
    private String fromAvatar;
    private String toAvatar;


    public AEAR_SHOW_TYPE getShowType() {
        return showType;
    }

    public void setShowType(AEAR_SHOW_TYPE showType) {
        this.showType = showType;
    }

    private AEAR_SHOW_TYPE showType;


    public enum  AEAR_SHOW_TYPE{ LOCAL_SHOW,
        GLOBAL_SHOW    };





    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getAmount() {
        return flower++;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getFromCityCode() {
        return fromCityCode;
    }

    public void setFromCityCode(int fromCityCode) {
        this.fromCityCode = fromCityCode;
    }

    public int getToCityCode() {
        return toCityCode;
    }

    public void setToCityCode(int toCityCode) {
        this.toCityCode = toCityCode;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public int getFromGender() {
        return fromGender;
    }

    public void setFromGender(int fromGender) {
        this.fromGender = fromGender;
    }

    public int getFromLevel() {
        return fromLevel;
    }

    public void setFromLevel(int fromLevel) {
        this.fromLevel = fromLevel;
    }

    public int getToGender() {
        return toGender;
    }

    public void setToGender(int toGender) {
        this.toGender = toGender;
    }

    public int getToLevel() {
        return toLevel;
    }

    public void setToLevel(int toLevel) {
        this.toLevel = toLevel;
    }

    public String getFromUid() {
        return fromUid;
    }

    public void setFromUid(String fromUid) {
        this.fromUid = fromUid;
    }

    public String getToUid() {
        return toUid;
    }

    public void setToUid(String toUid) {
        this.toUid = toUid;
    }

    public String getFromNickname() {
        return fromNickname;
    }

    public void setFromNickname(String fromNickname) {
        this.fromNickname = fromNickname;
    }

    public String getToNickname() {
        return toNickname;
    }

    public void setToNickname(String toNickname) {
        this.toNickname = toNickname;
    }

    public String getFromAvatar() {
        return fromAvatar;
    }

    public void setFromAvatar(String fromAvatar) {
        this.fromAvatar = fromAvatar;
    }

    public String getToAvatar() {
        return toAvatar;
    }

    public void setToAvatar(String toAvatar) {
        this.toAvatar = toAvatar;
    }



    public String getShowTime(){

        return "6秒前";

    }


    public String showSlideStr() {
        String retVal = "";
        if (showType == AEAR_SHOW_TYPE.GLOBAL_SHOW) {
            retVal = String.format("%s%s送给%s%d朵鲜花", getShowTime(), this.fromNickname, this.toNickname, this.amount);
        } else {
            retVal = String.format("%s%s%s送给%s%s%d朵鲜花", getShowTime(), this.fromNickname, this.fromCity, this.toNickname, this.toCity, this.amount);

        }
        return retVal;
    }



    static int flower = 1;
   public static SlideModel initWithSomeSample(AEAR_SHOW_TYPE type){
       SlideModel slideModel = new SlideModel();
       slideModel.fromUid="1004345";
       slideModel.toUid="5749945";
       slideModel.fromNickname="nickname_41";
       slideModel.toNickname="nickname_75";
       slideModel.fromCity="(苏州)";
       slideModel.toCity="(北京)";

       slideModel.amount=flower++;
       slideModel.showType = type;


       return slideModel;



   }


}
