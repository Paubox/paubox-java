package com.paubox.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FormListResponse {

    private List<Form> results;

    @JsonProperty("page_info")
    private PageInfo pageInfo;

    public List<Form> getResults() { return results; }
    public void setResults(List<Form> results) { this.results = results; }

    public PageInfo getPageInfo() { return pageInfo; }
    public void setPageInfo(PageInfo pageInfo) { this.pageInfo = pageInfo; }
}
