package com.learn.yzh.common.Enum.CardEnum;

public enum CardTypeEnum {
   // 1：储值卡；2：权益卡；11 辰星pos卡
   SAVINGS((byte)1,"储值卡"),
    LEGAL_RIGHT((byte)2,"权益卡"),
    xingchen_pos((byte)3,"辰星pos卡");
    private Byte code;
    private String msg;

    private CardTypeEnum(Byte code, String msg) {
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
        for (CardTypeEnum responseEnum : CardTypeEnum.values()) {
            if (responseEnum.getCode().equals(code)) {
                return responseEnum.msg;
            }
        }
        return null;
    }
}
