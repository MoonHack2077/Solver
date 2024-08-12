package com.example.matematicasDiscretas;

public class SimplexSolver {

    public double[] solve(double[] objectiveFunction, double[][] constraints) {
        double maxZ = 0;
        double[] optimalValues = new double[3];

        for (double x = 0; x <= constraints[0][2]; x++) {
            for (double y = 0; y <= constraints[1][2]; y++) {
                if (checkConstraints(x, y, constraints)) {
                    double Z = objectiveFunction[0] * x + objectiveFunction[1] * y;
                    if (Z > maxZ) {
                        maxZ = Z;
                        optimalValues[0] = Z;
                        optimalValues[1] = x;
                        optimalValues[2] = y;
                    }
                }
            }
        }

        return optimalValues;
    }

    private boolean checkConstraints(double x, double y, double[][] constraints) {
        for (double[] constraint : constraints) {
            if (constraint[0] * x + constraint[1] * y > constraint[2]) {
                return false;
            }
        }
        return true;
    }
}
