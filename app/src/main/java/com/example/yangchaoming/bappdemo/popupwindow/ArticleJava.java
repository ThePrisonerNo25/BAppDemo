package com.example.yangchaoming.bappdemo.popupwindow;

public class ArticleJava {
    public String title;
    public String body;
    public Integer viewCount;
    public Boolean payWall;
    public String titleImage;

    public ArticleJava() {
    }

    public ArticleJava(String title, String body, Integer viewCount, Boolean payWall, String titleImage) {
        this.title = title;
        this.body = body;
        this.viewCount = viewCount;
        this.payWall = payWall;
        this.titleImage = titleImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Boolean getPayWall() {
        return payWall;
    }

    public void setPayWall(Boolean payWall) {
        this.payWall = payWall;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    @Override
    public String toString() {
        return "ArticleJava{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", viewCount=" + viewCount +
                ", payWall=" + payWall +
                ", titleImage='" + titleImage + '\'' +
                '}';
    }

    //    val title: String?,
//    val body: String?,
//    val viewCount: Int,
//    val payWall: Boolean,
//    val titleImage: String?)
}
