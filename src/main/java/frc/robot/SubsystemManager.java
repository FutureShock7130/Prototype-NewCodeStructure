package frc.robot;

import java.util.ArrayList;
import java.util.List;

import frc.lib.loop.ILooper;
import frc.lib.loop.Loop;
import frc.lib.loop.Looper;
import frc.robot.subsystems.Subsystem;

public class SubsystemManager implements ILooper{
    private static SubsystemManager mInstance = null;

    private List<Subsystem> mAllSubsystems = new ArrayList<>();
    private List<Loop> mLoops = new ArrayList<>();

    public SubsystemManager getInstance() {

        if (mInstance == null) {
            mInstance = new SubsystemManager();
        }

        return mInstance;
    }

    public List<Subsystem> getSubsystems() {
        return mAllSubsystems;
    }

    public void output2SmartDashboard() {
        mAllSubsystems.forEach(s -> s.outputTelemetry());
    }

    public void output2Terminal() {
        mAllSubsystems.forEach(s -> s.outputLog());
    }

    private class EnabledLoop implements Loop {

        @Override
        public void onStart(double timeStamp) {
            mLoops.forEach(l -> l.onStart(timeStamp));
        }

        @Override
        public void onLoop(double timeStamp) {
            mAllSubsystems.forEach(s -> s.readPeriodInputs());
            mLoops.forEach(l -> l.onLoop(timeStamp));
            mAllSubsystems.forEach(s -> s.writePeriodOutputs());
        }

        @Override
        public void onStop(double timeStamp) {
            mLoops.forEach(l -> l.onStop(timeStamp));            
        }

    }

    private class DisabledLoop implements Loop {

        @Override
        public void onStart(double timeStamp) {

        }

        @Override
        public void onLoop(double timeStamp) {
            mAllSubsystems.forEach(s -> s.readPeriodInputs());
        }

        @Override
        public void onStop(double timeStamp) {
            
        }

    }

    public void registerEnabledLoops(Looper looper) {
        mAllSubsystems.forEach(s -> s.registerEnabledLoops(this));
        looper.register(new EnabledLoop());
    }

    public void registerDisabledLoops(Looper looper) {
        looper.register(new DisabledLoop());
    }

    @Override
    public void register(Loop loop) {
        mLoops.add(loop);
    }
}
