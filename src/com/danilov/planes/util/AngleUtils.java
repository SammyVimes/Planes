package com.danilov.planes.util;

public final class AngleUtils {

	//making angle not bigger than 360 and not smaller than -360
	public static double normalizeAngle(final double angle) {
		double normalized = 0;
		if (angle > 0) {
			normalized = angle % 360;
		} else {
			normalized = ((angle * (-1)) % 360) * (-1);
		}
		return normalized;
	}
	
	public static double toRad(final double angle) {
		return ((double)  Math.PI  * angle / 180);
	}
	
	public static double toDegrees(final double angleRad) {
		return ((double)  180  * angleRad / Math.PI);
	}
	
}
