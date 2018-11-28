/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.signum;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;
import static org.firstinspires.ftc.teamcode.oldcode.DriveTrain.drive_COEF;
import static org.firstinspires.ftc.teamcode.oldcode.DriveTrain.drive_THRESHOLD;

//Imports for front facing camera

@Autonomous(name="editMethod", group="Pushbot")
//@Disabled
public class ElijahTristanAutoET_editmethod extends LinearOpMode {
    Hardware8045neverrest Cosmo = new Hardware8045neverrest();   // Use a Pushbot's hardware
    public Orientation angles;
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    /* Declare OpMode members. */
    //Hardware8045 robot = new Hardware8045();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();
    static final double FORWARD_SPEED = 0.3;
    static final double TURN_SPEED = 0.3;
    static int goldPosition = 0;   // 0 is on left, 1 in center, 2 on right
    private static final String VUFORIA_KEY = "AWfr4/T/////AAAAGRMg80Ehu059mDMJI2h/y+4aBmz86AidOcs89UScq+n+QQyGFT4cZP+rzg1M9B/CW5bgDoVf16x6x3WlD5wYKZddt0UWQS65VIFPjZlM9ADBWvWJss9L1dj4X2LZydWltdeaBhkXTXFnKBkKLDcdTyC2ozJlcAUP0VnLMeI1n+f5jGx25+NdFTs0GPJYVrPQRjODb6hYdoHsffiOCsOKgDnzFsalKuff1u4Z8oihSY9pvv3me2gJjzrQKqp2gCRIZAXDdYzln28Z/8vNSU+aXr6eoRrNXPpYdAwyYI+fX2V9H04806eSUKsNYcPBSbVlhe2KoUsSD7qbOsBMagcEIdMZxo010kVCHHhnhV3IFIs8";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    private static final CameraDirection CAMERA_CHOICE = FRONT;
    public int currentEdit = 0;
    public int waitTime1 = 0;
    public int driveDis2 = 15;
    public int driveDis3 = 20;
    public boolean dpadPressedUp = false;
    public boolean dpadPressedDown = false;
    public boolean dpadPressedLeft = false;
    public boolean dpadPressedRight = false;
    public String[] position = {"base", "crater"};
    public int positionIndex = 1;
    public String arrow1 = " ";
    public String arrow2 = " ";
    public String arrow3 = " ";
    public String arrow4 = " ";

    public boolean dpadup = false;
    public boolean dpaddown = false;
    public boolean dpadleft = false;
    public boolean dpadright = false;
    public boolean button_a = false;
    public boolean button_b = false;
    public boolean button_x = false;
    public boolean button_y = false;
    public boolean start = false;
    public boolean backbutton = false;
    public boolean righttrigger = false;
    public boolean lefttrigger = false;
    public boolean rightbumper = false;
    public boolean leftbumper = false;
    public boolean rightstickx = false;
    public boolean rightsticky = false;
    public boolean leftstickx = false;
    public boolean leftsticky = false;
    public boolean rightstickbutton = false;
    public boolean leftstickbutton = false;



    /**********************************************************************************************\
     |--------------------------------- Pre Init Loop ----------------------------------------------|
     \**********************************************************************************************/

    /**********************************************************************************************\
     |--------------------------------- Vuforia Setup ----------------------------------------------|
     \**********************************************************************************************/

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    @Override
    public void runOpMode() {
        // get a reference to the RelativeLayout so we can change the background  for Edit mode
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);


        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.cameraDirection = CAMERA_CHOICE;
        initVuforia();
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        Cosmo.init(hardwareMap);


        /**********************************************************************************************\
         |--------------------------------------Init Loop-----------------------------------------------|
         \**********************************************************************************************/


        /** Activate Tensor Flow Object Detection. */
        if (tfod != null) {
            tfod.activate();
        }


