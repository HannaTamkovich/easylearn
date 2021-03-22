package com.easylearn.easylearn.category.dto;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CategoryParam {

    @NotNull
    private String name;

    @Nullable
    private Set<Long> wordIds = new HashSet<>(1);
}
