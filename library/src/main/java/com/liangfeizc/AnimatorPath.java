package com.liangfeizc;

/**
 * Created by liangfeizc on 7/5/15.
 */

import java.util.ArrayList;
import java.util.Collection;

/**
 * A simple Path object that holds information about the points along
 * a path. The API allows you to specify a move location (which essentially
 * jumps from the previous point in the path to the new one), a line location
 * (which creates a line segment from the previous location) and a curve
 * location (which creates a cubic Bezier curve from the previous location).
 */
public class AnimatorPath {

    // The points in the path
    ArrayList<PathPoint> mPoints = new ArrayList<>();

    /**
     * Move from the current path point the new one
     * specified by x and y. This will create a discontinuity if this point is
     * neither the first point in the path nor the same as the previous point
     * in the path.
     */
    public void moveTo(float x, float y) {
        mPoints.add(PathPoint.moveTo(x, y));
    }

    /**
     * Create a straight line from the current path point to the new one
     * specified by x and y.
     */
    public void lineTo(float x, float y) {
        mPoints.add(PathPoint.lineTo(x, y));
    }

    /**
     * Create a cubic Bezier curve from the current path point to the new one
     * specified by x, and y. The curve uses the current path location as the first anchor
     * point, the control points (c0X, c0Y) and (c1X, c1Y), and (x, y) as the end anchor point.
     */
    public void curveTo(float c0X, float c0Y, float c1X, float c1Y, float x, float y) {
        mPoints.add(PathPoint.curveTo(c0X, c0Y, c1X, c1Y, x, y));
    }

    /**
     * Returns a Collection of PathPoint objects that describe all points in the path.
     */
    public Collection<PathPoint> getPoints() {
        return mPoints;
    }
}
