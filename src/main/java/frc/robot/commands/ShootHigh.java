// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.BallSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootHigh extends SequentialCommandGroup {
  
  BallSubsystem ballSubsystem;
  double shooterSpeed;

  public ShootHigh(BallSubsystem ballSubsystem, double shooterSpeed) {
    this.ballSubsystem = ballSubsystem;
    this.shooterSpeed = shooterSpeed;
    addCommands(
      new InstantCommand(()->ballSubsystem.shootUp(shooterSpeed)),
      new WaitCommand(3),
      new InstantCommand(ballSubsystem::ballsUp).andThen(new WaitCommand(3), new InstantCommand(()->ballSubsystem.stop()), new InstantCommand(()->ballSubsystem.stopShooter()))
    );
  }
}
