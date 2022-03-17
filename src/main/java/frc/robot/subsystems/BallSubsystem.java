package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.cBallElevator;
import frc.robot.Constants.cIntake;

public class BallSubsystem extends SubsystemBase {
  
  WPI_TalonFX intakeMaster = new WPI_TalonFX(cIntake.intakeMotor);
  WPI_TalonFX seconedIntake = new WPI_TalonFX(cIntake.secondMotor);

  WPI_TalonFX outerIntake = new WPI_TalonFX(13);
  WPI_TalonFX intakeOperation = new WPI_TalonFX(12);


  WPI_TalonFX magazine = new WPI_TalonFX(cBallElevator.beltMotor);

  public BallSubsystem() {
    intakeMaster.configFactoryDefault();
    outerIntake.configFactoryDefault();
    intakeOperation.configFactoryDefault();

    seconedIntake.follow(intakeMaster);

    intakeOperation.configForwardSoftLimitThreshold(5200);
    intakeOperation.configReverseSoftLimitThreshold(00);
    intakeOperation.configForwardSoftLimitEnable(true);
    intakeOperation.configReverseSoftLimitEnable(true);

    intakeOperation.setSelectedSensorPosition(0);
    intakeOperation.setNeutralMode(NeutralMode.Brake);
  }

  public void ballsIn(){
    intakeMaster.set(0.3);
    outerIntake.set(0.3);
  }

  public void ballOut(){
    intakeMaster.set(-0.3);
    outerIntake.set(-0.3);
  }

  public boolean isOpen(){
    return intakeOperation.getSelectedSensorPosition() >= 5200;
  }

  public boolean isClosed(){
    return intakeOperation.getSelectedSensorPosition() <= 100;
  }

  public void openIntake(){
    intakeOperation.set(0.2);
  }

  public void closeIntake(){
    intakeOperation.set(-0.2);
  }

  public void stop(){
    intakeMaster.set(0);
    magazine.set(0);
  }

  public void stopOperation(){
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

  public void resetEncoder(){
    intakeOperation.setSelectedSensorPosition(0);
    intakeOperation.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void periodic() {}
}
