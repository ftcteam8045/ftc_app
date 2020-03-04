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
import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="R2D2", group="R2D2")
//@Disabled
public class R2D2 extends LinearOpMode {

    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    public DcMotor redleds = null;
    public DcMotor blueleds = null;


    /* local OpMode members. */
    //HardwareMap hwMap = null;


    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private boolean goldFound;      // Sound file present flags
    private boolean silverFound;




    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        blueleds = hardwareMap.get(DcMotor.class, "blueleds");
        redleds = hardwareMap.get(DcMotor.class, "redleds");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        blueleds.setDirection(DcMotor.Direction.FORWARD);
        redleds.setDirection(DcMotor.Direction.FORWARD);

        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        boolean awake = false;
        // Setup a variable for each drive wheel to save power level for telemetry
        double leftPower;
        double rightPower;

// List of available sound resources

        String  sounds[] ={"qword8", "qword9", "qword16", "qword22", "qr2d2s1", "qr2d2s2", "r2d2",
                "qr2d2s3", "qr2d2w1",  "qr2d2w2",  "qr2d2w3",  "qscaning",  "qsntnc1", "qsntnc2",
                "qsntnc4",  "qsntnc6",  "qsntnc7",  "qsntnc8",  "qsntnc9",  "qsntnc10",  "qsntnc13",
                "qsntnc16", "qsntnc18",  "qsntnc20",  "qword1",  "qword4", };

        Context myApp = hardwareMap.appContext;

        //boolean soundPlaying = false;
        int     soundIndex      = 0;
        int     soundID         = -1;
        int SoundID   = hardwareMap.appContext.getResources().getIdentifier("r2d2",   "raw", hardwareMap.appContext.getPackageName());
        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, SoundID);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, SoundID);

        double cyclestart = runtime.milliseconds();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            if (runtime.milliseconds()-cyclestart > 2000) {
                cyclestart = runtime.milliseconds();

                soundIndex = (int) ((Math.random() * sounds.length));

                SoundID   = hardwareMap.appContext.getResources().getIdentifier(sounds[soundIndex],   "raw", hardwareMap.appContext.getPackageName());
                SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, SoundID);
                //soundIndex +=1 ;
            }
            // awake or asleep
            double drive = -gamepad1.right_stick_y;
            double turn = gamepad1.right_stick_x;
            leftPower = Range.clip(drive + turn, -1.0, 1.0);
            rightPower = Range.clip(drive - turn, -1.0, 1.0);

            // Send calculated power to wheels
            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);

            if (gamepad1.x) {
                blueleds.setPower(1.0);

            } else {
                blueleds.setPower(0.0);
            }

            if (gamepad1.b) {
                redleds.setPower(1.0);

            } else {
                redleds.setPower(0.0);
            }
            /*
            //if (awake) {



            //}else {                                     // in sleep mode
                if (gamepad1.left_bumper || gamepad1.right_bumper) {
                    awake = false;
                }

                if (gamepad1.right_stick_y > 0.1) {
                    blueleds.setPower(gamepad1.right_stick_y);
                    // change the background color to match the color detected by the RGB sensor.
                    // pass a reference to the hue, saturation, and value array as an argument
                    // to the HSVToColor method.
                    relativeLayout.post(new Runnable() {
                        public void run() {
                            relativeLayout.setBackgroundColor(Color.BLUE);
                        }
                    });
                } else {
                    blueleds.setPower(0);
                }
                if (gamepad1.right_stick_y < -0.1) {
                    redleds.setPower(-gamepad1.right_stick_y);
                    // change the background color to match the color detected by the RGB sensor.
                    // pass a reference to the hue, saturation, and value array as an argument
                    // to the HSVToColor method.
                    relativeLayout.post(new Runnable() {
                        public void run() {
                            relativeLayout.setBackgroundColor(Color.RED);
                        }
                    });
                } else {
                    redleds.setPower(0);
                }

            }
            */


            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.addData("LEDs", "Blue (%.2f), Red (%.2f)", gamepad1.right_stick_y, -gamepad1.right_stick_y);
            telemetry.addData("runtime", "runtime (%.2f), cs (%.2f)", runtime.milliseconds(), cyclestart);
            telemetry.addData("soundIndex",soundIndex);
            telemetry.addData("soundname",sounds[soundIndex]);
            telemetry.addData("soundID",soundID);


            telemetry.update();




        }
    }
}
