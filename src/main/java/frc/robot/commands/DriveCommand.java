// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ChassisSubsystem;

public class DriveCommand extends CommandBase {
  DoubleSupplier turn, drive;
  BooleanSupplier slow;
  ChassisSubsystem chassisSubsystem;
  
  private SlewRateLimiter speedLimiter = new SlewRateLimiter(3);
  private SlewRateLimiter turnLimiter = new SlewRateLimiter(3);
  /** Creates a new DriveCommand. */
  public DriveCommand(ChassisSubsystem chassisSubsystem, DoubleSupplier drive, DoubleSupplier turn, BooleanSupplier slow) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.chassisSubsystem = chassisSubsystem;
    this.drive = drive;
    this.turn = turn;
    this.slow = slow;
    addRequirements(chassisSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    double moveSpeed = speedLimiter.calculate(drive.getAsDouble());
    double turnSpeed = turnLimiter.calculate(turn.getAsDouble()) * 0.57;
    if(slow.getAsBoolean()){
      moveSpeed*=0.5;
      turnSpeed*=0.5;
    }
    chassisSubsystem.drive(moveSpeed, turnSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    chassisSubsystem.drive(0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
