package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.BallSubsystem;
import frc.robot.subsystems.ChassisSubsystem;

public class IntakeAndShoot extends SequentialCommandGroup {
  public IntakeAndShoot(ChassisSubsystem chassisSubsystem, BallSubsystem ballSubsystem) {
    addCommands(
      new InstantCommand(()->chassisSubsystem.setBrake(NeutralMode.Brake)),
      new DriveCommand(chassisSubsystem, ()->0.3, ()->0, ()->false).withTimeout(3).alongWith(
        new SequentialCommandGroup(new WaitCommand(2), new InstantCommand(ballSubsystem::ballsIn), new WaitCommand(1), new InstantCommand(ballSubsystem::stop))),
      new InstantCommand(chassisSubsystem::resetEncoders), new RunCommand(chassisSubsystem::turn).withTimeout(3.5),
      new DriveCommand(chassisSubsystem, ()->0.3, ()->0, ()->false).withTimeout(2.7),
      new WaitCommand(1),
      new InstantCommand(()->ballSubsystem.shoot(0.45)),
      new WaitCommand(1),
      new InstantCommand(ballSubsystem::stop)
    );
  }
}
