package com.learn.yzh.common.model.voucher;

/**
 * ClassName:用作获取每个影院可用的券批次信息。
 *
 * @author licho
 * @create 2018-12-18 16:27
 */
public class AvailabilityVoucherApplyVo {
    private String voucherCode;//券类型id
    private Integer voucherType;//券类型
    private String voucherTypeName;//券类型名称
    private String voucherName;//券名称
    private String applyId;//批次id
    private String applyName;//批次名称
    private Integer applyCount;//批次数量
    private Integer availableCount;//可用数量
    private String discountComment;//优惠描述
    private String cinemaCode;//影院code
    private String companyCode;//院线编码

    public String getVoucherTypeName() {
        return voucherTypeName;
    }

    public void setVoucherTypeName(String voucherTypeName) {
        this.voucherTypeName = voucherTypeName;
    }

    public String getCinemaCode() {
        return cinemaCode;
    }

    public void setCinemaCode(String cinemaCode) {
        this.cinemaCode = cinemaCode;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public Integer getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(Integer voucherType) {
        this.voucherType = voucherType;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public Integer getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(Integer applyCount) {
        this.applyCount = applyCount;
    }

    public Integer getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(Integer availableCount) {
        this.availableCount = availableCount;
    }

    public String getDiscountComment() {
        return discountComment;
    }

    public void setDiscountComment(String discountComment) {
        this.discountComment = discountComment;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
}
