package com.eims.pagination;

import lombok.Data;

/**
 * @Description TODO
 * @Auth zongyuan.ma
 * @Date 2022/7/11 14:18
 * @Version V 1.0.0
 */
@Data
public class Pagination {

    public Long total;

    public Integer pageSize;

    public Integer currentPage;

    public Integer totalPages;
}
