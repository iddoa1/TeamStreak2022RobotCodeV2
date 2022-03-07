// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.cChassis;

public class ChassisSubsystem extends SubsystemBase {
  private WPI_TalonFX rightMotorMaster = new WPI_TalonFX(cChassis.rMotorM);
  private WPI_TalonFX rightMotorFollow = new WPI_TalonFX(cChassis.rMotorF);
  private WPI_TalonFX leftMotorMaster = new WPI_TalonFX(cChassis.lMotorM);
  private WPI_TalonFX leftMotorFollow = new WPI_TalonFX(cChassis.lMotorF);

  DifferentialDrive difDrive = new DifferentialDrive(leftMotorMaster, rightMotorMaster);
  
  //private final AHRS gyro = new AHRS(Port.kMXP);

  /** Creates a new ChassisSubsystem. */
  public ChassisSubsystem() {
    rightMotorMaster.configFactoryDefault();
    leftMotorMaster.configFactoryDefault();

    rightMotorFollow.follow(rightMotorMaster);
    leftMotorFollow.follow(leftMotorMaster);

    rightMotorMaster.setInverted(true);
    rightMotorFollow.setInverted(InvertType.FollowMaster);
  }

  public void drive(double move, double turn){

    if(Math.abs(move)<0.1) move=0;
    if(Math.abs(turn)<0.1) turn=0;

    difDrive.setMaxOutput(0.8);

    difDrive.curvatureDrive(move*0.7, turn*0.5, true);
  }

  public void setBrake(NeutralMode mode){
    rightMotorMaster.setNeutralMode(mode);
    leftMotorMaster.setNeutralMode(mode);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
