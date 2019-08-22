package com.learn.yzh.controller;


import com.learn.yzh.entity.base.Role;
import com.learn.yzh.service.RoleService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yzh
 * @since 2019-03-06
 */
@RestController
@RequestMapping("/order")
public class RoleController {

    private  static  final org.slf4j.Logger logger= LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/testFeign", produces = {"application/json;charset=UTF-8"})
    public Role test(){
        logger.info("===<be call base-server===>");

        Role role = roleService.getById(1);
        return role;
    }
}

