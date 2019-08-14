package com.learn.yzh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.time.LocalDate;

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
@TableName("employee")
@Document(indexName = "employee")
public class Employee extends Model<Employee> {

    private static final long serialVersionUID = 1L;

    /**
     * 员工编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 员工姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 出生日期
     */
    private LocalDate birthday;

    /**
     * 身份证号
     */
    @TableField("idCard")
    private String idCard;

    /**
     * 婚姻状况
     */
    private String wedlock;

    /**
     * 民族
     */
    @TableField("nationId")
    private Integer nationId;

    /**
     * 籍贯
     */
    @TableField("nativePlace")
    private String nativePlace;

    /**
     * 政治面貌
     */
    @TableField("politicId")
    private Integer politicId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 所属部门
     */
    @TableField("departmentId")
    private Integer departmentId;

    /**
     * 职称ID
     */
    @TableField("jobLevelId")
    private Integer jobLevelId;

    /**
     * 职位ID
     */
    @TableField("posId")
    private Integer posId;

    /**
     * 聘用形式
     */
    @TableField("engageForm")
    private String engageForm;

    /**
     * 最高学历
     */
    @TableField("tiptopDegree")
    private String tiptopDegree;

    /**
     * 所属专业
     */
    private String specialty;

    /**
     * 毕业院校
     */
    private String school;

    /**
     * 入职日期
     */
    @TableField("beginDate")
    private LocalDate beginDate;

    /**
     * 在职状态
     */
    @TableField("workState")
    private String workState;

    /**
     * 工号
     */
    @TableField("workID")
    private String workID;

    /**
     * 合同期限
     */
    @TableField("contractTerm")
    private Double contractTerm;

    /**
     * 转正日期
     */
    @TableField("conversionTime")
    private LocalDate conversionTime;

    /**
     * 离职日期
     */
    @TableField("notWorkDate")
    private LocalDate notWorkDate;

    /**
     * 合同起始日期
     */
    @TableField("beginContract")
    private LocalDate beginContract;

    /**
     * 合同终止日期
     */
    @TableField("endContract")
    private LocalDate endContract;

    /**
     * 工龄
     */
    @TableField("workAge")
    private Integer workAge;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
