package com.learn.yzh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.yzh.entity.Tree;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Yangcq
 * @since 2019-03-05
 */
public interface TreeService extends IService<Tree>{
    List<Tree> getAllTree();
}
