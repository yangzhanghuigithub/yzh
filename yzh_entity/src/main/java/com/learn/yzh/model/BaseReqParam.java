package com.learn.yzh.model;

/**
 * 基本参数
 * @author cui.
 *
 */
public class BaseReqParam {

	protected static final long serialVersionUID = 1L;
	public String memberCode;//会员编号
	public String token;//token 串
	public String CVersion;//版本编号
	public String cinemaCode;//影院编号
	public String OS;//影院编号
	public String companyCode;//企业code
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCinemaCode() {
		return cinemaCode;
	}
	public void setCinemaCode(String cinemaCode) {
		this.cinemaCode = cinemaCode;
	}
	public String getOS() {
		return OS;
	}
	public void setOS(String oS) {
		OS = oS;
	}
	public String getCVersion() {
		return CVersion;
	}
	public void setCVersion(String cVersion) {
		CVersion = cVersion;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMemberCode() {
		if(null == memberCode) {return  "";} else { return memberCode;}
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

}
