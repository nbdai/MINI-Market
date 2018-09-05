package com.oracle.sport.staticpage;

import freemarker.template.Template;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import javax.servlet.ServletContext;
import java.io.*;
import java.util.Map;

/**
 * @author DZL
 * @desc 生成静态化页面的类.
 */

public class StaticPageServiceImpl implements ServletContextAware {
    //xml方式注入需要提供getter
    private FreeMarkerConfigurer freeMarkerConfigurer;
    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }
    //静态化 商品  ActiveMQ
    public void productStaticPage(Map<String,Object> root, String id){
        //输出的路径  全路径
        String path = getPath("/html/" + id + ".html");
        System.out.println(path);
        File f = new File(path);
        File parentFile = f.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }
        Writer out = null;
        try {
            //读文件  UTF-8
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("product.html");

            //输出  UTF-8
            out = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
            //处理

            //替换死数据
            template.process(root, out);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            if(null != out){
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


    //获取路径
    public String getPath(String name){
        return aplication.getRealPath(name);
    }
    //声明
    private ServletContext aplication;
    @Override
    public void setServletContext(ServletContext servletContext) {
        // TODO Auto-generated method stub
        this.aplication = servletContext;
    }

    //静态化订单
}
