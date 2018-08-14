package com.gxyj.test.entity;

import java.io.Serializable;

public class B2cImage implements Serializable{
	private static final long serialVersionUID = 5695962542036057074L;

	private String imgId;

    private String imgCategoryId;

    private String imgName;

    private String imgUrl;

    private String imgDesc;

    private String imgOriginalName;

    private String imgAmount;

    private String memberId;

    private String isReferenced;

    private String imgDir;

    private String enumImgName;

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId == null ? null : imgId.trim();
    }

    public String getImgCategoryId() {
        return imgCategoryId;
    }

    public void setImgCategoryId(String imgCategoryId) {
        this.imgCategoryId = imgCategoryId == null ? null : imgCategoryId.trim();
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName == null ? null : imgName.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc == null ? null : imgDesc.trim();
    }

    public String getImgOriginalName() {
        return imgOriginalName;
    }

    public void setImgOriginalName(String imgOriginalName) {
        this.imgOriginalName = imgOriginalName == null ? null : imgOriginalName.trim();
    }

    public String getImgAmount() {
        return imgAmount;
    }

    public void setImgAmount(String imgAmount) {
        this.imgAmount = imgAmount == null ? null : imgAmount.trim();
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public String getIsReferenced() {
        return isReferenced;
    }

    public void setIsReferenced(String isReferenced) {
        this.isReferenced = isReferenced == null ? null : isReferenced.trim();
    }

    public String getImgDir() {
        return imgDir;
    }

    public void setImgDir(String imgDir) {
        this.imgDir = imgDir == null ? null : imgDir.trim();
    }

    public String getEnumImgName() {
        return enumImgName;
    }

    public void setEnumImgName(String enumImgName) {
        this.enumImgName = enumImgName == null ? null : enumImgName.trim();
    }
}