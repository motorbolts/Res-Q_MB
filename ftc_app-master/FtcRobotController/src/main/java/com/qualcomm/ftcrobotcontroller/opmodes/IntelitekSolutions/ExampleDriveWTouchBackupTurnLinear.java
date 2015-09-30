package com.qualcomm.ftcrobotcontroller.opmodes.IntelitekSolutions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class ExampleDriveWTouchBackupTurnLinear extends LinearOpMode {
    DcMotor leftMotor;
    DcMotor rightMotor;
    TouchSensor touchSensor;

    //Time constants
    int BACKUP_TIME = 1000;
    int TURN_TIME = 250;

    @Override
    public void runOpMode() throws InterruptedException {
        //get references to the motors from the hardware map
        leftMotor = hardwareMap.dcMotor.get("left_drive");
        rightMotor = hardwareMap.dcMotor.get("right_drive");

        //reverse the right motor
        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        //get a reference to the touch sensor
        touchSensor = hardwareMap.touchSensor.get("sensor_touch");

        // Wait for the start button to be pressed
        waitForStart();

        while(opModeIsActive()) {
            //Back up and turn if the touch sensor is pressed
            if(touchSensor.isPressed()) {
                //Set the motors to drive backwards at 25% power
                leftMotor.setPower(-0.25);
                rightMotor.setPower(-0.25);
                telemetry.addData("State", "Backing Up");
                sleep(BACKUP_TIME);

                //Set the motors to turn the robot right at 25% power
                leftMotor.setPower(0.25);
                rightMotor.setPower(-0.25);
                telemetry.addData("State", "Turning");
                sleep(TURN_TIME);
            } else {
                //Set the motors to drive forward at 50% power
                leftMotor.setPower(0.5);
                rightMotor.setPower(0.5);
                telemetry.addData("State", "Driving");
            }

            // Wait for a hardware cycle to allow other processes to run
            waitOneHardwareCycle();
        }
    }
}
