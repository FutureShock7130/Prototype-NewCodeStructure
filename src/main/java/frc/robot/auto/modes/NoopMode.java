// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto.modes;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.auto.actions.NoopAction;

public class NoopMode extends CommandBase {

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      new NoopAction();
  }

}
