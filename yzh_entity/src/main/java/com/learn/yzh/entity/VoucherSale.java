package com.learn.yzh.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author yzh
 * @since 2019-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_voucher_sale")
public class VoucherSale extends Model<VoucherSale> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private String id;

    /**
     * 批次id
     */
    private String applyId;

    /**
     * '券类型，券类型。 0：代金劵；1：折扣劵；2：兑换劵；3: 其他   4: 0元兑换券, 5:商品券, 6:异业券,7.商家券',
     */
    private Integer type;

    /**
     * 劵号
     */
    private String voucherNo;

    /**
     * 生成日期
     */
    @JsonFormat(pattern="yyyyMMddHHmmssSSS", timezone="GMT+8")
    private LocalDate makeDate;

    /**
     * 劵状态 0:未使用  1：已使用  2：作废
     */
    private Integer status;

    /**
     * 持劵人
     */
    private String memberCode;

    /**
     * 密码
     */
    private String bindPassword;

    /**
     * APP绑券时间
     */
    @JsonFormat(pattern="yyyyMMddHHmmssSSS", timezone="GMT+8")
    private LocalDateTime bindDatetime;

    /**
     * 绑定状态 0:未绑定 1:已绑定
     */
    private Integer bindStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyyMMddHHmmssSSS", timezone="GMT+8")
    private LocalDateTime createTime;

    /**
     * 使用时间
     */
    @JsonFormat(pattern="yyyyMMddHHmmssSSS", timezone="GMT+8")
    private LocalDateTime useTime;

    /**
     * t_voucher表的Id字段
     */
    private String voucherId;

    /**
     * 院线编码
     */
    private String companyCode;

    /**
     * 推广码
     */
    private String inviteCode;

    /**
     * 券获得渠道 1:直接绑定  会员关怀(2-13) 相关渠道  红包：21票务定向红包22购票赠送红包23限量领取红包24附近商家红包25卖品定向红包26购物赠送红包27影院评价送红包
     */
    private Integer chanel;

    /**
     * 有效截止日期
     */
    @JsonFormat(pattern="yyyyMMddHHmmssSSS", timezone="GMT+8")
    private LocalDateTime deadline;

    /**
     * 删除标识0:未删除 1:删除
     */
    @TableLogic
    private Integer delFlag;

    /**
     * 购票购卖品送的券，记录订单号
     */
    private String fromOrderNo;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
