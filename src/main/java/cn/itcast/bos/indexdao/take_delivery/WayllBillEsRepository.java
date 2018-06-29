package cn.itcast.bos.indexdao.take_delivery;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import cn.itcast.bos.domain.take_delivery.WayBill;

//es的dao接口
public interface WayllBillEsRepository extends ElasticsearchRepository<WayBill, Integer>{

}
