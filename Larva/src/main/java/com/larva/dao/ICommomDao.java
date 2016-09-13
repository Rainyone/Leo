package com.larva.dao;

import java.util.List;

import com.larva.model.AreaManage;
import com.larva.model.IspManage;

public interface ICommomDao {
	 List<AreaManage> selectProvAreas();
	 List<IspManage> selectIsps();
}
