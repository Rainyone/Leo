package com.larva.service.impl;

import com.github.pagehelper.PageInfo;
import com.larva.dao.ILoginLogDao;
import com.larva.model.Department;
import com.larva.model.LoginLog;
import com.larva.service.ILogService;
import com.larva.utils.Constants;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.mini.core.PageResult;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sxjun on 15-9-12.
 */
@Service("logService")
public class LogServiceImpl implements ILogService {
    @Resource
    private ILoginLogDao loginLogDao;
    /**
     *  {title: '账号', field: 'account', width: '10%'},
                    {field: 'loginIp', title: 'IP', width: '10%'},
                    {field: 'showLoginTime', title: '登录时间', width: '10%'},
                    {field: 'address', title: '登录地址', width: '10%'},
                    {field: 'detailAddress', title: '详细地址', width: '15%'},
                    {field: 'pointX', title: '经度', width: '10%'},
                    {field: 'pointY', title: '纬度', width: '10%'},
                    {field: 'province', title: '省', width: '5%'},
                    {field: 'city', title: '城市', width: '5%'},
                    {field: 'cityCode', title: '城市码', width: '5%'}
     * @param loginLog
     * @return
     */
    private Map<String,Object> getLogMap(LoginLog loginLog){
    	Map<String,Object> m = new HashMap<String,Object>();
		m.put("account", loginLog.getAccount());
		m.put("loginIp", loginLog.getLoginIp());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        loginLog.setShowLoginTime(format.format(loginLog.getLoginTime()));
		m.put("showLoginTime", loginLog.getShowLoginTime());
		m.put("address ", loginLog.getAddress());
		m.put("detailAddress ", loginLog.getDetailAddress());
		m.put("pointX ", loginLog.getPointX());
		m.put("pointY ", loginLog.getPointY());
		m.put("province ", loginLog.getProvince());
		m.put("city ", loginLog.getCity());
		m.put("cityCode ", loginLog.getCityCode());
		return m;
    }

    /**
     * 查询登录日志
     *
     * @param page
     * @param count
     * @return
     */
    @Override
    public Pager<Map<String,Object>> queryLoginLog(PagerReqVO pagerReqVO) {
    	List<Map<String,Object>> resultLogs = new ArrayList<Map<String,Object>>();
    	PageResult<LoginLog> pageInfo = loginLogDao.query(pagerReqVO.getPageNo(), pagerReqVO.getLimit());
        Map<String, Object> dataMap = new HashMap<>();
        List<LoginLog> list = pageInfo.getResults();
        for (LoginLog loginLog : list) {
        	resultLogs.add(getLogMap(loginLog));
        }
        return new Pager(resultLogs,pageInfo.getResultCount());
    }

    /**
     * 获取所有用户分布地址坐标
     *
     * @return
     */
    public ResultVO getAllUserLocations() {

        ResultVO resultVO = new ResultVO(true);

        PageResult<LoginLog> pageInfo = loginLogDao.query(1, Integer.MAX_VALUE);

        List<LoginLog> list = pageInfo.getResults();

        List<Map<String, Double>> mapList = new ArrayList<>();

        for (LoginLog loginLog : list) {
            BigDecimal pointX = loginLog.getPointX();
            BigDecimal pointY = loginLog.getPointY();
            if (pointX != null && pointY != null) {
                Map<String, Double> map = new HashMap<>();
                map.put("x", pointX.doubleValue());
                map.put("y", pointY.doubleValue());
                mapList.add(map);
            }
        }
        resultVO.setData(mapList);
        return resultVO;

    }

	@Override
	public int insertLoginLog(LoginLog loginLog) {
		return loginLogDao.insertLoginLog(loginLog);
	}
}
