    package top.huzhurong.scheduled;

import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 竹 on 2017/10/14.
 * Spring 自带的例子，添加了main方法和计数器
 */
public class TaskExecutorExample {
    private static class MessagePrinterTask implements Runnable{
        private static   AtomicInteger count = new AtomicInteger(0);
        private String message;
        public MessagePrinterTask(String message) {
            this.message = message;
        }
        @Override
        public void run() {

            System.out.println(this.message + ":" + count.getAndIncrement());

        }
    }

    private TaskExecutor taskExecutor;
    public TaskExecutorExample (TaskExecutor taskExecutor){
        this.taskExecutor = taskExecutor;
    }
    public  void printMessage(){
        for (int i=0;i<25;i++){
           this.taskExecutor.execute(new MessagePrinterTask("message"));
        }
    }

    public static void main(String[] args) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.initialize();//必须先初始化，否则会报错
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        threadPoolTaskExecutor.setCorePoolSize(8);
        threadPoolTaskExecutor.setMaxPoolSize(20);
        threadPoolTaskExecutor.setQueueCapacity(30);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);//设置成等待完成执行后在销毁
        //如果使用SimpleThreadPoolTaskExecutor,需引入 quartz
        new TaskExecutorExample(threadPoolTaskExecutor).printMessage();
        threadPoolTaskExecutor.destroy();
    }
}
