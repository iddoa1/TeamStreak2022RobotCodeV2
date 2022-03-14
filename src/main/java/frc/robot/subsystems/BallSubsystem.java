package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.cBallElevator;
import frc.robot.Constants.cIntake;

public class BallSubsystem extends SubsystemBase {
  
  WPI_TalonFX intakeMaster = new WPI_TalonFX(cIntake.intakeMotor);
  WPI_TalonFX seconedIntake = new WPI_TalonFX(cIntake.secondMotor);

  WPI_TalonFX magazine = new WPI_TalonFX(cBallElevator.beltMotor);

  WPI_TalonFX intakeOperation = new WPI_TalonFX(cIntake.intakeOperation);

  public BallSubsystem() {
    intakeMaster.configFactoryDefault();
    intakeOperation.configFactoryDefault();

    intakeOperation.setNeutralMode(NeutralMode.Brake);

    intakeOperation.setSelectedSensorPosition(0);

    intakeOperation.configForwardSoftLimitThreshold(cIntake.encoderDistance);
    intakeOperation.configReverseSoftLimitThreshold(00);
    intakeOperation.configForwardSoftLimitEnable(true);
    intakeOperation.configReverseSoftLimitEnable(true);

    seconedIntake.follow(intakeMaster);

  }

  public boolean isOpen(){
    return intakeOperation.getSelectedSensorPosition()>=cIntake.encoderDistance;
  }

  public boolean isClosed(){
    return intakeOperation.getSelectedSensorPosition()<=100;
  }

  public void openIntake(){
    if(!isOpen()) intakeOperation.set(0.3);
  }

  public void closeIntake(){
    if(!isClosed()) intakeOperation.set(0.3);
  }

  public void ballsIn(){
    intakeMaster.set(0.3);
  }

  public void ballOut(){
    intakeMaster.set(-0.3);
  }

  public void stop(){
    intakeMaster.set(0);
    magazine.set(0);
    intakeOperation.set(0);
  }

  public void shoot(double shooterSpeed){
    intakeMaster.set(shooterSpeed);
    magazine.set(0.4);
  }

  public void ballsUp(){
    magazine.set(0.32);
  }

  public void ballsDown(){
    magazine.set(-0.32);
  }

  public void spitBalls(){
    magazine.set(-0.32);
    intakeMaster.set(-0.3);
  }

  @Override
  public void periodic() {}
}
