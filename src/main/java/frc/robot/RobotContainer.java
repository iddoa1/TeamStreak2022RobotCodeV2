package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.controller;
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


public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem();
  private final ChassisSubsystem chassisSubsystem = new ChassisSubsystem();
  private final ShlongSubsystem shlongSubsystem = new ShlongSubsystem();
  private final BallSubsystem collectorSubsystem = new BallSubsystem();

  private final Joystick operatingController = new Joystick(0);
  private final Joystick seconedController = new Joystick(1);

  public BallSubsystem getSubsystem(){
    return collectorSubsystem;
  }

  public RobotContainer() {
    // Configure the button bindings
    CameraServer.startAutomaticCapture();
    configureButtonBindings();
    chassisSubsystem.setDefaultCommand(
        new DriveCommand(chassisSubsystem, () -> -operatingController.getRawAxis(controller.leftY), () -> operatingController.getRawAxis(controller.rightX), ()->operatingController.getRawButton(controller.topRight)));
  }

  private void configureButtonBindings() {
    new Trigger(() -> seconedController.getPOV() == controller.up).whenActive(elevatorSubsystem::elevatorUp, elevatorSubsystem)
        .whenInactive(elevatorSubsystem::stop, elevatorSubsystem);
    new Trigger(() -> seconedController.getPOV() == controller.down).whenActive(elevatorSubsystem::elevatorDown, elevatorSubsystem)
        .whenInactive(elevatorSubsystem::stop, elevatorSubsystem);
    
    new Trigger(() -> seconedController.getPOV() == controller.right).whenActive(new RunCommand(shlongSubsystem::handClose, shlongSubsystem))
        .whenInactive(shlongSubsystem::stop, shlongSubsystem);
    new Trigger(() -> seconedController.getPOV() == controller.left).whenActive(new RunCommand(shlongSubsystem::handOpen, shlongSubsystem))
        .whenInactive(shlongSubsystem::stop, shlongSubsystem);
    
    SmartDashboard.putData(new StartEndCommand(elevatorSubsystem::setCoast, elevatorSubsystem::setBrake, elevatorSubsystem).withTimeout(5).withName("Coast"));

    new JoystickButton(operatingController, controller.botLeft).whenActive(collectorSubsystem::ballsIn, collectorSubsystem)
    .whenInactive(collectorSubsystem::stop, collectorSubsystem);
    
    new JoystickButton(operatingController, controller.botRight).whenActive(()->collectorSubsystem.shoot(0.42), collectorSubsystem)
    .whenInactive(collectorSubsystem::stop, collectorSubsystem);

    new JoystickButton(seconedController, controller.a).whenPressed(new AutoClimb3(elevatorSubsystem, shlongSubsystem, ()->seconedController.getRawButton(2)));

    new Trigger(()->operatingController.getPOV()==controller.left).whenActive(()->collectorSubsystem.ballsUp()).whenInactive(()->collectorSubsystem.stop());
    new Trigger(()->operatingController.getPOV()==controller.right).whenActive(()->collectorSubsystem.ballsDown()).whenInactive(()->collectorSubsystem.stop());

    new JoystickButton(seconedController, controller.botRight).whenActive(()->collectorSubsystem.spitBalls()).whenInactive(()->collectorSubsystem.stop());
    new JoystickButton(seconedController, controller.topRight).whenActive(()->collectorSubsystem.ballsUp()).whenInactive(()->collectorSubsystem.stop());

    new JoystickButton(seconedController, controller.x).whenPressed(new AutoClimb4(elevatorSubsystem, shlongSubsystem, ()->seconedController.getRawButton(1)));

    //open the intake system.
    new JoystickButton(seconedController, controller.b).whenActive(collectorSubsystem::openIntake).whenInactive(collectorSubsystem::stop);

  }

  SendableChooser<Command> autoCommand;

  public void initAutoCommand(){
    autoCommand = new SendableChooser<Command>();
    autoCommand.addOption("auto", new ShootAndBack(chassisSubsystem, collectorSubsystem));
    autoCommand.addOption("null", null);

    SmartDashboard.putData("autonomous selector", autoCommand);
  }

  public Command getAutonomousCommand() {
    return autoCommand.getSelected();
  }

  public void setBrake(NeutralMode mode) {
    chassisSubsystem.setBrake(mode);
  }
}
