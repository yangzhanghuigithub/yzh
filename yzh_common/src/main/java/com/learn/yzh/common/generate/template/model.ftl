package ${packageName}.model.${moduleName};

import com.mplus.application.common.datasource.dao.DataEntity;

/**
 * ${functionName}Entity
 * @author ${classAuthor}
 * @version ${classVersion}
 */
public class ${ClassName} extends DataEntity {

    private static final long serialVersionUID = 8751282105532159742L;

	<#list ColumnInfos as ColumnInfo>

	private ${ColumnInfo.type} ${ColumnInfo.name};
	private boolean ${ColumnInfo.name}_updated;
	</#list>

	<#list ColumnInfos as ColumnInfo>
		public ${ColumnInfo.type} get${ColumnInfo.name?cap_first}() {
		return ${ColumnInfo.name};
	}

	public void set${ColumnInfo.name?cap_first}(${ColumnInfo.type} ${ColumnInfo.name}) {
		this.${ColumnInfo.name} = ${ColumnInfo.name};
		${ColumnInfo.name}_updated = true;
	}

	public boolean is${ColumnInfo.name?cap_first}_updated() {
		return ${ColumnInfo.name}_updated;
	}
	public void set${ColumnInfo.name?cap_first}_updated(boolean value) {
		${ColumnInfo.name}_updated = value;
	}
	</#list>
}


