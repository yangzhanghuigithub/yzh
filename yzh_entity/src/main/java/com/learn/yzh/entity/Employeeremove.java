package com.learn.yzh.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yj
 * @since 2019-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("employeeremove")
public class Employeeremove extends Model<Employeeremove> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer eid;

    /**
     * 调动后部门
     */
    @TableField("afterDepId")
    private Integer afterDepId;

    /**
     * 调动后职位
     */
    @TableField("afterJobId")
    private Integer afterJobId;

    /**
     * 调动日期
     */
    @TableField("removeDate")
    private LocalDate removeDate;

    /**
     * 调动原因
     */
    private String reason;

    private String remark;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
