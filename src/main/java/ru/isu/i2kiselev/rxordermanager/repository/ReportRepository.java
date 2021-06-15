package ru.isu.i2kiselev.rxordermanager.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.isu.i2kiselev.rxordermanager.model.Report;

@Repository
public interface ReportRepository extends ReactiveCrudRepository<Report, Integer> {

    @Query("select *")
    Flux<Report> getDailyReportData();

    @Query("select * ")
    Flux<Report> getReportByOrderId(Integer orderId);


}
