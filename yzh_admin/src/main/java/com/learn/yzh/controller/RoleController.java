package com.learn.yzh.controller;

import com.learn.yzh.entity.Role;
import com.learn.yzh.service.RoleService;
import org.slf4j.LoggerFactory;
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

    private  static  final org.slf4j.Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/testFeign", method = RequestMethod.GET)
    public String test(){
        logger.info("===<call base-server===>");
//        return roleService.testFeign().toString();
        Role forObject = restTemplate.getForObject("http://base-server/base/testFeign", Role.class, "");
        return forObject.toString();
    }
}

