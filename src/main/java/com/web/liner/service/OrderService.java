package com.web.liner.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.web.liner.vo.OrderTb;

@Service
public interface OrderService {
	OrderTb applyOrder(OrderTb order) throws Exception;
	OrderTb searchOrder(String orderCode, String phone) throws Exception;
	OrderTb searchOrder(long orderId) throws Exception;
	List<OrderTb> searchOrderList(String orderCode, String phone, String name, String from, String to, int pageNum, int curPage) throws Exception;
	int searchOrderListCount(String orderCode, String phone, String name, String from, String to)  throws Exception;
	OrderTb assignWorkerToOrder(long workerId, long orderId);
	OrderTb updateOrderState(long orderId, int state) throws Exception;
	OrderTb updateOrderStateByWorkerId(long workerId, int state) throws Exception;
	OrderTb updateOrderWorkerIdNull(long orderId) throws  Exception;
}
