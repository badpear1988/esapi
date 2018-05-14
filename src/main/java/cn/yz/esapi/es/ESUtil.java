package cn.yz.esapi.es;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ESUtil {
	//集群名，默认为elasticsearch
	private static final String CLUSTER_NAME = "elasticsearch";
	//ES集群中某个节点
	private static final String HOSTNAME = "node2";
	//连接端口号
	private static final int TCP_PORT = 9300;
	//构建Settings对象
	private static final Settings settings = Settings.builder().put("cluster.name",CLUSTER_NAME).build();
	//TransportClient对象，用于连接ES集群
	private static volatile TransportClient client;
	
	/**
     * 同步synchronized(*.class)代码块的作用和synchronized static方法作用一样,
     * 对当前对应的*.class进行持锁,static方法和.class一样都是锁的该类本身,同一个监听器
     * @return
     * @throws UnknownHostException
     */
	public static TransportClient getCilent() {
		if(client == null) {
			synchronized (TransportClient.class) {
				try {
					client = new PreBuiltTransportClient(settings)
							.addTransportAddress(new TransportAddress(InetAddress.getByName(HOSTNAME),TCP_PORT));
				}catch (UnknownHostException e){
					e.printStackTrace();
				}
			}
		}
		return client;
	}
	/**
	 * 获取索引管理的IndicesAdminClient
	 * 
	 */
	public static IndicesAdminClient getAdminClient() {
		return getCilent().admin().indices();
	}
	/**
	 * 判定索引是否存在
	 * @param indexName
	 * @return
	 */
	public static boolean isExists(String indexName) {
		IndicesExistsResponse reponse = getAdminClient().prepareExists(indexName).get();
		return reponse.isExists()?true:false;
	}
	/**
	 * 创建索引
	 * @param indexName 索引名
	 * @param shards 分片数
	 * @param replicas 副本数
	 */
	public static boolean createIndex(String indexName,int shards,int replicas) {
		Settings settings = Settings.builder()
				.put("index.number_of_shards", shards)
				.put("index.number_of_replicas", replicas)
				.build();
		CreateIndexResponse createIndexResponse = getAdminClient()
				.prepareCreate(indexName.toLowerCase())
				.setSettings(settings)
				.execute().actionGet();
		return createIndexResponse.isAcknowledged()?true:false;
	}
	/**
	 * 为索引indeName设置mapping
	 * @param indeName
	 * @param typeName
	 * @param mapping
	 */
	public static void setMapping(String indexName,String typeName,String mapping) {
		getAdminClient().preparePutMapping(indexName)
			.setType(typeName)
			.setSource(mapping,XContentType.JSON)
			.get();
	}
	/**
	 * 删除索引
	 * @param indexName
	 * @return
	 */
	public static boolean deleteIndex(String indexName) {
		DeleteIndexResponse deleteIndexResponse = getAdminClient()
				.prepareDelete(indexName.toLowerCase())
				.execute()
				.actionGet();
		return deleteIndexResponse.isAcknowledged()?true:false;
	}
}
