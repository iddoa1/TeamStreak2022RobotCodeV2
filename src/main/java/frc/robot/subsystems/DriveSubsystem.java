package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.cChassis;

public class DriveSubsystem extends SubsystemBase {

  private final WPI_TalonFX leftMotorMaster = new WPI_TalonFX(cChassis.lMotorM);
  private final WPI_TalonFX leftMotorSlave = new WPI_TalonFX(cChassis.lMotorF);
  private final WPI_TalonFX rightMotorMaster = new WPI_TalonFX(cChassis.rMotorM);
  private final WPI_TalonFX rightMotorSlave = new WPI_TalonFX(cChassis.rMotorF);

  private final AHRS gyro = new AHRS(Port.kMXP);

  private final PIDController leftPIDController = new PIDController(cChassis.kP, 0, 0);
  private final PIDController righPIDController = new PIDController(cChassis.kP, 0, 0);

  private DifferentialDriveOdometry odometry;

  private final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(cChassis.ks, cChassis.kv);

  public DriveSubsystem() {
    rightMotorMaster.configFactoryDefault();
    leftMotorMaster.configFactoryDefault();
    gyro.reset();

    rightMotorSlave.follow(rightMotorMaster);
    leftMotorSlave.follow(leftMotorMaster);

    rightMotorMaster.setInverted(true);
    rightMotorSlave.setInverted(InvertType.FollowMaster);

    odometry = new DifferentialDriveOdometry(gyro.getRotation2d());
  }

  public void setSpeeds(DifferentialDriveWheelSpeeds speeds){
    final double leftFeedForward = feedforward.calculate(speeds.leftMetersPerSecond);
    final double rightFeedForward = feedforward.calculate(speeds.rightMetersPerSecond);
    
    final double leftOutput = 
      leftPIDController.calculate(nativeSpeedToMetersPerSeconed(leftMotorMaster.getSelectedSensorVelocity()), speeds.leftMetersPerSecond);
    final double rightOutput = 
      righPIDController.calculate(nativeSpeedToMetersPerSeconed(rightMotorMaster.getSelectedSensorVelocity()), speeds.rightMetersPerSecond);

    leftMotorMaster.setVoltage(leftOutput+leftFeedForward);
    rightMotorMaster.setVoltage(rightOutput+rightFeedForward);
  }

  public void drive(double speed, double turn){
    var wheelSpeeds = cChassis.kinematics.toWheelSpeeds(new ChassisSpeeds(speed, 0, turn));
    setSpeeds(wheelSpeeds);
  }

  public void updateOdemetry(){
    odometry.update(
      gyro.getRotation2d(),
      nativeUnitsToDistanceMeters(leftMotorMaster.getSelectedSensorPosition()),
      nativeUnitsToDistanceMeters(rightMotorMaster.getSelectedSensorPosition())
      );
  }

  //returns the distance traveled in meters.
  public double nativeUnitsToDistanceMeters(double sensorCounts){
    double motorRotation = sensorCounts/cChassis.encoderResulotion;
    double wheelRotation = motorRotation/cChassis.kGearRatio;
    double positionMeters = wheelRotation *(2*Math.PI*cChassis.kWheelRadius);
    return positionMeters;
  }

  //returns the velocity in meters per seconed.
  public double nativeSpeedToMetersPerSeconed(double sensorRate){
    double ratePerSeconed = sensorRate*1000;
    return nativeSpeedToMetersPerSeconed(ratePerSeconed);
  }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
