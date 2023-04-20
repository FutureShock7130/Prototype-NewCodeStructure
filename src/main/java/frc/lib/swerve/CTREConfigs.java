package frc.lib.swerve;

import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import frc.robot.Constants;

public final class CTREConfigs {
  private static CTREConfigs mInstance = null;

  public CANCoderConfiguration swerveCanCoderConfig;

  public static CTREConfigs getInstance() {

    if (mInstance == null) {
      mInstance = new CTREConfigs();
    }

    return mInstance;
  }

  public CTREConfigs() {
    swerveCanCoderConfig = new CANCoderConfiguration();

    /* Swerve CANCoder Configuration */
    swerveCanCoderConfig.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
    swerveCanCoderConfig.sensorDirection = Constants.Swerve.canCoderInvert;
    swerveCanCoderConfig.initializationStrategy =
        SensorInitializationStrategy.BootToAbsolutePosition;
  }
}
