package com.learn.yzh.compnent;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import com.netflix.zuul.monitoring.MonitoringHelper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @program: yzh->GroovyRunner
 * @description: GroovyRunner
 * @author: yangzhanghui
 * @create: 2019-08-15 12:25
 **/
@Component
public class GroovyRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        MonitoringHelper.initMocks();
        FilterLoader.getInstance().setCompiler(new GroovyCompiler());
        try {
            FilterFileManager.setFilenameFilter(new GroovyFileFilter());
            FilterFileManager.init(20, "D:/codes/yzh/zuul_server/src/main/java/com/learn/yzh/groovy");
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
