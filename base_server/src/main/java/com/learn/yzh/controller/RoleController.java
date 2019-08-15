package com.learn.yzh.controller;

import com.learn.yzh.service.RoleService;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yzh
 * @since 2019-03-06
 */
@RestController
@RequestMapping("/base")
public class RoleController {

    private final Logger logger = Logger.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/testFeign", method = RequestMethod.GET)
//    @HystrixCommand(fallbackMethod = "defaltFeign")
    public String test(){
        logger.info("===<call base-server===>");
        return roleService.testFeign().toString();
//        Role forObject = restTemplate.getForObject("http://base-server/role/testFeign", Role.class, "");
//        return forObject.toString();
    }

    @CacheRemove(commandKey = "getUser")
    public String defaltFeign(){
        return "order 坏了";
    }
}

