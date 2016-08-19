package com.larva.vo;

public class TreeNode {
	private String id;//节点
	private String pId;//父节点
	private String name;//节点名称
	private boolean open=true;//是否展开
	private boolean isParent=false;//是否为父节点
	private boolean chkDisabled=false;
	private boolean nocheck=false;//是否选择
	private boolean checked=false;
	private String icon;//图片
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public boolean isParent() {
		return isParent;
	}
	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public boolean isNocheck() {
		return nocheck;
	}
	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}
	public boolean isChkDisabled() {
		return chkDisabled;
	}
	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
