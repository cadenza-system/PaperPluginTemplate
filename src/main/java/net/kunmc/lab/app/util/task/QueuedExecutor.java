package net.kunmc.lab.app.util.task;

import static java.lang.Math.max;
import static java.lang.Math.min;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import java.util.Deque;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.IntStream;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class QueuedExecutor implements Listener {

    /**
     * 1秒あたりに実行されるアクション（Runnable）の数
     */
    private int numberOfActionPerSec;

    // 実際に実行できるアクションの数。5秒ごとにリセットされ、次のターゲットを決定するために使用される
    private int count = 0;

    private final Deque<Runnable> taskQueue = new ConcurrentLinkedDeque();
    private boolean isRunning;
    private final float updatePeriodSeconds = 3L;

    public QueuedExecutor(int numberOfActionPerSec) {
        this.numberOfActionPerSec = numberOfActionPerSec;
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (isRunning) {

                    int Candidate;
                    float average = count / updatePeriodSeconds;
                    if (average > QueuedExecutor.this.numberOfActionPerSec * 0.9) {
                        Candidate = (int) max(1000,
                                (QueuedExecutor.this.numberOfActionPerSec * 1.2));
                    } else {
                        Candidate = (int) max(1000,
                                (QueuedExecutor.this.numberOfActionPerSec * 0.9));
                    }

                    QueuedExecutor.this.numberOfActionPerSec = min(1000000, Candidate);
                    count = 0;
                }
            }
        }, (long) updatePeriodSeconds * 1000, (long) updatePeriodSeconds * 1000);
    }

    @EventHandler(ignoreCancelled = true)
    public void onServerTickStart(ServerTickStartEvent event) {
        IntStream.range(0, numberOfActionPerSec / 20).forEach(i -> {
            Runnable task = taskQueue.poll();

            if (task == null) {
                isRunning = false;
                count = 0;
                return;
            }

            isRunning = true;
            task.run();
            count++;
        });
    }

    /**
     * タスクを追加する
     */
    public void offer(Runnable task) {
        taskQueue.offer(task);
    }

    /**
     * タスクをクリアする
     */
    public void clear() {
        taskQueue.clear();
    }
}
