// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.cBallElevator;
import frc.robot.Constants.cIntake;

public class BallSubsystem extends SubsystemBase {
  
  WPI_TalonFX outerMotor = new WPI_TalonFX(cIntake.outerIntakeMotor);
  WPI_TalonFX intakeMaster = new WPI_TalonFX(cIntake.intakeMotor);
  WPI_TalonFX seconedIntake = new WPI_TalonFX(cIntake.secondMotor);

  WPI_TalonFX magazine = new WPI_TalonFX(cBallElevator.beltMotor);

  Servo leftPiston = new Servo(9);
  Servo rightPiston = new Servo(8);

  public BallSubsystem() {
    intakeMaster.configFactoryDefault();
    outerMotor.configFactoryDefault();

    seconedIntake.follow(intakeMaster);
    open();
  }

  public void ballsIn(){
    intakeMaster.set(0.3);
    outerMotor.set(0.4);
  }

  public void ballOut(){
    intakeMaster.set(-0.3);
    outerMotor.set(-0.4);
  }

  public void stop(){
    intakeMaster.set(0);
    outerMotor.set(0);
    magazine.set(0);
  }

  public void shoot(double shooterSpeed){
    intakeMaster.set(shooterSpeed);
    magazine.set(0.4);
  }

  public void open(){
    leftPiston.set(0.8);
    rightPiston.set(0.8);
  }
  public void close(){
    leftPiston.set(0.2);
    rightPiston.set(0.2);
  }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
