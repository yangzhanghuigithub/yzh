package com.learn.yzh.common.Enum.Order;

public enum OrderTypeEnum {
    //1-单独影票；
    CINEMA_TICKET((byte)1,"影票"),
    // 2-单独商品；
    COMMODITY((byte)2,"商品"),
    // 3-影票和商品；
    BLEND_ORDER((byte)3,"混合"),
    // 4-订餐;
    RESTAURANT((byte)4,"订餐");

    private Byte code;
    private String msg;

    private OrderTypeEnum(Byte code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static String getMsgByCode(Byte code) {
        for (OrderTypeEnum orderTypeEnum : OrderTypeEnum.values()) {
            if (orderTypeEnum.getCode().equals(code)) {
                return orderTypeEnum.msg;
            }
        }
        return null;
    }
}
