/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.part_I.week_3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author psantama
 */
public class FastCollinearPoints {
    private static final int MIN_SEGMENT_LENGTH = 4;
    private List<LineSegment> segments;
    
    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points, boolean debug) {
        if (points == null) {
            throw new NullPointerException("null argument");
        }
        
        checkDuplicatedPoints(points);
        
        segments = new ArrayList<>();
        
        Point[] sorted = Arrays.copyOf(points, points.length);
        
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException("null point at index " + i);
            }
            
            // points sorted by slope regarding to the each point
            Arrays.sort(sorted, points[i].slopeOrder());
            
            if (debug) {
                System.out.println("---------------------");
                System.out.println("sorted: " + Arrays.toString(sorted));
            }
            
            for (int firstIdx = 1, lastIdx = 2; lastIdx < sorted.length; lastIdx++) {
                if (debug) {
                    System.out.println("slopeTo {" + points[i] + ", " + sorted[firstIdx] + "}: " + points[i].slopeTo(sorted[firstIdx]));
                }
                
                while (lastIdx < sorted.length && points[i].slopeTo(sorted[firstIdx]) == points[i].slopeTo(sorted[lastIdx])) {
                    if (debug) {
                        System.out.println("\tslopeTo {" + points[i] + ", " + sorted[lastIdx] + "}: " + points[i].slopeTo(sorted[lastIdx]));
                    }
                    
                    lastIdx++;
                }
                
                if (lastIdx - firstIdx >= MIN_SEGMENT_LENGTH - 1) {
                    Arrays.sort(sorted, firstIdx, lastIdx);
                    
                    LineSegment lineSegment = null;
                    
                    // including the pivot point in the segment
                    if (sorted[firstIdx].compareTo(points[i]) > 0) {
                        lineSegment = new LineSegment(points[i], sorted[lastIdx - 1]);
                    }else if (sorted[lastIdx - 1].compareTo(points[i]) < 0) {
                        lineSegment = new LineSegment(sorted[firstIdx], points[i]);
                    }else {
                        lineSegment = new LineSegment(sorted[firstIdx], sorted[lastIdx - 1]);
                    }
                    
                    // check if the segment was already found
                    if (!isDuplicatedSegment(lineSegment))
                        segments.add(lineSegment);
                    
                    System.out.println("lineSegment: " + lineSegment);
                }
                
                firstIdx = lastIdx;
            }
            
            System.out.println("---------------------");
        }
    }
    
    private void checkDuplicatedPoints(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i+1]) == 0)
                throw new IllegalArgumentException("repeated point at index " + i + ": " + points[i]);
        }
    }
    
    private boolean isDuplicatedSegment(LineSegment segment) {
        for (LineSegment ln : segments) {
            if (ln.toString().compareTo(segment.toString()) == 0)
                return true;
        }
        
        return false;
    }
    
    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }
    
    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("D:/Users/psantama/Downloads/collinear/input40.txt"));
        int n = Integer.parseInt(br.readLine());
        
        Point[] points = new Point[n];
        
        String line = null;
        int i = 0;
        while ((line = br.readLine()) != null) {
            String[] coors = line.trim().split("\\s+");
            
            points[i] = new Point(Integer.parseInt(coors[0]), Integer.parseInt(coors[1]));
            
            i++;
        }
        
        FastCollinearPoints bcp = new FastCollinearPoints(points, true);
        
        System.out.println("segments: ");
        
        for (LineSegment segment : bcp.segments) {
            System.out.println("\t" + segment);
        }
        
        System.out.println("# of segments: " + bcp.numberOfSegments());
    }
}
