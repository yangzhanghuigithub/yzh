package com.learn.yzh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.yzh.entity.Hr;
import com.learn.yzh.mapper.HrMapper;
import com.learn.yzh.service.HrService;
import io.github.lvyahui8.spring.annotation.DataProvider;
import io.github.lvyahui8.spring.annotation.InvokeParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yj
 * @since 2019-07-25
 */
@Service
@Transactional
public class HrServiceImpl extends ServiceImpl<HrMapper, Hr>  implements HrService {

    @Autowired
    HrMapper hrMapper;

    @Override
    @DataProvider("getHr")
    public Hr getHr(@InvokeParameter("hrId") String hrId) {
        return hrMapper.selectById(hrId);
    }
}
