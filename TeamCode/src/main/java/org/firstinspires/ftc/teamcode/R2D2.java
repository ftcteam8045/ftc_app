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

    // Declare OpMode members.

    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    public DcMotor redleds = null;
    public DcMotor blueleds = null;


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
        blueleds.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        redleds.setDirection(DcMotor.Direction.FORWARD);
        redleds.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        leftDrive.setPower(0.0);
        rightDrive.setPower(0.0);
//        blueleds.setPower(0.0);
//        redleds.setPower(0.0);

        boolean awake = false;
        // Setup a variable for each drive wheel to save power level for telemetry
        double leftPower;
        double rightPower;

// List of available sound resources

        String  sounds[] ={"qword8", "qword9", "qword16", "qword22", "qr2d2s1", "qr2d2s2", "r2d2",
                "qr2d2s3", "qr2d2w1",  "qr2d2w2",  "qr2d2w3",  "qscaning",  "qsntnc1", "qsntnc2",
                "qsntnc4",  "qsntnc6",  "qsntnc7",  "qsntnc8",  "qsntnc9",  "qsntnc10",  "qsntnc13",
                "qsntnc16", "qsntnc18",  "qsntnc20",  "qword1",  "qword4", };

        double  maxpower = 0.7;
        int     soundIndex      = 0;
        //int     soundID         = -1;
        int SoundID   = hardwareMap.appContext.getResources().getIdentifier("r2d2",   "raw", hardwareMap.appContext.getPackageName());
        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, SoundID);

        // Wait for the game to start (driver presses PLAY)


        waitForStart();
        runtime.reset();
        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, SoundID);

        double cyclestart = runtime.milliseconds();
        int lightIndex = 0;
        int cycletime = 3000;
        int heartbeat = 400;
        int blueheartbeat = 300;

        double hpower = 0.0;

// Remember the last state of the dpad to detect changes.

        boolean was_dpad_down   = false;


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

//            if (runtime.milliseconds() > 7000) {awake = true;}

            /** in SLEEP mode, just flash red lights slowly  **/
            if(!awake) {
                if (runtime.milliseconds() - cyclestart < heartbeat) {
                    //hpower = (runtime.milliseconds() - cyclestart) / (heartbeat / 2);
                    //hpower = 1.0;
                   redleds.setPower(1.0);
//                    if (runtime.milliseconds() - cyclestart > heartbeat / 2) {
//
//                        hpower = (1.0 - ((runtime.milliseconds() - cyclestart) - (heartbeat / 2)) / (heartbeat / 2));
//                        hpower = 1.0;
//                    }
                } else {
                    if (runtime.milliseconds() - cyclestart > cycletime){  cyclestart= runtime.milliseconds(); }
                    //hpower = 0.0;
                    redleds.setPower(0.0);
                }
                //redleds.setPower(hpower);
            }
            else {                                   /** AWAKE MODE  **/
                redleds.setPower (0.0);
                if (runtime.milliseconds() % (2*blueheartbeat) < blueheartbeat)
                {
                    blueleds.setPower(1.0);
                }else
                {
                    blueleds.setPower(0.0);
                }

                if (runtime.milliseconds() - cyclestart > cycletime) {
                    cyclestart = runtime.milliseconds();
                    soundIndex = (int) ((Math.random() * sounds.length));
                    SoundID = hardwareMap.appContext.getResources().getIdentifier(sounds[soundIndex], "raw", hardwareMap.appContext.getPackageName());
                    SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, SoundID);
                    //soundIndex +=1 ;

                }
            }
           /** ALWAYS executed in SLEEP and AWAKE modes **/
            double drive = -gamepad1.right_stick_y;
            double turn = gamepad1.right_stick_x;
            leftPower = Range.clip(drive - turn, -1.0, 1.0);
            rightPower = Range.clip(drive + turn, -1.0, 1.0);

            // Send calculated power to wheels
            leftDrive.setPower(leftPower * maxpower);
            rightDrive.setPower(rightPower * maxpower);

            /** Toggle AWAKE mode **/
            if (gamepad1.left_bumper || gamepad1.right_bumper || gamepad1.right_stick_button) {
                    awake = true ;
            }

            /** Play Random Sound **/
            if (gamepad1.dpad_down && !was_dpad_down) {
                soundIndex = (int) ((Math.random() * sounds.length));
                SoundID = hardwareMap.appContext.getResources().getIdentifier(sounds[soundIndex], "raw", hardwareMap.appContext.getPackageName());
                SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, SoundID);
            }
            // Remember the last state of the dpad to detect changes.
            was_dpad_down   = gamepad1.dpad_down;

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.addData("LEDs", "Blue (%.2f), Red (%.2f)", gamepad1.right_stick_y, -gamepad1.right_stick_y);
            telemetry.addData("runtime", "runtime (%.2f), cstart (%.2f)", runtime.milliseconds(), cyclestart);
//            telemetry.addData("soundIndex",soundIndex);
//            telemetry.addData("soundname",sounds[soundIndex]);
            telemetry.addData("heartpower",hpower);


            telemetry.update();




        }
    }
}
