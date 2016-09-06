package com.larva.inf.controller;

import com.larva.model.LogOrder;
import com.larva.service.InfService;
import com.larva.service.impl.InfServiceImpl;

public class InfLogOrderThread implements Runnable {
	private InfService infService = new InfServiceImpl();
	private LogOrder logOrder = new LogOrder();
	private int flag = -1;//新增标记
	private int oldState = -1;
	/**
	 * 
	 * @param infService 
	 * @param logOrder 订单日志对象
	 * @param oldState 原来的状态
	 * @param flag 更新标示：0新增
	 */
	public InfLogOrderThread(InfService infService,LogOrder logOrder,int oldState,int flag){
		this.logOrder = logOrder;
		this.flag = flag;
		this.infService = infService;
		this.oldState = oldState;
	}
	@Override
	public void run() {
		if(flag==0){//新增
			infService.saveLogOrder(logOrder);
		}else if(flag==1){//更新
			infService.updateLogOrder(logOrder.getId(), logOrder.getOrderState(), oldState);
		}else if(flag==2){//验证码更新
			infService.updateLogOrderByOrderNo(logOrder.getOrderNo(), logOrder.getOrderState());
		}
	}

}
