package com.loanflow.common.dto.application.response;


import com.loanflow.common.dto.response.PagedResponse;
import lombok.Builder;

import java.util.List;

public class ApplicationSearchResponse extends PagedResponse<ApplicationSummaryItem> {
    @Builder(builderMethodName = "searchBuilder")
    public ApplicationSearchResponse(List<ApplicationSummaryItem> content,
                                     int page,
                                     int size,
                                     long totalElements,
                                     int totalPages){
        super(content, page, size, totalElements, totalPages, page == totalPages - 1);
    }
}
