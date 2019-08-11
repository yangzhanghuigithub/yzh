<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${domain?cap_first}Dao" >
  
  <resultMap id="BaseResultMap" type="${domain?cap_first}" >
    <!-- WARNING - @generated do not modify. -->

	<#list ColumnInfos as ColumnInfo>
	  <result column="${ColumnInfo.name}" property="${ColumnInfo.property}" />
	</#list>  
  </resultMap>

  <sql id="Where_Clause" >
    <!-- WARNING - @generated do not modify. -->
    <where >
        <trim prefixOverrides="and | or" >
            <foreach collection="whereClause" item="item">
                <choose>
                  <when test="item.type == 'String'" >
                    and ${item_key} = "${item_value}"
                  </when>
                  <when test="item.type == 'Number'" >
                    and ${item_key} = ${item_value}
                  </when>
                  <when test="item.type == 'Date'" >
                    and ${item_key} = str_to_date("${item_value}", '%Y%m%d%H%i%s')
                  </when>
                </choose>
              </foreach>
        </trim>
    </where>
  </sql>


  <sql id="Base_Column_List" >
    <!-- WARNING - @generated do not modify. -->
    ${allColumns}

  </sql>

  <select id="selectByWhere" resultMap="BaseResultMap" parameterType="${domain?cap_first}" >
    <!--  WARNING - @generated do not modify. -->
    select
    <if test="distinct" >
      distinct
    </if>
    <include refid="Base_Column_List" />
      from ${table}
 
    <include refid="Where_Clause" />
 
    <if test="orderByClause != null" >
      order by ${orderByClause}
    </if>
  </select>
  
  <select id="selectAll" resultMap="BaseResultMap" parameterType="string">
    select 
      <include refid="Base_Column_List" />
    from ${table}
    <if test="_parameter != null" >
      order by ${orderByClause_}
    </if>
  </select>
  
  <select id="selectByPrimaryKey" resultMap="BaseResultMap" parameterType="${domain?cap_first}" >
    <!-- WARNING - @generated do not modify. -->
    select 
      <include refid="Base_Column_List" />
    from ${table}
    where ${whereKeys}
  </select>

  <select id="countByWhere" parameterType="${domain?cap_first}" resultType="java.lang.Integer" >
    <!--  WARNING - @generated do not modify. -->
    select count(*) from ${table}
    
    <if test="whereClause != null" >
    <include refid="Where_Clause" />
    </if>
  </select>

  <delete id="deleteByPrimaryKey" parameterType="${domain?cap_first}" >
    <!--  WARNING - @generated do not modify. -->
      delete from ${table}
      where ${whereKeys}
  </delete>

  <delete id="deleteByWhere" parameterType="${domain?cap_first}" >
    <!-- WARNING - @generated do not modify. -->
    delete from ${table}
    
    <if test="whereClause != null" >
      <include refid="Where_Clause" />
    </if>
  </delete>

  <insert id="insert" parameterType="${domain?cap_first}" >
    <!-- WARNING - @generated do not modify. -->
    insert into ${table} (${allColumns})
    values (${allValues})
  </insert>


  <update id="updateByWhere" parameterType="${domain?cap_first}" >
    <!-- WARNING - @generated do not modify. -->
    update ${table}
    
    <set > 
      <#list updateifColumns as updateifColumn>
      ${updateifColumn}
      </#list>
    </set>
    
    <if test="whereClause != null" >
      <include refid="Where_Clause" />
    </if>
  </update>
  
  <update id="updateByPrimaryKey" parameterType="${domain?cap_first}" >
    <!-- WARNING - @generated do not modify. -->
    update ${table}
    
    <set > 
      <#list updateifColumns as updateifColumn>
        ${updateifColumn}
      </#list>
    </set>
    
    where ${whereKeys}
  </update>
</mapper>