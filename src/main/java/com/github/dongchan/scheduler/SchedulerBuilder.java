package com.github.dongchan.scheduler;

import com.github.dongchan.scheduler.jdbc.JdbcCustomization;
import com.github.dongchan.scheduler.stats.StatsRegistry;
import com.github.dongchan.scheduler.task.OnStartup;
import com.github.dongchan.scheduler.task.Task;
import com.github.dongchan.scheduler.task.schedule.SystemClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.github.dongchan.scheduler.Scheduler.THREAD_PREFIX;
import static java.util.Optional.ofNullable;

/**
 * @author DongChan
 * @date 2020/10/23
 * @time 12:43 PM
 */
public class SchedulerBuilder {
    public static final Duration DEFAULT_POLLING_INTERVAL = Duration.ofSeconds(10);
    public static final Duration DEFAULT_HEARTBEAT_INTERVAL = Duration.ofMinutes(5);
    public static final Duration DEFAULT_DELETION_OF_UNRESOLVED_TASKS_DURATION = Duration.ofDays(14);
    private static final Logger log = LoggerFactory.getLogger(SchedulerBuilder.class);
    private static final int POLLING_CONCURRENCY_MULTIPLIER = 3;
    protected final List<OnStartup> startTasks = new ArrayList<>();
    protected final DataSource dataSource;
    protected final List<Task<?>> knownTasks = new ArrayList<>();
    protected Clock clock = new SystemClock();
    protected SchedulerName schedulerName;
    protected int pollingLimit;
    protected int executorThreads = 10;
    protected StatsRegistry statsRegistry = StatsRegistry.NOOP;
    protected Waiter waiter = new Waiter(DEFAULT_POLLING_INTERVAL, clock);
    protected Duration heartbeatInterval = DEFAULT_HEARTBEAT_INTERVAL;
    protected Serializer serializer = Serializer.DEFAULT_JAVA_SERIALIZER;
    protected boolean enableImmediateExecution = false;
    protected ExecutorService executorService;
    protected String tableName = JdbcTaskRepository.DEFAULT_TABLE_NAME;
    protected Duration deleteUnresolvedAfter = DEFAULT_DELETION_OF_UNRESOLVED_TASKS_DURATION;
    protected JdbcCustomization jdbcCustomization = null;


    public SchedulerBuilder(DataSource dataSource, List<Task<?>> knowTasks) {
        this.dataSource = dataSource;
        this.knownTasks.addAll(knowTasks);
        this.pollingLimit = calculatePollingLimit();
    }

    @SafeVarargs
    public final <T extends Task<?> & OnStartup> SchedulerBuilder startTasks(T... startTasks) {
        return startTasks(Arrays.asList(startTasks));
    }

    public <T extends Task<?> & OnStartup> SchedulerBuilder startTasks(List<T> startTasks) {
        knownTasks.addAll(startTasks);
        this.startTasks.addAll(startTasks);
        return this;
    }

    private int calculatePollingLimit() {
        return executorThreads * POLLING_CONCURRENCY_MULTIPLIER;
    }

    /**
     * @return
     */
    public Scheduler build() {

//        if (schedulerName == null) {
//            schedulerName = new SchedulerName.Hostname();
//        }
//
//        final TaskResolver taskResolver = new TaskResolver(statsRegistry, clock, knownTasks);
//        final JdbcCustomization jdbcCustomization = ofNullable(this.jdbcCustomization).orElse(new AutodetectJdbcCustomization(dataSource));
//        final JdbcTaskRepository taskRepository = new JdbcTaskRepository(dataSource, jdbcCustomization, tableName, taskResolver, schedulerName, serializer);
//
//        ExecutorService candidateExecutorService = executorService;
//        if (candidateExecutorService == null) {
//            candidateExecutorService = Executors.newFixedThreadPool(executorThreads, ExecutorUtils.defaultThreadFactoryWithPrefix(THREAD_PREFIX + "-"));
//        }
//
//        log.info("Creating scheduler with configuration: threads={}, pollInterval={}s, heartbeat={}s, enable-immediate-execution={}, table-name={}, name={}",
//                executorThreads,
//                waiter.getDuration().getSeconds(),
//                heartbeatInterval.getSeconds(),
//                enableImmediateExecution,
//                tableName,
//                schedulerName.getName()
//                );
//        return new Scheduler(clock, taskRepository, taskResolver, executorThreads, candidateExecutorService,
//                schedulerName, waiter, heartbeatInterval, enableImmediateExecution, statsRegistry, pollingLimit,
//                deleteUnresolvedAfter, startTasks);
        return null;
    }
}
