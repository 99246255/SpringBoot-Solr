package com.solr;

import com.solr.domain.Good;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;

import java.lang.reflect.Method;
import java.sql.Date;

@SuppressWarnings("deprecation")
public class SolrTest {
    private static HttpSolrServer solrServer;

    static {
        //注意请求地址格式：浏览器中的地址有 ‘#’，需要把‘#’去掉！
        solrServer = new HttpSolrServer("http://localhost:8983/solr/mysql");
        solrServer.setConnectionTimeout(5000);
    }

    /**
     * 添加单个文档 。
     */
    public static void insert(Good good) {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", good.getId());
        doc.addField("name", good.getName());
        doc.addField("number", good.getNumber());
        doc.addField("updateTime", good.getUpdateTime());

        try {
            solrServer.add(doc);
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据文档id删除文档 。
     */
    public static void deleteById(String id) {
        try {
            solrServer.deleteById(id+"");
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除所有文档，为安全起见，使用时再解注函数体 。
     */
    public static void deleteAll() {
        try {
            solrServer.deleteByQuery("*:*");
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新文档，其实也是通过insert操作来完成 。
     */
    public static void update(Good good) {
        insert(good);
    }

    /**
     * 根据文档id查询单个文档 。
     * @return
     */
    public static <T> T getById(String id, Class<T> clazz) {
        SolrQuery query = new SolrQuery();
        query.setQuery("id:" + id);

        try {
            QueryResponse rsp = solrServer.query(query);
            return rsp.getBeans(clazz).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param obj 对象索引
     * @param idName 主键名称
     */
    public static void deleteByObject(Object obj, String idName){
        try {
            Class<?> clazz = obj.getClass();
            //将idName的首字母变成大写
            if(Character.isLowerCase(idName.charAt(0))) idName = Character.toUpperCase(idName.charAt(0)) + idName.substring(1);
            Method method = clazz.getMethod("get"+idName);
            String idValue = (String) method.invoke(obj);
            deleteById(idValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Good good = new Good("123", "9999", "hjzgg5211314", new Date(System.currentTimeMillis()));
        update(good);
        System.out.println(getById("123", Good.class));
        deleteByObject(good, "id");
    }
}