package com.learn.yzh.common.model.voucher;

import com.learn.yzh.common.Enum.voucher.GiveVoucherStrategy;

import java.util.List;

/**
 * ClassName:SendVoucherResult
 *
 * @author licho
 * @create 2018-12-26 13:22
 */
public class SendVoucherResult {
    private List<String> successList;
    private List<String> failedList;
    private String giveVoucherStrategy;
    private Integer resultStatus;
}
