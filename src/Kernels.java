public class Kernels {

    public static float[][] ridge = {
            {-1, -1, -1},
            {-1, 8, -1},
            {-1, -1, -1}
    };
    public static float[][] sharpen = {
            {0, -1, 0},
            {-1, 5, -1},
            {0, -1, 0}
    };
    public static float[][] emboss = {
            {-2, -1, 0},
            {-1, 1, 1},
            {0, 1, 2}
    };

    public static float[][] blur = {
            {0.0625f, 0.125f, 0.0625f},
            {0.125f, 0.25f, 0.125f},
            {0.0625f, 0.125f, 0.0625f}
    };

}
