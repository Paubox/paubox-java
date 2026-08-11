package com.paubox.data;

public class PageInfo {

    private long count;
    private int pages;
    private int page;
    private int items;

    public long getCount() { return count; }
    public void setCount(long count) { this.count = count; }

    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public int getItems() { return items; }
    public void setItems(int items) { this.items = items; }
}
