package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.cClimber;

public class ShlongSubsystem extends SubsystemBase {

  
  private CANSparkMax turner = new CANSparkMax(cClimber.turner, MotorType.kBrushless);
  private DutyCycleEncoder encoder = new DutyCycleEncoder(cClimber.encoder);
  
  public ShlongSubsystem() {
    turner.restoreFactoryDefaults();
    turner.setIdleMode(IdleMode.kBrake);
    encoder.setDistancePerRotation(2*Math.PI);
  }

  public void open(){
    if(!isOpen()) turner.set(0.35); else turner.set(0);
  }

  public void close(){
  if(!(encoder.getDistance()<cClimber.closeLimit)) turner.set(-0.35); else turner.set(0);
  }

  public void stop(){
    turner.set(0);
  }

  public boolean isClose(){
    return encoder.getDistance()<4.01;
  }

  public boolean isOpen(){
    return encoder.getDistance()>=4.5;
  }


  public void handOpen(){
    turner.set(0.4);
  }

  public void handClose(){
    turner.set(-0.4);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Shlong", encoder.getDistance());
    SmartDashboard.putBoolean("shlong is open", encoder.getDistance()>=cClimber.openLimit);
  }
}
