package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.cBallElevator;
import frc.robot.Constants.cIntake;

public class BallSubsystem extends SubsystemBase {


  WPI_TalonFX intakeMaster = new WPI_TalonFX(cIntake.intakeMotor);
  WPI_TalonFX seconedIntake = new WPI_TalonFX(cIntake.secondMotor);

  WPI_TalonFX magazine = new WPI_TalonFX(cBallElevator.beltMotor);

  public BallSubsystem() {
    intakeMaster.configFactoryDefault();

    seconedIntake.follow(intakeMaster);
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
