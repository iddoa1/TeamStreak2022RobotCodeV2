package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.cBallElevator;
import frc.robot.Constants.cIntake;

public class BallSubsystem extends SubsystemBase {
  
  WPI_TalonFX intakeMaster = new WPI_TalonFX(cIntake.intakeMotor);
  WPI_TalonFX seconedIntake = new WPI_TalonFX(cIntake.secondMotor);

  WPI_TalonFX outerIntake = new WPI_TalonFX(cIntake.outerIntakeMotor);
  CANSparkMax intakeOperation = new CANSparkMax(cIntake.intakeOperation, MotorType.kBrushless);
  DutyCycleEncoder encoder = new DutyCycleEncoder(cIntake.intakeEncoder);

  WPI_TalonFX magazine = new WPI_TalonFX(cBallElevator.beltMotor);

  public BallSubsystem() {
    intakeMaster.configFactoryDefault();
    outerIntake.configFactoryDefault();

    encoder.reset();
    encoder.setDistancePerRotation(2*Math.PI);

    seconedIntake.follow(intakeMaster);
    outerIntake.setInverted(true);

    intakeOperation.setIdleMode(IdleMode.kBrake);
  }

  public boolean isOpen(){
    return encoder.getDistance()>=cIntake.openIntake;
  }

  public boolean isClosed(){
    return encoder.getDistance()<=cIntake.closedIntake;
  }

  public void openIntake(){
    if(!isOpen()) intakeOperation.set(0.25); else intakeOperation.set(0);
    SmartDashboard.putNumber("intake power", 0.25);
  }

  public void openHand(){
    intakeOperation.set(0.4);
  }

  public void closeHand(){
    intakeOperation.set(-0.4);
  }

  public void closeIntake(){
    if(!isClosed()) intakeOperation.set(-0.25); else intakeOperation.set(0);
  }

  public void stopOperation(){
    intakeOperation.set(0);
  }

  public void ballsIn(){
    intakeMaster.set(0.55);
    outerIntake.set(0.5);
  }

  public void ballOut(){
    intakeMaster.set(-0.3);
    outerIntake.set(-0.4);
  }

  public void stop(){
    intakeMaster.set(0);
    magazine.set(0);
    outerIntake.set(0);
  }

  public void shoot(double shooterSpeed){
    intakeMaster.set(shooterSpeed);
    magazine.set(0.4);
  }

  public void shootUp(double shooterSpeed){
    intakeMaster.set(shooterSpeed);
  }

  public void shootUpValue(){
    intakeMaster.set(0.47);
  }

  public void stopShooter(){
    intakeMaster.set(0);
  }

  public void ballsUp(){
    magazine.set(0.25);
  }

  public void ballsUpH(){
    magazine.set(0.3);
  }

  public void ballsDown(){
    magazine.set(-0.32);
  }

  public void spitBalls(){
    magazine.set(-0.32);
    intakeMaster.set(-0.3);
    outerIntake.set(-0.45);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("encoder", encoder.getDistance());
  }
}
