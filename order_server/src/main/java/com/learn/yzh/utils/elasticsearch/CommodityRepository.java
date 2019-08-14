package com.learn.yzh.utils.elasticsearch;

import com.learn.yzh.entity.Commodity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: yzh->CommodityRepository
 * @description: CommodityRepository
 * @author: yangzhanghui
 * @create: 2019-08-14 19:31
 **/
@Repository
public interface CommodityRepository extends ElasticsearchRepository<Commodity, String> {

}