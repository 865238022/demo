package top.huzhurong.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 竹 on 2017/10/14.
 */
@Component
public class TestB implements IMyTestService{
    @Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次
    @Override
    public void myTest(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(getClass().getName() + "进入测试" +simpleDateFormat.format(new Date()));
    }
}
