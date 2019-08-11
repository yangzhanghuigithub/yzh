package com.learn.yzh.common.model;


import com.learn.yzh.common.dao.DataEntity;

import java.util.Date;

/**
 * 院线公司
 * 
 * @author Lomis
 * @version 2015-12-01
 */
public class MovieCompany  {

	private static final long serialVersionUID = 1L;

	private String id; // 编号

	private String companyName;

	private String companyCode;

	private String companyZipcode;

	private String companyAddress; // 影院的内部编码

	private String companyDescription;

	private String createBy;

	private Date createTime;

	private String updateBy;

	private Date updateTime;

	private String delFlag;

	private String jpushKey;

	private String jPushMasterSecret;
	
	private String smsPort;
	
	private String smsName;
	
	private String smsPsw;
	
	private String smsPlatform;
	
	private String appUrl;
	
	private String logoName;
	
	private String applicationName;
	
	private String logoId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyZipcode() {
		return companyZipcode;
	}

	public void setCompanyZipcode(String companyZipcode) {
		this.companyZipcode = companyZipcode;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyDescription() {
		return companyDescription;
	}

	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getjPushKey() {
		return jpushKey;
	}

	public void setjPushKey(String jPushKey) {
		this.jpushKey = jPushKey;
	}

	public String getjPushMasterSecret() {
		return jPushMasterSecret;
	}

	public void setjPushMasterSecret(String jPushMasterSecret) {
		this.jPushMasterSecret = jPushMasterSecret;
	}

	public String getJpushKey() {
		return jpushKey;
	}

	public void setJpushKey(String jpushKey) {
		this.jpushKey = jpushKey;
	}

	public String getSmsPort() {
		return smsPort;
	}

	public void setSmsPort(String smsPort) {
		this.smsPort = smsPort;
	}

	public String getSmsName() {
		return smsName;
	}

	public void setSmsName(String smsName) {
		this.smsName = smsName;
	}

	public String getSmsPsw() {
		return smsPsw;
	}

	public void setSmsPsw(String smsPsw) {
		this.smsPsw = smsPsw;
	}

	public String getSmsPlatform() {
		return smsPlatform;
	}

	public void setSmsPlatform(String smsPlatform) {
		this.smsPlatform = smsPlatform;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getLogoName() {
		return logoName;
	}

	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getLogoId() {
		return logoId;
	}

	public void setLogoId(String logoId) {
		this.logoId = logoId;
	}

}