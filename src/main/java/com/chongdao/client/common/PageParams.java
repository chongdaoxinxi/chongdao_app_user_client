

package com.chongdao.client.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Transient;

public class PageParams {
    @Transient
    private int currPage = 1;
    @Transient
    private int pageSize = 10;
    @Transient
    private boolean isPage = false;
    @Transient
    private int queryCurrPage;
    
    @Transient
	private Boolean sort = false;
    @Transient
	private String sortcolumn1;
    @Transient
	private String sortcolumntype1;
    @Transient
	private String sortcolumn2;
    @Transient
	private String sortcolumntype2;

    public PageParams() {
    }

    @JsonIgnore
    public int getCurrPage() {
        return this.currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    @JsonIgnore
    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @JsonIgnore
    public boolean isPage() {
        return this.isPage;
    }

    public void setPage(boolean page) {
        this.isPage = page;
    }

    @JsonIgnore
    public int getQueryCurrPage() {
        return (this.getCurrPage() - 1) * this.getPageSize();
    }

    public void setQueryCurrPage(int queryCurrPage) {
        this.queryCurrPage = queryCurrPage;
    }
    

	public Boolean getSort() {
		return sort;
	}

	public void setSort(Boolean sort) {
		this.sort = sort;
	}

	public String getSortcolumn1() {
		return sortcolumn1;
	}

	public void setSortcolumn1(String sortcolumn1) {
		this.sortcolumn1 = sortcolumn1;
	}

	public String getSortcolumntype1() {
		return sortcolumntype1;
	}

	public void setSortcolumntype1(String sortcolumntype1) {
		this.sortcolumntype1 = sortcolumntype1;
	}

	public String getSortcolumn2() {
		return sortcolumn2;
	}

	public void setSortcolumn2(String sortcolumn2) {
		this.sortcolumn2 = sortcolumn2;
	}

	public String getSortcolumntype2() {
		return sortcolumntype2;
	}

	public void setSortcolumntype2(String sortcolumntype2) {
		this.sortcolumntype2 = sortcolumntype2;
	}
	
}
