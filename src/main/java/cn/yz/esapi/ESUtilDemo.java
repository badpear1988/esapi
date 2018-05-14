package cn.yz.esapi;

import cn.yz.esapi.es.*;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;


public class ESUtilDemo {
	public static void main(String args[]) {
		//1、判定索引是否存在
		boolean flag = ESUtil.isExists("yy");
		System.out.println("isExists:" + flag);
		//2、创建索引
		flag = ESUtil.createIndex("twitter", 3, 0);
		System.out.println("createIndex:" + flag);
		//3、设置mapping
		try {
            XContentBuilder builder = jsonBuilder()
                    .startObject()
                    .startObject("properties")
                    .startObject("user")
                    .field("type", "text")
                    .endObject()
                    .startObject("postdate")
                    .field("type", "date")
                    .field("format", "yyyy-MM-dd HH:mm:ss")
                    .endObject()
                    .startObject("message")
                    .field("type", "text")
                    .field("analyzer", "ik_max_word")
                    .field("search_analyzer", "ik_max_word")
                    .endObject()
                    .endObject()
                    .endObject();
            System.out.println(builder.string());
            ESUtil.setMapping("twitter", "tweet", builder.string());   
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
