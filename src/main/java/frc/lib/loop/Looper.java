package frc.lib.loop;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// By Team 254
public class Looper implements ILooper{

    public final double mPeriod;

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

    public void outputTelemetry() {
        SmartDashboard.putNumber("Looper DT", mDT);
    }
    
}
