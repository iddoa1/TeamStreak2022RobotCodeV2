// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.AutoClimb3;
import frc.robot.commands.AutoClimb4;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ShootAndBack;
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.BallSubsystem;
import frc.robot.subsystems.ShlongSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem();
  private final ChassisSubsystem chassisSubsystem = new ChassisSubsystem();
  private final ShlongSubsystem shlongSubsystem = new ShlongSubsystem();
  private final BallSubsystem collectorSubsystem = new BallSubsystem();

  private final Joystick joystick1 = new Joystick(0);
  private final Joystick joystick2 = new Joystick(1);

  public BallSubsystem getSubsystem(){
    return collectorSubsystem;
  }

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    CameraServer.startAutomaticCapture();
    configureButtonBindings();
    chassisSubsystem.setDefaultCommand(
        new DriveCommand(chassisSubsystem, () -> -joystick1.getRawAxis(1), () -> joystick1.getRawAxis(2), ()->joystick1.getRawButton(6)));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new Trigger(() -> joystick2.getPOV() == 0).whenActive(elevatorSubsystem::elevatorUp, elevatorSubsystem)
        .whenInactive(elevatorSubsystem::stop, elevatorSubsystem);
    new Trigger(() -> joystick2.getPOV() == 180).whenActive(elevatorSubsystem::elevatorDown, elevatorSubsystem)
        .whenInactive(elevatorSubsystem::stop, elevatorSubsystem);
    
    new Trigger(() -> joystick2.getPOV() == 90).whenActive(new RunCommand(shlongSubsystem::handClose, shlongSubsystem))
        .whenInactive(shlongSubsystem::stop, shlongSubsystem);
    new Trigger(() -> joystick2.getPOV() == 270).whenActive(new RunCommand(shlongSubsystem::handOpen, shlongSubsystem))
        .whenInactive(shlongSubsystem::stop, shlongSubsystem);
    
    SmartDashboard.putData(new StartEndCommand(elevatorSubsystem::setCoast, elevatorSubsystem::setBrake, elevatorSubsystem).withTimeout(5).withName("Coast"));

    new JoystickButton(joystick1, 7).whenActive(collectorSubsystem::ballsIn, collectorSubsystem)
    .whenInactive(collectorSubsystem::stop, collectorSubsystem);
    
    new JoystickButton(joystick1, 8).whenActive(()->collectorSubsystem.shoot(0.42), collectorSubsystem)
    .whenInactive(collectorSubsystem::stop, collectorSubsystem);

    //new JoystickButton(joystick2, 3).toggleWhenActive(new StartEndCommand(collectorSubsystem::open, collectorSubsystem::close));

    new JoystickButton(joystick2, 2).whenPressed(new AutoClimb3(elevatorSubsystem, shlongSubsystem, ()->joystick2.getRawButton(2)));

    new Trigger(()->joystick1.getPOV()==270).whenActive(()->collectorSubsystem.ballsUp()).whenInactive(()->collectorSubsystem.stop());
    new Trigger(()->joystick1.getPOV()==90).whenActive(()->collectorSubsystem.ballsDown()).whenInactive(()->collectorSubsystem.stop());

    new JoystickButton(joystick2, 8).whenActive(()->collectorSubsystem.spitBalls()).whenInactive(()->collectorSubsystem.stop());
    new JoystickButton(joystick2, 6).whenActive(()->collectorSubsystem.ballsUp()).whenInactive(()->collectorSubsystem.stop());

    new JoystickButton(joystick2, 1).whenPressed(new AutoClimb4(elevatorSubsystem, shlongSubsystem, ()->joystick2.getRawButton(1)));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */

  SendableChooser<Command> autoCommand;

  public void initAutoCommand(){
    autoCommand = new SendableChooser<Command>();
    autoCommand.addOption("auto", new ShootAndBack(chassisSubsystem, collectorSubsystem));
    autoCommand.addOption("null", null);

    SmartDashboard.putData("autonomous selector", autoCommand);
  }

  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return autoCommand.getSelected();
  }

  public void setBrake(NeutralMode mode) {
    chassisSubsystem.setBrake(mode);
  }
}
