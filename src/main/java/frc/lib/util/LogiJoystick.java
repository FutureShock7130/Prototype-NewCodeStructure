package frc.lib.util;

import edu.wpi.first.wpilibj.Joystick;

/** An utility class for Logitech F310 controller (X input mode).
 * 
 * @author Neil | 6th (hongnai4507@gmail.com)
 */
public class LogiJoystick {
    Joystick js;
    
    /** @param port The USB Port that Joystick inputs. You can see it in FRC Driver Station. */
    public LogiJoystick(int port) {
        js = new Joystick(port);
    }

    public boolean getA() {
        return js.getRawButton(1);
    }

    public boolean getAPressed() {
        return js.getRawButtonPressed(1);
    }

    public boolean getAReleased() {
        return js.getRawButtonReleased(1);
    }

    public boolean getB() {
        return js.getRawButton(2);
    }

    public boolean getBPressed() {
        return js.getRawButtonPressed(2);
    }

    public boolean getBReleased() {
        return js.getRawButtonReleased(2);
    }

    public boolean getY() {
        return js.getRawButton(4);
    }

    public boolean getYPressed() {
        return js.getRawButtonPressed(4);
    }

    public boolean getYReleased() {
        return js.getRawButtonReleased(4);
    }

    public boolean getX() {
        return js.getRawButton(3);
    }

    public boolean getXPressed() {
        return js.getRawButtonPressed(3);
    }

    public boolean getXReleased() {
        return js.getRawButtonReleased(3);
    }

    public boolean getDpadUp() {
        return js.getPOV() == 0;
    }

    public boolean getDpadDown() {
        return js.getPOV() == 180;
    }

    public boolean getDpadLeft() {
        return js.getPOV() == 270;
    }

    public boolean getDpadRight() {
        return js.getPOV() == 90;
    }

    public boolean getLeftBumper() {
        return js.getRawButton(5);
    }

    public boolean getLeftBumperPressed() {
        return js.getRawButtonPressed(5);
    }

    public boolean getLeftBumperReleased() {
        return js.getRawButtonReleased(5);
    }

    public boolean getRightBumper() {
        return js.getRawButton(6);
    }

    public boolean getRightBumperPressed() {
        return js.getRawButtonPressed(6);
    }

    public boolean getRightBumperReleased() {
        return js.getRawButtonReleased(6);
    }

    public double getLeftTrigger() {
        return js.getRawAxis(2);
    }

    public double getRightTrigger() {
        return js.getRawAxis(3);
    }

    public boolean getStartButton() {
        return js.getRawButton(8);
    }

    public boolean getStartButtonPressed() {
        return js.getRawButtonPressed(8);
    }

    public boolean getStartButtonReleased() {
        return js.getRawButtonReleased(8);
    }

    public boolean getBackButton() {
        return js.getRawButton(7);
    }

    public boolean getBackButtonPressed() {
        return js.getRawButtonPressed(7);
    }

    public boolean getBackButtonReleased() {
        return js.getRawButtonReleased(7);
    }

    public double getLeftY() {
        return js.getRawAxis(1);
    }

    public double getLeftX() {
        return js.getRawAxis(0);
    }

    public double getRightY() {
        return js.getRawAxis(5);
    }

    public double getRightX() {
        return js.getRawAxis(4);
    }

    public boolean getLeftStick() {
        return js.getRawButton(9);
    }

    public boolean getLeftStickPressed() {
        return js.getRawButtonPressed(9);
    }

    public boolean getLeftStickReleased() {
        return js.getRawButtonReleased(9);
    }

    public boolean getRightStick() {
        return js.getRawButton(10);
    }

    public boolean getRightStickPressed() {
        return js.getRawButtonPressed(10);
    }

    public boolean getRightStickReleased() {
        return js.getRawButtonReleased(10);
    }
}
