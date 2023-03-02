package Main.Utils.Timers;

import java.util.Date;

public class Timer {

    private Long start, end, interval;

    public Timer(Long interval) {
        this.start = new Date().getTime();
        this.end = start+interval;
        this.interval = interval;
    }

    public boolean touch() {
        Date date = new Date();
        if (date.getTime() > end) {
            return true;
        }
        else {
            return false;
        }
    }
}