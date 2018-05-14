package cn.yz.esapi;

import org.elasticsearch.client.transport.TransportClient;

import cn.yz.esapi.es.*;

public class getClient {
	public static void main(String args[]) {
		TransportClient client = ESUtil.getCilent();
		System.out.println(client);
	}
}
