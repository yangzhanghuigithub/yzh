package com.learn.yzh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.yzh.entity.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yj
 * @since 2019-07-25
 */
public interface MenuService extends IService<Menu> {

    List<Menu> getAllMenu();
}
