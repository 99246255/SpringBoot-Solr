package com.solr;

import com.solr.domain.SolrjMessage;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 集群模式测试代码，和单机模式区别只是一个用CloudSolrClient，一个用HttpSolrClient
 */
public class SolrCloudTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * Solr本地地址
     */
    private String zookeeperUrl = "localhost:2181/solr";
    //查询字符串key
    private String searchKey = "id";
    private boolean highlighting = true;
    private String highlightingPre = "<em>";
    private String highlightingPost = "</em>";
    private String highlightingField = "content";
    // Solr客户端对象
    private CloudSolrClient client;
    public void open() {
        // 1.创建SolrServer对象，以下这两个是线程安全的SolrServer实现类
        // CommonsHttpSolrServer 基于Http协议进行C/S数据交互
        // EmbeddedSolrServer
        // 内嵌式，只要设定好solr的home目录即可实现和solr的交互，不需要开启solr的服务器，本地交互
        client = new CloudSolrClient(zookeeperUrl);
        //指定Collection名称
        client.setDefaultCollection("solr");
        client.setZkClientTimeout(30000);
        client.setZkConnectTimeout(30000);
    }

    /**
     * 查找,分页查询
     *
     * @param qString 查找字符串
     * @return 返回查询结果列表
     */
    public List<SolrjMessage> search(String qString, Integer pageNum, Integer pageSiez) {
        try {
            SolrQuery query = new SolrQuery(searchKey + ":" + qString);// 查询字符串
            query.setStart((pageNum - 1) * pageSiez);// 设置查询开始下标
            query.setRows(pageSiez);// 查询行数

            //高亮配置
            query.setHighlight(highlighting);
            query.setHighlightSimplePre(highlightingPre);
            query.setHighlightSimplePost(highlightingPost);
            query.addHighlightField(highlightingField);

            QueryResponse response = client.query(query);// 获取查询返回对象

            SolrDocumentList docs = response.getResults();// 获取查询得到的所有Document
            //查询高亮信息
            Map<String, Map<String, List<String>>> highlightings = response.getHighlighting();
            List<SolrjMessage> list = new ArrayList<SolrjMessage>();
            for (SolrDocument doc : docs) {
                // 获取每个Document的详细信息
                SolrjMessage message = new SolrjMessage();
                message.setId((String) doc.getFieldValue("id"));
                message.setUrl((String) doc.getFieldValue("url"));
                message.setContent(doc.getFieldValue("content").toString());
                list.add(message);
            }

            //其他查询信息
            NamedList responseHeader = response.getResponseHeader();
            SimpleOrderedMap params = (SimpleOrderedMap) responseHeader.get("params");
            NamedList<Object> responseResponse = response.getResponse();
            SolrDocumentList responseBean = (SolrDocumentList) responseResponse.get("response");

            int qTime = response.getQTime();
            int status = response.getStatus();
            Object q = params.get("q");
            Object fq = params.get("fq");
            Object numFound = responseBean.getNumFound();
            Object start = responseBean.getStart();
            return list;
        } catch (SolrServerException e) {
            logger.error("solr服务异常", e);
            throw new RuntimeException("solr服务异常", e);
        } catch (IOException e) {
            logger.error("solr服务IO异常", e);
            throw new RuntimeException("solr服务IO异常", e);
        }
    }

    /**
     * Solr创建索引
     *
     * @param message 创建索引的文件
     */
    public void createIndex(SolrjMessage message) {

        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", message.getId());
        doc.addField("url", message.getUrl());
        doc.addField("content", message.getContent());
        try {
            client.add(doc);
            client.commit();
        } catch (SolrServerException e) {
            logger.error("solr服务异常", e);
            throw new RuntimeException("solr服务异常", e);
        } catch (IOException e) {
            logger.error("solr服务IO异常", e);
            throw new RuntimeException("solr服务IO异常", e);
        }
    }

    public void deleteIndex() throws IOException, SolrServerException {
        UpdateResponse response = client.deleteByQuery("id:id");
        client.commit();
        int status = response.getStatus();
        System.out.println("status = " + status);
    }

    public static void main(String[] args) throws IOException, SolrServerException  {

        SolrCloudTest solrCloudTest = new SolrCloudTest();
        solrCloudTest.open();
        SolrjMessage solrjMessage = new SolrjMessage();
        solrjMessage.setId("id");
        solrjMessage.setUrl("url");
        solrjMessage.setContent("this is content1");
        solrCloudTest.createIndex(solrjMessage);
        List<SolrjMessage> id = solrCloudTest.search("id", 1, 10);
        System.out.println(id.size());
//        solrCloudTest.deleteIndex();
    }
}
