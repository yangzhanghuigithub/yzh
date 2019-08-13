package com.learn.yzh.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {

    private Integer pageNumber;

    private Integer pageSize;

    private Long total;

    private List<T> rows;
}
