// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ShlongSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoClimb4 extends SequentialCommandGroup {
  ElevatorSubsystem elevatorSubsystem;
  ShlongSubsystem shlongSubsystem;

  /** Creates a new AutoClimb4. */
  public AutoClimb4(ElevatorSubsystem elevatorSubsystem, ShlongSubsystem shlongSubsystem,
      BooleanSupplier confirmation) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new RunCommand(elevatorSubsystem::elevatorUp).withInterrupt(elevatorSubsystem::isElevatorUp).alongWith(
            new RunCommand(shlongSubsystem::open).withInterrupt(shlongSubsystem::isOpen)),
        new WaitUntilCommand(confirmation),
        new RunCommand(elevatorSubsystem::elevatorDown).withInterrupt(elevatorSubsystem::isElevatorDown)
            .withTimeout(1),
        new ParallelCommandGroup(
                new SequentialCommandGroup(
                    new WaitCommand(3),
                    new RunCommand(shlongSubsystem::close).withTimeout(0.2),
                    new InstantCommand(shlongSubsystem::stop))),
        new RunCommand(elevatorSubsystem::openHalf).withInterrupt(elevatorSubsystem::elevatorHalfUp),
        new WaitUntilCommand(confirmation),
            new WaitCommand(1),
            new RunCommand(shlongSubsystem::close).withInterrupt(shlongSubsystem::isClose),
            new InstantCommand(shlongSubsystem::stop),
            new RunCommand(elevatorSubsystem::elevatorUp).withInterrupt(elevatorSubsystem::isElevatorUp).withTimeout(2),
            new WaitUntilCommand(confirmation),

        // Climbs to 3
        /**
        new WaitUntilCommand(confirmation),
        new RunCommand(elevatorSubsystem::elevatorUp).withInterrupt(elevatorSubsystem::isElevatorUp),
        new RunCommand(shlongSubsystem::close).withTimeout(0.3),
        new InstantCommand(shlongSubsystem::stop),
        new WaitUntilCommand(confirmation),
        */
        new RunCommand(elevatorSubsystem::elevatorDown).withTimeout(0.5),
        new RunCommand(elevatorSubsystem::elevatorDown).withInterrupt(confirmation).alongWith(
            new SequentialCommandGroup(
                new WaitCommand(1.5),
                new RunCommand(shlongSubsystem::open).withInterrupt(shlongSubsystem::isOpen))),
        new RunCommand(elevatorSubsystem::elevatorUp).withInterrupt(elevatorSubsystem::isElevatorUp),
        new RunCommand(shlongSubsystem::close).withInterrupt(shlongSubsystem::isClose),
        new InstantCommand(shlongSubsystem::stop));

    this.shlongSubsystem = shlongSubsystem;
    this.elevatorSubsystem = elevatorSubsystem;
    addRequirements(elevatorSubsystem, shlongSubsystem);
  }

  @Override
  public void end(boolean interrupted) {
    elevatorSubsystem.stop();
    shlongSubsystem.stop();
  }
}
