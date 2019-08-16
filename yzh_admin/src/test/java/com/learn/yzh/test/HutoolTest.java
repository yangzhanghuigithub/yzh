package com.learn.yzh.test;

import cn.hutool.aop.ProxyUtil;
import cn.hutool.aop.aspects.TimeIntervalAspect;
import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.file.LFUFileCache;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.cache.impl.WeakCache;
import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.DynaBean;
import cn.hutool.core.codec.BCD;
import cn.hutool.core.collection.BoundedPriorityQueue;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.convert.Converter;
import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.date.*;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.watch.SimpleWatcher;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.io.watch.watchers.DelayWatcher;
import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.*;
import cn.hutool.core.swing.ClipboardUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.text.StrSpliter;
import cn.hutool.core.text.csv.*;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.*;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.digest.*;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.db.nosql.mongo.MongoFactory;
import cn.hutool.db.nosql.redis.RedisDS;
import cn.hutool.dfa.WordTree;
import cn.hutool.extra.ftp.Ftp;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.extra.ssh.Sftp;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.LogFactory;
import cn.hutool.log.StaticLog;
import cn.hutool.log.dialect.commons.ApacheCommonsLogFactory;
import cn.hutool.log.dialect.console.ConsoleLogFactory;
import cn.hutool.log.dialect.jdk.JdkLogFactory;
import cn.hutool.log.level.Level;
import cn.hutool.setting.dialect.Props;
import com.jcraft.jsch.Session;
import com.learn.yzh.entity.User;
import com.mongodb.client.MongoDatabase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import redis.clients.jedis.Jedis;

import javax.xml.xpath.XPathConstants;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.security.KeyPair;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
public class HutoolTest {

    private static final Logger log = LoggerFactory.getLogger(HutoolTest.class);

    @Test
    public void test() throws IOException {
        int a = 1;
        String aStr = Convert.toStr(a);

        long[] b = {1,2,3,4,5};
        String bStr = Convert.toStr(b);

        String c = "2017-05-06";
        Date d = Convert.toDate(c);

        Object[] e = {"a", "你", "好", "", 1};
        List<?> list = Convert.convert(List.class, e);
        List<?> list2 = Convert.toList(e);

        String f = "123456789";
        //结果为："１２３４５６７８９"
        String g = Convert.toSBC(f);
        //结果为"123456789"
        String dbc = Convert.toDBC(g);

        String h = "我是一个小小的可爱的字符串";
        //结果："e68891e698afe4b880e4b8aae5b08fe5b08fe79a84e58fafe788b1e79a84e5ad97e7aca6e4b8b2"
        String hex = Convert.toHex(h, CharsetUtil.CHARSET_UTF_8);
        //结果为："我是一个小小的可爱的字符串"
        String raw = Convert.hexStrToStr(hex, CharsetUtil.CHARSET_UTF_8);
        //注意：在4.1.11之后hexStrToStr将改名为hexToStr
        String raw2 = Convert.hexToStr(hex, CharsetUtil.CHARSET_UTF_8);

        //结果为："\\u6211\\u662f\\u4e00\\u4e2a\\u5c0f\\u5c0f\\u7684\\u53ef\\u7231\\u7684\\u5b57\\u7b26\\u4e32"
        String unicode = Convert.strToUnicode(h);
        //结果为："我是一个小小的可爱的字符串"
        String raw3 = Convert.unicodeToStr(unicode);

        String i = "我不是乱码";
        //转换后result为乱码
        String result = Convert.convertCharset(i, CharsetUtil.UTF_8, CharsetUtil.ISO_8859_1);
        String raw4 = Convert.convertCharset(result, CharsetUtil.ISO_8859_1, "UTF-8");
        Assert.assertEquals(raw, i);

        long t = 4535345;
        //结果为：75
        long minutes = Convert.convertTime(t, TimeUnit.MILLISECONDS, TimeUnit.MINUTES);

        double j = 67556.32;
        //结果为："陆万柒仟伍佰伍拾陆元叁角贰分"
        String digitUppercase = Convert.digitToChinese(j);

        //去包装
        Class<?> wrapClass = Integer.class;
        //结果为：int.class
        Class<?> unWraped = Convert.unWrap(wrapClass);
        //包装
        Class<?> primitiveClass = long.class;
        //结果为：Long.class
        Class<?> wraped = Convert.wrap(primitiveClass);

        int k = 3423;
        ConverterRegistry converterRegistry = ConverterRegistry.getInstance();
        String result1 = converterRegistry.convert(String.class, k);
        Assert.assertEquals("3423", result);

        ConverterRegistry converter = ConverterRegistry.getInstance();
        //此处做为示例自定义String转换，因为Hutool中已经提供String转换，请尽量不要替换
        //替换可能引发关联转换异常（例如覆盖String转换会影响全局）
        converter.putCustom(String.class, CustomConverter.class);

        //当前时间
        Date date = DateUtil.date();
        //当前时间
        Date date2 = DateUtil.date(Calendar.getInstance());
        //当前时间
        Date date3 = DateUtil.date(System.currentTimeMillis());
        //当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
        String now = DateUtil.now();
        //当前日期字符串，格式：yyyy-MM-dd
        String today= DateUtil.today();

        String dateStr = "2017-03-01";
        Date date4 = DateUtil.parse(dateStr);
        Date date5 = DateUtil.parse(dateStr, "yyyy-MM-dd");

        //结果 2017/03/01
        String format = DateUtil.format(date, "yyyy/MM/dd");
        //常用格式的格式化，结果：2017-03-01
        String formatDate = DateUtil.formatDate(date);
        //结果：2017-03-01 00:00:00
        String formatDateTime = DateUtil.formatDateTime(date);
        //结果：00:00:00
        String formatTime = DateUtil.formatTime(date);

        //获得年的部分
        DateUtil.year(date);
        //获得月份，从0开始计数
        DateUtil.month(date);
        //获得月份枚举
        DateUtil.monthEnum(date);

        String dateStr1 = "2017-03-01 22:33:23";
        Date date1 = DateUtil.parse(dateStr1);
        //一天的开始，结果：2017-03-01 00:00:00
        Date beginOfDay = DateUtil.beginOfDay(date1);
        //一天的结束，结果：2017-03-01 23:59:59
        Date endOfDay = DateUtil.endOfDay(date1);

        //结果：2017-03-03 22:33:23
        Date newDate = DateUtil.offset(date, DateField.DAY_OF_MONTH, 2);
        //常用偏移，结果：2017-03-04 22:33:23
        DateTime newDate2 = DateUtil.offsetDay(date, 3);
        //常用偏移，结果：2017-03-01 19:33:23
        DateTime newDate3 = DateUtil.offsetHour(date, -3);

        //昨天
        DateUtil.yesterday();
        //明天
        DateUtil.tomorrow();
        //上周
        DateUtil.lastWeek();
        //下周
        DateUtil.nextWeek();
        //上个月
        DateUtil.lastMonth();
        //下个月
        DateUtil.nextMonth();

        String dateStr2 = "2017-03-01 22:33:23";
        Date date7 = DateUtil.parse(dateStr2);

        String dateStr3 = "2017-04-01 23:33:23";
        Date date8 = DateUtil.parse(dateStr3);
        //相差一个月，31天
        long betweenDay = DateUtil.between(date1, date2, DateUnit.DAY);

        //Level.MINUTE表示精确到分
        long between = 232452345;
        String formatBetween = DateUtil.formatBetween(between, BetweenFormater.Level.MINUTE);
        //输出：31天1小时
        Console.log(formatBetween);

        TimeInterval timer = DateUtil.timer();
        //---------------------------------
        //-------这是执行过程
        //---------------------------------
        timer.interval();//花费毫秒数
        timer.intervalRestart();//返回花费时间，并重置开始时间
        timer.intervalMinute();//花费分钟数

        //年龄
        DateUtil.ageOfNow("1990-01-30");
        //是否闰年
        DateUtil.isLeapYear(2017);

        BufferedInputStream in = FileUtil.getInputStream("d:/test.txt");
        BufferedOutputStream out = FileUtil.getOutputStream("d:/test2.txt");
        long copySize = IoUtil.copy(in, out, IoUtil.DEFAULT_BUFFER_SIZE);

        File file = FileUtil.file("d:/test.jpg");
        String type = FileTypeUtil.getType(file);
        //输出 jpg则说明确实为jpg文件
        Console.log(type);

        FileTypeUtil.putFileType("ffd8ffe000104a464946", "new_jpg");


        //这里只监听文件或目录的修改事件
        WatchMonitor watchMonitor = WatchMonitor.create(file, WatchMonitor.ENTRY_MODIFY);
        watchMonitor.setWatcher(new Watcher(){
            @Override
            public void onCreate(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("创建：{}-> {}", currentPath, obj);
            }

            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("修改：{}-> {}", currentPath, obj);
            }

            @Override
            public void onDelete(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("删除：{}-> {}", currentPath, obj);
            }

            @Override
            public void onOverflow(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("Overflow：{}-> {}", currentPath, obj);
            }
        });

