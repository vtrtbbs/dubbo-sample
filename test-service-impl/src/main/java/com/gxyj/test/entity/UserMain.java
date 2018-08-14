package com.gxyj.test.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;




public class UserMain  implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "uuid2")
    private String id;

    private String userCode;

    private String nickName;

    private String userName;

    private String userPhone;

    private String password;

    private String headIcon;

    private String bigIcon;

    private Integer channelFlag;

    private Integer userType;
    
    private String signId;

    private Integer sex;

    private Long userLevel;

    private String userDesc;

    private Integer liveFlag;

    private Integer loginFlag;

    private Integer lockFlag;

    private String appId;

    private Date createTime;

    private Date modifyTime;

    private Integer version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon == null ? null : headIcon.trim();
    }

    public String getBigIcon() {
        return bigIcon;
    }

    public void setBigIcon(String bigIcon) {
        this.bigIcon = bigIcon == null ? null : bigIcon.trim();
    }

    public Integer getChannelFlag() {
        return channelFlag;
    }

    public void setChannelFlag(Integer channelFlag) {
        this.channelFlag = channelFlag;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getSignId() {
		return signId;
	}

	public void setSignId(String signId) {
		this.signId = signId;
	}

	public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Long getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Long userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc == null ? null : userDesc.trim();
    }

    public Integer getLiveFlag() {
        return liveFlag;
    }

    public void setLiveFlag(Integer liveFlag) {
        this.liveFlag = liveFlag;
    }

    public Integer getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(Integer loginFlag) {
        this.loginFlag = loginFlag;
    }

    public Integer getLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(Integer lockFlag) {
        this.lockFlag = lockFlag;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
    /**
     * 详细信息
     */
    public Map<String,Object> toDetailMap(UserMain userMain){
    	if( userMain == null ){
    		return null;
    	}
    	Map<String,Object> retMap = new LinkedHashMap<String,Object>(18);
    	retMap.put("userId", userMain.getId());
		retMap.put("userCode", userMain.getUserCode());
		retMap.put("nickName", userMain.getNickName()==null?"":userMain.getNickName());
		retMap.put("userName", userMain.getUserName()==null?"":userMain.getUserName());
		retMap.put("userPhone", userMain.getUserPhone()==null?"":userMain.getUserPhone());
		retMap.put("headIcon", userMain.getHeadIcon()==null?"":userMain.getHeadIcon());
		retMap.put("bigIcon", userMain.getBigIcon()==null?"":userMain.getBigIcon());
		retMap.put("channelFlag", userMain.getChannelFlag()==null?0:userMain.getChannelFlag());
		retMap.put("userType", userMain.getUserType()==null?0:userMain.getUserType());
		retMap.put("signId", userMain.getSignId()==null?0:userMain.getSignId());
		retMap.put("sex", userMain.getSex()==null?0:userMain.getSex());
		retMap.put("userLevel", userMain.getUserLevel()==null?0:userMain.getUserLevel());
		retMap.put("userDesc", userMain.getUserDesc());
		retMap.put("liveFlag", userMain.getLiveFlag()==null?2:userMain.getLiveFlag());
		retMap.put("loginFlag", userMain.getLoginFlag()==null?1:userMain.getLoginFlag());
		retMap.put("lockFlag", userMain.getLockFlag()==null?1:userMain.getLockFlag());
		return retMap;
    }
    
    /**
     * 可看信息
     */
    public Map<String,Object> toLookMap(UserMain userMain){
    	if( userMain==null ){
    		return null;
    	}
    	Map<String,Object> retMap = new LinkedHashMap<String,Object>(10);
    	retMap.put("userId", userMain.getId());
		retMap.put("userCode", userMain.getUserCode());
		retMap.put("nickName", userMain.getNickName()==null?"":userMain.getNickName());
//		retMap.put("userName", userMain.getUserName()==null?"":userMain.getUserName());
//		retMap.put("userPhone", userMain.getUserPhone()==null?"":userMain.getUserPhone());
		retMap.put("headIcon", userMain.getHeadIcon()==null?"":userMain.getHeadIcon());
		retMap.put("bigIcon", userMain.getBigIcon()==null?"":userMain.getBigIcon());
		retMap.put("channelFlag", userMain.getChannelFlag()==null?0:userMain.getChannelFlag());
//		retMap.put("userType", userMain.getUserType()==null?0:userMain.getUserType());
//		retMap.put("signId", userMain.getSignId()==null?0:userMain.getSignId());
		retMap.put("sex", userMain.getSex()==null?0:userMain.getSex());
		retMap.put("userLevel", userMain.getUserLevel()==null?0:userMain.getUserLevel());
		retMap.put("userDesc", userMain.getUserDesc());
//		retMap.put("liveFlag", userMain.getLiveFlag()==null?2:userMain.getLiveFlag());
//		retMap.put("loginFlag", userMain.getLoginFlag()==null?1:userMain.getLoginFlag());
		return retMap;
    }
    
    /**
     * 简易信息
     */
    public Map<String,Object> toSimpleMap(UserMain userMain){
    	if( userMain==null ){
    		return null;
    	}
    	Map<String,Object> retMap = new LinkedHashMap<String,Object>(8);
    	retMap.put("userId", userMain.getId());
		retMap.put("userCode", userMain.getUserCode());
		retMap.put("nickName", userMain.getNickName()==null?"":userMain.getNickName());
		retMap.put("headIcon", userMain.getHeadIcon()==null?"":userMain.getHeadIcon());
		retMap.put("sex", userMain.getSex()==null?0:userMain.getSex());
		retMap.put("userLevel", userMain.getUserLevel()==null?0:userMain.getUserLevel());
		return retMap;
    }
}