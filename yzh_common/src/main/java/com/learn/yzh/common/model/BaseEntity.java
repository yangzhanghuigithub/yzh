package com.learn.yzh.common.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @Author huanghongfei
 * @Description
 * @Date Create in 10:40 2018/3/26
 */
@MappedSuperclass
public class BaseEntity implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue
    private Long id;
    /**
     * 删除标记
     */
    @Column(name = "is_del", nullable = false, length = 1)
    private Integer isdel;
    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false, length = 19)
    private String createtime;
    /**
     * 更新时间
     */
    @Column(name = "update_time", nullable = false, length = 19)
    private String updatetime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}