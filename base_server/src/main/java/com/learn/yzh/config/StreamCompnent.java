package com.learn.yzh.config;

import com.learn.yzh.service.SinkSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * @program: yj->StreamCompnent
 * @description: Stream
 * @author: yangzhanghui
 * @create: 2019-08-06 13:14
 **/
@EnableBinding({SinkSender.class})
public class StreamCompnent {

    private static Logger loger = LoggerFactory.getLogger(StreamCompnent.class);

    @StreamListener(Sink.INPUT)
    public void stream(Object payload){
        loger.info("Receiver : " + payload);
    }
}
