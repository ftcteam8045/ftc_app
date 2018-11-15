
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.*;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.signum;
import static org.firstinspires.ftc.teamcode.oldcode.DriveTrain.drive_COEF;
import static org.firstinspires.ftc.teamcode.oldcode.DriveTrain.drive_THRESHOLD;
//Lara + Liesel positioning code


@Autonomous(name = "Auto MEc", group = "Cosmo")
//@Disabled
public class Auto_mecanumDrive extends LinearOpMode {

    /* Declare OpMode members. */
    Hardware8045 Cosmo = new Hardware8045();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();

    // State used for updating telemetry
    public Orientation angles;
    public Acceleration gravity;



    @Override
    public void runOpMode() {

        final double FORWARD_SPEED = 0.3;
        final double TURN_SPEED = 0.3;
        final int cycletime = 500;

        final int goldPosition = 0;   // 0 is on left, 1 in center, 2 on right
        final boolean teamIsRed = true;



        /*
         * Initialize the drive system variables.the Robot
         * The init() method of the hardware class does all the work here
         */
        Cosmo.init(hardwareMap);


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();


        if (teamIsRed) {
            Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_RED);
        } else {
            Cosmo.LEDDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_BLUE);
        }


        // Actual Init loop
        while (!opModeIsActive()) {

        }

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way

//        // Step 1:  Drive forward for 1.5 seconds
//        Cosmo.leftFront.setPower(FORWARD_SPEED);
//        Cosmo.rightFront.setPower(FORWARD_SPEED);
//        Cosmo.leftRear.setPower(FORWARD_SPEED);
//        Cosmo.rightRear.setPower(FORWARD_SPEED);
//        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.5)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

// goldposition 0 = left,1 = center, 2 = right

        
        if (goldPosition == 0) {        // left position
//            mecanumTurn(0.3, 45);
//            mecanumTurn(0.3, -45);
            mecanumDrive(0.5, 18, 0, 0);     // drive forward
            mecanumDrive(0.5, 15, 0, 90);    // drive left
            mecanumDrive(0.5, 12, 0, 0);     // drive forward
//            // Step 3.5: Strafe left
//            // Step 2:  Spin right for 1.3 seconds
//            Cosmo.leftFront.setPower(TURN_SPEED);
//            Cosmo.rightFront.setPower(-TURN_SPEED);
//            Cosmo.leftRear.setPower(-TURN_SPEED);
//            Cosmo.rightRear.setPower(TURN_SPEED);
//            runtime.reset();
//            while (opModeIsActive() && (runtime.seconds() < 1.0)) {
//                telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
//                telemetry.update();
//
//            }
//
//            // Step 1:  Drive forward for .5 seconds
//            Cosmo.leftFront.setPower(FORWARD_SPEED);
//            Cosmo.rightFront.setPower(FORWARD_SPEED);
//            Cosmo.leftRear.setPower(FORWARD_SPEED);
//            Cosmo.rightRear.setPower(FORWARD_SPEED);
//            runtime.reset();
//            while (opModeIsActive() && (runtime.seconds() < 0.75)) {
//                telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
//                telemetry.update();
//            }
        }

        if (goldPosition == 1) {

            // Step 1:  Drive forward for 1.5 seconds
            Cosmo.leftFront.setPower(FORWARD_SPEED);
            Cosmo.rightFront.setPower(FORWARD_SPEED);
            Cosmo.leftRear.setPower(FORWARD_SPEED);
            Cosmo.rightRear.setPower(FORWARD_SPEED);
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < 1.5)) {
                telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
                telemetry.update();
            }
        }

        if (goldPosition == 2) {


            // Step 3.5: Strafe right
            // Step 2:  Spin right for 1.3 seconds
            Cosmo.leftFront.setPower(-TURN_SPEED);
            Cosmo.rightFront.setPower(TURN_SPEED);
            Cosmo.leftRear.setPower(TURN_SPEED);
            Cosmo.rightRear.setPower(-TURN_SPEED);
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < 1.0)) {
                telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
                telemetry.update();

            }


            // Step 1:  Drive forward for .5 seconds
            Cosmo.leftFront.setPower(FORWARD_SPEED);
            Cosmo.rightFront.setPower(FORWARD_SPEED);
            Cosmo.leftRear.setPower(FORWARD_SPEED);
            Cosmo.rightRear.setPower(FORWARD_SPEED);
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < 0.75)) {
                telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
                telemetry.update();
            }
        }

        // Step 4:  Stop and close the claw.
        Cosmo.leftFront.setPower(0);
        Cosmo.rightFront.setPower(0);
        Cosmo.leftRear.setPower(0);
        Cosmo.rightRear.setPower(0);

