package com.paubox.data;

/**
 * Query-parameter holder for listing form submissions. This class is never
 * serialized to JSON; its fields become URL query parameters. Null fields are
 * omitted so the API defaults apply.
 */
public class FormSubmissionListRequest {

    private String submissionId;
    private String order;
    private String orderBy;
    private Integer page;
    private Integer items;

    public FormSubmissionListRequest() {
    }

    public String getSubmissionId() { return submissionId; }
    public void setSubmissionId(String submissionId) { this.submissionId = submissionId; }

    public String getOrder() { return order; }
    public void setOrder(String order) { this.order = order; }

    public String getOrderBy() { return orderBy; }
    public void setOrderBy(String orderBy) { this.orderBy = orderBy; }

    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }

    public Integer getItems() { return items; }
    public void setItems(Integer items) { this.items = items; }
}
