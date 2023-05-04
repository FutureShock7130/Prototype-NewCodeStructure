package frc.lib.loop;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** Ｗpilib預設的Loop frequency只有固定50hz，但透過這個Custom Looper可以讓它達到100hz（似乎是Java的上限）的速度，
 * 讓PID Controller、Feedforward、trajectory或是其他Loop可以跑得更流暢（50hz -> 100hz，就像是FPS翻倍）。
 * 
 * <p>你們可能會想說，奇怪我翻遍整個機器人程式怎麼都找不到Loop，其實Subsystem跟Command裡面的Periodic()，
 * 是一種重複執行的行為，也就算是一種Loop。
 * 
 *  @author Team 254 
 *  @author Neil | 6th (hongnai4507@gmail.com)*/
public class Looper implements ILooper{

    private final double mPeriod;

    private boolean mRunning;

    private final Notifier mNotifier;
    private final List<Loop> mLoops;
    private final Object mTastRunnableLock = new Object();
    private double mTimeStamp = 0;
    private double mDT = 0;
    private int mTotalLoops;

    private Runnable runnable = new Runnable() {
        public void run() {
            synchronized (mTastRunnableLock) {
                if (mRunning) {
                    double now = Timer.getFPGATimestamp();

                    for (Loop loop : mLoops) {
                        loop.onLoop(now);
                    }

                    mDT = now - mTimeStamp;
                    mTimeStamp = now;
                }
            }
        }
    };

    /**
     * 
     * @param mPeriod Loop的更新週期，單位為秒(seconds)。
     */
    public Looper(double mPeriod) {
        this.mPeriod = mPeriod;
        mNotifier = new Notifier(runnable);
        mRunning = false;
        mLoops = new ArrayList<>();
        mTotalLoops = mLoops.size();
    }

    @Override
    public synchronized void register(Loop loop) {
        synchronized (mTastRunnableLock) {
            mLoops.add(loop);
        }
    }

    /**Start the loops. */
    public synchronized void start() {
        if (!mRunning) {
            System.out.println("Starting loops");

            synchronized (mTastRunnableLock) {
                int i = 1;
                mTimeStamp = Timer.getFPGATimestamp();
                for (Loop loop : mLoops) {
                    loop.onStart(mTimeStamp);
                    System.out.println("Starting " + loop + " (" + i + "/" + mTotalLoops + ")");
                    i++;
                }
                mRunning = true;
            }

            mNotifier.startPeriodic(mPeriod);
        }
    }

    /** Stop the loops. */
    public synchronized void stop() {
        if (mRunning) {
            System.out.println("Stopping loops");
            mNotifier.stop();

            synchronized (mTastRunnableLock) {
                int i = 1;
                mRunning = false;
                mTimeStamp = Timer.getFPGATimestamp();
                for (Loop loop : mLoops) {
                    loop.onStop(mTimeStamp);
                    System.out.println("Stopping " + loop + " (" + i + "/" + mTotalLoops + ")");
                    i++;
                }
            }
        }

    }

    /**Output the LooperDT to the SmartDashboard */
    public void outputTelemetry() {
        SmartDashboard.putNumber("Looper DT", mDT);
    }
    
}
