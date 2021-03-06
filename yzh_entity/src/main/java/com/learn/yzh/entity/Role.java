package com.learn.yzh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.learn.yzh.entity.base.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yzh
 * @since 2019-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tp_role")
public class Role extends Model<Role> implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 角色名称
     */
    private String roleName;

    @TableField(exist = false)
    private Permission[] permissions;

    @TableField(exist = false)
    @JsonFormat(pattern="yyyyMMddHHmmssSSS", timezone="GMT+8")
    private Date createDate;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}
