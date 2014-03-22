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
	
	//v etoy figne sam 4ert nogy slomit! no ya do nee kak-to doper
	public static double calculateAngleBetweenPoints(final float x1, final float y1,
													 final float x2, final float y2) {
		final float a = x2 - x1; //horizontal cateth
		final float b = (y2 - y1); //vertical cateth
		//for tests
		boolean targetBelow = b < 0;
		boolean targetToTheLeft = a < 0;
		
		double tangens = (double) b/a;
		double rad = Math.atan(tangens);
		if (targetToTheLeft) {
			rad += Math.PI;
		}
		return toDegrees(rad);
	}
	
}