//        robot.leftClaw.setPosition(1.0);
//        robot.rightClaw.setPosition(0.0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }

    //  Drive routine using the IMU and Mecanum wheels
    //  Robot Orientation is to the field
    //  Drive direction is from the robot

    public void mecanumDrive(double speed, double distance, double robot_orientation, double drive_direction) {
        double max;
        double multiplier;
        int right_start;
        int left_start;
        int moveCounts;
        //int drive_direction = -90;
        moveCounts = (int) (distance * Cosmo.COUNTS_PER_INCH);
        right_start = Cosmo.rightRear.getCurrentPosition();
        left_start = Cosmo.leftRear.getCurrentPosition();
        double lfpower;
        double lrpower;
        double rfpower;
        double rrpower;

        double lfbase;
        double lrbase;
        double rfbase;
        double rrbase;
        lfbase = signum(distance) * Math.cos(Math.toRadians(drive_direction + 45));
        lrbase = signum(distance) * Math.sin(Math.toRadians(drive_direction + 45));
        rfbase = signum(distance) * Math.sin(Math.toRadians(drive_direction + 45));
        rrbase = signum(distance) * Math.cos(Math.toRadians(drive_direction + 45));
        while (((abs(Cosmo.rightRear.getCurrentPosition() - right_start) + abs(Cosmo.leftRear.getCurrentPosition() - left_start)) / 2 < abs(moveCounts)) && opModeIsActive() /* ENCODERS*/) {//Should we average all four motors?
            //Determine correction
            double correction = robot_orientation - getheading();
            if (correction <= -180) {
                correction += 360;
            } else if (correction >= 180) {                      // correction should be +/- 180 (to the left negative, right positive)
                correction -= 360;
            }
            lrpower = lrbase; //MIGHT BE MORE EFFECIENT TO COMBINE THESE WITHT HE ADJUSTMENT PART AND SET ADJUSTMENT TO ZERO IF NOT NEEDED
            lfpower = lfbase;
            rrpower = rrbase;
            rfpower = rfbase;
            if (abs(correction) > drive_THRESHOLD) {//If you are off
                //Apply power to one side of the robot to turn the robot back to the right heading
                double right_adjustment = Range.clip((drive_COEF * correction / 45), -1, 1);
                lrpower -= right_adjustment;
                lfpower -= right_adjustment;
                rrpower = rrbase + right_adjustment;
                rfpower = rfbase + right_adjustment;

            }//Otherwise you Are at the right orientation

            //Determine largest power being applied in either direction
            max = abs(lfpower);
            if (abs(lrpower) > max) max = abs(lrpower);
            if (abs(rfpower) > max) max = abs(rfpower);
            if (abs(rrpower) > max) max = abs(rrpower);

            multiplier = speed / max; //multiplier to adjust speeds of each wheel so you can have a max power of 1 on atleast 1 wheel

            lfpower *= multiplier;
            lrpower *= multiplier;
            rfpower *= multiplier;
            rrpower *= multiplier;

            Cosmo.leftFront.setPower(lfpower);
            Cosmo.leftRear.setPower(lrpower);
            Cosmo.rightFront.setPower(rfpower);
            Cosmo.rightRear.setPower(rrpower);

//            RobotLog.ii("[GromitIR] ", Double.toString(18.7754*Math.pow(sharpIRSensor.getVoltage(),-1.51)), Integer.toString(left_front.getCurrentPosition()));

        }
        //gromit.driveTrain.stopMotors();
        Cosmo.leftFront.setPower(0.0);
        Cosmo.rightFront.setPower(0.0);
        Cosmo.rightRear.setPower(0.0);
        Cosmo.leftRear.setPower(0.0);
    }


    // Turn using the IMU and meccanum drive
    public void mecanumTurn(double speed, double target_heading) {
        if (speed > 1) speed = 1.0;
        //else if(speed <= 0) speed = 0.1;

        double correction = target_heading - getheading();
        if (correction <= -180) {
            correction += 360;   // correction should be +/- 180 (to the left negative, right positive)
        } else if (correction >= 180) {
            correction -= 360;
        }

        while (abs(correction) >= Cosmo.turn_THRESHOLD && opModeIsActive()) { //opmode active?{
            correction = target_heading - getheading();
            if (abs(correction) <= Cosmo.turn_THRESHOLD) break;

            if (correction <= -180)
                correction += 360;   // correction should be +/- 180 (to the left negative, right positive)
            if (correction >= 180) correction -= 360;
            /**^^^^^^^^^^^MAYBE WE ONLY NEED TO DO THIS ONCE?????*/

            double adjustment = Range.clip((Math.signum(correction) * Cosmo.turn_MIN_SPEED + Cosmo.turn_COEF * correction / 100), -1, 1);  // adjustment is motor power: sign of correction *0.07 (base power)  + a proportional bit

            Cosmo.leftFront.setPower(-adjustment * speed);
            Cosmo.leftRear.setPower(-adjustment * speed);
            Cosmo.rightFront.setPower((adjustment * speed));
            Cosmo.rightRear.setPower((adjustment * speed));
        }
//        gromit.driveTrain.stopMotors();
        Cosmo.leftFront.setPower(0.0);
        Cosmo.rightFront.setPower(0.0);
        Cosmo.rightRear.setPower(0.0);
        Cosmo.leftRear.setPower(0.0);
    }


    public float getheading() {
        // Acquiring the angles is relatively expensive; we don't want
        // to do that in each of the three items that need that info, as that's
        // three times the necessary expense.
        angles = Cosmo.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return angles.firstAngle; //For a -180 to 180 range
        //return (angles.firstAngle + 180 + 180)%360; // for a zero to 360 range
    }
}






