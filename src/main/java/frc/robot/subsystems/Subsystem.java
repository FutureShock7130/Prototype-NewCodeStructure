package frc.robot.subsystems;

import frc.lib.loop.ILooper;

/**為了使用 Custom Looper 以及一些 Design Pattern 而設計的
 * Custom Subsystem。
 * 
 * @author Team 254
 * @author Neil | 6th (hongnai4507@gmail.com)
*/
public abstract class Subsystem {

    // Optional. Same as registerEnabledLoops().
    public void readPeriodicInputs() {}

    // Optional. Same as registerEnabledLoops().
    public void writePeriodicOutputs() {}

    /** Run periodic loops in custom period (up to 100hz). 
     * 
     * @param iLooper Use to register loops. Try iLooper.register(new Loop( ) { });
    */
    public void registerEnabledLoops(ILooper iLooper) {}

    /**Output the information to the Terminal (Driver Station Log). */
    public void outputLog() {}

    /**Output the information to the SmartDashboard. */
    public void outputTelemetry() {}

}
