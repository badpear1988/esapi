package cn.yz.esapi;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.transport.TransportClient;

import cn.yz.esapi.es.*;


public class MultiGetDemo {
	public static void main(String args[]) {
		TransportClient client = ESUtil.getCilent();
		MultiGetResponse mgResponse = client.prepareMultiGet()
				.add("yy", "blog", "1", "2")
				.add("my-index", "persion", "1", "2" ,"2")
				.get();
		for (MultiGetItemResponse response:mgResponse) {
			GetResponse rp = response.getResponse();
			if(rp!=null && rp.isExists()) {
				System.out.println(rp.getSourceAsString());
			}
		}
	}
}
