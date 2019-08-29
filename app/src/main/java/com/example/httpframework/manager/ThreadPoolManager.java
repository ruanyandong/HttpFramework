package com.example.httpframework.manager;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {

    private static final ThreadPoolManager threadPoolManager = new ThreadPoolManager();
    public static ThreadPoolManager getInstance() {
        return threadPoolManager;
    }

    // 线程安全
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();
    private DelayQueue<HttpTask> mDelayQueue = new DelayQueue<>();

    // 线程池
    private ThreadPoolExecutor mThreadPoolExecutor;
    private ThreadPoolManager(){
        mThreadPoolExecutor = new ThreadPoolExecutor(3, 6, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                addTask(r);
            }
        });
        mThreadPoolExecutor.execute(coreThread);
        mThreadPoolExecutor.execute(delayThread);
    }

    // 将处理失败或者等待的网络请求添加到队列中
    public void addTask(Runnable runnable) {
        if (runnable != null){
            try {
                mQueue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 将失败了的网络请求添加到队列中
    public void addDelayTask(HttpTask ht){
        if (ht != null){
            ht.setDelayTime(3000);
            mDelayQueue.offer(ht);
        }
    }


    // 创建核心线程 将队列mQueue中的请求拿出来，交给线程池处理
    public Runnable coreThread = new Runnable() {
        Runnable runnable = null;
        @Override
        public void run() {
            while(true){
                try {
                    runnable = mQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mThreadPoolExecutor.execute(runnable);
            }
        }
    };

    // 创建延迟线程 将失败队列中的请求拿出来，交给线程池处理
    public Runnable delayThread = new Runnable() {
        HttpTask ht = null;
        @Override
        public void run() {
            while (true){
                try {
                    ht = mDelayQueue.take();
                    if (ht.getRetryCount() < 3){
                        mThreadPoolExecutor.execute(ht);
                        ht.setRetryCount(ht.getRetryCount()+1);
                        Log.d("ruanyandong", "run: 重试机制=》"+ht.getRetryCount()+" "+System.currentTimeMillis());
                    }else {
                        Log.d("ruanyandong", "run: 总是失败，放弃执行");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

}
