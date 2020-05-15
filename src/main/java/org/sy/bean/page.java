package org.sy.bean;

import java.util.List;

/**
 * @Auther: ？？？
 * @Date:2019/09/17 17:56
 * @Description: (qwq..)
 */
public class page {
    private Integer pageSize = 2;//每页数据
    private Integer curPage = 1;//当前第几页
    private List<?> list;
    private Long count; //有多少条数据

    private Long totals;//总页数

}
