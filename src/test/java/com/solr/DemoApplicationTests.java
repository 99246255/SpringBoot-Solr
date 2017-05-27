package com.solr;

import com.solr.webmagic.JDPipeline;
import com.solr.webmagic.JDProductProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.monitor.SpiderMonitor;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	JDPipeline jdPipeline;

	/**
	 * 京东爬虫
	 * @throws Exception
	 */
	@Test
	public void testJDProductProcessor() throws Exception{
		//chromedriver.exe需与浏览器版本对应
		String chromeDriverPath = JDProductProcessor.class.getClassLoader().getResource("chromedriver.exe").getFile();
		Spider jdSpider = Spider.create(new JDProductProcessor())
				.addUrl("http://www.jd.com/allSort.aspx")// JD全部分类
				.addPipeline(jdPipeline)
				.setDownloader(new SeleniumDownloader(chromeDriverPath))
				.thread(5);
		// 注册爬虫监控
		SpiderMonitor.instance().register(jdSpider);
		jdSpider.run();
	}

}
