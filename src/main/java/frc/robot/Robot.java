// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.lib.loop.Looper;
import frc.lib.util.LogiJoystick;
import frc.robot.manual.CommandHandler;
import frc.robot.manual.impl.TeleopSwerve;
import frc.robot.subsystems.SubsystemManager;
import frc.robot.subsystems.impl.Swerve;

public class Robot extends TimedRobot {
  private final LogiJoystick mDriverJoystick = new LogiJoystick(0);

  private final Looper mEnabledLooper = new Looper(Constants.kLooperDt);
  private final Looper mDisabledLooper = new Looper(Constants.kLooperDt);
  private final SubsystemManager mSubsystemManager = new SubsystemManager();
  private final CommandHandler mCommandHandler = new CommandHandler();

  private final Swerve mSwerve;

  public Robot() {
    mSwerve = Swerve.getInstance();
  }

  @Override
  public void robotInit() {
    mSubsystemManager.setSubsystems(mSwerve);
    mCommandHandler.registerCommands(new TeleopSwerve(mSwerve, mDriverJoystick));
    mSubsystemManager.registerEnabledLoops(mEnabledLooper);
    mSubsystemManager.registerDisabledLoops(mDisabledLooper);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    mSubsystemManager.output2SmartDashboard();
    mSubsystemManager.output2Terminal();
  }

  @Override
  public void disabledInit() {
    mEnabledLooper.stop();
    mDisabledLooper.start();
  }

  @Override
  public void disabledPeriodic() {
    mDisabledLooper.outputTelemetry();
  }

  @Override
  public void autonomousInit() {
    mDisabledLooper.stop();
    mEnabledLooper.start();
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    mDisabledLooper.stop();
    mEnabledLooper.start();
  }

  @Override
  public void teleopPeriodic() {
    mCommandHandler.handleAll();
    mCommandHandler.outputTelemetry();
    mEnabledLooper.outputTelemetry();
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}
