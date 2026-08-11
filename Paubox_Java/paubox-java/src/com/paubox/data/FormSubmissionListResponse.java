package com.paubox.data;

import java.util.List;

public class FormSubmissionListResponse {

    private List<FormSubmission> data;
    private long total;
    private int page;
    private int items;

    public List<FormSubmission> getData() { return data; }
    public void setData(List<FormSubmission> data) { this.data = data; }

    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public int getItems() { return items; }
    public void setItems(int items) { this.items = items; }

    @Override
    public String toString() {
        return "FormSubmissionListResponse [total=" + total + ", page=" + page + ", items=" + items + "]";
    }
}
