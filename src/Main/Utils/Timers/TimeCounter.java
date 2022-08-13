package Main.Utils.Timers;

import Main.Maps.Cell;
import Main.Objects.Materials.Material;
import Main.Singletones.GameExecutor;
import Main.Utils.Messenger;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class TimeCounter implements Runnable {

    private Thread thread;
    private static boolean active;
    private List<ObjectTimeline> timelines = new ArrayList<>();

    public TimeCounter() {
        this.active = true;
        thread = new Thread(this, "Main.Utils.Timers.TimeCounter");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Messenger.systemMessage("TimeCounter() interruptedException Catched", this.getClass());
        }
        Messenger.systemMessage("thread has been initializated", this.getClass());
        thread.start();
    }


    @Override
    public void run() {
        Messenger.systemMessage("method run() used", this.getClass());
        try {
            renderTime();
        } catch (InterruptedException e) {
            Messenger.systemMessage("interrupted exception in run()", this.getClass());
        }
    }

    private void renderTime() throws InterruptedException {
        while (active) {
            Thread.sleep(1000);
            touchTimelines();
        }
    }

    private void touchTimelines() {
        if (timelines.size() > 0) {
            try {
                for (ObjectTimeline timeline : timelines) {
                    if (timeline.touch()) {
                        timelines.remove(timeline);
                    }
                }
            }
            catch (ConcurrentModificationException e) {
            }
        }
    }


    public void createObjectTimeline(Material material, Cell cell) {
        ObjectTimeline newTimeline = new ObjectTimeline(material, GameExecutor.getGame().getCurrentMap(), cell);
        timelines.add(newTimeline);
    }

    public static void setActive(boolean active) {
        TimeCounter.active = active;
    }
}
