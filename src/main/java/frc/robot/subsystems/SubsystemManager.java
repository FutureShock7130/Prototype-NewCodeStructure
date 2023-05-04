package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import frc.lib.loop.ILooper;
import frc.lib.loop.Loop;
import frc.lib.loop.Looper;

/**SubsystemManager for custom Subsystems. 
 * 
 * @author  Team 254
 * @author  Neil | 6th (hongnai4507@gmail.com)
*/
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

    public void setSubsystems(Subsystem... subsystems) {
        mAllSubsystems = Arrays.asList(subsystems);
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
            mAllSubsystems.forEach(s -> s.readPeriodicInputs());
            mLoops.forEach(l -> l.onLoop(timeStamp));
            mAllSubsystems.forEach(s -> s.writePeriodicOutputs());
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
            mAllSubsystems.forEach(s -> s.readPeriodicInputs());
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
