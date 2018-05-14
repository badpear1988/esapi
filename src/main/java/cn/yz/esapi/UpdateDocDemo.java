package cn.yz.esapi;

import cn.yz.esapi.es.*;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;

public class UpdateDocDemo {
	public static void main(String args[]) throws Exception {
		TransportClient client = ESUtil.getCilent();
		UpdateRequest request = new UpdateRequest();
		request.index("yy").type("blog").id("2").doc(
				jsonBuilder().startObject()
				.field("title", "单例模式解读")
				.endObject()
				);
		UpdateResponse response = client.update(request).get();
		System.out.println(response.status());
		System.out.println(response.getType());
		System.out.println(response.getId());
		System.out.println(response.getVersion());
	}
}
