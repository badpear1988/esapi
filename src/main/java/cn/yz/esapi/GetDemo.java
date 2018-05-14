package cn.yz.esapi;

import cn.yz.esapi.es.*;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;

public class GetDemo {
	public static void main(String args[]) {
		TransportClient client = ESUtil.getCilent();
		GetResponse response = client.prepareGet("yy","blog","1").get();
		System.out.println(response.isExists());
        System.out.println(response.getIndex());
        System.out.println(response.getType());
        System.out.println(response.getId());
        System.out.println(response.getVersion());
        String source=response.getSourceAsString();
        System.out.println(source);
	}
}
