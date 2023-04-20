package frc.robot.subsystems;

public abstract class Subsystem {

    public void readPeriodInputs() {}

    public void writePeriodOutputs() {}

    public void registerEnabledLoops() {}

    public void outputLog() {}

    public void outputTelemetry() {}

    public void stop() {}
}
