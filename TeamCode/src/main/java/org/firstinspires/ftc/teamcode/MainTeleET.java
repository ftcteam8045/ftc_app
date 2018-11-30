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
//    public Servo    flagServo  = null;



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



        if(gamepad1.y) {
            // move to 0 degrees.
            Cosmo.flagServo.setPosition(0.4);
        } else if (gamepad1.x) {
            // move to 90 degrees.
            Cosmo.flagServo.setPosition(0.5);
        } else if (gamepad1.a) {
            // move to 180 degrees.
            Cosmo.flagServo.setPosition(0.6);
        }


        /**
         * DRIVE Functions HERE
         */

         drivesmart(-gamepad1.right_stick_x, -gamepad1.right_stick_y,  gamepad1.left_stick_x);
//        if (gamepad1.right_trigger >= 0.01) {
//            Cosmo.liftmotor.setPower(gamepad1.right_trigger);
//
//        }
//        if (gamepad1.left_trigger >= 0.01) {
//            Cosmo.liftmotor.setPower(-gamepad1.left_trigger);
//
//        }


        //set drive speed
        if (gamepad1.left_bumper) {
            topSpeed = 1.0;
        } else if (gamepad1.left_trigger > 0.1) {
            topSpeed = 0.2;
        } else {
            topSpeed = 0.5;
        }

//
//        // BACK mode
//        if (gamepad1.right_stick_button) {
//            if (reverseDrive == false) {
//                reverseDrive = true;
//
//            }
//            else {
//                reverseDrive = false;
//            }
//        }



        telemetry.addData("TimeLeft: ",timeLeft);
        telemetry.addData("Right -X: ", -gamepad1.right_stick_x);
        telemetry.addData("Right -Y: ", -gamepad1.right_stick_y);
        telemetry.addData(" Left -X: ", -gamepad1.left_stick_x);
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

////        //Determine largest power being applied in either direction  apply scales so that the Top wheel speed is not over 1 and scaled down to the topSpeed
//        double max = abs(lfpower);
//        if (abs(lrpower) > max) max = abs(lrpower);
//        if (abs(rfpower) > max) max = abs(rfpower);
//        if (abs(rrpower) > max) max = abs(rrpower);
//            double multiplier = topSpeed / max; //multiplier to adjust speeds of each wheel so you can have a max power of 1 on atleast 1 wheel
//        double multiplier = (topSpeed / max) + 0.2*Math.abs(x);  // try to boost up the strafing power
//        double multiplier = (((topSpeed) * sqrt(pow(x,2.) + pow(y,2.))) / max) ;  // take ibto account the joysticks position from the origin

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
