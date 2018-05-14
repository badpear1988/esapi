package cn.yz.esapi;

import cn.yz.esapi.es.*;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.transport.TransportClient;

public class DeleteDocDemo {
	public static void main(String args[]) {
		TransportClient client = ESUtil.getCilent();
		DeleteResponse response = client.prepareDelete("yy", "blog", "1").get();
		System.out.println(response.status());
		System.out.println(response.getType());
		System.out.println(response.getId());
		System.out.println(response.getVersion());
	}
}
