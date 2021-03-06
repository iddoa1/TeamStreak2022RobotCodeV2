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
      new InstantCommand(ballSubsystem::openHand),
      new WaitCommand(3.2), new InstantCommand(ballSubsystem::stopOperation),
      new InstantCommand(()->chassisSubsystem.setBrake(NeutralMode.Brake)),
      new DriveCommand(chassisSubsystem, ()->0.3, ()->0, ()->false).withTimeout(2.7).alongWith(
        new SequentialCommandGroup(new WaitCommand(1.5), new InstantCommand(ballSubsystem::ballsIn), new WaitCommand(1.5), new InstantCommand(ballSubsystem::stop))),
      new InstantCommand(chassisSubsystem::resetEncoders), new RunCommand(chassisSubsystem::turn).withTimeout(2),
      new DriveCommand(chassisSubsystem, ()->0.3, ()->0, ()->false).withTimeout(2.75),
      new WaitCommand(0.5),
      new InstantCommand(()->ballSubsystem.shoot(0.25)),
      new WaitCommand(3.5),
      new InstantCommand(ballSubsystem::stop), new InstantCommand(ballSubsystem::stopShooter)
    );
  }
}
