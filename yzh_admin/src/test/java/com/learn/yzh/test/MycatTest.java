package com.learn.yzh.test;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.yzh.TestConfig;
import com.learn.yzh.entity.VoucherSale;
import com.learn.yzh.mapper.VoucherSaleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
public class MycatTest extends TestConfig {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private VoucherSaleMapper voucherSaleMapper;

    @Test
    public void testQuery(){
        List<VoucherSale> voucherSales = voucherSaleMapper.selectByMap(new HashMap<>());
        System.out.println(voucherSales.size());
    }

    @Test
    public void testQueryPage(){
        IPage<VoucherSale> voucherSalePage = new Page<VoucherSale>(1,10);
        Wrapper<VoucherSale> treeWrapper = new QueryWrapper<VoucherSale>();
        IPage<Map<String, Object>> voucherPage = voucherSaleMapper.selectMapsPage(voucherSalePage, treeWrapper);
        System.out.println(JSONObject.toJSONString(voucherPage));
    }

    @Test
    public void testInsert(){
        Date d = new Date();
        LocalDate date = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.now();
        VoucherSale voucherSale = new VoucherSale();
        voucherSale.setId("11");
        voucherSale.setApplyId("111");
        voucherSale.setType(1);
        voucherSale.setVoucherNo("1");
        voucherSale.setMakeDate(date);
        voucherSale.setStatus(1);
        voucherSale.setMemberCode("111");
        voucherSale.setBindPassword("111");
        voucherSale.setBindDatetime(dateTime);
        voucherSale.setBindStatus(1);
        voucherSale.setCreateTime(dateTime);
        voucherSale.setUseTime(dateTime);
        voucherSale.setVoucherId("111");
        voucherSale.setCompanyCode("111");
        voucherSale.setInviteCode("1111");
        voucherSale.setChanel(1);
        voucherSale.setDeadline(dateTime);
        voucherSale.setDelFlag(1);
        voucherSale.setFromOrderNo("111");
        int i = voucherSaleMapper.insert(voucherSale);
        System.out.println(i);
    }

    @Test
    public void testUpdate(){
        VoucherSale voucherSale = new VoucherSale();
        voucherSale.setId("11");
        voucherSale.setFromOrderNo("22");
        int i = voucherSaleMapper.updateById(voucherSale);
        System.out.println(i);
    }

    @Test
    public void testDelete(){
        int i = voucherSaleMapper.deleteById("11");
        System.out.println(JSONObject.toJSONString(i));
    }

    @Test
    public void testJoin(){
        List<VoucherSale> voucherSales = voucherSaleMapper.selectJoinData();
        System.out.println(JSONObject.toJSONString(voucherSales));
    }
}