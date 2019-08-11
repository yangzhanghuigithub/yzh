package com.learn.yzh.common.Enum.Order;

public enum OrderStatusEnum {

    //订单状态：1000-已创建；
    ESTABLISH(1000, "已创建"),
    // 1001-已完成；
    COMPLETE(1001, "已完成"),
    // 1002-订单失败；
    FAIL(1002,"订单失败"),
    // 1003-订单取消；
    CANCEL(1003,"订单取消"),
    // 1007-订单出票中；
    OUT_OF_TICKET(1007,"订单出票中"),
    // 1302-退款成功；
    REFUND_COMPLETE(1302,"退款成功"),
    // 1304-退款申请中；
    REFUND_APPLY(1304,"退款申请中"),
    // 1305-退款失败；
    REFUND_FAIL(1305,"退款失败"),
    // 1401-已出餐；
    OUT_MEAL(1401,"已出餐"),
    // 1402-已配送
    DISTRIBUTION_COMPLETE(1402,"已配送");

    private Integer code;
    private String msg;

    private String codes;
    private String msgs;

    private OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    OrderStatusEnum(String codes, String msgs) {
        this.codes = codes;
        this.msgs = msgs;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public String getMsgs() {
        return msgs;
    }

    public void setMsgs(String msgs) {
        this.msgs = msgs;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static String getMsgByCode(Integer code) {
        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
            if (orderStatusEnum.getCode().equals(code)) {
                return orderStatusEnum.msg;
            }
        }
        return null;
    }
    public static String getMsgByCodes(String codes) {
        if("1501,1502".equals(codes)){
            return "退款成功";
        }else{
            for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
                if (orderStatusEnum.getCode().equals(Integer.valueOf(codes))) {
                    return orderStatusEnum.msg;
                }
            }
        }

        return null;
    }

}
