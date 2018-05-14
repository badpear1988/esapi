package cn.yz.esapi;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ExitsDemo {
	public static void main(String args[]) throws UnknownHostException {
		//1、设置集群名称
		Settings settings = Settings.builder().put("cluster.name","elasticsearch").build();
		//2、创建client
		TransportClient client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new TransportAddress(InetAddress.getByName("node2"),9300));
		//3、获取IndicesAdminClient对象
		IndicesAdminClient indicesAdminClient = client.admin().indices();
		//4、判断索引是否存在
		IndicesExistsResponse reponse = indicesAdminClient.prepareExists("website").get();
		System.out.println(reponse.isExists());
	}
}
