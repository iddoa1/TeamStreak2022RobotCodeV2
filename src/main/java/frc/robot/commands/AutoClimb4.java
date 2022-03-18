package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ShlongSubsystem;

public class AutoClimb4 extends SequentialCommandGroup {
  ElevatorSubsystem elevatorSubsystem;
  ShlongSubsystem shlongSubsystem;

  /** Creates a new AutoClimb4. */
  public AutoClimb4(ElevatorSubsystem elevatorSubsystem, ShlongSubsystem shlongSubsystem,
      BooleanSupplier confirmation) {
    addCommands(
        new RunCommand(()->elevatorSubsystem.openUntil(120000)).withInterrupt(()->elevatorSubsystem.isOpenUntil(120000)).alongWith(
            new RunCommand(shlongSubsystem::open).withInterrupt(shlongSubsystem::isOpen)),
        new WaitUntilCommand(confirmation),
        new RunCommand(shlongSubsystem::open).withInterrupt(shlongSubsystem::isOpen),
        new RunCommand(elevatorSubsystem::elevatorDown).withTimeout(3).alongWith(
                new SequentialCommandGroup(
                    new WaitCommand(2.7),
                    new RunCommand(shlongSubsystem::close).withTimeout(0.2),
                    new InstantCommand(shlongSubsystem::stop)), new InstantCommand(elevatorSubsystem::stop)),
        new WaitCommand(1),
        new RunCommand(()->elevatorSubsystem.openUntil(95000)).withInterrupt(()->elevatorSubsystem.isOpenUntil(95000)),
            new WaitCommand(1),
            new RunCommand(shlongSubsystem::close).withInterrupt(shlongSubsystem::isClose),
            new InstantCommand(shlongSubsystem::stop),
            new WaitUntilCommand(confirmation),
            new RunCommand(()->elevatorSubsystem.openUntil(138000)).withInterrupt(()->elevatorSubsystem.isOpenUntil(138000)).withTimeout(2),
            new WaitUntilCommand(confirmation),
        new RunCommand(elevatorSubsystem::elevatorDown).withTimeout(0.5),
        new RunCommand(elevatorSubsystem::elevatorDown).withInterrupt(confirmation).alongWith(
            new SequentialCommandGroup(
                new WaitCommand(1.5),
                new RunCommand(shlongSubsystem::open).withInterrupt(shlongSubsystem::isOpen))),
        new RunCommand(()->elevatorSubsystem.openUntil(100000)).withInterrupt(()->elevatorSubsystem.isOpenUntil(100000)),
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
