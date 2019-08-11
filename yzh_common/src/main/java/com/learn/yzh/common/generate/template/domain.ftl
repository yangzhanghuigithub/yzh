 package domain;

import com.learn.yzh.common.dao.DataEntity;

public class ${domain?cap_first} extends DataEntity {

    private static final long serialVersionUID = 8751282105532159742L;
    
    <#list ColumnInfos as ColumnInfo>
      private ${ColumnInfo.type} ${ColumnInfo.property};
    </#list>
    
    <#list ColumnInfos as ColumnInfo>
    public ${ColumnInfo.type} get${ColumnInfo.property?cap_first}() {
        return ${ColumnInfo.property};
    }

    public void set${ColumnInfo.property?cap_first}(${ColumnInfo.type} ${ColumnInfo.property}) {
        this.${ColumnInfo.property} = ${ColumnInfo.property};
    }

    </#list>  
}