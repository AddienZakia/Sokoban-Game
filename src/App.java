import java.util.Scanner;

public class App {

    static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void print(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (array[i][j] == null) {
                    array[i][j] = "X";
                    System.out.print("X ");
                } else
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
                if (obstaclePos[j][0] == posX && obstaclePos[j][1] == posY || area[posY][posX] != null) {
                    posX = randomize(width - 2, 1);
                    posY = randomize(width - 2, 1);
                }
            }

            obstaclePos[i][0] = posX;
            obstaclePos[i][1] = posY;

            area[posY][posX] = String.valueOf(value);
        }
    }

    static char inputUser() {
        Scanner data = new Scanner(System.in);
        String res = data.nextLine();

        return res.isEmpty() ? 'c' : res.toLowerCase().charAt(0);
    }

    static void printObstacleO(String[][] area, int[][] Opos) {
        for (int[] x : Opos) {
            String areaValue = area[x[0]][x[1]];
            if (!areaValue.equals("*") && !areaValue.equals("#")) {
                area[x[0]][x[1]] = "O";
            }
        }
    }

    static Boolean checkFinishObstacle(String[][] area, int[][] ObstacleOpos) {
        int total = 0;
        for (int[] x : ObstacleOpos) {
            if (area[x[0]][x[1]].equals("#"))
                total++;
        }

        return total == ObstacleOpos.length ? true : false;
    }

    static void controller(String[][] area, int userXpos, int userYpos, int width, int height,
            int[][] obstacleOpos) {

        Boolean err = false;
        while (true) {
            printObstacleO(area, obstacleOpos);
            print(area);
            if (err)
                System.out.println("Kesalahan input");

            System.out.print("Input : ");
            char input = inputUser();

            if (input == 'a') {
                Boolean cekKotak = area[userYpos][userXpos > 0 ? userXpos - 1 : userXpos].equals("#");
                if (userXpos == 0 || (userXpos == 1 && cekKotak)) {
                    err = true;
                } else {
                    if (userXpos > 0 && cekKotak) {
                        area[userYpos][userXpos - 2] = "#";
                    }

                    err = false;
                    area[userYpos][userXpos] = "X";
                    area[userYpos][--userXpos] = "*";
                }
            } else if (input == 's') {
                Boolean cekKotak = area[userYpos < height - 1 ? userYpos + 1 : userYpos][userXpos].equals("#");
                if (userYpos == width - 1 || (cekKotak && userYpos + 1 == width - 1)) {
                    err = true;
                } else {
                    if (userYpos < height - 1 && cekKotak) {
                        area[userYpos + 2][userXpos] = "#";
                    }

                    err = false;
                    area[userYpos][userXpos] = "X";
                    area[++userYpos][userXpos] = "*";
                }
            } else if (input == 'd') {
                Boolean cekKotak = area[userYpos][userXpos < width - 1 ? userXpos + 1 : userXpos].equals("#");
                if (userXpos == width - 1 || (cekKotak && userXpos + 1 == width - 1)) {
                    err = true;
                } else {
                    if (userXpos < width - 1 && cekKotak) {
                        area[userYpos][userXpos + 2] = "#";
                    }

                    err = false;
                    area[userYpos][userXpos] = "X";
                    area[userYpos][++userXpos] = "*";
                }
            } else if (input == 'w') {
                Boolean cekKotak = area[userYpos > 0 ? userYpos - 1 : userYpos][userXpos].equals("#");
                if (userYpos == 0 || (cekKotak && userYpos - 1 == 0)) {
                    err = true;
                } else {
                    if (userYpos > 0 && cekKotak) {
                        area[userYpos - 2][userXpos] = "#";
                    }

                    err = false;
                    area[userYpos][userXpos] = "X";
                    area[--userYpos][userXpos] = "*";
                }
            } else
                err = true;

            Boolean finish = checkFinishObstacle(area, obstacleOpos);
            if (finish) {
                clear();

                print(area);

                System.out.println("Finished");
                break;
            }

            clear();
        }

    }

    public static void main(String[] args) {

        // # = kotak, O = available, * = user, x = jalan, @ = border
        double level = 5;
        int width = 5, height = 5;

        int totalObstacle = (int) Math.ceil(level / 3);
        String[][] area = new String[width][height];

        // Create random obstacle X position
        randomPosObstacle(totalObstacle, width, area, '#');

        // Create reandom obstacle O position
        randomPosObstacle(totalObstacle, width, area, 'O');

        // Get O Obstacle position
        int obsPos = 0;
        int[][] obstacleOpos = new int[totalObstacle][2];
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                if (area[y][x] != null && area[y][x].equals("O")) {
                    obstacleOpos[obsPos][0] = y;
                    obstacleOpos[obsPos][1] = x;
                    obsPos++;
                }
            }
        }

        // Create user position
        int userXpos = randomize(width, 0);
        int userYpos = randomize(width, 0);

        while (area[userYpos][userXpos] != null) {
            userXpos = randomize(width, 0);
            userYpos = randomize(width, 0);
        }

        area[userYpos][userXpos] = "*";

        // controller
        controller(area, userXpos, userYpos, width, height, obstacleOpos);
    }
}
