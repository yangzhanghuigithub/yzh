package com.learn.yzh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.yzh.entity.VoucherSale;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yzh
 * @since 2019-03-27
 */
public interface VoucherSaleMapper extends BaseMapper<VoucherSale> {

    List<VoucherSale> selectJoinData();
}
