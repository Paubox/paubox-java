package com.paubox.data;

/**
 * Query-parameter holder for listing forms. This object is never JSON-serialized;
 * its fields are appended to the request URL as query parameters. Null fields are omitted.
 */
public class FormListRequest {

    private Integer customerId;
    private String formId;
    private String search;
    private String order;
    private String orderBy;
    private Boolean archived;
    private Boolean active;
    private Integer page;
    private Integer items;

    public FormListRequest() {
    }

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public String getFormId() { return formId; }
    public void setFormId(String formId) { this.formId = formId; }

    public String getSearch() { return search; }
    public void setSearch(String search) { this.search = search; }

    public String getOrder() { return order; }
    public void setOrder(String order) { this.order = order; }

    public String getOrderBy() { return orderBy; }
    public void setOrderBy(String orderBy) { this.orderBy = orderBy; }

    public Boolean getArchived() { return archived; }
    public void setArchived(Boolean archived) { this.archived = archived; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }

    public Integer getItems() { return items; }
    public void setItems(Integer items) { this.items = items; }
}
