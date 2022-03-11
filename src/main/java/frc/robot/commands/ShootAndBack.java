// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.BallSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootAndBack extends SequentialCommandGroup {
  /** Creates a new ShootAndBack. */
  public ShootAndBack(ChassisSubsystem chassisSubsystem, BallSubsystem collectorSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
    new InstantCommand(()->collectorSubsystem.shoot(0.45)),
    new WaitCommand(1),
    new InstantCommand(collectorSubsystem::stop),
    new DriveCommand(chassisSubsystem,()-> -0.5,()-> 0,()-> false).withTimeout(1.4));
  }
}
