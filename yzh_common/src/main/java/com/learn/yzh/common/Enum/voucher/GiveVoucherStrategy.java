package com.learn.yzh.common.Enum.voucher;
/*
枚举类，枚举发券公共接口的发券策略。
 */
public enum GiveVoucherStrategy {
    ALL_SUCCESS(1),ANY_SUCCESS(2),ALMOST_MORE_VOUCHER(3);
    private Integer giveVoucherStrategy;
    private GiveVoucherStrategy(Integer code){
        this.giveVoucherStrategy=code;
    }

    public Integer getGiveVoucherStrategy() {
        return giveVoucherStrategy;
    }


    public static GiveVoucherStrategy mapOf(Integer giveVoucherStrategy){
        for(GiveVoucherStrategy strategy:GiveVoucherStrategy.values()){
            if(strategy.getGiveVoucherStrategy().equals(giveVoucherStrategy)){
                return strategy;
            }
        }
        return null;
    }
}
