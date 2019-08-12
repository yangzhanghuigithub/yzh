package com.learn.yzh.controller;


import com.learn.yzh.entity.Role;
import com.learn.yzh.service.RoleService;
import org.apache.log4j.Logger;
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
@RequestMapping("/role")
public class RoleController {

    private final Logger logger = Logger.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/testFeign")
    public Role test(){
        logger.info("===<be call base-server===>");

        Role role = roleService.getById(1);
        return role;
    }
}