        //设置监听目录的最大深入，目录层级大于制定层级的变更将不被监听，默认只监听当前层级目录
        watchMonitor.setMaxDepth(3);
        //启动监听
        watchMonitor.start();

        WatchMonitor.createAll(file, new SimpleWatcher(){
            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                Console.log("EVENT modify");
            }
        }).start();

        Watcher watcher = null;
        WatchMonitor monitor = WatchMonitor.createAll("d:/", new DelayWatcher(watcher, 500));
        monitor.start();

        String path = "config.properties";
        InputStream inp = this.getClass().getResource(path).openStream();

        ClassPathResource resource = new ClassPathResource("test.properties");
        Properties properties = new Properties();
        properties.load(resource.getStream());
        Console.log("Properties: {}", properties);

        FileWriter writer = new FileWriter("test.properties");
        writer.write("test");
        writer.append("test");

        int[] qq = {};
        int[] ww = null;
        ArrayUtil.isEmpty(qq);
        ArrayUtil.isEmpty(ww);
        ArrayUtil.isNotEmpty(qq);

        String[] newArray = ArrayUtil.newArray(String.class, 3);

        Integer[] l = {1,2,3};
        Integer[] cloneB = ArrayUtil.clone(l);
        Assert.assertArrayEquals(l, cloneB);

        int[] m = {1,2,3};
        int[] clone = ArrayUtil.clone(m);
        Assert.assertArrayEquals(m, clone);

        Integer[] n = {1,2,3,4,5,6};
        Integer[] filter = ArrayUtil.filter(n, new Editor<Integer>(){
            @Override
            public Integer edit(Integer t) {
                return (t % 2 == 0) ? t : null;
            }});
        Assert.assertArrayEquals(filter, new Integer[]{2,4,6});

        String[] keys = {"a", "b", "c"};
        Integer[] values = {1,2,3};
        Map<String, Integer> map = ArrayUtil.zip(keys, values, true);
        //{a=1, b=2, c=3}

        String str = "我是一个字符串";
        String hexy = HexUtil.encodeHexStr(str, CharsetUtil.CHARSET_UTF_8);
        //hex是：e68891e698afe4b880e4b8aae5ad97e7aca6e4b8b2
        String decodedStr = HexUtil.decodeHexStr(hexy);
        //解码后与str相同

        String ID_18 = "321083197812162119";
        String ID_15 = "150102880730303";
        //是否有效
        boolean valid = IdcardUtil.isValidCard(ID_18);
        boolean valid15 = IdcardUtil.isValidCard(ID_15);
        //转换
        String convert15To18 = IdcardUtil.convert15To18(ID_15);
        Assert.assertEquals(convert15To18, "150102198807303035");
        //年龄
        DateTime date9 = DateUtil.parse("2017-04-10");
        int age = IdcardUtil.getAgeByIdCard(ID_18, date9);
        Assert.assertEquals(age, 38);
        int age2 = IdcardUtil.getAgeByIdCard(ID_15, date9);
        Assert.assertEquals(age2, 28);
        //生日
        String birth = IdcardUtil.getBirthByIdCard(ID_18);
        Assert.assertEquals(birth, "19781216");
        String birth2 = IdcardUtil.getBirthByIdCard(ID_15);
        Assert.assertEquals(birth2, "19880730");
        //省份
        String province = IdcardUtil.getProvinceByIdCard(ID_18);
        Assert.assertEquals(province, "江苏");
        String province2 = IdcardUtil.getProvinceByIdCard(ID_15);
        Assert.assertEquals(province2, "内蒙古");

        ImageUtil.scale(null,5);
        ImageUtil.cut(null,2,3);
        ImageUtil.convert(null,null);
        ImageUtil.gray (null);

        double te1=123456.123456;
        double te2=123456.128456;
        Console.log(NumberUtil.round(te1,4));//结果:123456.12
        Console.log(NumberUtil.round(te2,4));//结果:123456.13
        Console.log(NumberUtil.roundStr(te1,2));//结果:123456.12
        Console.log(NumberUtil.roundStr(te2,2));//结果:123456.13


        //  0 -> 取一位整数
        //  0.00 -> 取一位整数和两位小数
        //  00.000 -> 取两位整数和三位小数
        //  # -> 取所有整数部分
        //  #.##% -> 以百分比方式计数，并取两位小数
        //  #.#####E0 -> 显示为科学计数法，并取五位小数
        //  ,### -> 每三位以逗号进行分隔，例如：299,792,458
        //  光速大小为每秒,###米 -> 将格式嵌入文本

        long o=299792458;//光速
        String form = NumberUtil.decimalFormat(",###", o);//299,792,458

        //  NumberUtil.isNumber 是否为数字
        //  NumberUtil.isInteger 是否为整数
        //  NumberUtil.isDouble 是否为浮点数
        //  NumberUtil.isPrimes 是否为质数
        //  NumberUtil.range 方法根据范围和步进，生成一个有序整数列表。
        //  NumberUtil.appendRange 将给定范围内的整数添加到已有集合中
        //  NumberUtil.factorial 阶乘
        //  NumberUtil.sqrt 平方根
        //  NumberUtil.divisor 最大公约数
        //  NumberUtil.multiple 最小公倍数
        //  NumberUtil.getBinaryStr 获得数字对应的二进制字符串
        //  NumberUtil.binaryToInt 二进制转int
        //  NumberUtil.binaryToLong 二进制转long
        //  NumberUtil.compare 比较两个值的大小
        //  NumberUtil.toStr 数字转字符串，自动并去除尾小数点儿后多余的0

        String ip= "127.0.0.1";
        long iplong = 2130706433L;
        //根据long值获取ip v4地址
        String ip2= NetUtil.longToIpv4(iplong);
        //根据ip地址计算出long型的数据
        long ip3= NetUtil.ipv4ToLong(ip);
        //检测本地端口可用性
        boolean result2= NetUtil.isUsableLocalPort(6379);
        //是否为有效的端口
        boolean result3= NetUtil.isValidPort(6379);
        //隐藏掉IP地址
        String result4 =NetUtil.hideIpPart(ip);

        int[] startEnd1 = PageUtil.transToStartEnd(1, 10);//[0, 10]
        int[] startEnd2 = PageUtil.transToStartEnd(2, 10);//[10, 20]
        int totalPage = PageUtil.totalPage(20, 3);//7

        //参数意义分别为：当前页、总页数、每屏展示的页数
        int[] rainbow = PageUtil.rainbow(5, 20, 6);
        //结果：[3, 4, 5, 6, 7, 8]


        //  RandomUtil.randomInt 获得指定范围内的随机数
        //  RandomUtil.randomBytes 随机bytes
        //  RandomUtil.randomEle 随机获得列表中的元素
        //  RandomUtil.randomEleSet 随机获得列表中的一定量的不重复元素，返回Set
        //  RandomUtil.randomString 获得一个随机的字符串（只包含数字和字符）
        //  RandomUtil.randomNumbers 获得一个只包含数字的字符串
        //  RandomUtil.randomUUID 随机UUID
        //  RandomUtil.weightRandom 权重随机生成器，传入带权重的对象，然后根据权重随机获取对象

        ObjectUtil.equal(1,2);
        ObjectUtil.isBasicType(1);

        StrUtil.hasBlank("","");
        StrUtil.hasEmpty("","");
        String fileName = StrUtil.removeSuffix("pretty_girl.jpg", ".jpg");
        String str1 = "abcdefgh";
        String strSub1 = StrUtil.sub(str1, 2, 3); //strSub1 -> c
        String strSub2 = StrUtil.sub(str1, 2, -3); //strSub2 -> cde
        String strSub3 = StrUtil.sub(str1, 3, 2); //strSub2 -> c
        String template = "{}爱{}，就像老鼠爱大米";
        String str2 = StrUtil.format(template, "我", "你"); //str -> 我爱你，就像老鼠爱大米

        String resultExtractMulti = ReUtil.extractMulti("(\\w)aa(\\w)", template, "$1-$2");
        Assert.assertEquals("Z-a", resultExtractMulti);

        String resultDelFirst = ReUtil.delFirst("(\\w)aa(\\w)", template);
        Assert.assertEquals("ZZbbbccc中文1234", resultDelFirst);

        List<String> resultFindAll = ReUtil.findAll("\\w{2}", template, 0, new ArrayList<String>());
        ArrayList<String> expected = CollectionUtil.newArrayList("ZZ", "Za", "aa", "bb", "bc", "cc", "12", "34");
        Assert.assertEquals(expected, resultFindAll);

        String escape = ReUtil.escape("我有个$符号{}");
        Assert.assertEquals("我有个\\$符号\\{\\}", escape);

        Document docResult=XmlUtil.readXML("");
        //结果为“ok”
        Object value = XmlUtil.getByXPath("//returnsms/message", docResult, XPathConstants.STRING);

        ZipUtil.zip("d:/aaa");
        //将aaa目录下的所有文件目录打包到d:/bbb/目录下的aaa.zip文件中
        ZipUtil.zip("d:/aaa", "d:/bbb/");
        //将aaa目录下的所有文件目录打包到d:/bbb/目录下的ccc.zip文件中
        ZipUtil.zip("d:/aaa", "d:/bbb/ccc.zip");
        //将aaa目录以及其目录下的所有文件目录打包到d:/bbb/目录下的ccc.zip文件中
        ZipUtil.zip("d:/aaa", "d:/bbb/ccc.zip", true);
        ZipUtil.zip(FileUtil.file("d:/bbb/ccc.zip"), false,
            FileUtil.file("d:/test1/file1.txt"),
            FileUtil.file("d:/test1/file2.txt"),
            FileUtil.file("d:/test2/file1.txt"),
            FileUtil.file("d:/test2/file2.txt")
        );
        //将test.zip解压到e:\\aaa目录下，返回解压到的目录
        File unzip = ZipUtil.unzip("E:\\aaa\\test.zip", "e:\\aaa");

        Method[] methods = ReflectUtil.getMethods(CustomConverter.class);
        Method method = ReflectUtil.getMethod(CustomConverter.class, "getId");
        ReflectUtil.newInstance(CustomConverter.class);
        CustomConverter rr =new CustomConverter();
        ReflectUtil.invoke(rr, "setA", 10);

        String str3 = RuntimeUtil.execForStr("ipconfig");

        Clipboard clipboard = ClipboardUtil.getClipboard();
        Transferable dd = null;
        ClipboardUtil.set(dd);
        DataFlavor ee = null;
        ClipboardUtil.get(ee);
        ClipboardUtil.setStr("");
        ClipboardUtil.getStr();
        ClipboardUtil.setImage(new BufferedImage(1,1,1));
        ClipboardUtil.getImage();

        List<String> names = EnumUtil.getNames(TestEnum.class);
        //结果：[TEST1, TEST2, TEST3]
        List<Object> types = EnumUtil.getFieldValues(TestEnum.class, "type");
        //结果：[type1, type2, type3]
        Map<String,TestEnum> enumMap = EnumUtil.getEnumMap(TestEnum.class);
        enumMap.get("TEST1"); // 结果为：TestEnum.TEST1
        Map<String, Object> enumMap2 = EnumUtil.getNameFieldMap(TestEnum.class, "type");
        enumMap2.get("TEST1"); // 结果为：type1

        Method method2 = ReflectUtil.getMethod(TestClass.class, "intTest", Integer.class);
        Type type2 = TypeUtil.getParamType(method2, 0);
        // 结果：Integer.class

        Method method3 = ReflectUtil.getMethod(TestClass.class, "getList");
        Type type3 = TypeUtil.getReturnType(method3);
        // 结果：java.util.List<java.lang.String>
        Type type4 = TypeUtil.getTypeArgument(type3);
        // 结果：String.class

        //生成的UUID是带-的字符串，类似于：a5c8a5e8-df2b-4706-bea4-08d0939410e3
        String uuid = IdUtil.randomUUID();
        //生成的是不带-的字符串，类似于：b17f24ff026d40949c85a24f4f375d42
        String simpleUUID = IdUtil.simpleUUID();
        //生成类似：5b9e306a4df4f8c54a39fb0c
        String id = ObjectId.next();
        //方法2：从Hutool-4.1.14开始提供
        String id2 = IdUtil.objectId();
        //参数1为终端ID
        //参数2为数据中心ID
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        long id3 = snowflake.nextId();

        boolean isEmail = Validator.isEmail("loolly@gmail.com");
        Validator.isMactchRegex("需要验证字段的正则表达式", "被验证内容");
        Validator.validateChinese("我是一段zhongwen", "内容中包含非中文");

        String[] p = {"abc", "bcd", "def"};
        Console.log(p);//控制台输出：[abc, bcd, def]

        Console.log("This is Console log for {}.", "test");
        //控制台输出：This is Console log for test.

        String strForTest = "123456ABCDEF";
        //转BCD
        byte[] bcd = BCD.strToBcd(strForTest);
        //解码BCD
        String str5 = BCD.bcdToStr(bcd);
        Assert.assertEquals(strForTest, str5);

        Dict dict = Dict.create()
                .set("key1", 1)//int
                .set("key2", 1000L)//long
                .set("key3", DateTime.now());//Date
        Long v2 = dict.getLong("key2");//1000

        //通常使用
        String result5 = StrFormatter.format("this is {} for {}", "a", "b");
        Assert.assertEquals("this is a for b", result5);
        //转义{}
        String result6 = StrFormatter.format("this is \\{} for {}", "a", "b");
        Assert.assertEquals("this is {} for a", result6);
        //转义\
        String result7 = StrFormatter.format("this is \\\\{} for {}", "a", "b");
        Assert.assertEquals("this is \\a for b", result7);

        String str6 = "a, ,efedsfs,   ddf";
        //参数：被切分字符串，分隔符逗号，0表示无限制分片数，去除两边空格，忽略空白项
        List<String> split = StrSpliter.split(str6, ',', 0, true, true);
        //  splitPath 切分字符串，分隔符为"/"
        //  splitPathToArray 切分字符串，分隔符为"/"，返回数组

        String q = null;
        Assert.assertNull("");
        //  isTrue 是否True
        //  isNull 是否是null值，不为null抛出异常
        //  notNull 是否非null值
        //  notEmpty 是否非空
        //  notBlank 是否非空白符
        //  notContain 是否为子串
        //  notEmpty 是否非空
        //  noNullElements 数组中是否包含null元素
        //  isInstanceOf 是否类实例
        //  isAssignable 是子类和父类关系
        //  state 会抛出IllegalStateException异常

        boolean isBean = BeanUtil.isBean(HashMap.class);//false
        Class<CustomConverter> cust = BeanUtil.fillBeanWithMap(new HashMap<String, Object>(), CustomConverter.class, true);
        Class<CustomConverter> cust2 = BeanUtil.fillBeanWithMapIgnoreCase(new HashMap<String, Object>(), CustomConverter.class, true);
        CustomConverter cust3 = BeanUtil.mapToBean(new HashMap<String, Object>(), CustomConverter.class, true);
        CustomConverter cust4 = BeanUtil.mapToBeanIgnoreCase(new HashMap<String, Object>(), CustomConverter.class, true);
        Map<String, Object> map1 = BeanUtil.beanToMap(new CustomConverter());
        BeanUtil.copyProperties("","",true);

        User user = new User();
        DynaBean bean = DynaBean.create(user);
        //我们也可以通过反射构造对象
        DynaBean bean2 = DynaBean.create(User.class);
        bean.set("name", "李华");
        bean.set("age", 12);
        String name = bean.get("name");//输出“李华”
        //执行指定方法
        Object invoke = bean2.invoke("testMethod");
        Assert.assertEquals("test for 李华", invoke);

        String[] col= new String[]{"a","b","c","d","e"};
        List<String> colList = CollUtil.newArrayList(col);
        String str7 = CollUtil.join(colList, "#"); //str -> a#b#c#d#e

        //Integer比较器
        Comparator<Integer> comparator = new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };

        //新建三个列表，CollUtil.newArrayList方法表示新建ArrayList并填充元素
        List<Integer> list1 = CollUtil.newArrayList(1, 2, 3);
        List<Integer> list5 = CollUtil.newArrayList(4, 5, 6);
        List<Integer> list3 = CollUtil.newArrayList(7, 8, 9);

        //参数表示把list1,list2,list3合并并按照从小到大排序后，取0~2个（包括第0个，不包括第2个），结果是[1,2]
        @SuppressWarnings("unchecked")
        List<Integer> result8 = CollUtil.sortPageAll(0, 2, comparator, list1, list5, list3);
        System.out.println(result);     //输出 [1,2]

        HashMap<String, String> map2 = CollUtil.newHashMap();
        HashSet<String> set2 = CollUtil.newHashSet();
        ArrayList<String> list4 = CollUtil.newArrayList();

        //  int[] a1 = CollUtil.range(6);       //[0,1,2,3,4,5]
        //  int[] a2 = CollUtil.range(4, 7);    //[4,5,6]
        //  int[] a3 = CollUtil.range(4, 9, 2); //[4,6,8]

        List<?> objects = list4.subList(1, 3);
        List<String> sub = CollUtil.sub(list4, 1, 2);

        String[] keys2 = new String[]{"a", "b", "c"};
        Integer[] values2 = new Integer[]{1, 2, 3};
        Map<String, Integer> map3 = CollUtil.zip(Arrays.asList(keys2), Arrays.asList(values));
        System.out.println(map3);    // {b=2, c=3, a=1}

        String tt = "a,b,c";
        String yy = "1,2,3";
        Map<String, String> map4 = CollUtil.zip(tt,yy, ",");
        System.out.println(map4);   // {b=2, c=3, a=1}

        //  String r = "伦家是一个非常长的字符串";
        //  String encode = Base64.getEncoder();
        //  Assert.assertEquals("5Lym5a625piv5LiA5Liq6Z2e5bi46ZW/55qE5a2X56ym5Liy", encode);
        //  String decodeStr = Base64.decodeStr(encode);
        //  Assert.assertEquals(a, decodeStr);
        //  String a = "伦家是一个非常长的字符串";
        //  String encode = Base32.encode(a);
        //  Assert.assertEquals("4S6KNZNOW3TJRL7EXCAOJOFK5GOZ5ZNYXDUZLP7HTKCOLLMX46WKNZFYWI", encode);
        //  String decodeStr = Base32.decodeStr(encode);
        //  Assert.assertEquals(a, decodeStr);

        CsvReader reader = CsvUtil.getReader();
        //从文件中读取CSV数据
        CsvData data = reader.read(FileUtil.file("test.csv"));
        List<CsvRow> rows = data.getRows();
        //遍历行
        for (CsvRow csvRow : data) {
            //getRawList返回一个List列表，列表的每一项为CSV中的一个单元格（既逗号分隔部分）
            Console.log(csvRow.getRawList());
        }

        //指定路径和编码
        CsvWriter write = CsvUtil.getWriter("e:/testWrite.csv", CharsetUtil.CHARSET_UTF_8);
        //按行写出
        write.write(
                new String[] {"a1", "b1", "c1"},
                new String[] {"a2", "b2", "c2"},
                new String[] {"a3", "b3", "c3"}
        );

        StrBuilder builder = StrBuilder.create();
        builder.append("aaa").append("你好").append('r');
        //结果：aaa你好r

        AnnotatedElement sr = null;
        AnnotationUtil.getAnnotations(sr,true);


        //  ReverseComparator 反转比较器，排序时提供反序
        //  VersionComparator 版本比较器，支持如：1.3.20.8，6.82.20160101，8.5a/8.5c等版本形式
        //  PropertyComparator Bean属性比较器，通过Bean的某个属性来对Bean对象进行排序
        //  IndexedComparator 按照数组的顺序正序排列，数组的元素位置决定了对象的排序先后
        //  ComparatorChain 比较器链。此链包装了多个比较器，最终比较结果按照比较器顺序综合多个比较器结果。
        //  PinyinComparator 按照GBK拼音顺序对给定的汉字字符串排序。

        //  ExceptionUtil
        //  getMessage 获得完整消息，包括异常名
        //  wrap 包装一个异常为指定类型异常
        //  wrapRuntime 使用运行时异常包装编译异常
        //  getCausedBy 获取由指定异常类引起的异常
        //  isCausedBy 判断是否由指定异常类引起
        //  stacktraceToString 堆栈转为完整字符串

        //  DependencyException 依赖异常
        //  StatefulException 带有状态码的异常
        //  UtilException 工具类异常
        //  NotInitedException 未初始化异常
        //  ValidateException 验证异常

        //  MathUtil
        //  排列
        //  arrangementCount 计算排列数
        //  arrangementSelect 排列选择（从列表中选择n个排列）
        //  组合
        //  combinationCount 计算组合数，即C(n, m) = n!/((n-m)! * m!)
        //  combinationSelect 组合选择（从列表中选择n个组合）

        //  ThreadUtil
        //  ThreadUtil.execute
        //  直接在公共线程池中执行线程
        //  ThreadUtil.newExecutor
        //  获得一个新的线程池
        //  ThreadUtil.excAsync
        //  执行异步方法
        //  ThreadUtil.newCompletionService
        //  创建CompletionService，调用其submit方法可以异步执行多个任务，最后调用take方法按照完成的顺序获得其结果。若未完成，则会阻塞。
        //  ThreadUtil.newCountDownLatch
        //  新建一个CountDownLatch，一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
        //  ThreadUtil.sleep
        //  挂起当前线程，是Thread.sleep的封装，通过返回boolean值表示是否被打断，而不是抛出异常。
        //  ThreadUtil.getStackTrace
        //  此部分包括两个方法：
        //  getStackTrace 获得堆栈列表
        //  getStackTraceElement 获得堆栈项

        properties = new Properties();
        try {
            Class clazz = User.class;
            InputStream inputestream = clazz.getResourceAsStream("db.properties");
            properties.load(inputestream);
        }catch (IOException e2) {
            e2.printStackTrace();
        }
        Props props = new Props("db.properties");
        String user2 = props.getProperty("user");
        String driver = props.getStr("driver");

        log.info("我在XXX 改了 {} 变量", "name");
        log.error("错误消息", e);

        log.debug("This is {} log", Level.DEBUG);
        log.info("This is {} log", Level.INFO);
        log.warn("This is {} log", Level.WARN);

        // 自动选择日志实现
        log.debug("This is {} log", "default");
        Console.log("----------------------------------------------------------------------");
        //自定义日志实现为Apache Commons Logging
        LogFactory.setCurrentLogFactory(new ApacheCommonsLogFactory());
        log.debug("This is {} log", "custom apache commons logging");
        Console.log("----------------------------------------------------------------------");
        //自定义日志实现为JDK Logging
        LogFactory.setCurrentLogFactory(new JdkLogFactory());
        log.info("This is {} log", "custom jdk logging");
        Console.log("----------------------------------------------------------------------");
        //自定义日志实现为Console Logging
        LogFactory.setCurrentLogFactory(new ConsoleLogFactory());
        log.info("This is {} log", "custom Console");
        Console.log("----------------------------------------------------------------------");
        StaticLog.info("This is static {} log.", "INFO");

        //新建FIFOCache
        Cache<String,String> fifoCache = CacheUtil.newFIFOCache(3);
        //加入元素，每个元素可以设置其过期时长，DateUnit.SECOND.getMillis()代表每秒对应的毫秒数，在此为3秒
        fifoCache.put("key1", "value1", DateUnit.SECOND.getMillis() * 3);
        fifoCache.put("key2", "value2", DateUnit.SECOND.getMillis() * 3);
        fifoCache.put("key3", "value3", DateUnit.SECOND.getMillis() * 3);
        //由于缓存容量只有3，当加入第四个元素的时候，根据FIFO规则，最先放入的对象将被移除
        fifoCache.put("key4", "value4", DateUnit.SECOND.getMillis() * 3);
        //value1为null
        String value1 = fifoCache.get("key1");

        Cache<String, String> lfuCache = CacheUtil.newLFUCache(3);
        //通过实例化对象创建
        //LFUCache<String, String> lfuCache = new LFUCache<String, String>(3);
        lfuCache.put("key1", "value1", DateUnit.SECOND.getMillis() * 3);
        lfuCache.get("key1");//使用次数+1
        lfuCache.put("key2", "value2", DateUnit.SECOND.getMillis() * 3);
        lfuCache.put("key3", "value3", DateUnit.SECOND.getMillis() * 3);
        lfuCache.put("key4", "value4", DateUnit.SECOND.getMillis() * 3);
        //由于缓存容量只有3，当加入第四个元素的时候，根据LRU规则，最少使用的将被移除（2,3被移除）
        String value2 = lfuCache.get("key2");//null
        String value3 = lfuCache.get("key3");//null

        Cache<String, String> lruCache = CacheUtil.newLRUCache(3);
        //通过实例化对象创建
        //LRUCache<String, String> lruCache = new LRUCache<String, String>(3);
        lruCache.put("key1", "value1", DateUnit.SECOND.getMillis() * 3);
        lruCache.put("key2", "value2", DateUnit.SECOND.getMillis() * 3);
        lruCache.put("key3", "value3", DateUnit.SECOND.getMillis() * 3);
        lruCache.get("key1");//使用时间推近
        lruCache.put("key4", "value4", DateUnit.SECOND.getMillis() * 3);
        //由于缓存容量只有3，当加入第四个元素的时候，根据LRU规则，最少使用的将被移除（2被移除）
        String value4 = lruCache.get("key");//null

        //创建缓存，默认4毫秒过期
        TimedCache<String, String> timedCache = CacheUtil.newTimedCache(4);
        //实例化创建
        //TimedCache<String, String> timedCache = new TimedCache<String, String>(4);
        timedCache.put("key1", "value1", 1);//1毫秒过期
        timedCache.put("key2", "value2", DateUnit.SECOND.getMillis() * 5);
        timedCache.put("key3", "value3");//默认过期(4毫秒)
        //启动定时任务，每5毫秒秒检查一次过期
        timedCache.schedulePrune(5);
        //等待5毫秒
        ThreadUtil.sleep(5);
        //5毫秒后由于value2设置了5毫秒过期，因此只有value2被保留下来
        String value7 = timedCache.get("key1");//null
        String value5 = timedCache.get("key2");//value2
        //5毫秒后，由于设置了默认过期，key3只被保留4毫秒，因此为null
        String value6 = timedCache.get("key3");//null
        //取消定时清理
        timedCache.cancelPruneSchedule();

        WeakCache<String, String> weakCache = CacheUtil.newWeakCache(DateUnit.SECOND.getMillis() * 3);

        //参数1：容量，能容纳的byte数
        //参数2：最大文件大小，byte数，决定能缓存至少多少文件，大于这个值不被缓存直接读取
        //参数3：超时。毫秒
        LFUFileCache cache = new LFUFileCache(1000, 500, 2000);
        byte[] bytes = cache.getFileBytes("d:/a.jpg");

        JSONObject json1 = JSONUtil.createObj();
        json1.put("a", "value1");
        json1.put("b", "value2");
        json1.put("c", "value3");
        json1.getStr("key");
        json1.getInt("key");
        json1.getLong("key");
        json1.getDouble("key");
        json1.getBigDecimal("key");

        String jsonStr = "{\"b\":\"value2\",\"c\":\"value3\",\"a\":\"value1\"}";
        //方法一：使用工具类转换
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        //方法二：new的方式转换
        JSONObject jsonObject2 = new JSONObject(jsonStr);
        //JSON对象转字符串
        jsonObject.toString();

        //方法1
        JSONArray array = JSONUtil.createArray();
        //方法2
        JSONArray array2 = new JSONArray();
        array.add("value1");
        array.add("value2");
        array.add("value3");
        //转为JSONArray字符串
        array.toString();
        String jsonStr2 = "[\"value1\", \"value2\", \"value3\"]";
        JSONArray array3 = JSONUtil.parseArray(jsonStr);

        String content = "test中文";
        //随机生成密钥
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
        //加密
        byte[] encrypt = aes.encrypt(content);
        //解密
        byte[] decrypt = aes.decrypt(encrypt);
        //加密为16进制表示
        String encryptHex = aes.encryptHex(content);
        //解密为字符串
        String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);

        byte[] key2 = SecureUtil.generateKey(SymmetricAlgorithm.DESede.getValue()).getEncoded();
        SymmetricCrypto des = new SymmetricCrypto(SymmetricAlgorithm.DESede, key);
        //加密
        byte[] encrypt2 = des.encrypt(content);
        //解密
        byte[] decrypt2 = des.decrypt(encrypt);
        //加密为16进制字符串（Hex表示）
        String encryptHex2 = des.encryptHex(content);
        //解密为字符串
        String decryptStr2 = des.decryptStr(encryptHex);

        // 随机生成密钥
        byte[] key3 = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        // 构建
        AES aes3 = SecureUtil.aes(key);
        // 加密
        byte[] encrypt3 = aes.encrypt(content);
        // 解密
        byte[] decrypt3 = aes.decrypt(encrypt);
        // 加密为16进制表示
        String encryptHex3 = aes.encryptHex(content);
        // 解密为字符串
        String decryptStr3 = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);

        AES aes11 = new AES(Mode.CTS, Padding.PKCS5Padding, "0CoJUm6Qyw8W8jud".getBytes(), "0102030405060708".getBytes());

        RSA rsa = new RSA();
        //获得私钥
        rsa.getPrivateKey();
        rsa.getPrivateKeyBase64();
        //获得公钥
        rsa.getPublicKey();
        rsa.getPublicKeyBase64();
        //公钥加密，私钥解密
        byte[] encrypt4 = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        byte[] decrypt4 = rsa.decrypt(encrypt, KeyType.PrivateKey);
        //Junit单元测试
        //Assert.assertEquals("我是一段测试aaaa", StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));
        //私钥加密，公钥解密
        byte[] encrypt5 = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
        byte[] decrypt5 = rsa.decrypt(encrypt2, KeyType.PublicKey);
        //Junit单元测试
        //Assert.assertEquals("我是一段测试aaaa", StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8));

        KeyPair pair = SecureUtil.generateKeyPair("RSA");
        pair.getPrivate();
        pair.getPublic();

        String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIL7pbQ+5KKGYRhw7jE31hmA"
                + "f8Q60ybd+xZuRmuO5kOFBRqXGxKTQ9TfQI+aMW+0lw/kibKzaD/EKV91107xE384qOy6IcuBfaR5lv39OcoqNZ"
                + "5l+Dah5ABGnVkBP9fKOFhPgghBknTRo0/rZFGI6Q1UHXb+4atP++LNFlDymJcPAgMBAAECgYBammGb1alndta"
                + "xBmTtLLdveoBmp14p04D8mhkiC33iFKBcLUvvxGg2Vpuc+cbagyu/NZG+R/WDrlgEDUp6861M5BeFN0L9O4hz"
                + "GAEn8xyTE96f8sh4VlRmBOvVdwZqRO+ilkOM96+KL88A9RKdp8V2tna7TM6oI3LHDyf/JBoXaQJBAMcVN7fKlYP"
                + "Skzfh/yZzW2fmC0ZNg/qaW8Oa/wfDxlWjgnS0p/EKWZ8BxjR/d199L3i/KMaGdfpaWbYZLvYENqUCQQCobjsuCW"
                + "nlZhcWajjzpsSuy8/bICVEpUax1fUZ58Mq69CQXfaZemD9Ar4omzuEAAs2/uee3kt3AvCBaeq05NyjAkBme8SwB0iK"
                + "kLcaeGuJlq7CQIkjSrobIqUEf+CzVZPe+AorG+isS+Cw2w/2bHu+G0p5xSYvdH59P0+ZT0N+f9LFAkA6v3Ae56OrI"
                + "wfMhrJksfeKbIaMjNLS9b8JynIaXg9iCiyOHmgkMl5gAbPoH/ULXqSKwzBw5mJ2GW1gBlyaSfV3AkA/RJC+adIjsRGg"
                + "JOkiRjSmPpGv3FOhl9fsBPjupZBEIuoMWOC8GXK/73DHxwmfNmN7C9+sIi4RBcjEeQ5F5FHZ";

        RSA rsa6 = new RSA(PRIVATE_KEY, null);

        String s = "2707F9FD4288CEF302C972058712F24A5F3EC62C5A14AD2FC59DAB93503AA0FA17113A020EE4EA35EB53F"
                + "75F36564BA1DABAA20F3B90FD39315C30E68FE8A1803B36C29029B23EB612C06ACF3A34BE815074F5EB5AA3A"
                + "C0C8832EC42DA725B4E1C38EF4EA1B85904F8B10B2D62EA782B813229F9090E6F7394E42E6F44494BB8";

        byte[] aByte = HexUtil.decodeHex(s);
        byte[] decrypt6 = rsa.decrypt(aByte, KeyType.PrivateKey);
        //Junit单元测试
        //Assert.assertEquals("虎头闯杭州,多抬头看天,切勿只管种地", StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));

        byte[] data2 = "我是一段测试字符串".getBytes();
        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA);
        //签名
        byte[] signed = sign.sign(data2);
        //验证签名
        boolean verify = sign.verify(data2, signed);

        Digester md5 = new Digester(DigestAlgorithm.MD5);
        String digestHex = md5.digestHex(content);
        //Junit单元测试
        //Assert.assertEquals("5393554e94bf0eb6436f240a4fd71282", digestHex);

        String md5Hex1 = DigestUtil.md5Hex(content);
        //Junit单元测试
        //Assert.assertEquals("5393554e94bf0eb6436f240a4fd71282", md5Hex1);

        byte[] key4 = "password".getBytes();
        HMac mac = new HMac(HmacAlgorithm.HmacMD5, key);
        String macHex1 = mac.digestHex(content);
        //Junit单元测试
        //Assert.assertEquals("b977f4b13f93f549e06140971bded384", macHex1);

        WordTree tree = new WordTree();
        tree.addWord("大");
        tree.addWord("大土豆");
        tree.addWord("土豆");
        tree.addWord("刚出锅");
        tree.addWord("出锅");
        //正文
        String text = "我有一颗大土豆，刚出锅的";
        // 匹配到【大】，就不再继续匹配了，因此【大土豆】不匹配
        // 匹配到【刚出锅】，就跳过这三个字了，因此【出锅】不匹配（由于刚首先被匹配，因此长的被匹配，最短匹配只针对第一个字相同选最短）
        List<String> matchAll = tree.matchAll(text, -1, false, false);
        Assert.assertEquals(matchAll.toString(), "[大, 土豆, 刚出锅]");
        // 【大】被匹配，最短匹配原则【大土豆】被跳过，【土豆继续被匹配】
        // 【刚出锅】被匹配，由于不跳过已经匹配的词，【出锅】被匹配
        matchAll = tree.matchAll(text, -1, true, false);
        Assert.assertEquals(matchAll.toString(), "[大, 土豆, 刚出锅, 出锅]");
        // 匹配到【大】，由于到最长匹配，因此【大土豆】接着被匹配
        // 由于【大土豆】被匹配，【土豆】被跳过，由于【刚出锅】被匹配，【出锅】被跳过
        matchAll = tree.matchAll(text, -1, false, true);
        Assert.assertEquals(matchAll.toString(), "[大, 大土豆, 刚出锅]");
        // 匹配到【大】，由于到最长匹配，因此【大土豆】接着被匹配，由于不跳过已经匹配的关键词，土豆继续被匹配
        // 【刚出锅】被匹配，由于不跳过已经匹配的词，【出锅】被匹配
        matchAll = tree.matchAll(text, -1, true, true);
        Assert.assertEquals(matchAll.toString(), "[大, 大土豆, 土豆, 刚出锅, 出锅]");

        //        #-------------------------------------------------------------------------------
        //# Redis客户端配置样例
        //# 每一个分组代表一个Redis实例
        //# 无分组的Pool配置为所有分组的共用配置，如果分组自己定义Pool配置，则覆盖共用配置
        //# 池配置来自于：https://www.cnblogs.com/jklk/p/7095067.html
        //#-------------------------------------------------------------------------------
        //
        //#----- 默认（公有）配置
        //# 地址，默认localhost
        //                host = localhost
        //# 端口，默认6379
        //                port = 6379
        //# 超时，默认2000
        //                timeout = 2000
        //# 连接超时，默认timeout
        //                connectionTimeout = 2000
        //# 读取超时，默认timeout
        //                soTimeout = 2000
        //# 密码，默认无
        //                password =
        //# 数据库序号，默认0
        //                database = 0
        //# 客户端名，默认"Hutool"
        //        clientName = Hutool
        //# SSL连接，默认false
        //                ssl = false;
        //
        //#----- 自定义分组的连接
        //                [custom]
        //# 地址，默认localhost
        //                host = localhost
        //# 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        //        BlockWhenExhausted = true;
        //# 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
        //        evictionPolicyClassName = org.apache.commons.pool2.impl.DefaultEvictionPolicy
        //# 是否启用pool的jmx管理功能, 默认true
        //        jmxEnabled = true;
        //# 是否启用后进先出, 默认true
        //        lifo = true;
        //# 最大空闲连接数, 默认8个
        //        maxIdle = 8
        //# 最小空闲连接数, 默认0
        //        minIdle = 0
        //# 最大连接数, 默认8个
        //        maxTotal = 8
        //# 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        //        maxWaitMillis = -1
        //# 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        //        minEvictableIdleTimeMillis = 1800000
        //# 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        //        numTestsPerEvictionRun = 3;
        //# 对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
        //                SoftMinEvictableIdleTimeMillis = 1800000
        //# 在获取连接的时候检查有效性, 默认false
        //        testOnBorrow = false
        //# 在空闲时检查有效性, 默认false
        //        testWhileIdle = false
        //# 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        //        timeBetweenEvictionRunsMillis = -1

        Jedis jedis = RedisDS.create().getJedis();
        //master slave 组成主从集群
        MongoDatabase db = MongoFactory.getDS("master", "slave").getDb("test");

        //GET请求
        String content2 = HttpUtil.get("");
        //POST请求
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("city", "北京");
        String result9 = HttpUtil.post("", paramMap);

        // 最简单的HTTP请求，可以自动通过header等信息判断编码，不区分HTTP和HTTPS
        String result10 = HttpUtil.get("https://www.baidu.com");
        // 当无法识别页面编码的时候，可以自定义请求页面的编码
        String result11 = HttpUtil.get("https://www.baidu.com",10);
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap1 = new HashMap<>();
        paramMap.put("city", "北京");
        String result12 = HttpUtil.get("https://www.baidu.com", paramMap);

        String result13= HttpUtil.post("https://www.baidu.com", paramMap);
        //文件上传只需将参数中的键指定（默认file），值设为文件对象即可，对于使用者来说，文件上传与普通表单提交并无区别
        paramMap.put("file", FileUtil.file("D:\\face.jpg"));
        String result14= HttpUtil.post("https://www.baidu.com", paramMap);

        String fileUrl = "http://mirrors.sohu.com/centos/7.3.1611/isos/x86_64/CentOS-7-x86_64-DVD-1611.iso";
        //将文件下载后保存在E盘，返回结果为下载文件大小
        long size = HttpUtil.downloadFile(fileUrl, FileUtil.file("e:/"));
        System.out.println("Download size: " + size);

        //带进度显示的文件下载
        HttpUtil.downloadFile(fileUrl, FileUtil.file("e:/"), new StreamProgress(){
            @Override
            public void start() {
                Console.log("开始下载。。。。");
            }
            @Override
            public void progress(long progressSize) {
                Console.log("已下载：{}", FileUtil.readableFileSize(progressSize));
            }
            @Override
            public void finish() {
                Console.log("下载完成！");
            }
        });

        //链式构建请求
        String result15 = HttpRequest.post("")
                .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
                .form(paramMap)//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
        Console.log(result15);

        String json = "";
        String result16 = HttpRequest.post("")
                .body(json)
                .execute().body();

        //  指定请求头
        //  自定义Cookie（cookie方法）
        //  指定是否keepAlive（keepAlive方法）
        //  指定表单内容（form方法）
        //  指定请求内容，比如rest请求指定JSON请求体（body方法）
        //  超时设置（timeout方法）
        //  指定代理（setProxy方法）
        //  指定SSL协议（setSSLProtocol）
        //  简单验证（basicAuth方法）

        HttpResponse res = HttpRequest.post("").execute();
        Console.log(res.getStatus());
        HttpResponse res2 = HttpRequest.post("").execute();
        //预定义的头信息
        Console.log(res.header(Header.CONTENT_ENCODING));
        //自定义头信息
        Console.log(res.header("Content-Disposition"));


        //  HtmlUtil.restoreEscaped 还原被转义的HTML特殊字符
        //  HtmlUtil.encode 转义文本中的HTML字符为安全的字符
        //  HtmlUtil.cleanHtmlTag 清除所有HTML标签
        //  HtmlUtil.removeHtmlTag 清除指定HTML标签和被标签包围的内容
        //  HtmlUtil.unwrapHtmlTag 清除指定HTML标签，不包括内容
        //  HtmlUtil.removeHtmlAttr 去除HTML标签中的属性
        //  HtmlUtil.removeAllHtmlAttr 去除指定标签的所有属性
        //  HtmlUtil.filter 过滤HTML文本，防止XSS攻击

        //请求列表页
        String listContent = HttpUtil.get("https://www.oschina.net/action/ajax/get_more_news_list?newsType=&p=2");
        //使用正则获取所有标题
        List<String> titles = ReUtil.findAll("<span class=\"text-ellipsis\">(.*?)</span>", listContent, 1);
        for (String title : titles) {
            //打印标题
            Console.log(title);
        }
        System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");

        CronUtil.start();
        //使用deamon模式，
        CronUtil.start(true);
        CronUtil.stop();

        //支持秒级别定时任务
        CronUtil.setMatchSecond(true);
        CronUtil.schedule("*/2 * * * * *", new Task() {
            @Override
            public void execute() {
                Console.log("1211112323");
            }
        });

        // 支持秒级别定时任务
        CronUtil.setMatchSecond(true);
        CronUtil.start();


        //  tos: 对方的邮箱地址，可以是单个，也可以是多个（Collection表示）
        //  subject：标题
        //  content：邮件正文，可以是文本，也可以是HTML内容
        //  isHtml： 是否为HTML，如果是，那参数3识别为HTML内容
        //  files： 可选：附件，可以为多个或没有，将File对象加在最后一个可变参数中即可
        MailUtil.send("hutool@foxmail.com", "测试", "邮件来自Hutool测试", false);
        MailUtil.send("hutool@foxmail.com", "测试", "<h1>邮件来自Hutool测试</h1>", true, FileUtil.file("d:/aaa.xml"));
        ArrayList<String> tos = CollUtil.newArrayList(
                "person1@bbb.com",
                "person2@bbb.com",
                "person3@bbb.com",
                "person4@bbb.com");
        MailUtil.send(tos, "测试", "邮件来自Hutool群发测试", false);

        MailAccount account = new MailAccount();
        account.setHost("smtp.yeah.net");
        account.setPort(25);
        account.setAuth(true);
        account.setFrom("hutool@yeah.net");
        account.setUser("hutool");
        account.setPass("q1w2e3");
        MailUtil.send(account, CollUtil.newArrayList("hutool@foxmail.com"), "测试", "邮件来自Hutool测试", false);

        //  ServletUtil
        //  getParamMap 获得所有请求参数
        //  fillBean 将请求参数转为Bean
        //  getClientIP 获取客户端IP，支持从Nginx头部信息获取，也可以自定义头部信息获取位置
        //  getHeader、getHeaderIgnoreCase 获得请求header中的信息
        //  isIE 客户浏览器是否为IE
        //  isMultipart 是否为Multipart类型表单，此类型表单用于文件上传
        //  getCookie 获得指定的Cookie
        //  readCookieMap 将cookie封装到Map里面
        //  addCookie 设定返回给客户端的Cookie
        //  write 返回数据给客户端
        //  setHeader 设置响应的Header

        // 生成指定url对应的二维码到文件，宽和高都是300像素
        QrCodeUtil.generate("http://hutool.cn/", 300, 300, FileUtil.file("d:/qrcode.jpg"));
        QrConfig config = new QrConfig(300, 300);
        // 设置边距，既二维码和背景之间的边距
        config.setMargin(3);
        // 设置前景色，既二维码颜色（青色）
        config.setForeColor(Color.CYAN.getRGB());
        // 设置背景色（灰色）
        config.setBackColor(Color.GRAY.getRGB());
        // 生成二维码到文件，也可以到流
        QrCodeUtil.generate("http://hutool.cn/", config, FileUtil.file("e:/qrcode.jpg"));
        // decode -> "http://hutool.cn/"
        String decode = QrCodeUtil.decode(FileUtil.file("d:/qrcode.jpg"));

        //自动根据用户引入的模板引擎库的jar来自动选择使用的引擎
        //TemplateConfig为模板引擎的选项，可选内容有字符编码、模板路径、模板加载方式等，默认通过模板字符串渲染
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig());
        //假设我们引入的是Beetl引擎，则：
        Template template2 = engine.getTemplate("Hello ${name}");
        //Dict本质上为Map，此处可用Map
        String result17 = template2.render(Dict.create().set("name", "Hutool"));
        //输出：Hello Hutool
        engine = TemplateUtil.createEngine(new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH));
        Template template3 = engine.getTemplate("templates/velocity_test.vtl");
        String result18 = template3.render(Dict.create().set("name", "Hutool"));
        //  CLASSPATH 从ClassPath加载模板
        //  FILE 从File本地目录加载模板
        //  WEB_ROOT 从WebRoot目录加载模板
        //  STRING 从模板文本加载模板
        //  COMPOSITE 复合加载模板（分别从File、ClassPath、Web-root、String方式尝试查找模板）

        //新建会话，此会话用于ssh连接到跳板机（堡垒机），此处为10.1.1.1:22
        Session session = JschUtil.getSession("10.1.1.1", 22, "test", "123456");
        // 将堡垒机保护的内网8080端口映射到localhost，我们就可以通过访问http://localhost:8080/访问内网服务了
        JschUtil.bindPort(session, "172.20.12.123", 8080, 8080);
        //  generateLocalPort 生成一个本地端口（从10001开始尝试，找到一个未被使用的本地端口）
        //  unBindPort 解绑端口映射
        //  openAndBindPortToLocal 快捷方法，将连接到跳板机和绑定远程主机端口到本地使用一个方法搞定
        //  close 关闭SSH会话

        Sftp sftp= JschUtil.createSftp("172.0.0.1", 22, "root", "123456");
        //进入远程目录
        sftp.cd("/opt/upload");
        //上传本地文件
        sftp.put("e:/test.jpg", "/opt/upload");
        //下载远程文件
        sftp.get("/opt/upload/test.jpg", "e:/test2.jpg");
        //关闭连接
        sftp.close();

        //匿名登录（无需帐号密码的FTP服务器）
        Ftp ftp = new Ftp("172.0.0.1");
        //进入远程目录
        ftp.cd("/opt/upload");
        //上传本地文件
        ftp.upload("/opt/upload", FileUtil.file("e:/test.jpg"));
        //下载远程文件
        ftp.download("/opt/upload", "test.jpg", FileUtil.file("e:/test2.jpg"));
        //关闭连接
        ftp.close();

        //  布隆过滤(Hutool-bloomFilter)

        //  切面代理工具-ProxyUtil
        Animal cat = ProxyUtil.proxy(new Cat(), TimeIntervalAspect.class);
        cat.eat();

        
    }

    @Test
    public void test4(){
        QrConfig config = new QrConfig(300, 300);
        // 设置边距，既二维码和背景之间的边距
        config.setMargin(3);
        // 设置前景色，既二维码颜色（青色）
        config.setForeColor(Color.CYAN.getRGB());
        // 设置背景色（灰色）
        config.setBackColor(Color.RED.getRGB());
        config.setImg("d:/1.jpg");
        // 生成二维码到文件，也可以到流
        QrCodeUtil.generate("http://hutool.cn/", config, FileUtil.file("d:/qrcode.jpg"));
    }

    @Test
    public void test5(){
        // decode -> "http://hutool.cn/"
        String decode = QrCodeUtil.decode(FileUtil.file("d:/qrcode.jpg"));
        System.out.println(decode);
    }

    interface Animal{
        void eat();
    }

    class Cat implements Animal{
        @Override
        public void eat() {
            Console.log("猫吃鱼");
        }

    }

    @Test
    public void test3(){
        //支持秒级别定时任务
        CronUtil.setMatchSecond(true);
        CronUtil.schedule("*/2 * * * * *", new Task() {
            @Override
            public void execute() {
                Console.log("1211112323");
            }
        });

        // 支持秒级别定时任务
        CronUtil.setMatchSecond(true);
        CronUtil.start();
    }

    @Test
    public void test2(){
        Date date = new Date();
        //new方式创建
        DateTime time = new DateTime(date);
        Console.log(time);
        //of方式创建
        DateTime now = DateTime.now();
        DateTime dt = DateTime.of(date);

        DateTime dateTime = new DateTime("2017-01-05 12:34:23", DatePattern.NORM_DATETIME_FORMAT);
        //年，结果：2017
        int year = dateTime.year();
        //季度（非季节），结果：Season.SPRING
        Season season = dateTime.seasonEnum();
        //月份，结果：Month.JANUARY
        Month month = dateTime.monthEnum();
        //日，结果：5
        int day = dateTime.dayOfMonth();

        //默认情况下DateTime为可变对象，此时offsite == dateTime
        DateTime offsite = dateTime.offset(DateField.YEAR, 0);
        //设置为不可变对象后变动将返回新对象，此时offsite != dateTime
        dateTime.setMutable(false);
        offsite = dateTime.offset(DateField.YEAR, 0);

        String dateStr = dateTime.toString();
        //结果：2017/01/05
    }

    @Test
    public void CollUtil_Filter() {
        Map<String, Object> m = new HashMap<String, Object>() {{
            put("k1", "v1");
            put("k2", "v2");
            put("k3", "v3");
        }};
        String[] inc = {"k1", "k3"};//需要的key
        List<String> incList = Arrays.asList(inc);
        m = CollectionUtil.filter(m, new Editor<Map.Entry<String, Object>>() {
            @Override
            public Map.Entry<String, Object> edit(Map.Entry<String, Object> stringObjectEntry) {
                if (incList.contains(stringObjectEntry.getKey())) {
                    return stringObjectEntry;
                }
                return null;
            }
        });
    }
}

