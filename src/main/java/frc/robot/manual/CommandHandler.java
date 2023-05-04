package frc.robot.manual;

import java.util.Arrays;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CommandHandler {
    
    ManualCommand[] mAllCommands;

    double initT = 0;
    double lastT;
    double dT;

    public CommandHandler() {}

    public void registerCommands(ManualCommand... cmds) {
        mAllCommands = cmds;
    }

    public void handleAll() {
        for (ManualCommand cmd : mAllCommands) {
            cmd.handle();
        }

        lastT = Timer.getFPGATimestamp();
        dT = lastT - initT;
        initT = lastT;
    }

    public void outputTelemetry() {
        SmartDashboard.putNumber("Command dT", dT);
    }
}
