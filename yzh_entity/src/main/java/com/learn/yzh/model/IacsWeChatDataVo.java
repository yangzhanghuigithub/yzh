package com.learn.yzh.model;

import lombok.Data;

/**
 * @ClassName: IacsWeChatDataVo
 * @Author: shenlele
 * @Date: 2018/10/11 11:02
 * @Description:
 */
@Data
public class IacsWeChatDataVo {

    String touser;

    String msgtype;

    int agentid;

    Object text;//实际接收Map类型数据

    Object image;//实际接收Map类型数据

    Object voice;//实际接收Map类型数据

    Object video;//实际接收Map类型数据

    Object file;//实际接收Map类型数据

    Object news;//实际接收Map类型数据

    Object textcard;//实际接收Map类型数据

    Object mpnews;//实际接收Map类型数据

    Object markdown;//实际接收Map类型数据
}