class CustomConverter implements Converter<String> {
    @Override
    public String convert(Object value, String defaultValue) throws IllegalArgumentException {
        return "Custom: " + value.toString();
    }
}

//定义枚举
enum TestEnum{
    TEST1("type1"), TEST2("type2"), TEST3("type3");

    private TestEnum(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return this.type;
    }
}

class TestClass {
    public List<String> getList(){
        return new ArrayList<>();
    }

    public Integer intTest(Integer integer) {
        return 1;
    }
}

/**
 * 有界优先队列Demo
 * @author Looly
 *
 */class BoundedPriorityQueueDemo {

    public static void main(String[] args) {
        //初始化队列，设置队列的容量为5（只能容纳5个元素），元素类型为integer使用默认比较器，在队列内部将按照从小到大排序
        BoundedPriorityQueue<Integer> queue = new BoundedPriorityQueue<Integer>(5);

        //初始化队列，使用自定义的比较器
        queue = new BoundedPriorityQueue<>(5, new Comparator<Integer>(){

            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        //定义了6个元素，当元素加入到队列中，会按照从小到大排序，当加入第6个元素的时候，队列末尾（最大的元素）将会被抛弃
        int[] array = new int[]{5,7,9,2,3,8};
        for (int i : array) {
            queue.offer(i);
        }

        //队列可以转换为List哦~~
        ArrayList<Integer> list = queue.toList();

        System.out.println(queue);
    }
}