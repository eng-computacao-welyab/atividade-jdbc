package dev.welyab.bict.paradigmas.atividadejdbc.util.pagination;

import java.util.List;

public class PagedResult<T> {

    private final List<T> values;
    private final Page page;

    public PagedResult(List<T> values, Page page) {
        this.values = values;
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    public List<T> getValues() {
        return values;
    }

    public int getResultSize() {
        return values.size();
    }

    public boolean hasNextPage() {
        return values.size() < page.getPageSize();
    }

    public Page nextPage() {
        if (!hasNextPage()) {
            throw new IllegalStateException("No next page available");
        }
        return page.nextPage();
    }
}
