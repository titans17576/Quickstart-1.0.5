package util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.List;

/*
 * sigma
 */
@Config
public class robot{
    public DcMotorEx  liftMotor;
    // public DcMotorEx leftFront, leftRear, rightRear, rightFront,
    private List<DcMotorEx> motors;
    //public CRServo claw;
    public Servo claw, arm, extendo, intakeWrist, intakeClaw, specArm, intakeWrist2;
    public robot() {

    }

    public robot(HardwareMap hardwareMap) {
        /*
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftRear = hardwareMap.get(DcMotorEx.class, "leftRear");
        rightRear = hardwareMap.get(DcMotorEx.class, "rightRear");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
         */
        arm = hardwareMap.get(Servo.class, "armServo");
        claw = hardwareMap.get(Servo.class, "clawServo");
        extendo = hardwareMap.get(Servo.class, "extendoServo");
        intakeWrist = hardwareMap.get(Servo.class, "intakeWristServo");
        intakeClaw = hardwareMap.get(Servo.class, "intakeClawServo");
        specArm = hardwareMap.get(Servo.class, "specArmServo");
        /*intakeWrist2 = hardwareMap.get(Servo.class, "intakeWrist2Servo");*/
        liftMotor = hardwareMap.get(DcMotorEx.class, "liftMotor");

        /*leftFront.setPower(0);
        leftRear.setPower(0);
        rightRear.setPower(0);
        rightFront.setPower(0);
        //claw.setPower(0);

        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftRear.setDirection(DcMotorSimple.Direction.FORWARD);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
*/
        liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
/*
        motors = Arrays.asList(leftFront, leftRear, rightRear, rightFront);

        for (DcMotorEx motor : motors) {
            MotorConfigurationType motorConfigurationType = motor.getMotorType().clone();
            motorConfigurationType.setAchieveableMaxRPMFraction(1.0);
            motor.setMotorType(motorConfigurationType);
        }*/
    }
}