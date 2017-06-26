package com.solr.controller;

import com.solr.domain.Product;
import com.solr.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 京东商品搜索
 */
@Controller
public class JDProductController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/jd", method = RequestMethod.GET)
    public String tosearch() {
        return "jd";
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jd");
        String queryString = request.getParameter("queryString");
        // 若什么都不输入，则表示搜索全部商品
        queryString = StringUtils.isEmpty(queryString) ? "" : queryString;
        String querystr = queryString.replaceAll(" ", "");// 分词不支持空格分隔
        String sort = request.getParameter("sort");
        String pageNumber = request.getParameter("pageNumber");
        String pageSize = request.getParameter("pageSize");
        if (StringUtils.isEmpty(queryString)) {
            return modelAndView;
        }
        try {
            if (StringUtils.isEmpty(pageNumber) || StringUtils.isEmpty(pageSize)) {
                pageNumber = String.valueOf("1");
                pageSize = String.valueOf("60");
            }
            Sort sort1;
            if(StringUtils.isEmpty(sort)){
                sort1 = new Sort(Sort.Direction.ASC, "id");
            }else{
                sort1 = new Sort(Sort.Direction.ASC, sort);
            }
            PageRequest pageRequest = new PageRequest(Integer.parseInt(pageNumber) - 1, Integer.parseInt(pageSize), sort1);
            Page<Product> query = productService.query(querystr, pageRequest);
            modelAndView.addObject("queryString", queryString);
            modelAndView.addObject("sort", sort);
            modelAndView.addObject("results", query.getContent());
            modelAndView.addObject("count", query.getTotalElements());
            modelAndView.addObject("pageNumber", pageNumber);
            modelAndView.addObject("pageSize", pageSize);
            modelAndView.addObject("totalPages", query.getTotalPages());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }
//    弃用自己写的html（界面太丑哈哈哈），如需使用，在pom中添加thymeleaf，去掉jsp 相关依赖
//    @RequestMapping(value = "/index", method = RequestMethod.GET)
//    public String jdsearch() {
//        return "jdsearch";
//    }
//    @RequestMapping(value = "/jdsearch", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> jdsearch(HttpServletRequest request) {
//        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
//        String queryString = request.getParameter("queryString");
//        // 若什么都不输入，则表示搜索全部商品
//        queryString = StringUtils.isEmpty(queryString) ? "" : queryString;
//        try {
//            PageRequest pageable = getPageRequest(request);
//            Page<Product> query = productService.query(queryString, pageable);
//            List list = query.getContent();
//            stringObjectHashMap.put("results", list);
//            stringObjectHashMap.put("totalElements", query.getTotalElements());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return stringObjectHashMap;
//    }
//    /**
//     * 获取分页请求
//     */
//    protected PageRequest getPageRequest(HttpServletRequest request){
//        int page = 1;
//        int size = 10;
//        Sort sort = null;
//        try {
//            String sortName = request.getParameter("sortName");
//            String sortOrder = request.getParameter("sortOrder");
//            if(StringUtils.isNotBlank(sortName) && StringUtils.isNotBlank(sortOrder)){
//                if(sortOrder.equalsIgnoreCase("desc")){
//                    sort = new Sort(Sort.Direction.DESC, sortName);
//                }else{
//                    sort = new Sort(Sort.Direction.ASC, sortName);
//                }
//            }
//            page = Integer.parseInt(request.getParameter("pageNumber")) - 1;
//            size = Integer.parseInt(request.getParameter("pageSize"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if(sort == null){
//            sort = new Sort(Sort.Direction.ASC, "id");
//        }
//        PageRequest pageRequest = new PageRequest(page, size, sort);
//        return pageRequest;
//    }

}
