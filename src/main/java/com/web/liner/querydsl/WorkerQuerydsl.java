package com.web.liner.querydsl;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.web.liner.vo.WorkerTb;
import org.springframework.stereotype.Repository;
import static com.web.liner.vo.QWorkerTb.workerTb;
import static com.web.liner.vo.QAccountTb.accountTb;
import static com.web.liner.vo.QOrderTb.orderTb;

import java.util.List;

@Repository
public class WorkerQuerydsl {

    private final JPAQueryFactory jpaQueryFactory;

    public WorkerQuerydsl(JPAQueryFactory jpaQueryFactory) {
        super();
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<WorkerTb> findWorkerList(String phone, String name, int authFlag, int pageNum, int curPage){
        return jpaQueryFactory.select(Projections.fields(WorkerTb.class,
                    workerTb.regDt,
                    workerTb.authFlag,
                    workerTb.name,
                    workerTb.phone,
                    workerTb.workerId,
                    workerTb.kakaoId,
                    workerTb.accountTb.account,
                    workerTb.accountTb.bank,
                    workerTb.state,
                    workerTb.count
//                    ExpressionUtils.as(JPAExpressions
//                            .select(orderTb.count())
//                            .from(orderTb)
//                            .where(orderTb.workerTb.workerId.eq(workerTb.workerId), orderTb.state.eq(4)), "count")
                ))
                .from(workerTb)
                .join(accountTb).on(workerTb.accountTb.accountId.eq(accountTb.accountId))
                .where(workerTb.phone.like("%" + phone + "%"), workerTb.name.like("%" + name + "%"), workerTb.authFlag.eq(authFlag))
                .offset(curPage * pageNum)
                .limit(pageNum)
                .fetch();
    }

    public long findWorkerListCount(String name, String phone, int authFlag){
        return jpaQueryFactory.select(workerTb)
                .from(workerTb)
                .where(workerTb.phone.like("%" + phone + "%"), workerTb.name.like("%" + name + "%"), workerTb.authFlag.eq(authFlag))
                .fetchCount();
    }

}
