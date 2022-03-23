package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.BallSubsystem;

public class ShootAndBack extends SequentialCommandGroup {
  public ShootAndBack(ChassisSubsystem chassisSubsystem, BallSubsystem collectorSubsystem) {
    addCommands(
      new InstantCommand(collectorSubsystem::openHand),
      new WaitCommand(3.2), new InstantCommand(collectorSubsystem::stopOperation),
      new InstantCommand(()->collectorSubsystem.shoot(0.3)),
      new WaitCommand(1),
      new InstantCommand(collectorSubsystem::stop), new InstantCommand(collectorSubsystem::stopShooter),
      new DriveCommand(chassisSubsystem,()-> -0.5,()-> 0,()-> false).withTimeout(0.7));
  }
}
