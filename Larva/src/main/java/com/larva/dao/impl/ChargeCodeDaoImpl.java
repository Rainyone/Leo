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


@Repository("chargeCodeDao")
public class ChargeCodeDaoImpl extends MiniDao implements IChargeCodeDao {
	
    @Override
    public int save(ChargeCode chargeCode) {
        return this.insert(chargeCode);
    }

	@Override
	public PageResult<ChargeCode> selectChargeCodes(int pageNow, int pageSize) {
		return this.paginateResult("select * from t_charge_code where state = 1 order by create_time desc ", pageNow, pageSize, ChargeCode.class);
	}

	@Override
	public int deleteChargeCode(String id,String userId) {
		String updateById = "update t_charge_code set state = 0,update_people_name=? where id = ? and state =1";
		return this.execute(updateById, userId,id);
	}

	@Override
	public int editChargeCode(ChargeCode cc) {
		String updateSql = "update `t_charge_code` set `send_type` = ? , `ver_code_url` = ? , `charge_code` = ? , `code_name` = ? , "
				+ " `linke_name` = ? , `inf_type` = ? , `return_form` = ? ,  `back_msg_type` = ? , `date_limit` = ? , "
				+ " `back_form` = ? , `month_limit` = ? , `channel_type` = ? , `update_people_name` = ? , "
				+ " `order_id_code` = ?  , `url` = ? , `success_flag` = ? , `order_back` = ? , `detail` = ?,`phone_no` = ?,`ver_code_success_flag` = ?,`callbacksuccess` = ?,`callbackcolumn` = ? where `id` = ?";
		return this.execute(updateSql, cc.getSendType(),cc.getVerCodeUrl(),cc.getChargeCode(),cc.getCodeName(),cc.getLinkeName(),cc.getInfType(),
				cc.getReturnForm(),cc.getBackMsgType(),cc.getDateLimit(),cc.getBackForm(),cc.getMonthLimit(),cc.getChannelType(),cc.getUpdatePeopleName(),
				cc.getOrderIdCode(),cc.getUrl(),cc.getSuccessFlag(),cc.getOrderBack(),cc.getDetail(),cc.getPhoneNo(),cc.getVerCodeSuccessFlag(),cc.getCallbacksuccess(),cc.getCallbackcolumn(),cc.getId());
	}

}
