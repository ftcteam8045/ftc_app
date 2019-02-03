package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.text.SimpleDateFormat;
import java.util.Date;


@TeleOp(name="CRServo_Test", group="Rick")  // @Autonomous(...) is the other common choice
public class vexmotor extends OpMode
{
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    //Declare CRServo (Vex 393 Motor)
    private CRServo vexMotor;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        //Initalize Vex Motor to same name in robot config file
        vexMotor = hardwareMap.crservo.get("sweeper");


    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        telemetry.addData("Status", "Running: " + runtime.toString());

        //If X Button is pressed, run CRServo at half speed, otherwise stop
        if(gamepad1.x){
            vexMotor.setPower(.5);
        }else if (gamepad1.y ){
            vexMotor.setPower(0);
        }else {
            vexMotor.setPower(0);
        }


    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}