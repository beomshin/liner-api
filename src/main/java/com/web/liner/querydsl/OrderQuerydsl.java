package com.web.liner.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.web.liner.vo.OrderTb;

import javax.annotation.Resource;
import static com.web.liner.vo.QOrderTb.orderTb;

@Resource
public class OrderQuerydsl {

    private final JPAQueryFactory jpaQueryFactory;

    public OrderQuerydsl(JPAQueryFactory jpaQueryFactory) {
        super();
        this.jpaQueryFactory = jpaQueryFactory;
    }


}
