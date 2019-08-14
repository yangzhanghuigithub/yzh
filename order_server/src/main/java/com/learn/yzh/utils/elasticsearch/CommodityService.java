package com.learn.yzh.utils.elasticsearch;

import com.learn.yzh.entity.Commodity;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @program: yzh->CommodityService
 * @description: CommodityService
 * @author: yangzhanghui
 * @create: 2019-08-14 19:30
 **/

public interface CommodityService {

    long count();

    Commodity save(Commodity commodity);

    void delete(Commodity commodity);

    Iterable<Commodity> getAll();

    List<Commodity> getByName(String name);

    Page<Commodity> pageQuery(Integer pageNo, Integer pageSize, String kw);

}