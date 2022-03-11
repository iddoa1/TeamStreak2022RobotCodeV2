package frc.robot;


import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;


public final class Constants {

    public static final class cIntake{
        //ports
        public static final int intakeMotor = 6;
        public static final int secondMotor = 7;
        public static final int outerIntakeMotor = 12;


        public static final double power = 0.3;
    }

    public static final class cChassis{

        //limitations
        public static final int kMaxSpeed = 3;
        public static final int kMAxAcceleration = 3;
        public static final double kMaxAngularSpeed = 2*Math.PI;

        //PID
        public static final double ks = 0.56478;
        public static final double kv = 5.3626E-05;
        public static final double ka = 5.3643E-06; 

        public static final double driveP = 0.087;

        //Autonomous trajectory tracking.
        public static final double trackWidth = 0.535;
        public static final double wheelRadius = Units.inchesToMeters(3);
        public static final DifferentialDriveKinematics kinematics = 
        new DifferentialDriveKinematics(trackWidth);

        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;

        //ports
        public static final int rMotorM = 3;
        public static final int rMotorF = 4;
        public static final int lMotorM = 1;
        public static final int lMotorF = 2;
    }

    public static final class controller{
        //imports
        public static final int controllerPort = 0;
        public static final int climberControllerPort = 1;

        //triggers
        public static final int botLeft = 7;
        public static final int botRight = 8;
        public static final int topRight = 6;
        public static final int topLeft = 10;

        //values for the POV buttons in degrees
        public static final int up = 0;
        public static final int left = 270;
        public static final int down = 180;
        public static final int right = 90;

        //Axis index numbers
        public static final int leftY = 1;
        public static final int leftX = 0;
        public static final int rightY = 3;
        public static final int rightX = 2;

        //Buttons
        public static final int x = 1; //Shooter
        public static final int b = 3; //intake
        public static final int a = 2; //slow
        public static final int y = 4;
    }

    public static final class cBallElevator{
        //ports
        public static final int beltMotor = 5;


        //power
        public static final double power = 0.25;
    }

    public static final class cLimelight{
        //ports
        public static final double tolorens = 0.15;

        //PID
        public static final double limelightP = 0;
        public static final double limelightI = 0;
        public static final double limelightD = 0;
    }

    public static final class cShooter{

        //digital channels
        public static final int channel1 = 0;
        public static final int channel2 = 1;

        //ports
        public static final int shooterMotor = 6;
        public static final int seconedMotor = 7;

        //PID
        public static final double shooterP = 0;
        public static final double shooterI = 0;
        public static final double shooterD = 0;

        //in rpm
        public static final int tolorens = 300;

        public static final double feedForwardKs = 0.01;
        public static final double feedForwardKv = 0.01;

        public static final int fastShooterValue = 8000;
        public static final int slowShooterValue = 6500;

        public static final double[][] table = {{0, 100}};
    }

    public static final class cClimber{

        public static final double offset=3.9;

        //limits in radians
        public static final double maxAngle = Math.PI/4;
        public static final double midAngle = Math.PI/5;

        //tolorens
        public static final double tolorens = 0.25;

        //ports
        public static final int climberMasterM = 8;
        public static final int climberFollowM = 9;

        //encoder channels
        public static final int channel1 = 3;
        public static final int channel2 = 4;

        //PID
        public static final double climberP = 3;
        public static final double climberI = 0;
        public static final double climberD = 0;

        public static final double ks = 0;
        public static final double kcos = 0;
        public static final double kv = 0;

        //constaraints
        public static final double kMaxVelocityRadPerSec = 7;
        public static final double kMaxVelcityRadPerSecSquard = 20;

        public static final int encoder = 2;

        public static final int magneticSwitch = 5;

        public static final double maxDis = 8150;

        //motor power
        public static final double powerUp = 0.3;
        public static final double powerDown = 0.5;

        //ports
        public static final int turner = 10;
        
    }
}
