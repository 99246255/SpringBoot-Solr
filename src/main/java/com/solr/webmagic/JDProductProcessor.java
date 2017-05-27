package com.solr.webmagic;

import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * JD商品抓取
 */
public class JDProductProcessor implements PageProcessor {

    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    // 未改版之前
//    private static final String URL_LIST = "http://list\\.jd\\.com/.*\\.html";

    // 改版之后
    private static final String URL_LIST = "http://list\\.jd\\.com/list\\.html\\?cat=.*";

    @Override
    public void process(Page page) {
        // 所有商品分类入口
        if (page.getUrl().regex("http://www\\.jd\\.com/allSort\\.aspx").match()) {
            List<String> links = page.getHtml().links().regex(URL_LIST).all();// 获取商品分类链接匹配URL_LIST的链接信息
            // 添加商品分类页面所匹配到的所有商品信息到请求中
            page.addTargetRequests(links);
        } else { //处理商品列表页
            List<String> names = page.getHtml().xpath("//li[@class='gl-item']/div[@class='gl-i-wrap j-sku-item']/div[@class='p-name']/a/em/text()").all();
            List<String> prices = page.getHtml().xpath("//li[@class='gl-item']/div[@class='gl-i-wrap j-sku-item']/div[@class='p-price']/strong[@class='J_price']/i/text()").all();
            List<String> comments = page.getHtml().xpath("//li[@class='gl-item']/div[@class='gl-i-wrap j-sku-item']/div[@class='p-commit']/strong/a/text()").all();
            List<String> links = page.getHtml().xpath("//li[@class='gl-item']/div[@class='gl-i-wrap j-sku-item']/div[@class='p-img']/a/@href").all();
            List<String> top3Pic = page.getHtml().xpath("//li[@class='gl-item']/div[@class='gl-i-wrap j-sku-item']/div[@class='p-img']/a/img/@src").all(); //获取页面初始化的前三张图片地址
            List<String> lazyPic = page.getHtml().xpath("//li[@class='gl-item']/div[@class='gl-i-wrap j-sku-item']/div[@class='p-img']/a/img/@data-lazy-img").all(); // 获取懒加载的图片地址
            List<String> pics = new ArrayList<>();
            int size = names.size();
            for (int i = 0 ; i< size;  i++){
                String s = lazyPic.get(i);
                if(StringUtils.isEmpty(s)){
                    s = top3Pic.get(i);
                }
                pics.add(s);
            }
//            pics.addAll(top3Pic.subList(0, 10)); //获取前三张图片,实际是10张
//            pics.addAll(lazyPic.subList(10, lazyPic.size())); //获取除前三张之外的图片
            String category = page.getHtml().xpath("//div[@id='J_selector']/div[@class='s-title']/h3/b/text()").get();
            if ("".equals(category)) {
                category = page.getHtml().xpath("//div[@id='J_selector']/div[@class='s-title']/h3/em/text()").get();
            }
            page.putField("names", names);
            page.putField("prices", prices);
            page.putField("comments", comments);
            page.putField("links", links);
            page.putField("pics", pics);
            page.putField("category", category);

            // 获取当前页url
            String url = page.getUrl().get();
            if (url.endsWith(".html")) {// 若请求url是以.html结尾则表示是从商品分类页面中抽取到的商品url，即为该商品分类下的首页,以下代码获取该商品分类下的总页数，构建商品分页。并将分页信息添加到爬虫列表
                // 获取总页数
                String pages = page.getHtml().xpath("//div[@id='J_filter']/div[@class='f-line top']/div[@id='J_topPage']/span[@class='fp-text']/i/text()").get();
                if (StringUtils.isNotEmpty(pages)) {
                    int pageCount = Integer.parseInt(pages);
                    // 将分页信息添加到爬虫列表
                    for (int i = 2; i <= pageCount; i++) { //这里需要排除第一页,默认第一次打开的页面即为第一页，因此从第2页开始，下标为2
                        String link = buildUrl(url, i);
                        page.addTargetRequest(link);
                    }
                }
            }
        }
    }

    public Site getSite() {
        return site;
    }


    /**
     * 构建商品分页url
     *
     * @param str
     * @return
     */
    private static String buildUrl(String str, int pageNumber) {
        String link = "http://list.jd.com/list.html?cat=";
        str = str.replace("http://list.jd.com/", "").replace(".html", "");
        if (str.contains("-")) {
            str = str.replace("-", ",");
            link = link + str + "&page=" + pageNumber;
        }
        return link;
    }

}
