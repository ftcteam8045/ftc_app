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

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;

import java.util.List;

//Imports for front facing camera

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;

import java.util.ArrayList;
import java.util.List;

@Autonomous(name="Main Auto", group="Pushbot")
//@Disabled
public class ElijahTristanAutoET extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    /* Declare OpMode members. */
    Hardware8045 robot = new Hardware8045();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();
    static final double FORWARD_SPEED = 0.3;
    static final double TURN_SPEED = 0.3;
    static int goldPosition = 1;   // 0 is on left, 1 in center, 2 on right
    private static final String VUFORIA_KEY = "AWfr4/T/////AAAAGRMg80Ehu059mDMJI2h/y+4aBmz86AidOcs89UScq+n+QQyGFT4cZP+rzg1M9B/CW5bgDoVf16x6x3WlD5wYKZddt0UWQS65VIFPjZlM9ADBWvWJss9L1dj4X2LZydWltdeaBhkXTXFnKBkKLDcdTyC2ozJlcAUP0VnLMeI1n+f5jGx25+NdFTs0GPJYVrPQRjODb6hYdoHsffiOCsOKgDnzFsalKuff1u4Z8oihSY9pvv3me2gJjzrQKqp2gCRIZAXDdYzln28Z/8vNSU+aXr6eoRrNXPpYdAwyYI+fX2V9H04806eSUKsNYcPBSbVlhe2KoUsSD7qbOsBMagcEIdMZxo010kVCHHhnhV3IFIs8";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = FRONT;
    public int currentEdit = 0;
    public int waitTime1 = 0;
    public int driveDis2 = 15;
    public int driveDis3 = 20;
    public boolean dpadPressedUp = false;
    public boolean dpadPressedDown = false;
    public boolean dpadPressedLeft = false;
    public boolean dpadPressedRight = false;
    public int sideBase = 1;
    public String[] side = {"base", "crater"};
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
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

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

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.cameraDirection = CAMERA_CHOICE;
        initVuforia();
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        robot.init(hardwareMap);


        /**********************************************************************************************\
         |--------------------------------------Init Loop-----------------------------------------------|
         \**********************************************************************************************/


        /** Activate Tensor Flow Object Detection. */
        if (tfod != null) {
            tfod.activate();
        }
        // Send telemetry message to signify robot waiting;
        while(!opModeIsActive()) {


            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
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
                                telemetry.addData("Gold Mineral Position", "Left");
                            } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                telemetry.addData("Gold Mineral Position", "Right");
                            } else {
                                telemetry.addData("Gold Mineral Position", "Center");
                            }
                        }
                    }
                    telemetry.update();
                }
            }





//            // --- Eli Test Code -- \\
//            telemetry.addLine("VVV Eli's Test VVV");
//            telemetry.addLine().addData(arrow1, waitTime1).addData("Drive Distance One", arrow1);
//            telemetry.addLine().addData(arrow2, driveDis2).addData("Drive Distance One", arrow2);
//            telemetry.addLine().addData(arrow3, driveDis3).addData("Drive Distance One", arrow3);
//            telemetry.addLine().addData(arrow4, "side:       ").addData(side[sideBase], arrow4);
//            telemetry.addLine().addData("", currentEdit).addData("current edit number test", ' ');
//            telemetry.update();
//            // -------------------- \\
//
//
//
//            //Variable Change GUI
//
//            if (gamepad1.dpad_down) {
//                dpadPressedDown = true;
//            } else if (gamepad1.dpad_down == false && dpadPressedDown) {
//                dpadPressedDown = false;
//                currentEdit += 1;
//                if (currentEdit > 3) {
//                    currentEdit = 0;
//                }
//            }
//
//            if (gamepad1.dpad_up) {
//                dpadPressedUp = true;
//            } else if (gamepad1.dpad_up == false && dpadPressedUp) {
//                dpadPressedUp = false;
//                currentEdit -= 1;
//                if (currentEdit < 0) {
//                    currentEdit = 2;
//                }
//            }
//
//
//            if (currentEdit == 0) {
//                arrow1 = "<>";
//            } else {
//                arrow1 = "    ";
//            }
//            if (currentEdit == 1) {
//                arrow2 = "<>";
//            } else {
//                arrow2 = "    ";
//            }
//            if (currentEdit == 2) {
//                arrow3 = "<>";
//            } else {
//                arrow3 = "    ";
//            }
//            if (currentEdit == 3) {
//                arrow4 = "<>";
//            } else {
//                arrow4 = "    ";
//            }
//
//            if (gamepad1.dpad_left) {
//                dpadPressedLeft = true;
//            } else if (gamepad1.dpad_left == false && dpadPressedLeft) {
//                dpadPressedLeft = false;
//                if (currentEdit == 0) {
//                    waitTime1 -= 1;
//                }
//                if (currentEdit == 1) {
//                    driveDis2 -= 1;
//                }
//                if (currentEdit == 2) {
//                    driveDis3 -= 1;
//                }
//                if (currentEdit == 3) {
//                    if (sideBase == 1) {
//                        sideBase = 0;
//                    } else {
//                        sideBase = 1;
//                    }
//                }
//            }
//
//            if (gamepad1.dpad_right) {
//                dpadPressedRight = true;
//            } else if (gamepad1.dpad_right == false && dpadPressedRight) {
//                dpadPressedRight = false;
//                if (currentEdit == 0) {
//                    waitTime1 += 1;
//                }
//                if (currentEdit == 1) {
//                    driveDis2 += 1;
//                }
//                if (currentEdit == 2) {
//                    driveDis3 += 1;
//                }
//                if (currentEdit == 3) {
//                    if (sideBase == 1) {
//                        sideBase = 0;
//                    } else {
//                        sideBase = 1;
//                    }
//                }
//            }
        }



        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        robot.leftFront.setPower(FORWARD_SPEED);
        robot.rightFront.setPower(FORWARD_SPEED);
        robot.leftRear.setPower(FORWARD_SPEED);
        robot.rightRear.setPower(FORWARD_SPEED);
        runtime.reset();
        if (opModeIsActive()) {



                if(side[sideBase] == side[0]) {
                    if (goldPosition == 0) {

                    }

                    if (goldPosition == 1) {

                    }

                    if (goldPosition == 2) {

                    }
                }

                if(side[sideBase] == side[0]) {
                    if (goldPosition == 0) {

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
    }

