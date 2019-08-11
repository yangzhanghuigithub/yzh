package com.learn.yzh.common.Enum.CardEnum;

public enum CardSaleRuleDiscountEnum {

    SUBSIDY((byte) 1, "补贴金额"),
    DISCOUNT((byte) 2, "折扣");
    private Byte code;
    private String msg;

    private CardSaleRuleDiscountEnum(Byte code, String msg) {
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
        for (CardSaleRuleDiscountEnum responseEnum : CardSaleRuleDiscountEnum.values()) {
            if (responseEnum.getCode().equals(code)) {
                return responseEnum.msg;
            }
        }
        return null;
    }
}
