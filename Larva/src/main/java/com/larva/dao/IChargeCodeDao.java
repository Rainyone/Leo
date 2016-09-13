package com.larva.dao;

import com.github.pagehelper.PageInfo;
import com.larva.model.Account;
import com.larva.model.AppManage;
import com.larva.model.ChargeCode;
import com.larva.model.Department;
import com.mini.core.PageResult;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author sxjun
 * @time 2015/8/27 16:22
 */
public interface IChargeCodeDao  {
    int save(ChargeCode chargeCode);
    PageResult<ChargeCode> selectChargeCodes(int pageNow, int pageSize);
    /**
     * 根据id删除计费代码
     * @param id
     * @return
     */
	int deleteChargeCode(String id,String userId);
	/**
	 * 修改计费代码
	 * @param cc
	 * @return
	 */
	int editChargeCode(ChargeCode cc);
}
