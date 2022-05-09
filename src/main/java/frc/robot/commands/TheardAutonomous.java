package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.BallSubsystem;
import frc.robot.subsystems.ChassisSubsystem;

public class TheardAutonomous extends SequentialCommandGroup {

  public TheardAutonomous(BallSubsystem ballSubsystem, ChassisSubsystem chassisSubsystem) {
    addCommands(
      new InstantCommand(ballSubsystem::openHand),
      new WaitCommand(3.1), new InstantCommand(ballSubsystem::stopOperation),
      new InstantCommand(()->chassisSubsystem.setBrake(NeutralMode.Brake)),
      new DriveCommand(chassisSubsystem, ()->0.45, ()->0, ()->false).withTimeout(1.5).alongWith(
        new SequentialCommandGroup(new WaitCommand(0.75), new InstantCommand(ballSubsystem::ballsIn), new WaitCommand(3), new InstantCommand(ballSubsystem::stop))),
      new InstantCommand(chassisSubsystem::resetEncoders), new RunCommand(chassisSubsystem::turn).withTimeout(2),
      new DriveCommand(chassisSubsystem, ()->-0.4, ()->0, ()->false).withTimeout(0.7),
      new WaitCommand(0.5),
      new ShootHigh(ballSubsystem, 0.46)
  );}
}
