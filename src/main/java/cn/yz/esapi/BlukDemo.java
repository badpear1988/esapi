package cn.yz.esapi;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;

import cn.yz.esapi.es.ESUtil;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

import java.io.IOException;
import java.util.Date;

public class BlukDemo {
	public static void main(String args[]) throws Exception {
		TransportClient client = ESUtil.getCilent();
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		
		bulkRequest.add(client.prepareIndex("twitter", "tweet", "1")
				.setSource(jsonBuilder()
						.startObject()
						.field("user", "kimchy")
						.field("postDate", new Date())
						.field("message", "trying out Elasticsearch")
						.endObject()
				)
		);
		bulkRequest.add(client.prepareIndex("twitter", "tweet", "2")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "another post")
                        .endObject()
                )
        );
		//批量执行
		BulkResponse bulkResponse = bulkRequest.get();
		System.out.println(bulkResponse.status());
		if(bulkResponse.hasFailures()) {
			System.out.println("存在失败操作");
		}
	}
}
