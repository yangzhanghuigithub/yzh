package com.sia.controller;

import com.sia.hunter.annotation.OnlineTask;
import com.sia.hunter.helper.JSONHelper;

import java.util.HashMap;
import java.util.Map;


/**
 * @program: onlinetaskclient->OnlineTaskExample
 * @description: OnlineTaskExample
 * @author: yangzhanghui
 * @create: 2019-08-21 17:53
 **/
@RestController
public class OnlineTaskExample {

    /**
     * OnlineTask示例，标准格式
     * <p>
     * （1）方法上有@OnlineTask注解，用来标注是否被抓取，可以添加description描述，描述该Task的作用
     * <p>
     * （2）方法上有@RequestMapping注解，因为OnlineTask必须对外提供HTTP访问
     * <p>
     * （3）@RequestMapping注解中，请使用value（或path）属性（因为低版本Spring没有path属性，为了兼容，优先抓取value属性的值），且value 以"/"为前缀（减少处理复杂度），路径不能包含"\"（用作替换）
     * <p>
     * （4）@RequestMapping注解中，method中必须要有POST方法（需要传参），且使用@CrossOrigin支持跨域（POST方法默认不允许跨域）或者使用过滤器（Filter）让Task可以跨域
     * <p>
     * （5）请使用 @ResponseBody 标注返回值。类上如果使用 @RestController，则 @ResponseBody可选，如果使用@Controller，则@ResponseBody必选
     * <p>
     * （6）方法返回值是String（JSON），JSON是一个Map，必须有"status" 属性，值为{success,failure,unknown}，用于处理逻辑；必须有 "result" 属性，值为HTTP调用的返回值
     * <p>
     * （7）方法可以无参；若有入参，则只能有一个，且是String（JSON），请使用 @RequestBody 标注
     * <p>
     * （8）@OnlineTask注解使用了AOP技术，保证调用的方法是单例单线程
     * <p>
     * （9）OnlineTask的业务逻辑处理请尽量保证幂等
     * <p>
     * （10）现支持类上使用@RequestMapping注解
     * /
     *
     * @param json
     * @return
     */
    // （1）方法上有@OnlineTask注解，用来标注是否被抓取，可以添加description描述，描述该Task的作用
    @OnlineTask(description = "在线任务示例",enableSerial=true)
    // （2）方法上有@RequestMapping注解，因为OnlineTask必须对外提供HTTP访问
    // （3）@RequestMapping注解中，请使用value（或path）属性（因为低版本Spring没有path属性，为了兼容，优先抓取value属性的值），且value 以"/"为前缀（减少处理复杂度），路径不能包含"\"（用作替换）
    @RequestMapping(value = "/example", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    // （4）@RequestMapping注解中，method中必须要有POST方法（需要传参），且使用@CrossOrigin支持跨域（POST方法默认不允许跨域）或者使用过滤器（Filter）让Task可以跨域
    @CrossOrigin(methods = { RequestMethod.POST }, origins = "*")
    // （5）请使用 @ResponseBody 标注返回值。类上如果使用 @RestController，则 @ResponseBody可选，如果使用@Controller，则@ResponseBody必选
    @ResponseBody
    // （6）方法返回值是String（JSON），JSON是一个Map，必须有"status" 属性，值为{success,failure,unknown}，用于处理逻辑；必须有 "result"属性，值为HTTP调用的返回值
    // （7）方法可以无参；若有入参，则只能有一个，且是String（JSON），请使用 @RequestBody 标注
    public String example(@RequestBody String json) {

        /**
         * TODO：客户端业务逻辑处理
         */
        // 返回结果存储结构，请使用Map
        Map<String, String> info = new HashMap<String, String>();
        // 返回的信息必须包含以下两个字段
        info.put("status", "success");// status字段表明此次Task调用是否成功，非 success 都是失败
        info.put("result", "as you need");// result字段表示此次Task调用的返回结果（之后可能传递给其他Task） ，其值可能作为其他Task的输入，所以只能是String（JSON）类型
        // 返回值也是String（JSON）类型，客户端包里有JSONHelper，可直接使用
        return JSONHelper.toString(info);
    }

    @OnlineTask(description = "success,无入参",enableSerial=true)
    @RequestMapping(value = "/success-noparam", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @CrossOrigin(methods = { RequestMethod.POST }, origins = "*")
    @ResponseBody
    public String taskOne() {
        Map<String, String> info = new HashMap<String, String>();
        info.put("result", "success-noparam");
        info.put("status", "success");
        System.out.println("调用taskOne任务成功");

        return JSONHelper.toString(info);
    }

    @OnlineTask(description = "success,有入参",enableSerial=true)
    @RequestMapping(value = "/success-param", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @CrossOrigin(methods = { RequestMethod.POST }, origins = "*")
    @ResponseBody
    public String taskTwo(@RequestBody String json) {
        Map<String, String> info = new HashMap<String, String>();
        info.put("result", "success-param"+"入参是："+json);
        info.put("status", "success");
        System.out.println("调用taskTwo任务成功");

        return JSONHelper.toString(info);
    }
}