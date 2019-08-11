package com.learn.yzh.common.card;

import java.io.Serializable;

public class OrderPageParm implements Serializable {
    private static final long serialVersionUID = 5959079136533175085L;
    /**
     * 叶码
     */
    private Integer pageIndex;
    /**
     * 每叶条数
     */
    private Integer pageSize;
    /**
     * 所属城市
     */
    private String cities;
    /**
     * 公司ID
     */
    private String cinemaCodes;
    /**
     * 用户手机号
     */
    private String memberPhone;
    /**
     * 开始时间
     */
    private String searchTimeBegin;
    /**
     * 结束时间
     */
    private String searchTimeEnd;
    /**
     * 卡名
     */
    private String cardName;
    /**
     * 卡号
     */
    private String cardCode;
    /**
     * 卡类型
     */
    private String orderType;

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

    public String getCities() {
        return cities;
    }

    public void setCities(String cities) {
        this.cities = cities;
    }

    public String getCinemaCodes() {
        return cinemaCodes;
    }

    public void setCinemaCodes(String cinemaCodes) {
        this.cinemaCodes = cinemaCodes;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getSearchTimeBegin() {
        return searchTimeBegin;
    }

    public void setSearchTimeBegin(String searchTimeBegin) {
        this.searchTimeBegin = searchTimeBegin;
    }

    public String getSearchTimeEnd() {
        return searchTimeEnd;
    }

    public void setSearchTimeEnd(String searchTimeEnd) {
        this.searchTimeEnd = searchTimeEnd;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
