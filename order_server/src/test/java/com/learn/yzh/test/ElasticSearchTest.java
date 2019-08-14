package com.learn.yzh.test;
import com.learn.yzh.TestConfig;
import com.learn.yzh.entity.Employee;
import com.learn.yzh.utils.elasticsearch.EmployService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: yzh->ElasticSearchTest
 * @description: ElasticSearchTest
 * @author: yangzhanghui
 * @create: 2019-08-13 19:58
 **/

public class ElasticSearchTest extends TestConfig {

    @Autowired
    private EmployService employeeService;

    @Test
    public void createIndex() {

        //1. 给ES中索引(保存)一个文档
        Employee emp = new Employee();
        emp.setId("sssss");
        emp.setName("1111");
//        emp.setGender("111");
//        emp.setBirthday(LocalDate.now());
//        emp.setIdCard("111");
//        emp.setWedlock("1111");
//        emp.setNationId(0);
//        emp.setNativePlace("11111");
//        emp.setPoliticId(0);
//        emp.setEmail("111");
//        emp.setPhone("1111");
//        emp.setAddress("11111");
//        emp.setDepartmentId(0);
//        emp.setJobLevelId(0);
//        emp.setPosId(0);
//        emp.setEngageForm("111");
//        emp.setTiptopDegree("1111");
//        emp.setSpecialty("1111");
//        emp.setSchool("1111");
//        emp.setBeginDate(LocalDate.now());
//        emp.setWorkState("1111");
//        emp.setWorkID("11111");
//        emp.setContractTerm(0.0D);
//        emp.setConversionTime(LocalDate.now());
//        emp.setNotWorkDate(LocalDate.now());
//        emp.setBeginContract(LocalDate.now());
//        emp.setEndContract(LocalDate.now());
//        emp.setWorkAge(0);

        Employee save = employeeService.save(emp);
        System.out.println(save.toString());

    }
}
