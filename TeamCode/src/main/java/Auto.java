import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import util.robot;

public class Auto {
    public Follower follower;
    public Telemetry telemetry;
    public enum Side{
        BUCKET,
        OBSERVATION,
    }

    private robot R;
    public boolean actionBusy;

    private liftFSM LiftFSM;
    private clawFSM ClawFSM;

    private Side side;
    public Timer transferTimer = new Timer();
    public int transferState = -1;
    public Path forwards, backwards;

    public Pose startPose,
            specimen1Pose,specimen2Pose, specimen3Pose,
            shortBack1Pose, longBack2Pose, longBack3Pose, longBack4Pose,
            shift2Pose, shift3Pose, shift4Pose,
            pickup2Pose, pickup3Pose, pickup4Pose,
            specimenControlPoint1Pose, specimenControlPoint2Pose,
            curveControlPoint1Pose, curveControlPoint2Pose;


    public PathChain moveCurve, push23, gather3;
    public Path goal1, goal2, goal3, shift, backup2, backup3;

    public Auto(robot Robot, Telemetry telemetry, Follower follower, Side side) {
        ClawFSM = new clawFSM(Robot, telemetry);
        LiftFSM = new liftFSM(Robot, telemetry);


        this.follower = follower;
        this.telemetry = telemetry;
        this.side = side;

        createPose();
        buildPaths();

        init();
    }
    public void createPose(){
        switch(side){
            case BUCKET:
                break;
            case OBSERVATION:
                startPose = new Pose(10.500, 60.500, Math.toRadians(0));
                specimen1Pose = new Pose(35.000, 71.500, Math.toRadians(0));
                specimen2Pose = new Pose(35,68,Math.toRadians(0));
                specimen3Pose = new Pose(35, 64.5,Math.toRadians(0));
                specimenControlPoint1Pose = new Pose(17, 46.5); // What is the direction on the robot?
                specimenControlPoint2Pose = new Pose(22, 64);
                curveControlPoint1Pose = new Pose(34.5, 33.5);
                curveControlPoint2Pose = new Pose(59, 41.5);
                longBack2Pose= new Pose(66, 25, Math.toRadians(180));
                longBack3Pose= new Pose(66, 14,Math.toRadians(180));
                shortBack1Pose = new Pose(26.5, 71.5, Math.toRadians(0));
                shift2Pose = new Pose(20, 14, Math.toRadians(180));
                shift3Pose = new Pose(20, 25,Math.toRadians(180)) ;
                pickup2Pose = new Pose(9.5, 14, Math.toRadians(180));
                pickup3Pose = new Pose(9.5, 25, Math.toRadians(180));
                break;
        }
    }
    public void buildPaths(){
        switch(side) {
            case BUCKET:
                break;
            case OBSERVATION:
                goal1 = new Path(new BezierCurve(new Point(startPose), new Point(specimen1Pose)));
                goal1.setConstantHeadingInterpolation(specimen1Pose.getHeading());

            moveCurve = follower.pathBuilder()
                        .addPath(new BezierLine(new Point(specimen1Pose), new Point(shortBack1Pose)));
                        .setConstantHeadingInterpolation(parkPose.getHeading())
                        .addPath(new BezierCurve(new Point(shortBack1Pose), new Point(curveControlPoint1Pose), new Point(curveControlPoint2Pose),  Point(longBack2Pose)));
                        .setLinearHeadingInterpolation(specimen1Pose.getHeading(), longBack2.getHeading());
                        .build();
                break;

            push23 = follower.pathBuilder()
                    .addPath(new BezierLine(new Point(longBack2Pose), new Point(pickup3Pose)));
                    .setConstantHeadingInterpolation(pickup3Pose.getHeading());
                    .addPath(new BezierLine(new Point(pickup3Pose), new Point(longBack2Pose)));
                    .setConstantHeadingInterpolation(longBack2.getHeading());
                    .addPath(new BezierLine(new Point(longBack2Pose), new Point(longBack3Pose)));
                    .setConstantHeading(longBack2Pose.getHeading());
                    .addPath(new BezierLine(new Point(longBack3Pose), new Point(pickup3Pose)));
                    .setConstantHeadingInterpolation(longBack3Pose.getHeading());
                    .build();
            break;
        }



        moveCurve = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(9.484, 107.064),
                                new Point(34.440, 107.813),
                                new Point(13.227, 73.373),
                                new Point(39.182, 73.373)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();
        forwards = new Path(new BezierLine(new Point(0,0, Point.CARTESIAN), new Point(10,0, Point.CARTESIAN)));
        forwards.setConstantHeadingInterpolation(0);
        backwards = new Path(new BezierLine(new Point(10,0, Point.CARTESIAN), new Point(0,0, Point.CARTESIAN)));
        backwards.setConstantHeadingInterpolation(0);

    }
    public void init() {
        LiftFSM.initialize();
    }
    public void start(){

    }
    public void update(){
        follower.update();
        LiftFSM.update();
        ClawFSM.update();


        transfer(); 
    }
    public void transfer(){
        switch(transferState){
            case 1:
                actionBusy = true;
                ClawFSM.setState(clawFSM.ClawState.CLOSED);
                transferTimer.resetTimer();
                setTransferState(2);
                break;
            case 2:
                if(transferTimer.getElapsedTimeSeconds() > 1.5){
                    LiftFSM.setState(liftFSM.LiftState.MID);
                    transferTimer.resetTimer();
                    setTransferState(3);
                }
                break;
            case 3:
                if(LiftFSM.actionNotBusy()){
                    LiftFSM.setState(liftFSM.LiftState.LOW);
                    transferTimer.resetTimer();
                    setTransferState(4);
                }
                break;
            case 4:
                if(LiftFSM.actionNotBusy()){
                    ClawFSM.setState(clawFSM.ClawState.OPEN);
                    actionBusy = false;
                    setTransferState(-1);
                }
                break;
        }
    }
    public void setTransferState(int x) {
        transferState = x;
        telemetry.addData("Transfer", x);

    }

    public void startTransfer() {
        if (actionNotBusy()) {
            setTransferState(1);
        }

    }
    public boolean actionNotBusy() {
        return !actionBusy;
    }

    public boolean notBusy() {
        return (!follower.isBusy() && actionNotBusy());
    }

}