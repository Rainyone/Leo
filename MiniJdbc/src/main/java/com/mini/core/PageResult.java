package com.mini.core;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装类
 * 
 * @author sxjun
 * @date 2015-3-16 下午10:58:31 
 *
 */
public class PageResult<T> implements Serializable {
	
	private static final long serialVersionUID = 8616490771677129692L;
	/**
	 * 当前页，从1开始
	 */
	private int pageIndex;	// 当前页，从1开始
	
	/**
	 * 每页的数据量
	 */
	private int pageSize;
	
	/**
	 * 页数
	 */
	private int pageCount;
	
	/**
	 * 总记录数
	 */
	private long resultCount;
	
	/**
	 * 记录
	 */
	private List<T> results;
	
	public PageResult(int pageIndex, int pageSize) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}
	
	public PageResult(List<T> results, int pageIndex, int pageSize, int resultCount) {
		this.pageIndex = pageIndex;
		// 默认20，如果pageSize非大于0的时候，采用默认值
		this.pageSize = pageSize > 0 ? pageSize : 20;
		this.resultCount = resultCount;
		this.pageCount = (resultCount / pageSize + (resultCount % pageSize == 0 ? 0 : 1));
		this.results = results;
	}
	
	public PageResult(int pageIndex, int pageSize, int resultCount) {
		this.pageIndex = pageIndex;
		// 默认20，如果pageSize非大于0的时候，采用默认值
		this.pageSize = pageSize > 0 ? pageSize : 20;
		this.resultCount = resultCount;
		this.pageCount = (resultCount / pageSize + (resultCount % pageSize == 0 ? 0 : 1));
	}
	
	/**
	 * 获取当前页，从第1页开始
	 * @return
	 */
	public int getPageIndex() {
		return pageIndex;
	}
	
	/**
	 * 设置当前页
	 * @param pageIndex
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	/**
	 * 获取每页数据量
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * 设置每页的数据量
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 获取页数
	 * @return
	 */
	public int getPageCount() {
		this.pageCount = (int)(resultCount / pageSize + (resultCount % pageSize == 0 ? 0 : 1));
		return pageCount;
	}
	
	/**
	 * 设置页数
	 * @param pageCount
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	/**
	 * 获取总记录数
	 * @return
	 */
	public long getResultCount() {
		return resultCount;
	}
	
	/**
	 * 设置总记录数
	 * @param resultCount
	 */
	public void setResultCount(long resultCount) {
		this.resultCount = resultCount;
	}
	
	/**
	 * 获取记录
	 * @return
	 */
	public List<T> getResults() {
		return results;
	}
	
	/**
	 * 设置记录
	 * @param results
	 */
	public void setResults(List<T> results) {
		this.results = results;
	}
	
	/**
	 * 是否有下一页
	 * @return
	 */
	public boolean hasNextPage() {
		return pageIndex < pageCount ? true : false;
	}
	
	/**
	 * 是否有上一页
	 * @return
	 */
	public boolean hasPrevPage() {
		return pageIndex > 1 ? true : false;
	}
	
	/**
	 * 获取第一条数据的index
	 * @return
	 */
	public int getFirstRecordIndex() {
		if (pageIndex > 0 && pageSize > 0) {
			return (pageIndex - 1) * pageSize;
		} else {
			return 0;
		}
	}
}
