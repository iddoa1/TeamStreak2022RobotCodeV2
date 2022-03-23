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
      new WaitCommand(3.2), new InstantCommand(ballSubsystem::stopOperation),
      new InstantCommand(()->chassisSubsystem.setBrake(NeutralMode.Brake)),
      new DriveCommand(chassisSubsystem, ()->0.3, ()->0, ()->false).withTimeout(2.7).alongWith(
        new SequentialCommandGroup(new WaitCommand(1.5), new InstantCommand(ballSubsystem::ballsIn), new WaitCommand(1.5), new InstantCommand(ballSubsystem::stop))),
      new InstantCommand(chassisSubsystem::resetEncoders), new RunCommand(chassisSubsystem::turn).withTimeout(2),
      new DriveCommand(chassisSubsystem, ()->-0.2, ()->0, ()->false).withTimeout(0.5),
      new WaitCommand(1),
      new ShootHigh(ballSubsystem, 0.47)
  );}
}
