// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.lib.loop.Looper;

public class Robot extends TimedRobot {
  private final Looper mEnabledLooper = new Looper(Constants.kLooperDt);
  private final Looper mDisabledLooper = new Looper(Constants.kLooperDt);

  private final SubsystemManager mSubsystemManager = new SubsystemManager();

  public Robot() {}

  @Override
  public void robotInit() {
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
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
      mDisabledLooper.stop();
      mEnabledLooper.start();
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {
      mEnabledLooper.stop();
      mDisabledLooper.start();
  }

  @Override
  public void teleopInit() {
      mDisabledLooper.stop();
      mEnabledLooper.start();
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {
      mEnabledLooper.stop();
      mDisabledLooper.start();
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