        while(!opModeIsActive()  && !isStopRequested()) {

            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("Press left Joystick to edit ","");
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    if (updatedRecognitions.size() == 3) {
                        int goldMineralX = -1;
                        int silverMineral1X = -1;
                        int silverMineral2X = -1;
                        for (Recognition recognition : updatedRecognitions) {
                            if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                goldMineralX = (int) recognition.getLeft();
                            } else if (silverMineral1X == -1) {
                                silverMineral1X = (int) recognition.getLeft();
                            } else {
                                silverMineral2X = (int) recognition.getLeft();
                            }
                        }
                        if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                            if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                telemetry.addData("Gold Mineral Position", "Right");
//                                goldPosition = 2;
                            } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                telemetry.addData("Gold Mineral Position", "Left");
//                                goldPosition = 0;
                            } else {
                                telemetry.addData("Gold Mineral Position", "Center");
//                                goldPosition = 1;
                            }
                        }
                    }
                    telemetry.update();
                }
            }

            // edit Menu params
            if (gamepad1.start || gamepad1.back || gamepad1.left_stick_button) {             // edit parameters  & write the new file
                // change the background color to yellow
                relativeLayout.post(new Runnable() {
                    public void run() {
                        relativeLayout.setBackgroundColor(Color.YELLOW);
                    }
                });


                editParameters();

                relativeLayout.post(new Runnable() {    // change back to black
                    public void run() {
                        relativeLayout.setBackgroundColor(Color.BLACK);
                    }
                });

            }
        }

        // Wait for the game to start (driver presses PLAY)
        waitForStart();




        mecanumDrive(0.5, 18, 0, 0);
        mecanumDrive(0.5, 18, 0, 90);
        mecanumDrive(0.5, 18, 0, 180);
        runtime.reset();
        if (opModeIsActive()) {
                if(position[positionIndex] == position[0]) {
                    if (goldPosition == 0) {
//                        while (opModeIsActive() && runtime.seconds() <= 20.00) {
//                            mecanumDrive(0.5, 18, 0, 0);
//                            mecanumDrive(0.5, 18, 0, 90);
//                            mecanumDrive(0.5, 18, 0, 180);
//                        }
                    }

                    if (goldPosition == 1) {

                    }

                    if (goldPosition == 2) {

                    }
                }

                if(position[positionIndex] == position[1]) {
                    if (goldPosition == 0) {
                        mecanumDrive(0.125, 1, 0, 0);
                    }

                    if (goldPosition == 1) {

                    }

                    if (goldPosition == 2) {

                    }
                }

            }
            if (tfod != null) {
                tfod.shutdown();
            }

        }

    public void editParameters() {

        while (!gamepad1.right_stick_button && !opModeIsActive() && !isStopRequested()) {   // while haven't presse exit button, not in play mode, and not in stop
            telemetry.addLine("===> Press Right Joystick to exit EDIT mode <===");
            // Send telemetry message to signify robot waiting;


            telemetry.addLine("VVV Eli's Test VVV");
            telemetry.addLine().addData("", currentEdit).addData("current edit number", ' ');
            telemetry.addLine().addData(arrow1, waitTime1).addData("Drive Distance One", arrow1);
            telemetry.addLine().addData(arrow2, driveDis2).addData("Drive Distance two", arrow2);
            telemetry.addLine().addData(arrow3, driveDis3).addData("Drive Distance three", arrow3);
            telemetry.addLine().addData(arrow4,  positionIndex).addData(position[positionIndex], arrow4);
//            telemetry.addLine().addData(arrow4, "Color       ");
            telemetry.update();

            if (gamepad1.dpad_down) {
                dpadPressedDown = true;
            } else if (gamepad1.dpad_down == false && dpadPressedDown) {
                dpadPressedDown = false;
                currentEdit += 1;
                if (currentEdit > 3) {
                    currentEdit = 0;
                }
            }

            if (gamepad1.dpad_up) {
                dpadPressedUp = true;
            } else if (gamepad1.dpad_up == false && dpadPressedUp) {
                dpadPressedUp = false;
                currentEdit -= 1;
                if (currentEdit < 0) {
                    currentEdit = 2;
                }
            }


            if (currentEdit == 0) {
                arrow1 = "<>";
            } else {
                arrow1 = "    ";
            }
            if (currentEdit == 1) {
                arrow2 = "<>";
            } else {
                arrow2 = "    ";
            }
            if (currentEdit == 2) {
                arrow3 = "<>";
            } else {
                arrow3 = "    ";
            }
            if (currentEdit == 3) {
                arrow4 = "<>";
            } else {
                arrow4 = "    ";
            }
            if (currentEdit == 0) {
                arrow5 = "<>";
            } else {
                arrow5 = "    ";
            }
            if (currentEdit == 1) {
                arrow6 = "<>";
            } else {
                arrow6 = "    ";
            }
            if (currentEdit == 2) {
                arrow7 = "<>";
            } else {
                arrow7 = "    ";
            }
            if (currentEdit == 3) {
                arrow8 = "<>";
            } else {
                arrow8 = "    ";
            }
            if (currentEdit == 2) {
                arrow9 = "<>";
            } else {
                arrow9 = "    ";
            }
            if (currentEdit == 3) {
                arrow10 = "<>";
            } else {
                arrow10 = "    ";
            }


            if (gamepad1.dpad_left) {
                dpadPressedLeft = true;
            } else if (gamepad1.dpad_left == false && dpadPressedLeft) {
                dpadPressedLeft = false;
                if (currentEdit == 0) {
                    waitTime1 -= 1;
                }
                if (currentEdit == 1) {
                    driveDis2 -= 1;
                }
                if (currentEdit == 2) {
                    driveDis3 -= 1;
                }
                if (currentEdit == 3) {
                    if (positionIndex == 1) {
                        positionIndex = 0;
                    } else {
                        positionIndex = 1;
                    }
                }
            }

            if (gamepad1.dpad_right) {
                dpadPressedRight = true;
            } else if (gamepad1.dpad_right == false && dpadPressedRight) {
                dpadPressedRight = false;
                if (currentEdit == 0) {
                    waitTime1 += 1;
                }
                if (currentEdit == 1) {
                    driveDis2 += 1;
                }
                if (currentEdit == 2) {
                    driveDis3 += 1;
                }
                if (currentEdit == 3) {
                    if (positionIndex == 1) {
                        positionIndex = 0;
                    } else {
                        positionIndex = 1;
                    }
                }
            }
//                if (gamepad1.y) {
//                    break;
//                }
        }
        telemetry.update();
    }



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

            Cosmo.leftFront.setPower(-lfpower);
            Cosmo.leftRear.setPower(lrpower);
            Cosmo.rightFront.setPower(-rfpower);
            Cosmo.rightRear.setPower(rrpower);

//            RobotLog.ii("[GromitIR] ", Double.toString(18.7754*Math.pow(sharpIRSensor.getVoltage(),-1.51)), Integer.toString(left_front.getCurrentPosition()));

        }
        //gromit.driveTrain.stopMotors();
        Cosmo.leftFront.setPower(0.0);
        Cosmo.rightFront.setPower(0.0);
        Cosmo.rightRear.setPower(0.0);
        Cosmo.leftRear.setPower(0.0);
    }
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

            Cosmo.leftFront.setPower(adjustment * speed);
            Cosmo.leftRear.setPower(adjustment * speed);
            Cosmo.rightFront.setPower(adjustment * speed);
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

