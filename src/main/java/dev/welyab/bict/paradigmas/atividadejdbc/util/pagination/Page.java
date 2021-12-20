package dev.welyab.bict.paradigmas.atividadejdbc.util.pagination;

public class Page {

    private Integer pageNumber;
    private Integer pageSize;

    public Page(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return pageNumber * pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Page nextPage() {
        return new Page(pageNumber + 1, pageSize);
    }
}
