package com.easylearn.easylearn.core.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    private Collection<T> content;
    private long totalPages;
    private long totalElements;
    private long number;

}