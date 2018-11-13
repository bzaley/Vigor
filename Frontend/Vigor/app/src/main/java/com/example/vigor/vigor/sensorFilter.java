package com.example.vigor.vigor;

// Meant to filter out values that are approximately considered to be steps
public class sensorFilter {

    private sensorFilter() {}

    public static float sum(float[] array) {
        float sumValue = 0;
        for ( int i = 0; i < array.length; i++){
            sumValue += array[i];
        }
        return sumValue;
    }

    public static float[] cross(float[] initialArray, float[] secondArray) {
        float[] finalArray = new float[3];
        finalArray[0] = initialArray[1] * secondArray[2] - initialArray[2] * secondArray[1];
        finalArray[1] = initialArray[2] * secondArray[0] - initialArray[0] * secondArray[2];
        finalArray[2] = initialArray[0] * secondArray[1] - initialArray[1] * secondArray[0];
        return finalArray;
    }

    public static float norm(float[] array) {
        float normValue = 0;
        for (int i = 0; i < array.length; i++) {
            normValue += array[i] * array[i];
        }
        return (float) Math.sqrt(normValue);
    }

    public static float dot(float[] arrayA, float[] arrayB) {
        float dotValue = arrayA[0] * arrayB[0] + arrayA[1] * arrayB[1] + arrayA[2] * arrayB[2];
        return dotValue;
    }

    public static float[] normalize(float[] array) {
        float[] finVal = new float[array.length];
        float norm = norm(array);
        for (int i = 0; i < array.length; i++) {
            finVal[i] = array[i] / norm;
        }
        return finVal;
    }
}
