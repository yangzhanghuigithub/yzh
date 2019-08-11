package com.learn.yzh.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * @program: yj->ETUInfo
 * @description: Excel实体类
 * @author: yangzhanghui
 * @create: 2019-07-31 22:13
 **/
@Data
public class ETUInfo extends BaseRowModel {
    @ExcelProperty(value ={"TxHash"},index = 0)
    private String txHash;

    @ExcelProperty(value ={"Date"},index =1)
    private String date;

    @ExcelProperty(value ={"From"},index = 2)
    private String from;

    @ExcelProperty(value ={"To"},index = 3)
    private String to;

    @ExcelProperty(value ={"Quantity"},index = 4)
    private String quantity;
}