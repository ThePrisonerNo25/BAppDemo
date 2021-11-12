package com.example.yangchaoming.bappdemo.splash.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class SplashAdvertising {
    @Id
    String id; //广告id ,
    String imgPopupUrl; //弹窗广告图路径（相对路径） ,
    Integer uploadFileTypeFile; //0 图片  1 视频
    String startTime;//展示开始时间（yyyy-MM-dd HH:mm:ss） ,
    String endTime;//展示结束时间（yyyy-MM-dd HH:mm:ss） ,
    String showStartTime;// 展示开始时间点（HH:mm） ,
    String showEndTime;// 展示结束时间点（HH:mm） ,
    Integer maxEveryday; //每人每天最多展示次数（弹框广告） ,
    Integer maxEveryone; //每个用户最多展示次数（弹框广告） ,
    Integer gotoType; //跳转类型（0 不跳转 1 内部跳转 2 跳H5 3外部小程序 4微盟H5） ,
    String  link; //  广告链接路径 ,

    String lastShowTime; //最近一次展示时间 （yyyy-MM-dd HH:mm:ss）
    int oneDayShowedTimes;//一天已经展示了多少次
    int totalShowedTimes;//总共展示了多少次
    String sourcePath;//文件路径
    long sourceDownLoadId;//源文件下载id

    @Generated(hash = 1038042777)
    public SplashAdvertising(String id, String imgPopupUrl, Integer uploadFileTypeFile,
            String startTime, String endTime, String showStartTime, String showEndTime,
            Integer maxEveryday, Integer maxEveryone, Integer gotoType, String link,
            String lastShowTime, int oneDayShowedTimes, int totalShowedTimes,
            String sourcePath, long sourceDownLoadId) {
        this.id = id;
        this.imgPopupUrl = imgPopupUrl;
        this.uploadFileTypeFile = uploadFileTypeFile;
        this.startTime = startTime;
        this.endTime = endTime;
        this.showStartTime = showStartTime;
        this.showEndTime = showEndTime;
        this.maxEveryday = maxEveryday;
        this.maxEveryone = maxEveryone;
        this.gotoType = gotoType;
        this.link = link;
        this.lastShowTime = lastShowTime;
        this.oneDayShowedTimes = oneDayShowedTimes;
        this.totalShowedTimes = totalShowedTimes;
        this.sourcePath = sourcePath;
        this.sourceDownLoadId = sourceDownLoadId;
    }

    @Generated(hash = 1984342522)
    public SplashAdvertising() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgPopupUrl() {
        return imgPopupUrl;
    }

    public void setImgPopupUrl(String imgPopupUrl) {
        this.imgPopupUrl = imgPopupUrl;
    }

    public Integer getUploadFileTypeFile() {
        return uploadFileTypeFile;
    }

    public void setUploadFileTypeFile(Integer uploadFileTypeFile) {
        this.uploadFileTypeFile = uploadFileTypeFile;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getShowStartTime() {
        return showStartTime;
    }

    public void setShowStartTime(String showStartTime) {
        this.showStartTime = showStartTime;
    }

    public String getShowEndTime() {
        return showEndTime;
    }

    public void setShowEndTime(String showEndTime) {
        this.showEndTime = showEndTime;
    }

    public Integer getMaxEveryday() {
        return maxEveryday;
    }

    public void setMaxEveryday(Integer maxEveryday) {
        this.maxEveryday = maxEveryday;
    }

    public Integer getMaxEveryone() {
        return maxEveryone;
    }

    public void setMaxEveryone(Integer maxEveryone) {
        this.maxEveryone = maxEveryone;
    }

    public Integer getGotoType() {
        return gotoType;
    }

    public void setGotoType(Integer gotoType) {
        this.gotoType = gotoType;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLastShowTime() {
        return lastShowTime;
    }

    public void setLastShowTime(String lastShowTime) {
        this.lastShowTime = lastShowTime;
    }

    public int getOneDayShowedTimes() {
        return oneDayShowedTimes;
    }

    public void setOneDayShowedTimes(int oneDayShowedTimes) {
        this.oneDayShowedTimes = oneDayShowedTimes;
    }

    public int getTotalShowedTimes() {
        return totalShowedTimes;
    }

    public void setTotalShowedTimes(int totalShowedTimes) {
        this.totalShowedTimes = totalShowedTimes;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public long getSourceDownLoadId() {
        return sourceDownLoadId;
    }

    public void setSourceDownLoadId(long sourceDownLoadId) {
        this.sourceDownLoadId = sourceDownLoadId;
    }

    @Override
    public String toString() {
        return "SplashAdvertising{" +
                "id='" + id + '\'' +
                ", imgPopupUrl='" + imgPopupUrl + '\'' +
                ", uploadFileTypeFile=" + uploadFileTypeFile +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", showStartTime='" + showStartTime + '\'' +
                ", showEndTime='" + showEndTime + '\'' +
                ", maxEveryday=" + maxEveryday +
                ", maxEveryone=" + maxEveryone +
                ", gotoType=" + gotoType +
                ", link='" + link + '\'' +
                ", lastShowTime='" + lastShowTime + '\'' +
                ", oneDayShowedTimes=" + oneDayShowedTimes +
                ", totalShowedTimes=" + totalShowedTimes +
                ", sourcePath='" + sourcePath + '\'' +
                ", sourceDownLoadId=" + sourceDownLoadId +
                '}';
    }
}

//appId (string, optional): 跳转小程序的appId ,
//businessData (string, optional): 业务数据（json格式的字符串） ,
//businessDataText (string, optional): 业务数据文本 ,
//endTime (string, optional): 展示结束时间（yyyy-MM-dd HH:mm:ss） ,
//fireType (integer, optional): 触发条件 0:点击进入触发 1:约课成功后触发 2:购买课程后触发 3:添加会员后触发 4:保存备课后触发 ,
//gotoType (integer, optional): 跳转类型（0 不跳转 1 内部跳转 2 跳H5 3外部小程序 4微盟H5） ,
//id (string, optional): 广告id ,
//imgAndroidUrl (string, optional): android端广告图路径（相对路径） ,
//imgIosUrl (string, optional): ios端广告图路径（相对路径） ,
//imgPopupUrl (string, optional): 弹窗广告图路径（相对路径） ,
//link (string, optional): 广告链接路径 ,
//maxEveryday (integer, optional): 每人每天最多展示次数（弹框广告） ,
//maxEveryone (integer, optional): 每个用户最多展示次数（弹框广告） ,
//originId (string, optional): 跳转目标小程序的原始id ,
//position1 (integer, optional): 展示页面(对应接口传参position1) ,
//position2 (integer, optional): banner类型的对应页面展示位置（0 顶部 1 中部 ） ,
//section (string, optional): 业务板块（供内部跳转使用） ,
//sectionText (string, optional): 业务板块文本 ,
//showEndTime (string, optional): 展示结束时间点（HH:mm） ,
//showStartTime (string, optional): 展示开始时间点（HH:mm） ,
//startTime (string, optional): 展示开始时间（yyyy-MM-dd HH:mm:ss） ,
//status (integer, optional): 广告状态（0 下线 1 上线 2 待编辑，默认为 2） ,
//title (string, optional): 标题 ,
//type (integer, optional): 广告类型（0 banner 1 弹窗 2开屏）