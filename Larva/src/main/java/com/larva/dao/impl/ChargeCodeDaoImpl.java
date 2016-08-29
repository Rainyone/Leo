package com.larva.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.larva.dao.IAccountDao;
import com.larva.dao.IAppManageDao;
import com.larva.dao.IChargeCodeDao;
import com.larva.model.Account;
import com.larva.model.AppManage;
import com.larva.model.ChargeCode;
import com.mini.core.PageResult;
import com.mini.core.dao.IMiniDao;
import com.mini.core.dao.MiniDao;

/**
 * @author sxjun
 * @time 2015/8/27 16:23
 */

@Repository("chargeCodeDao")
public class ChargeCodeDaoImpl extends MiniDao implements IChargeCodeDao {
	
    @Override
    public int save(ChargeCode chargeCode) {
        return this.insert(chargeCode);
    }

	@Override
	public PageResult<ChargeCode> selectChargeCodes(int pageNow, int pageSize) {
		return this.paginateResult("select * from t_charge_code where state = 1 ", pageNow, pageSize, ChargeCode.class);
	}

}
