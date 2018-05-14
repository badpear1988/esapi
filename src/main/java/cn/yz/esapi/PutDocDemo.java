package cn.yz.esapi;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import cn.yz.esapi.es.*;

public class PutDocDemo {
	public static void main(String args[]) throws Exception {
		TransportClient client = ESUtil.getCilent();
		String json = "{" +
				"\"id\":\"1\"," +
				"\"title\":\"Java涉及模式之装饰模式\"," +
				"\"content\":\"在不必改变原类文件和使用继承的情况下，动态地扩展一个对象的功能。\"," +
                "\"postdate\":\"2018-02-03 14:38:00\"," +
                "\"url\":\"csdn.net/79239072\"" +
                "}";
		System.out.println(json);
		IndexResponse response = client.prepareIndex("yy","blog","1")
				.setSource(json,XContentType.JSON)
				.get();
		System.out.println(response.status());
		
		XContentBuilder doc1 = jsonBuilder()
				.startObject()
				.field("id", "2")
				.field("title", "Java设计模式之单例模式")
				.field("content", "枚举单例模式可以防反射攻击。")
				.field("postdate", "2018-05-10 19:53:00")
				.field("url", "csdn.net/79247746")
				.endObject();
		System.out.println(doc1.toString());
		response = client.prepareIndex("yy","blog","2")
				.setSource(doc1)
				.get();
		System.out.println(response.status());
		
	}
}
