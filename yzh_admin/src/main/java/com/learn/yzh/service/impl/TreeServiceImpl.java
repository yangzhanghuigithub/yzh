package com.learn.yzh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.yzh.entity.Tree;
import com.learn.yzh.mapper.TreeMapper;
import com.learn.yzh.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Yangcq
 * @since 2019-03-05
 */
@Service
public class TreeServiceImpl extends ServiceImpl<TreeMapper, Tree> implements TreeService {

    @Autowired
    private TreeMapper treeMapper;

    @Override
    public List<Tree> getAllTree() {
        List<Tree> trees = treeMapper.selectByMap(new HashMap<>());
        return trees;
    }
}
