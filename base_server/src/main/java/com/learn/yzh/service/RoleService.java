package com.learn.yzh.service;

import com.learn.yzh.config.FeignConfig;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yzh
 * @since 2019-03-06
 */
@FeignClient(value = "base-server", configuration = FeignConfig.class)
//@Headers({"Content-Type: application/json","Accept: application/json"})
public interface RoleService{

    @RequestLine("POST /role/testFeign")
//    @RequestMapping(value = "/role/testFeign", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    Role testFeign();
}
