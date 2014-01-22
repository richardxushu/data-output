package com.taotaosou.data.himport.client.mq;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.StreamMessage;

import com.taotaosou.data.mq.proto.ProductLabelDataMessage.ProductLabelPBDataMessage;
import com.taotaosou.data.mq.subscriber.ActiveMQSubscriber;
import com.taotaosou.data.mq.util.PBDataParseUtil;

/**
 * 
 * ProductLabel消息客户端
 * 
 * @author lanxin.liao
 * @version 2013-9-16 下午2:11:26
 */
public class ProductLabelNotifyClient extends ActiveMQSubscriber {

	private MessageConsumer productConsumer;

	public void init() throws JMSException {
		if (productConsumer == null) {
			productConsumer = createConsumer();
		}
	}

	/**
	 * 收集(从库)有延迟的商品信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public ProductLabelPBDataMessage collectMessage() throws Exception {
		StreamMessage message = (StreamMessage) productConsumer.receive();
		return PBDataParseUtil.parseProductLabelMessage(message);
	}

}
