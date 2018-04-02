package com.linkedin.android.learning_android_accessibility.models;

import java.util.Date;

public class CardItem {

    private int avatarId;
    private String name;
    private Date date;
    private String country;
    private String shareText;
    private int imageId;
    private boolean isLiked;
    private boolean isFavorited;

    public CardItem() {
    }

    public CardItem(int avatarId, String name, Date date, String country, String shareText, int imageId, boolean
            isLiked, boolean isFavorited) {
        this.avatarId = avatarId;
        this.name = name;
        this.date = date;
        this.country = country;
        this.shareText = shareText;
        this.imageId = imageId;
        this.isLiked = isLiked;
        this.isFavorited = isFavorited;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getShareText() {
        return shareText;
    }

    public void setShareText(String shareText) {
        this.shareText = shareText;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public void setFavorited(boolean favorited) {
        isFavorited = favorited;
    }
}
