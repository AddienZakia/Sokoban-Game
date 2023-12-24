import java.util.Scanner;

public class App {

    static void print(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                // if (i == 0 || j == 0 || (i + 1) % array.length == 0 || (j + 1) % array.length
                // == 0)
                // System.out.print("@ ");
                // else
                if (array[i][j] == null)
                    System.out.print("X ");
                else
                    System.out.print(array[i][j] + " ");
            }
            System.out.println("");
        }
    }

    static int randomize(int max, int min) {
        return (int) (Math.floor(Math.random() * max) + (min));
    }

    static void randomPosObstacle(int totalObstacle, int width, String[][] area, char value) {
        int[][] obstaclePos = new int[totalObstacle][2];
        for (int i = 0; i < totalObstacle; i++) {
            int posX = randomize(width - 2, 1);
            int posY = randomize(width - 2, 1);

            // check duplicaate
            for (int j = 0; j < totalObstacle; j++) {
                if (obstaclePos[j][0] == posX && obstaclePos[j][1] == posY || area[posX][posY] != null) {
                    posX = randomize(width - 2, 1);
                    posY = randomize(width - 2, 1);
                }
            }

            obstaclePos[i][0] = posX;
            obstaclePos[i][1] = posY;

            area[posX][posY] = String.valueOf(value);
        }
    }

    public static void main(String[] args) throws Exception {

        // # = kotak, O = available, * = user, x = jalan, @ = border
        double level = 4;
        int width = 5, height = 5;

        int totalObstacle = (int) Math.ceil(level / 3);
        String[][] area = new String[width][height];

        // Create random position obstacle;
        randomPosObstacle(totalObstacle, width, area, '#');

        // Create obstacle place
        randomPosObstacle(totalObstacle, width, area, 'O');

        // Create user position
        int userXpos = randomize(5, 0);
        int userYpos = randomize(5, 0);

        while (area[userXpos][userYpos] != null) {
            userXpos = randomize(5, 0);
            userYpos = randomize(5, 0);
        }

        area[userXpos][userYpos] = "*";

        // controller

        // print
        print(area);
    }
}
