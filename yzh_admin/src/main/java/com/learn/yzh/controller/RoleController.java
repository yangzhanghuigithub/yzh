package com.learn.yzh.controller;

import com.learn.yzh.entity.Role;
import com.learn.yzh.service.RoleService;
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
@RequestMapping("/role")
public class RoleController {

    private final Logger logger = Logger.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/testFeign", method = RequestMethod.GET)
    public String test(){
//        return roleService.testFeign().toString();
        logger.info("===<call base-server===>");
        Role forObject = restTemplate.getForObject("http://base-server/role/testFeign", Role.class, "");
        return forObject.toString();
    }
}

