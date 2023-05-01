package moment.hong.batch.job;

import lombok.extern.slf4j.Slf4j;
import moment.hong.batch.domain.Market;
import moment.hong.batch.repository.MarketRepository;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class QuartzJob implements Job {
    @Autowired
    private MarketRepository marketRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Quartz Job Executed");
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        log.info("dataMap date : {}", dataMap.get("date"));
        log.info("dataMap executeCount : {}", dataMap.get("executeCount"));

        //JobDataMap을 통해 Job의 실행 횟수를 받아서 횟수 + 1 을 한다.
        int cnt = (int) dataMap.get("executeCount");
        dataMap.put("executeCount", ++cnt);

        //Market 테이블에 pooney_현재시간 데이터를 insert 한다.
        Market market = new Market();
        market.updateName(String.format("모이개_%s_요구사항 테스트 🐕", dataMap.get("date")));
        market.updatePrice(3000 + cnt);
        marketRepository.save(market);
    }
}