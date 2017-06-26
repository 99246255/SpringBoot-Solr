//package com.solr;
//
//import org.apache.solr.client.solrj.SolrQuery;
//import org.apache.solr.client.solrj.SolrRequest;
//import org.apache.solr.client.solrj.SolrServerException;
//import org.apache.solr.client.solrj.impl.HttpSolrServer;
//import org.apache.solr.client.solrj.response.*;
//import org.apache.solr.common.SolrDocumentList;
//import org.apache.solr.common.params.FacetParams;
//import org.apache.solr.common.params.GroupParams;
//import org.apache.solr.common.util.NamedList;
//import org.junit.Test;
//
//import java.net.MalformedURLException;
//import java.text.SimpleDateFormat;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//
///**
// * Created by chenyu on 2017/5/27.
// */
//@SuppressWarnings("deprecation")
//public class SolrTest {
//
//
//    private String BigSolrURL = "127.0.0.1:8983/solr";
//
//    @Test
//    public void slorQuery() throws SolrServerException, MalformedURLException {
//
//        HttpSolrServer server = new HttpSolrServer(BigSolrURL);
//        /*
//         * server.setConnectionTimeout(180 * 1000); server.setSoTimeout(240 *
//         * 1000); server.setMaxTotalConnections(1200);
//         * server.setDefaultMaxConnectionsPerHost(100); server.setMaxRetries(3);
//         * server.setAllowCompression(true);
//         */
//        // 定义查询字符串
//        SolrQuery query = new SolrQuery("*:*");
//
//        // 设置查询关键字
//        /* query.setQuery("Content:* AND Spare3:1 "); */
//        // 指定查询返回字段
//        /* query.setParam("fl", "Content,IndexTime"); */
//        // 设置高亮
//        query.setHighlight(true).setHighlightSimplePre("<span class='red'>")
//                .setHighlightSimplePost("</span>");
//        query.setParam("hl.fl", "Content");//设置高亮字段
//        query.setParam("fl", "ID,Published");
//
//        //排除条件      - NOT
//        //wbQuery.addFilterQuery("OriginType:wb -Spare3:0");
//        //wbQuery.addFilterQuery("OriginType:wb NOT Spare3:0");
//        // 时间条件过滤
//        /* query.addFilterQuery("Content:超哥"); */
//        /*
//         * query.addFilterQuery(
//         * "Published:[1995-12-31T23:59:59.999Z TO 2016-03-06T00:00:00Z]");
//         */
//        query.addFilterQuery("Published:[* TO NOW]");
//
//        // 实现分页的查询
//        query.setStart(0);
//        query.setRows(10);
//        // 设定排序，如果需要对field进行排序就必须在schema.xml中对该field配置stored="true"属性
//        //set会清空原来的sort条件，add不会清空原来的，会在原来的基础上添加 sort=Published asc,Author asc(多条件排序)
//        query.setSort(IContentCommon.IndexField.Published.getName(),
//                SolrQuery.ORDER.asc);
//
//        query.addSort(IContentCommon.IndexField.Published.getName(),
//                SolrQuery.ORDER.asc);
//
//        QueryResponse res = server.query(query);
//        System.out.println(query);
//        // 查询出来的结果都保存在SolrDocumentList中
//        SolrDocumentList sdl = res.getResults();
//        System.out.println("总数：" + sdl.getNumFound());
//        System.out.println(sdl.getMaxScore());
//        for (SolrDocument sd : sdl) {
//
//            Object id = sd.get("ID");
//            // 打印高亮信息
//            System.out.println(res.getHighlighting().get(id).get("Content"));
//            //
//            System.out.println(sd.get("ID") + "#" + sd.get("Content") + "#"
//                    + sd.get("WeiboId") + "#" + sd.get("Published") + "#"
//                    + sd.get("OriginType"));
//
//            /* System.out.println(sd.getFieldValue("ID")); */
//            Date date = (Date) sd.get("Published");
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String dstring = sdf.format(date);
//            System.out.println(dstring);
//        }
//
//    }
//
//   /*
//   java对象 <---> xml文件之间的转换
//   JAXB注解JAXB能够使用Jackson对JAXB注解的支持实现(jackson-module-jaxb-annotations)，既方便生成XML，也方便生成JSON
//    @XmlRootElement對类标识自动转化类中有get 、set方法的属性，没有get、set方法的属性无法转化
//    @XmlElement对属性标识，属性无需get、set方法
//
//     public Analyzer analyzer = new IKAnalyzer(true);//true开启智能分词
//         拿IKAnalyzer分词器为例，IKAnalyzer的切分方式是细粒度切分，当不需要智能处理时，
//     其就把切出的所有词输出，但若启动了智能处理，那么接下来就是要进行消歧工作。
//  智能分词结果:
//    张三|说的|确实|在理
// 最细粒度分词结果:
//    张三|三|说的|的确|的|确实|实在|在理
//
//(1) TermAttribute： 表示token的字符串信息。比如"I'm"
//(2) TypeAttribute： 表示token的类别信息(在上面讲到)。比如 I'm 就属于<APOSTROPHE>，有撇号的类型
//(3) OffsetAttribute：表示token的首字母和尾字母在原文本中的位置。比如 I'm 的位置信息就是(0,3)
//(4) PositionIncrementAttribute：这个有点特殊，它表示tokenStream中的当前token与前一个token在实际的原文本中相隔的词语数量。
//
//
//
//
//
//
//  Math.round 四舍五入Math.round(1.4)=1  Math.round(1.5)=2
//  Math.ceil向上取整Math.ceil(1.4)=2.0
//  Math.floor向下取整
//
//
//
//
//http://blog.csdn.net/zwx19921215/article/details/41820483
//
//http://www.cnblogs.com/chenz/articles/3229997.html
//
//http://blog.csdn.net/huoyunshen88/article/details/38082455
//
//--------slor日期格式存储----------
//TrieDateField  DateField 表示一个精确到毫秒的时间，值的格式是：
//YYYY-MM-DD T hh:mm:ss Z
//
//--------------来源类型-------------
//BasicContent
//
//-------字符串转换成日期工具类-----------------
//DateUtilsIis.parse(strDate,strPattern)
//
//------------错误码类型类-------------------
//ErrorCodeConstant
//
////ArrayList线程安全
//List<Map<String,Object>> data=Collections.synchronizedList(new ArrayList<Map<String,Object>>());
//
//
//-------------监听器------------------
//InitDataListener --> springmvc配置的监听器
//
//
//
//----------LinkedHashMap-----------
//LinkedHashMap实体虽然是以Hash的顺序存放在Map的数组table里面，但是实体之间却用链表的形式保持了存入的先后关系。
//LinkedHashMap保存了记录的插入顺序，所以当你需要输出的顺序和输入的相同,那么用LinkedHashMap可以实现，它还可以按读取顺序来排列。
//Collections.sort方法对arraylist排序
//
//
//
//    *
//    *
//    */
//
//    /**
//     * 测试分组
//     */
//    @Test
//    public void facet() throws MalformedURLException, SolrServerException{
//        HttpSolrServer server = new HttpSolrServer(URL);
//        SolrQuery query = new SolrQuery("*:*");
//        query.setIncludeScore(false);
//        query.setFacet(true);
//        /* query.addFacetField("Province"); */
//        query.addFacetField("Spare3","UserLocation");//两个域有各自独立的结果
//
//        /*  query.addFacetField(new String[] {"salary","publishDate","educateBackground","jobExperience","companytype","jobsType" });//设置需要facet的字段*/
//        query.addFacetQuery("Spare3:[4 TO 7]");
//
//        /*
//         * FacetComponet有两种排序选择，分别是count和index，
//         * count是按每个词出现的次数，index是按词的字典顺序。如果查询参数不指定facet.sort，solr默认是按count排序。
//         */
//        /* query.setFacetSort("index"); */
//        query.setFacetSort("count");
//        /*query.setFacetLimit(101);  */ // 设置返回结果条数 ,-1表示返回所有,默认值为100
//        /* query.setParam(FacetParams.FACET_OFFSET, "100");*/   //开始条数,偏移量,它与facet.limit配合使用可以达到分页的效果
//        query.setFacetSort(FacetParams.FACET_SORT_COUNT);
//        query.setFacetMinCount(1);//设置 限制 count的最小返回值，默认为0
//        query.setFacetMissing(false);//不统计null的值
//        /* query.setFacetPrefix("湖");//设置前缀 */
//
//
//
//
//        System.out.println(query.toString());
//
//        QueryResponse res = server.query(query);
//
//
//        System.out.println("-------单个facet结果--------");
//        List<Count> spare3List = res.getFacetField("Spare3").getValues();
//        for (Count count : spare3List) {
//            System.out.println(count.getName() + "#" + count.getCount());
//        }
//
//        System.out.println("-------所有facet结果--------");
//        // 得到所有Facet结果
//        List<FacetField> facets = res.getFacetFields();//返回的facet列表
//
//        for (FacetField facet :facets) {
//
//            System.out.println(facet.getName());
//
//            System.out.println("----------------");
//
//            List<Count> counts = facet.getValues();
//            int i=1;//计数器
//            for (Count count : counts){
//
//                System.out.println(i+count.getName()+":"+ count.getCount());
//                i++;
//            }
//
//            System.out.println();
//
//        }
//
//        System.out.println("-------FacetQuery结果--------");
//        // 得到FacetQuery结果
//        Map<String, Integer> facetQueryResult = res.getFacetQuery();
//
//        for (Map.Entry<String, Integer> fqr : facetQueryResult.entrySet()) {
//            System.out.println(fqr.getKey() + ":" + fqr.getValue());
//        }
//
//
//
//
//        res.getFacetDate(null);
//        res.getFacetDates();
//        res.getFacetRanges();
//
//        /*
//                           query.set("group", "true");
//                         query.set("group.field", groupField);
//                        query.set("group.format", "grouped"); // default:simple,
//                        query.set("group.main", "false"); // when
//                        // /*group.format=simple
//                        query.set("group.cache.percent", "50"); // default is 0;
//                        query.set("rows", topCount);
//                        query.setParam("shards.tolerant", true);
//         * */
//        /**
//         *
//         *
//         *
//         * 控制台输出结果：
//         *
//         *
//         q=Usergender%3A*&fl=*&facet=true&facet.field=Spare3&facet.query=Spare3%3A%5B4+TO+7%5D&facet.sort=count&facet.limit=10
//         0#35389
//         4#22778
//         3#22333
//         2#16567
//         1#15571
//         6#13161
//         5#12339
//         7#11550
//         8#2659
//         9#1399
//         Spare3:[4 TO 7]:59828
//
//
//         -------------- slor图形界面结果------------
//
//
//         "facet_counts": {
//         "facet_queries": {
//         "Spare3:[4 TO 7]": 59828
//         },
//         "facet_fields": {
//         "Spare3": [
//         "0",
//         35389,
//         "4",
//         22778,
//         "3",
//         22333,
//         "2",
//         16567,
//         "1",
//         15571,
//         "6",
//         13161,
//         "5",
//         12339,
//         "7",
//         11550,
//         "8",
//         2659,
//         "9",
//         1399,
//         "10",
//         500,
//         "11",
//         187,
//         "12",
//         115,
//         "13",
//         51,
//         "14",
//         34,
//         "15",
//         30,
//         "16",
//         3
//         ]
//         },
//         "facet_dates": {},
//         "facet_ranges": {}
//         }
//
//
//
//         facet.range
//         http://…/select?&facet=true&facet.range=price&facet.range.start=5000&facet.range.end=8000&facet.range.gap=1000
//
//         solrconfig.xml配置
//         <requestHandler name="/browse" class="solr.SearchHandler" name="/select">
//         <lst name="defaults">
//         <str name="echoParams">explicit</str>
//         <int name="rows">10</int>
//         <str name="df">text</str>
//
//         str name="facet">on</str>
//         <str name="facet.range">Price</str>
//         <int name="f.Price.facet.range.start">0</int>
//         <int name="f.Price.facet.range.end">5000</int>
//         <int name="f.Price.facet.range.gap">1000</int>
//         </lst>
//         </requestHandler>
//
//         facet.range节点中表示按范围分段的字段为Price
//         f.Price.facet.range.start表示起始值为0
//         f.Price.facet.range.end表示最大值为 5000
//         f.Price.facet.range.gap表示每次间隔1000进行分段 ，
//         */
//
//
//
//    }
//
//
//
//
//
//
//    @Test
//    public  void group()  {
//        try {
//            HttpSolrServer server = new HttpSolrServer(URL);
//            SolrQuery query = new SolrQuery("*:*");
//
//            /*-----增加group-----------*/
//            query.setParam(GroupParams.GROUP,true);
//            query.setParam(GroupParams.GROUP_FIELD,"Spare3","UserLocation");
//            query.setParam(GroupParams.GROUP_LIMIT,"3");//每个分组返回的文档数量
//            query.setParam(GroupParams.GROUP_FORMAT, "grouped");// 默认为 grouped所有文档分组显示 ,simple的话所有文档显示在一个集合中
//            query.setParam(GroupParams.GROUP_MAIN,"false");//format为grouped的时候，必须为false
//            query.setParam(GroupParams.GROUP_TOTAL_COUNT, true);//返回分组个数ngroups：
//
//            query.setRows(100);//限制返回group个数
//
//            System.out.println(query.toString());
//
//            QueryResponse res = server.query(query);
//            System.out.println("--------group分组结果----------------");
//     /* ------得到分组结果-----*/
//            GroupResponse groupResponse =res.getGroupResponse();
//            if(groupResponse !=null) {
//                //得到每个分组字段集合（多个分组字段）
//                List<GroupCommand> groupList =groupResponse.getValues();
//
//                //遍历每个字段分组结果
//                for(GroupCommand groupCommand : groupList){
//                    System.out.println("--------group字段----------------");
//                    System.out.println(  groupCommand.getName());
//                    System.out.println("--------单个group字段结果----------------");
//                    List<Group> groups =groupCommand.getValues();
//                    for(Group group : groups) {
//                        System.out.println(group.getGroupValue()+"数量为："+group.getResult().getNumFound());
//                        System.out.println(group.getResult());
//                        System.out.println("第一个文档:"+group.getResult().get(0));
//                        System.out.println("第二个文档:"+group.getResult().get(1));
//                        System.out.println("第三个文档:"+group.getResult().get(2));
//                        System.out.println("第一个文档内容:"+group.getResult().get(0).get("Content"));
//                        System.out.println("第二个文档内容:"+group.getResult().get(1).get("Content"));
//                        System.out.println("第三个文档内容:"+group.getResult().get(2).get("Content"));
//                    }
//                }
//
//            }
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//    }
//
//    // 高亮部分(自动摘要)，lucene中是通过highlighter来操作高亮和摘要的
//    @Test
//    public void Highlight() {
//        HttpSolrServer server = new HttpSolrServer(URL);
//        SolrQuery query = new SolrQuery("Content:\"财经周刊\"  OR  UserLocation:北京 ");
//        // 设置高亮，以下两种方式都行（相当于开启高亮功能）
//        // query.setHighlight(true);
//        query.setParam("hl", "true"); // highlighting
//
//        /*
//         * 给多个字段开启高亮功能
//         * query.addHighlightField("Content");
//         * query.addHighlightField("UserLocation");
//         */
//
//        //hl.fl: 用空格或逗号隔开的字段列表。要启用某个字段的highlight功能，就得保证该字段在schema中是stored。
//        //如果该参数未被给出，那么就会高亮默认字段 standard handler会用df参数，dismax字段用qf参数。
//        //你可以使用星号去方便的高亮所有字段。如果你使用了通配符，那么要考虑启用hl.requiredFieldMatch选项。
//        query.setParam("hl.fl", "Content", "UserLocation");
//
//        // 高亮显示字段前后添加html代码
//        query.setHighlightSimplePre("<font color=\"red\">");
//        query.setHighlightSimplePost("</font>");//高亮时显示的格式，默认是<em></em>
//        query.setHighlightSnippets(3);
//        //这是highlighted片段的最大数。默认值为1，也几乎不会修改。获取高亮分片数，一般搜索词可能分布在文章中的不同位置，其所在一定长度的语句即为一个片段，默认为1，但根据业务需要有时候需要多取出几个分片。
//        //如果某个特定的字段的该值被置为0，这就表明该字段被禁用高亮了
//        query.setHighlightFragsize(0);// 每个snippet返回的最大字符数。默认是100.如果为0，那么该字段不会被fragmented且整个字段的值会被返回。大字段时不会这么做
//        query.set("hl.usePhraseHighlighte", true);//如果一个查询中含有短语（引号框起来的）那么会保证一定要完全匹配短语的才会被高亮。
//
//        //有时我们查询根据条件“java OR (empId:1000 AND empId:1001)”搜索时，结果如果高亮显示，可能出现1000,1001数字也会高亮，
//        //但是我们只希望java关键字高亮，这个时候可以用下面的方法,只对lucene和solr关键字进行高亮显示（solr不作为搜索条件也可以）
//        query.setParam("hl.q", "lucene solr");
//        query.setRows(10);
//        SolrDocumentList list = new SolrDocumentList();
//
//        SolrDocument document = null;
//
//        System.out.println(query);
//        try {
//            QueryResponse response = server.query(query);
//            SolrDocumentList documents = response.getResults();
//
//            System.out.println("----原文档-----");
//            System.out.println(documents);
//            for (SolrDocument doc : documents) {
//                System.out.println(doc.getFieldValue("Content"));
//                System.out.println(doc.getFieldValue("UserLocation"));
//            }
//            // 第一个Map的键是文档的ID，第二个Map的键是高亮显示的字段名,List<String>中封装的就是高亮与摘要处理过后的内容了
//
//            Map<String, Map<String, List<String>>> map = response
//                    .getHighlighting();
//            //得到的结果documents替换为高亮内容或者加入一个新的list中
//            for (int i = 0; i < documents.size(); i++) {
//
//                document = documents.get(i);
//
//                document.setField("Content",
//                        map.get(document.getFieldValue("ID")).get("Content"));
//
//                document.setField(
//                        "UserLocation",
//                        map.get(document.getFieldValue("ID")).get(
//                                "UserLocation"));
//
//                list.add(document);
//
//            }
//            System.out.println(list);
//            System.out.println(documents);
//
//            for (SolrDocument doc : documents) {
//                System.out.println(doc.getFieldValue("Content"));
//                System.out.println(doc.getFieldValue("UserLocation"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//
//
//    @Test
//    public void test(){
//
//        HttpSolrServer server = new HttpSolrServer("http://localhost/solr");
//        SolrQuery query = new SolrQuery("Content:我们");
//        QueryResponse response;
//        try {
//            response = server.query(query);
//
//            System.out.println(response.getResults());
//        } catch (SolrServerException e) {
//
//            e.printStackTrace();
//        }
//
//
//    }
//
//    @Test
//    public void lucene(){
//
//        Field field=new Field("id","11",Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS);
//        field.setBoost(2);
//        Document document = new Document();
//        //DefaultSimilarity继承TFIDFSimilarity
//        //VSM(向量空间模型)   余弦相似度  TF/IDF(term frequency/inverse document frequency)是一种统计方法
//        //默认的评分类
//
//        //EnumMap是专门为枚举类型量身定做的Map实现。虽然使用其它的Map实现（如HashMap）也能完成枚举类型实例到值得映射，但是使用EnumMap会更加高效：
//        //它只能接收同一枚举类型的实例作为键值，并且由于枚举类型实例的数量相对固定并且有限，所以EnumMap使用数组来存放与枚举类型对应的值。这使得EnumMap的效率非常高。
//    }
//
//
//    @Test
//    public void treeMap(){
//
//        //传入的比较器只能根据key来排序，TreeMap如不指定排序器，默认将按照key值进行升序排序
//        //指定排序器按照key值降序排列 ，
//        //Comparator中泛型必须传入key类型的的超类TreeMap(Comparator<? super K> comparator)
//
//        TreeMap<String, Integer> treeMap=new TreeMap<String, Integer>(new Comparator<Object>() {
//
//            @Override
//            public int compare(Object o1, Object o2) {
//                return o2.hashCode()-(o1.hashCode());
//
//                //如果key是String类型   return o2.compareTo(o1);
//            }
//        }) ;
//        treeMap.put("2", 1);
//        treeMap.put("b", 1);
//        treeMap.put("1", 1);
//        treeMap.put("a", 1);
//        System.out.println("treeMap="+treeMap);
//
//    }
//
//
//    //单个字段分组统计
//    //参考文章：http://shiyanjun.cn/archives/78.html
//    @Test
//    public void stats(){
//
//        SolrQuery query = new SolrQuery("Content:盗墓笔记");
//        HttpSolrServer server = new HttpSolrServer(BigSolrURL);
//        query.setGetFieldStatistics("ForwardNumber");
//        query.setGetFieldStatistics("RepeatNum");
//        try {
//            System.out.println(query );
//            QueryResponse response = server.query(query);
//            Map<String, FieldStatsInfo> map=    response.getFieldStatsInfo();
//
//
//            for( String  key :map.keySet()){
//
//                FieldStatsInfo     fieldStatsInfo=    map.get(key);
//                System.out.println(fieldStatsInfo);
//                System.out.println(fieldStatsInfo.getName());
//                System.out.println(fieldStatsInfo.getCount());
//                System.out.println(fieldStatsInfo.getMax());
//                System.out.println(fieldStatsInfo.getMin());
//                System.out.println(fieldStatsInfo.getSum());
//                System.out.println(fieldStatsInfo.getMean());
//                System.out.println(fieldStatsInfo.getMissing());
//                System.out.println(fieldStatsInfo.getStddev());//标准差
//            }
//
//        } catch (SolrServerException e) {
//
//            e.printStackTrace();
//        }
//    }
//
//
//
//    //一次对多个字段独立分组统计
//    //这相当于执行两个带有GROUP BY(facet)子句的SQL，这两个GROUP BY分别只对一个字段进行汇总统计
//    @Test
//    public void statsFacet(){
//
//        SolrQuery query = new SolrQuery("Content:盗墓笔记");
//        HttpSolrServer server = new HttpSolrServer(BigSolrURL);
//        //第一个参数是要统计的字段(stats)，第二个参数是分组(Facet)字段
//        //setGetFieldStatistics不能少
//        query.setGetFieldStatistics("ForwardNumber");
//        query.setGetFieldStatistics("RepeatNum");
//        query.addStatsFieldFacets("ForwardNumber", "Spare3","Province");
//        query.addStatsFieldFacets("RepeatNum", "Spare3","Province");
//        System.out.println(query);
//        try {
//            QueryResponse response = server.query(query);
//
//
//            Map<String, FieldStatsInfo> map=    response.getFieldStatsInfo();
//
//            for( String  key :map.keySet()){
//                System.out.println("stats:"+key);
//                FieldStatsInfo     fieldStatsInfo=    map.get(key);
//
//                System.out.println(fieldStatsInfo);
//                System.out.println(fieldStatsInfo.getName());
//                System.out.println(fieldStatsInfo.getCount());
//                System.out.println(fieldStatsInfo.getMax());
//                System.out.println(fieldStatsInfo.getMin());
//                System.out.println(fieldStatsInfo.getSum());
//                System.out.println(fieldStatsInfo.getMean());
//                System.out.println(fieldStatsInfo.getMissing());
//                System.out.println(fieldStatsInfo.getStddev());//标准差
//
//                Map<String, List<FieldStatsInfo>>        maps=    fieldStatsInfo.getFacets();//得到每个(facet)分组
//                for ( String  facet: maps.keySet()){
//                    System.out.println("facet:"+facet);
//                    List<FieldStatsInfo> lists=     maps.get(facet);
//                    for(FieldStatsInfo facetFieldStatsInfo: lists ){
//
//                        System.out.println(facetFieldStatsInfo);
//                        System.out.println(facetFieldStatsInfo.getName());
//                        System.out.println(facetFieldStatsInfo.getCount());
//                        System.out.println(facetFieldStatsInfo.getMax());
//                        System.out.println(facetFieldStatsInfo.getMin());
//                        System.out.println(facetFieldStatsInfo.getSum());
//                        System.out.println(facetFieldStatsInfo.getMean());
//                        System.out.println(facetFieldStatsInfo.getMissing());
//                        System.out.println(facetFieldStatsInfo.getStddev());//标准差
//                    }
//                }
//
//            }
//
//
//
//
//
//
//
//        } catch (SolrServerException e) {
//
//            e.printStackTrace();
//        }
//
//    }
//
//    //参考文章：http://blog.csdn.net/a925907195/article/details/42241265
//    //多个字段分组只支持(count函数)
//    @Test
//    public  void facetpivot()  {
//
//        try {
//            HttpSolrServer solrServer = new HttpSolrServer(URL);
//
//            SolrQuery query = new SolrQuery("Content:盗墓笔记");
//
//            query.setFacet(true);
//            //  query.add("facet.pivot", "Spare3,UserLocation");//根据这两维度来分组查询
//            query.addFacetPivotField("Spare3,Province");
//            System.out.println(query);
//            QueryResponse response = solrServer.query(query, SolrRequest.METHOD.POST);
//            NamedList<List<PivotField>> namedList = response.getFacetPivot();
//            System.out.println(namedList);//底下为啥要这样判断，把这个值打印出来，你就明白了
//            if(namedList != null){
//                List<PivotField> pivotList = null;
//                for(int i=0;i<namedList.size();i++){
//                    pivotList = namedList.getVal(i);
//                    if(pivotList != null){
//                        for(PivotField pivot:pivotList){
//                            int pos = 0;
//                            int neg = 0;
//                            List<PivotField> fieldList = pivot.getPivot();
//                            if(fieldList != null){
//                                for(PivotField field:fieldList){
//                                    int proValue = (Integer) field.getValue();
//                                    int count = field.getCount();
//                                    if(proValue == 1){
//                                        pos = count;
//                                    }else{
//                                        neg = count;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}