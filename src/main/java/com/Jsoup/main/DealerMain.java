package com.Jsoup.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Jsoup.dao.DealerMapper;
import com.Jsoup.model.Dealer;

@Component
public class DealerMain {

    @Autowired
    private DealerMapper dealerMapper;
    
    public static void main(String[] args) {
        HttpClient client = new DefaultHttpClient();
        //我们要爬取的一个地址，这里可以从数据库中抽取数据，然后利用循环，可以爬取一个URL队列
        String url="https://dealer.autohome.com.cn/china/0/0/0/0/2/1/0/0.html";
        //抓取的数据
        try {
            List<Dealer> bookdatas=URLParser(client, url);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //循环输出抓取的数据
    }
    
    
    public void save(String address, Integer page){
        HttpClient client = new DefaultHttpClient();
        String url="https://dealer.autohome.com.cn/"+address+"/0/0/0/0/"+page+"/1/0/0.html";
        //抓取的数据
        try {
            List<Dealer> bookdatas=URLParser(client, url);
            for (Dealer dealer : bookdatas) {
                dealerMapper.insertSelective(dealer);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    public static List<Dealer> URLParser (HttpClient client, String url) throws Exception {
        //用来接收解析的数据
        List<Dealer> JingdongData = new ArrayList<Dealer>();
        //获取网站响应的html，这里调用了HTTPUtils类
        HttpResponse response = getRawHtml(client, url);      
        //获取响应状态码
        int StatusCode = response.getStatusLine().getStatusCode();
        //如果状态响应码为200，则获取html实体内容或者json文件
        if(StatusCode == 200){
            String entity = EntityUtils.toString (response.getEntity(),"utf-8");    
            JingdongData = getData(entity);
            EntityUtils.consume(response.getEntity());
        }else {
            //否则，消耗掉实体
            EntityUtils.consume(response.getEntity());
        }
        return JingdongData;
    }
    
    
    public static HttpResponse getRawHtml(HttpClient client, String personalUrl) {
        //获取响应文件，即html，采用get方法获取响应数据
        HttpGet getMethod = new HttpGet(personalUrl);
        HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1,
                HttpStatus.SC_OK, "OK");
        try {
            //执行get方法
            response = client.execute(getMethod);
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            // getMethod.abort();
        }
        return response;
    }
    
    
    public static List<Dealer> getData (String html) throws Exception{
        //获取的数据，存放在集合中
        List<Dealer> data = new ArrayList<Dealer>();
        //采用Jsoup解析
        Document doc = Jsoup.parse(html);
        //获取html标签中的内容
        Elements elements=doc.select("ul[class=list-box]").select("li[class=list-item]");
        for (Element ele:elements) {
            String shopName=ele.select("ul[class=info-wrap]").select("li").eq(0).select("a").select("span").html();
            String dealerName=ele.select("ul[class=info-wrap]").select("li").eq(1).select("span").select("em").html();
            String tel=ele.select("ul[class=info-wrap]").select("li").eq(2).select("span[class=tel]").html();
            String addr=ele.select("ul[class=info-wrap]").select("li").eq(3).select("span[class=info-addr]").html();
            String promotion=ele.select("ul[class=info-wrap]").select("li").eq(4).select("a").html();
            Dealer dealer = new Dealer();
            dealer.setAddress(addr);
            dealer.setDealerName(dealerName);
            dealer.setPhone(tel);
            dealer.setShopName(shopName);
            dealer.setPromotion(promotion);
            data.add(dealer);
        }
        //返回数据
        return data;
    }
    
}
