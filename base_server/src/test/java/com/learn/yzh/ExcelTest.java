package com.learn.yzh;

import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.learn.yzh.config.AggregateCompnent;
import com.learn.yzh.config.MqSender;
import com.learn.yzh.entity.Hr;
import com.learn.yzh.model.ETUInfo;
import com.learn.yzh.model.ExcelModel;
import com.learn.yzh.service.SinkSender;
import com.learn.yzh.utils.EasyExcelUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: yj->ExcelTest
 * @description: Excel测试类
 * @author: yangzhanghui
 * @create: 2019-07-31 21:42
 **/
@RunWith(SpringJUnit4ClassRunner.class)
public class ExcelTest extends TestConfig {

    @Autowired
    private MqSender sender;

    @Autowired
    private SinkSender sinkSender;

    @Autowired
    private AggregateCompnent aggregateCompnent;

    @Test
    public void sinkSender(){
        sinkSender.output().send(MessageBuilder.withPayload("From SinkSender").build());
    }

    @Test
    public void send() throws Exception{
        sender.send();
    }

    @Test
    public void Testsync() throws Exception {
        Hr userFinal = aggregateCompnent.getUserFinal("3");
        System.out.println(userFinal.toString());
    }

    /**
     * 写入Excel
     *
     * @throws FileNotFoundException
     * @author Lynch
     */
    @Test
    public void test() throws FileNotFoundException {
        List<ExcelModel> excelModelList = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            ExcelModel excelModel = new ExcelModel();
            excelModel.setAddress("address" + i);
            excelModel.setAge(i + "");
            excelModel.setEmail("email" + i);
            excelModel.setHeigh("heigh" + i);
            excelModel.setLast("last" + i);
            excelModel.setName("name" + i);
            excelModel.setSax("sax" + i);
            excelModelList.add(excelModel);
        }

        long beginTime = System.currentTimeMillis();
        OutputStream out = new FileOutputStream("D:/aaa.xlsx");
        EasyExcelUtil.writeExcelWithModel(out, excelModelList, ExcelModel.class, ExcelTypeEnum.XLSX);
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("总共耗时 %s 毫秒", (endTime - beginTime)));

        excelModelList = null;
    }

    /**
     * 读取Excel
     *
     * @throws FileNotFoundException
     * @author Lynch
     */
    @Test
    public void readExcel() throws FileNotFoundException {
        try {
            InputStream inputStream=new FileInputStream("D:/aaa.xlsx");
            //读入文件，每一行对应一个 Model，获取 Model 列表
            List<ExcelModel> objectList = EasyExcelUtil.readExcelWithModel(inputStream, ExcelModel.class, ExcelTypeEnum.XLSX);
            for(ExcelModel excelModel: objectList) {
                System.out.println(excelModel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadExcelWithStringList(){
        try(
                InputStream inputStream=new FileInputStream("C:\\Users\\Shinelon\\IdeaProjects\\webmagic\\webmagic\\src\\test\\java\\etherscan-page1-1000.xls");
                OutputStream outputStream=new FileOutputStream("C:\\Users\\Shinelon\\IdeaProjects\\webmagic\\webmagic\\src\\test\\java\\etherscan-page1-1000String.xlsx")
        ) {
            //读入文件,每一行对应一个List<String>
            List<List<String>> stringLists = EasyExcelUtil.readExcelWithStringList(inputStream, ExcelTypeEnum.XLS);

            //定义Excel正文背景颜色
            TableStyle tableStyle=new TableStyle();
            tableStyle.setTableContentBackGroundColor(IndexedColors.WHITE);

            //定义Excel正文字体大小
            Font font=new Font();
            font.setFontHeightInPoints((short) 10);

            tableStyle.setTableContentFont(font);

            Table table=new Table(0);
            table.setTableStyle(tableStyle);

            EasyExcelUtil.writeExcelWithStringList(outputStream,stringLists,table,ExcelTypeEnum.XLSX);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testReadExcelWithModel(){
        try (
                InputStream inputStream=new FileInputStream("C:\\Users\\Shinelon\\IdeaProjects\\webmagic\\webmagic\\src\\test\\java\\etherscan-page1-1000.xls");
                OutputStream outputStream=new FileOutputStream("C:\\Users\\Shinelon\\IdeaProjects\\webmagic\\webmagic\\src\\test\\java\\etherscan-page1-1000model.xlsx")
        ) {
            //读入文件,每一行对应一个 Model ,获取Model 列表
            List<Object> objectList = EasyExcelUtil.readExcelWithModel(inputStream,ETUInfo.class,ExcelTypeEnum.XLS);
            List<ETUInfo> etuInfoList=(List)objectList;
            //定义Excel正文背景颜色
            TableStyle tableStyle=new TableStyle();
            tableStyle.setTableContentBackGroundColor(IndexedColors.WHITE);

            //定义Excel正文字体大小
            Font font=new Font();
            font.setFontHeightInPoints((short) 10);
            tableStyle.setTableContentFont(font);

            Table table=new Table(0);
            table.setTableStyle(tableStyle);

            EasyExcelUtil.writeExcelWithModel(outputStream, etuInfoList, ExcelModel.class, ExcelTypeEnum.XLSX);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
