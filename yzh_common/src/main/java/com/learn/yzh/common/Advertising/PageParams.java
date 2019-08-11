package com.learn.yzh.common.Advertising;

import java.io.Serializable;
import java.util.List;

public class PageParams implements Serializable {
    private static final long serialVersionUID = 6192838882322883915L;
    private String adPage;
    private String isShow;
    private String adName;
    private Integer pageIndex;
    private Integer pageSize;
    private String companyCode;
    private String cinemaCode;
    private List<String> cinemaCodes;
    private Integer cinemaChooseWay;
    private String userId;
    private String cinemaCodeList;

    public String getCinemaCodeList() {
        return cinemaCodeList;
    }

    public void setCinemaCodeList(String cinemaCodeList) {
        this.cinemaCodeList = cinemaCodeList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getCinemaCodes() {
        return cinemaCodes;
    }

    public void setCinemaCodes(List<String> cinemaCodes) {
        this.cinemaCodes = cinemaCodes;
    }
    public Integer getCinemaChooseWay() {
        return cinemaChooseWay;
    }

    public void setCinemaChooseWay(Integer cinemaChooseWay) {
        this.cinemaChooseWay = cinemaChooseWay;
    }

    public String getCinemaCode() {
        return cinemaCode;
    }

    public void setCinemaCode(String cinemaCode) {
        this.cinemaCode = cinemaCode;
    }

    public String getAdPage() {
        return adPage;
    }

    public void setAdPage(String adPage) {
        this.adPage = adPage;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
