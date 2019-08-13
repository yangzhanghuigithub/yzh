package com.learn.yzh.service;

import com.learn.yzh.config.FeignConfig;
import com.learn.yzh.entity.Role;
import feign.Headers;
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
@FeignClient(value = "base-server", configuration = FeignConfig.class)
@Headers({"Content-Type: application/json","Accept: application/json"})
public interface RoleService{

    @RequestLine("POST /role/testFeign")
    Role testFeign();
}
