package com.learn.yzh;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yangbin on 2017/11/17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BaseApp.class)
@ActiveProfiles("dev")
@Transactional
public class TestConfig {

}
