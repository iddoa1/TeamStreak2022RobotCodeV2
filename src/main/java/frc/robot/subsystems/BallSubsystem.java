package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.cBallElevator;
import frc.robot.Constants.cIntake;

public class BallSubsystem extends SubsystemBase {
  
  WPI_TalonFX intakeMaster = new WPI_TalonFX(cIntake.intakeMotor);
  WPI_TalonFX seconedIntake = new WPI_TalonFX(cIntake.secondMotor);

  WPI_TalonFX outerIntake = new WPI_TalonFX(cIntake.outerIntakeMotor);
  WPI_TalonFX intakeOperation = new WPI_TalonFX(cIntake.intakeOperation);
  DutyCycleEncoder encoder = new DutyCycleEncoder(cIntake.intakeEncoder);

  WPI_TalonFX magazine = new WPI_TalonFX(cBallElevator.beltMotor);

  public BallSubsystem() {
    intakeMaster.configFactoryDefault();
    outerIntake.configFactoryDefault();

    seconedIntake.follow(intakeMaster);
  }

  public boolean isOpen(){
    return encoder.getDistance()>=cIntake.openIntake;
  }

  public boolean isClosed(){
    return encoder.getDistance()<=cIntake.closedIntake;
  }

  public void openIntake(){
    if(!isOpen()) intakeOperation.set(0.2); else intakeOperation.set(0);
  }

  public void closeIntake(){
    if(!isClosed()) intakeOperation.set(-0.2); else intakeOperation.set(0);
  }

  public void stopOperation(){
    intakeOperation.set(0);
  }

  public void ballsIn(){
    intakeMaster.set(0.3);
    outerIntake.set(0.3);
  }

  public void ballOut(){
    intakeMaster.set(-0.3);
    outerIntake.set(-0.3);
  }

  public void stop(){
    intakeMaster.set(0);
    magazine.set(0);
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
