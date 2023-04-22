package frc.robot.subsystems;

import frc.lib.loop.ILooper;

public abstract class Subsystem {

    public void readPeriodInputs() {}

    public void writePeriodOutputs() {}

    public void registerEnabledLoops(ILooper iLooper) {}

    public void outputLog() {}

    public void outputTelemetry() {}

}
