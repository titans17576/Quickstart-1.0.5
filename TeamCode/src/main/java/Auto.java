import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import util.robot;

public class Auto {
    private Follower follower;
    private robot R;

    private liftFSM LiftFSM;
    private clawFSM ClawFSM;
    public Auto(Robot robot, Telemetry telemetry, Follower follower) {
        claw = new ClFSM(hardwareMap, clawState);
        lift = new LiftSubsystem(hardwareMap, telemetry);


        this.follower = follower;
        this.telemetry = telemetry;

        
        init();
    }

    public void init() {

    }
    public void start(){

    }
    public void update(){

    }
    public void transfer(){

    }
}