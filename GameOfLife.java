import java.awt.*;
import java.util.Random;

public class GameOfLife {
    private byte[][] cells;
    private byte[][] cellsNew;

    private int arraySizeX = 100;
    private int arraySizeY = 100;

    private int canvasWidth = 900;
    private int canvasHeight = 900;

    public GameOfLife() {
        // setting up
        cells = new byte[arraySizeX][arraySizeY];
        cellsNew = new byte[arraySizeX][arraySizeY];
//        // glider
//        cells[2][3] = 1;
//        cells[3][4] = 1;
//        cells[4][2] = 1;
//        cells[4][3] = 1;
//        cells[4][4] = 1;

        // random filling
        Random rnd = new Random();

        for (int i = 0; i < cells.length; i += 1) {
            for (int j = 0; j < cells[0].length; j += 1) {
                cells[i][j] = (byte) rnd.nextInt(2);
            }
        }

        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        StdDraw.setXscale(0, arraySizeX - 1);
        StdDraw.setYscale(0, arraySizeY - 1);
        StdDraw.clear(Color.WHITE);
        StdDraw.setPenColor(Color.BLACK);
    }

    public static void main(String[] args) {
        GameOfLife game = new GameOfLife();

        for (; ; ) {

            game.draw();
            long tStart = System.currentTimeMillis();
            game.nextEpoch();

            long tFrame = System.currentTimeMillis() - tStart;
            String time = "frame: " + tFrame + "ms";
            String fps = "fps: " + (1000.0 / tFrame);

            System.out.println(time + " " + fps);
        }
    }

    public void nextEpoch() {
        for (int i = 1; i < cells.length - 1; i++) {
            for (int j = 1; j < cells[0].length - 1; j++) {
                int count = 0;

                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        count += cells[i + k][j + l];
                    }
                }

                count -= cells[i][j];

                if (count == 3 && cells[i][j] == 0) {
                    cellsNew[i][j] = 1;
                } else {
                    if ((count == 2 || count == 3) && cells[i][j] == 1) {
                        cellsNew[i][j] = 1;
                    } else {
                        cellsNew[i][j] = 0;
                    }
                }
            }
        }

        for (int i = 0; i < cellsNew.length; i++) {
            System.arraycopy(cellsNew[i], 0, cells[i], 0, cellsNew.length);
        }
    }

    public void draw() {
        StdDraw.clear(Color.WHITE);

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (cells[i][j] == 1)
                    StdDraw.filledCircle(i, j, 0.5f);
            }
        }
    }
}
