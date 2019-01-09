package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


@TeleOp(name = "MainTele", group = "8045")  // @Autonomous(...) is the other common choice
//@Disabled
public class MainTeleET extends OpMode {

    Hardware8045 Cosmo;

    // variables used during the configuration process

    private ElapsedTime runtime = new ElapsedTime();
    double timeLeft;

//    double turnDirection;
    public double topSpeed = 0.5 ;
    public boolean reverseDrive = false;
    //Booleans

    public boolean aIsReleased = true;

    //Back mode
    public boolean frontIsForward = true;
    public boolean rightbtnIsReleased = true;

    //Drive type
    public double driveType = 0;
    public String driveMode = "Normal";
    public boolean run = false;

    @Override
    public void init() {
        Cosmo = new Hardware8045();
        Cosmo.init(hardwareMap);
        telemetry.addData("Status", "Initializing");
        telemetry.update();

        timeLeft = 120;



        telemetry.addData("Finished", "Initializing");
        telemetry.update();
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        timeLeft = 120 - runtime.seconds();

//        // BACK mode


        if (gamepad1.right_stick_button) {
            if (rightbtnIsReleased) {
                rightbtnIsReleased = false;
                frontIsForward = !frontIsForward;
            }
        } else {
            rightbtnIsReleased = true;
        }

        if (gamepad1.left_stick_button) {
            if (gamepad1.dpad_up) {
                driveType = 0;
            } else if (gamepad1.dpad_left) {
                driveType = 1;
            } else if (gamepad1.dpad_right) {
                driveType = 2;
            }
        }

        /**  set drive speed  **/
        if (gamepad1.left_bumper) {
            topSpeed = 1.0;
        } else if (gamepad1.left_trigger > 0.1) {
            topSpeed = 0.4;
        } else {
            topSpeed = 1.0;
        }

        /** DRIVE  HERE   */
        if (driveType == 0) {
            drivesmart(-gamepad1.right_stick_x, -gamepad1.right_stick_y, gamepad1.left_stick_x);
            driveMode = "Normal";
        } else if(driveType == 1) {
            drivesmart(-gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
            driveMode = "Gamer";
        } else {
            drivesmart(-gamepad1.left_stick_x, -gamepad1.right_stick_y, gamepad1.right_stick_x);
            driveMode = "South Paw";
        }

        /**    Raise and lower the lift    **/
        if (gamepad2.right_trigger >= 0.1) {
            Cosmo.liftmotor.setPower(-gamepad2.right_trigger);
        }
        else {
            Cosmo.liftmotor.setPower(0);
        }
        if (gamepad2.left_trigger >= 0.1)  {
            Cosmo.liftmotor.setPower(gamepad2.left_trigger);
        }
        else {
            Cosmo.liftmotor.setPower(0);
        }

        /**ONE HIT LIFT **/
        if (gamepad2.right_bumper && run == false) {
            frontIsForward = frontIsForward;
            //move lift up
            Cosmo.liftmotor.setPower(1);
            sleep(1400);
            Cosmo.liftmotor.setPower(0);
            sleep(100);
            //strafe right
            drivesmart(1, 0, 0);
            sleep(1000);
            drivesmart(0, 0, 0);
            sleep(100);
            //pull up and hang
            Cosmo.liftmotor.setPower(-1);
            sleep(1600);
            Cosmo.liftmotor.setPower(0);
            run = true;
        }

//        if (run == true) {
//            Cosmo.liftmotor.setPower(0);
//            sleep(50);
//            Cosmo.liftmotor.setPower(-1);
//            sleep(10);
//        }

        telemetry.addLine().addData
("Drive Mode", driveMode);
        telemetry.addData("TimeLeft: ",timeLeft);
        telemetry.addData("Right -X: ", -gamepad1.right_stick_x);
        telemetry.addData("Right -Y: ", -gamepad1.right_stick_y);
        telemetry.addData(" Left -X: ", -gamepad1.left_stick_x);
        telemetry.addData("Right Button Is Released", rightbtnIsReleased);
        telemetry.addData("Front Is Forward", frontIsForward);
        telemetry.update();

    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * This method puts the current thread to sleep for the given time in msec.
     * It handles InterruptException where it recalculates the remaining time
     * and calls sleep again repeatedly until the specified sleep time has past.
     *
     * @param sleepTime specifies sleep time in msec.
     */
    public static void sleep(long sleepTime) {
        long wakeupTime = System.currentTimeMillis() + sleepTime;

        while (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
            }
            sleepTime = wakeupTime - System.currentTimeMillis();
        }
    }   //sleep


    public void drivesmart(double x, double y, double turn) {

        if (frontIsForward) {             // driving with the front facing forward

        } else {                            // driving with the rear facing forward
            y = -y;
            x = -x;
        }

        double lfpower;
        double lrpower;
        double rfpower;
        double rrpower;

        double rotation = turn*0.75;  // knock down the turn power

        //Determine largest power being applied in either direction

        lfpower = ( y - x + rotation);
        lrpower = ( y + x + rotation);
        rfpower = ( y + x - rotation);
        rrpower = ( y - x - rotation);

        lfpower = lfpower * topSpeed;
        lrpower = lrpower * topSpeed;
        rfpower = rfpower * topSpeed;
        rrpower = rrpower * topSpeed;

        Cosmo.leftFront.setPower(lfpower);
        Cosmo.leftRear.setPower(lrpower);
        Cosmo.rightFront.setPower(rfpower);
        Cosmo.rightRear.setPower(rrpower);

    }
//


    public void stop() {
        Cosmo.leftFront.setPower(0.0);
        Cosmo.leftRear.setPower(0.0);
        Cosmo.rightFront.setPower(0.0);
        Cosmo.rightRear.setPower(0.0);
    }




}
