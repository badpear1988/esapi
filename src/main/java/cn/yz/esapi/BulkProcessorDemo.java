package cn.yz.esapi;

import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import cn.yz.esapi.es.ESUtil;

public class BulkProcessorDemo {
	public static void main(String args[]) throws Exception{
		TransportClient client = ESUtil.getCilent();
		BulkProcessor bulkProcessor = BulkProcessor.builder(
				client, 
				new BulkProcessor.Listener() {
					@Override
					public void berforeBulk(long executionId, BulkRequest request) {
						//设置bulk批处理的预备工作
						System.out.println("请求数：" + request.numberOfActions());
					}
					@Override
					public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
						//设置bulk批处理的善后工作
						if(!response.hasFailures()) {
							System.out.println("执行成功!");
						}else {
							System.out.println("执行失败!");
						}
					}
					@Override
					public void afterBulk(long executionId, BulkRequest request,Throwable failure) {
						System.out.println(failure);
					}
				})
				.setBulkActions(1000)//设置提交批处理操作的请求阀值数
				.setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))//设置提交批处理操作的请求大小阀值
				.setFlushInterval(TimeValue.timeValueSeconds(5))//设置刷新索引时间间隔
				.setConcurrentRequests(1)//设置并发处理线程数
				//设置回滚策略，等待时间为100ms，retry次数为3次
				.setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100),3))
				.build();
	}
}
