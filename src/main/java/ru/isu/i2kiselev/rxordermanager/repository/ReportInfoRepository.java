package ru.isu.i2kiselev.rxordermanager.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ru.isu.i2kiselev.rxordermanager.model.Report;
import ru.isu.i2kiselev.rxordermanager.model.ReportInfo;

public interface ReportInfoRepository extends ReactiveCrudRepository<ReportInfo, Integer> {

    @Query("select * ")
    Mono<ReportInfo> getDailyReportInfo();


    @Query("select * ")
    Mono<ReportInfo> getReportInfoByOrderId(Integer orderId);

    @Query("select * ")
    Mono<ReportInfo> getMonthlyReportInfo();
}
