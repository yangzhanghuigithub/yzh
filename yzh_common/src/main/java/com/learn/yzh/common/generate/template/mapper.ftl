package dao;

import java.util.List;
 
@Repository
@Mapper
public interface ${domain?cap_first}Dao {

    public List<${domain?cap_first}> selectByWhere(${domain?cap_first} vo);
    
    public List<${domain?cap_first}> selectAll(String orderBy);
    
    public ${domain?cap_first} selectByPrimaryKey(${domain?cap_first} vo);

    public int countByWhere(${domain?cap_first} vo);
    
    public int deleteByPrimaryKey(${domain?cap_first} vo);
    
    public int deleteByWhere(${domain?cap_first} vo);
    
    public int insert(${domain?cap_first} vo);
    
    public int updateByWhere(${domain?cap_first} vo);
    
    public int updateByPrimaryKey(${domain?cap_first} vo);
}
