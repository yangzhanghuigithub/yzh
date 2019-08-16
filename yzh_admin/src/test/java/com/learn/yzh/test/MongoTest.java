package com.learn.yzh.test;

import com.alibaba.fastjson.JSONObject;
import com.learn.yzh.TestConfig;
import com.learn.yzh.entity.VoucherSale;
import com.learn.yzh.mapper.VoucherSaleMapper;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class MongoTest extends TestConfig {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private VoucherSaleMapper voucherSaleMapper;

    @Test
    public void testInsertBatch(){
        List<VoucherSale> voucherSales = voucherSaleMapper.selectByMap(new HashMap<>());
        mongoTemplate.insert(voucherSales, VoucherSale.class);
    }

    @Test
    public void testInsertOne(){
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
        mongoTemplate.insert(voucherSale);
    }

    @Test
    public void testQuery(){
        Query query = new Query(Criteria.where("name").is("中国"));
        List<VoucherSale> voucherSales = mongoTemplate.find(query, VoucherSale.class);
        String s = JSONObject.toJSONString(voucherSales);
        System.out.println(s);
    }

    @Test
    public void testQueryBatchPageSort(){
        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("name").is("中国"),Criteria.where("name").is("哈哈哈哈"));
        Query query = new Query(criteria);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        query.with(sort);
        query.skip(1).limit(3);
        List<VoucherSale> voucherSales = mongoTemplate.find(query, VoucherSale.class,"");
        String s = JSONObject.toJSONString(voucherSales);
        System.out.println(s);
    }

    @Test
    public void testQueryRegix(){
        Query query = new Query(Criteria.where("id").regex("11"));
        List<VoucherSale> voucherSales = mongoTemplate.find(query, VoucherSale.class);
        System.out.println(JSONObject.toJSONString(voucherSales));
    }

    @Test
    public void testUpdate(){
        Query query = new Query(Criteria.where("id").is("11"));
        List<VoucherSale> voucherSales = mongoTemplate.find(query, VoucherSale.class);
        Update update = new Update();
        update.set("name","哈哈哈哈");
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, VoucherSale.class);
        System.out.println(JSONObject.toJSONString(updateResult));
    }

    @Test
    public void testDelete(){
        DeleteResult remove = mongoTemplate.remove(new Query(), VoucherSale.class);
        System.out.println(JSONObject.toJSONString(remove));
    }
}