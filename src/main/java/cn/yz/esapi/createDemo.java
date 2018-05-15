package cn.yz.esapi;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class createDemo {
	public static void main(String args[]) throws UnknownHostException {
		//1、设置集群名称
		Settings settings = Settings.builder().put("cluster.name","elasticsearch").build();
		//2、创建client
		TransportClient client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new TransportAddress(InetAddress.getByName("node1"),9300));
		//3、获取IndicesAdminClient对象
		IndicesAdminClient indiceAdminClient = client.admin().indices();
		//4、创建索引
		CreateIndexResponse ciResponse = indiceAdminClient.prepareCreate("index1").get();
		System.out.println(ciResponse.isAcknowledged());
	}
}
