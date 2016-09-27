package com.larva.model;

import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;
import java.io.Serializable;
import java.util.Date;

@Entity(table="t_app_manage", id={"id"}, strategy=StrategyType.NULL)
public class AppManage extends WeakEntity
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  public String getId()
  {
    return super.getStr("id");
  }

  public AppManage setId(String id) {
    super.set("id", id);
    return this;
  }

  public Date getUpdateTime() {
    return (Date)super.get("update_time");
  }

  public AppManage setUpdateTime(Date update_time) {
    super.set("update_time", update_time);
    return this;
  }

  public static final class Columns
  {
    public static final String id = "id";
    public static final String update_time = "update_time";
  }
}