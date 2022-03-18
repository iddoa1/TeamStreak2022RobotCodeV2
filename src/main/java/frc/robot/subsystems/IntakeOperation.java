// package frc.robot.subsystems;

// import com.ctre.phoenix.motorcontrol.NeutralMode;
// import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

// import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.wpilibj.DutyCycleEncoder;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.PIDSubsystem;

// public class IntakeOperation extends PIDSubsystem {

  
  // WPI_TalonFX intakeOperation = new WPI_TalonFX(12);
  // DutyCycleEncoder encoder = new DutyCycleEncoder(6);

//   public IntakeOperation() {
//     super(
//         new PIDController(6, 0, 0));

//     getController().setTolerance(0.2);
//     intakeOperation.configFactoryDefault();
//     encoder.setDistancePerRotation(2*Math.PI);

//     intakeOperation.setNeutralMode(NeutralMode.Brake);
//   }

//   @Override
//   public void useOutput(double output, double setpoint) {
//     intakeOperation.setVoltage(output);
//     SmartDashboard.putNumber("intake volts", output);
//   }

//   @Override
//   public double getMeasurement() {
//     SmartDashboard.putNumber("intake encoder", encoder.getDistance());
//     SmartDashboard.putBoolean("intake open", isOpen());
//     SmartDashboard.putBoolean("intake closed", isClosed());

//     SmartDashboard.putNumber("intake setPoint", getController().getSetpoint());
//     return encoder.getDistance();
//   }

//   public void resetEncoder(){
//     intakeOperation.setSelectedSensorPosition(0);
//   }
  
//   public boolean isOpen(){
//     return encoder.getDistance() >= 4.7;
//   }

//   public boolean isClosed(){
//     return encoder.getDistance() <= 4.1;
//   }
// }
