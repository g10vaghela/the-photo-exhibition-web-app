package com.photoexhibition.service.search.criteria;

public class BaseSearchCriteria {
	protected boolean isPagination;
	protected int paginationPage;
	protected int paginationDelta;
	public boolean isPagination() {
		return isPagination;
	}
	public void setPagination(boolean isPagination) {
		this.isPagination = isPagination;
	}
	public int getPaginationPage() {
		return paginationPage;
	}
	public void setPaginationPage(int paginationPage) {
		this.paginationPage = paginationPage;
	}
	public int getPaginationDelta() {
		return paginationDelta;
	}
	public void setPaginationDelta(int paginationDelta) {
		this.paginationDelta = paginationDelta;
	}
}
