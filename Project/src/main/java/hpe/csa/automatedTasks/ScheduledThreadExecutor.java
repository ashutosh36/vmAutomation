package hpe.csa.automatedTasks;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadExecutor {
	private final ScheduledExecutorService scheduler=Executors.newScheduledThreadPool(1);
	
	public void runCleanupService() {
		CleanupTask clean1=new CleanupTask();
		System.out.println("Startuing service"+ new Date());
		ScheduledFuture<?> result= scheduler.scheduleAtFixedRate(clean1, 2, 5, TimeUnit.SECONDS);
		//long startTimeGap=setHourTomorrow(7).getTime()-new Date().getTime();
		//ScheduledFuture<?> result= scheduler.scheduleAtFixedRate(clean1,startTimeGap, 24*60*60*1000, TimeUnit.MILLISECONDS);
	}
	
	public Date setHourTomorrow(int hour) {
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
}
