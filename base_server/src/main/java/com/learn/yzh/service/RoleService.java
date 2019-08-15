package com.learn.yzh.service;

import com.learn.yzh.config.FeignConfig;
import com.learn.yzh.entity.Role;
import com.learn.yzh.service.impl.RoleServiceImpl;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yzh
 * @since 2019-03-06
 */
@FeignClient(value = "order-server", configuration = FeignConfig.class, fallback = RoleServiceImpl.class)
//@Headers({"Content-Type: application/json","Accept: application/json"})
public interface RoleService{

    @RequestLine("POST /order/testFeign")
//    @RequestMapping(value = "/role/testFeign", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    Role testFeign();
}
