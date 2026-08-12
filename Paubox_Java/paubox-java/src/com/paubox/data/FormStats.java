package com.paubox.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FormStats {

    @JsonProperty("active_form_count")
    private long activeFormCount;

    @JsonProperty("total_submission_count")
    private long totalSubmissionCount;

    @JsonProperty("submissions_last_7_days")
    private long submissionsLast7Days;

    public long getActiveFormCount() { return activeFormCount; }
    public void setActiveFormCount(long activeFormCount) { this.activeFormCount = activeFormCount; }

    public long getTotalSubmissionCount() { return totalSubmissionCount; }
    public void setTotalSubmissionCount(long totalSubmissionCount) { this.totalSubmissionCount = totalSubmissionCount; }

    public long getSubmissionsLast7Days() { return submissionsLast7Days; }
    public void setSubmissionsLast7Days(long submissionsLast7Days) { this.submissionsLast7Days = submissionsLast7Days; }
}
