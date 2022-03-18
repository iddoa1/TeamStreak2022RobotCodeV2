package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.cClimber;

public class ElevatorSubsystem extends SubsystemBase {
  WPI_TalonFX elevatorMotorM = new WPI_TalonFX(cClimber.climberMasterM);
  WPI_TalonFX elevatorMotorF = new WPI_TalonFX(cClimber.climberFollowM);
  
  DigitalInput magneticSwitch = new DigitalInput(cClimber.magneticSwitch);

  boolean reseted = false;

  /** Creates a new ClimbSubsystem. */
  public ElevatorSubsystem() {
    elevatorMotorF.configFactoryDefault();
    elevatorMotorM.configFactoryDefault();

    setBrake();
    elevatorMotorF.follow(elevatorMotorM);
    elevatorMotorM.setSelectedSensorPosition(0);
    
    //elevatorMotorM.configForwardSoftLimitThreshold(cClimber.elevatorMaxDis);
    //elevatorMotorM.configForwardSoftLimitEnable(true);
  }

  @Override
  public void periodic() {
    if (isElevatorDown()) elevatorMotorM.setSelectedSensorPosition(0);
    if (!reseted) reseted = isElevatorDown();
    
    SmartDashboard.putNumber("elevator", elevatorMotorM.getSelectedSensorPosition());
  }

  public void elevatorUp() {
    if (reseted)
      elevatorMotorM.set(0.2);
    SmartDashboard.putBoolean("elevator stop", false);
  }

  public void elevatorDown() {
    if (reseted)
      if(!isElevatorDown()) elevatorMotorM.set(-0.3); else elevatorMotorM.set(0);
  }

  public void stop() {
    elevatorMotorM.set(0);
    SmartDashboard.putBoolean("elevator stop", true);
  }

  public void setBrake() {
    elevatorMotorM.setNeutralMode(NeutralMode.Brake);
    elevatorMotorF.setNeutralMode(NeutralMode.Brake);
  }

  public void setCoast() {
    elevatorMotorM.setNeutralMode(NeutralMode.Coast);
    elevatorMotorF.setNeutralMode(NeutralMode.Coast);
  }

  public boolean isElevatorDown() {
    return !magneticSwitch.get();
  }

  public boolean isElevatorUp() {
    return elevatorMotorM.getSelectedSensorPosition() >= cClimber.elevatorMaxDis;
  }

  public void openUntil(double dis){
    if(!isOpenUntil(dis)) elevatorMotorM.set(0.15); else elevatorMotorM.set(0);
  }

  public boolean isOpenUntil(double dis){
    return elevatorMotorM.getSelectedSensorPosition()>=dis;
  }
}
