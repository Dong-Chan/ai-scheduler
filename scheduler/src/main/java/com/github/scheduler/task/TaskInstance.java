package com.github.scheduler.task;

import lombok.Getter;

import java.util.function.Supplier;

/**
 * @author DongChan
 * @date 2020/10/22
 * @time 5:02 PM
 */
@Getter
public final class TaskInstance<T> {
    private final String taskName;
    private final String id;
    private final Supplier<T> dataSupplier;


    public TaskInstance(String taskName, String id) {
        this(taskName, id, (T) null);
    }

    public TaskInstance(String taskName, String id, T data) {
        this(taskName, id, () -> data);
    }

    public TaskInstance(String taskName, String id, Supplier<T> dataSupplier) {
        this.taskName = taskName;
        this.id = id;
        this.dataSupplier = dataSupplier;
    }


    public String getTaskAndInstance(){
        return taskName + "_" + id;
    }

    public T getData(){
        return dataSupplier.get();
    }
}
