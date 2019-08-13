package com.learn.yzh.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("tp_role_permission")
public class RolePermission extends Model<RolePermission> {

    private static final long serialVersionUID = 1L;
    @TableId
    private String id;

    private String roleId;

    private String permissionId;

    @TableField(exist = false)
    private String url;
    @TableField(exist = false)
    private String roleName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
