package com.learn.yzh.common.model.card;

import java.io.Serializable;
import java.math.BigDecimal;

public class CardSaleDTO implements Serializable {
    private static final long serialVersionUID = -5657238437022463384L;
    /**
     * 定单号
     */
    private String id;
    /**
     * 用户手机号
     */
    private String memberPhone;
    /**
     * 支付时间
     */
    private String payTime;
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 卡名
     */
    private String cardName;
    /**
     * 卡类型
     */
    private Byte cardType;
    /**
     * 影院名称
     */
    private String cinemaName;
    /**
     * 储值额
     */
    private BigDecimal cardAmount;
    /**
     * 充值前金额
     */
    private BigDecimal beforeAmount;
    /**
     * 充值后金额
     */
    private BigDecimal afterAmount;
    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Byte getCardType() {
        return cardType;
    }

    public void setCardType(Byte cardType) {
        this.cardType = cardType;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public BigDecimal getCardAmount() {
        return cardAmount;
    }

    public void setCardAmount(BigDecimal cardAmount) {
        this.cardAmount = cardAmount;
    }

    public BigDecimal getBeforeAmount() {
        return beforeAmount;
    }

    public void setBeforeAmount(BigDecimal beforeAmount) {
        this.beforeAmount = beforeAmount;
    }

    public BigDecimal getAfterAmount() {
        return afterAmount;
    }

    public void setAfterAmount(BigDecimal afterAmount) {
        this.afterAmount = afterAmount;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
