package ru.progwards.java1.SeaBattle.dienerdas;

import ru.progwards.java1.SeaBattle.SeaBattle;
import ru.progwards.java1.SeaBattle.SeaBattle.FireResult;

import java.util.Arrays;

public class SeaBattleAlg {
    // Тестовое поле создаётся конструктором
    //     SeaBattle seaBattle = new SeaBattle(true);
    //
    // Обычное поле создаётся конструктором по умолчанию:
    //     SeaBattle seaBattle = new SeaBattle();
    //     SeaBattle seaBattle = new SeaBattle(false);
    //
    // Посомтреть результаты стрельбы можно в любой момент,
    // выведя объект класса SeaBattle на консоль. Например так:
    //     System.out.println(seaBattle);
    //
    //
    // Вид тестового поля:
    //
    //           0 1 2 3 4 5 6 7 8 9    координата x
    //         0|.|.|.|.|.|.|.|X|.|.|
    //         1|.|.|.|.|.|X|.|.|.|.|
    //         2|X|X|.|.|.|.|.|.|.|.|
    //         3|.|.|.|.|.|.|.|X|X|X|
    //         4|.|.|.|.|X|.|.|.|.|.|
    //         5|.|.|.|.|X|.|.|Х|.|.|
    //         6|.|.|.|.|.|.|.|Х|.|X|
    //         7|X|.|X|.|.|.|.|Х|.|X|
    //         8|X|.|.|.|.|.|.|X|.|.|
    //         9|X|.|.|.|X|.|.|.|.|.|

    private static final int MINUS = 0b01;
    private static final int PLUS = 0b10;

    char[][] area;
    SeaBattle seaBattle;
    int strike;
    int direction;
    boolean printField = false;

    void countStrikes() {
        strike++;
    }

    void print(boolean doPrint) {
        if (!doPrint)
            return;
        for (int y = 0; y < seaBattle.getSizeY(); y++) {
            String str = "|";
            for (int x = 0; x < seaBattle.getSizeX(); x++) {
                str += area[x][y] + "|";
            }
            System.out.println(str);
        }
        System.out.println("************************");
    }

    void start(SeaBattle seaBattle) {
        this.seaBattle = seaBattle;
        strike = 0;
        area = new char[seaBattle.getSizeX()][seaBattle.getSizeY()];
        for (int x = 0; x < seaBattle.getSizeX(); x++) {
            Arrays.fill(area[x], ' ');
        }
    }

    void signShoot(int x, int y, SeaBattle.FireResult result) {
        if (result != FireResult.MISS) {
            area[x][y] = 'X';
            countStrikes();
        } else {
            area[x][y] = '*';
        }
    }

    void signPoint(int x, int y) {
        if (x < 0 || y < 0 || x >= seaBattle.getSizeX() || y >= seaBattle.getSizeY())
            return;
        if (area[x][y] == ' ')
            area[x][y] = '.';
    }

    void signStrike(int x, int y) {
        signPoint(x - 1, y - 1);
        signPoint(x - 1, y);
        signPoint(x - 1, y + 1);
        signPoint(x + 1, y - 1);
        signPoint(x + 1, y);
        signPoint(x + 1, y + 1);
        signPoint(x, y - 1);
        signPoint(x, y + 1);
    }

    void signCrushed() {
        for (int x = 0; x < seaBattle.getSizeX(); x++) {
            for (int y = 0; y < seaBattle.getSizeY(); y++) {
                if (area[x][y] == 'X')
                    signStrike(x, y);
            }
        }
    }

    boolean controlStrike(SeaBattle.FireResult result, int fireDirection) {
        switch (result) {
            case DESTROYED:
                direction = 0;
                return true;
            case HIT:
                return false;
            case MISS:
                direction &= ~fireDirection;
                return false;
        }
        return false;
    }

    boolean ruinLateral(int x, int y) {
        int i = 1;
        boolean destroyed = false;
        direction = PLUS | MINUS;
        do {
            if ((direction & MINUS) != 0)
                destroyed = controlStrike(bombard((x - i), y), MINUS);
            if ((direction & PLUS) != 0)
                destroyed = controlStrike(bombard((x + i), y), PLUS);
            i++;
        } while (direction != 0);
        return destroyed;
    }

    boolean ruinVertical(int x, int y) {
        int i = 1;
        boolean destroyed = false;
        direction = PLUS | MINUS;
        do {
            if ((direction & MINUS) != 0)
                destroyed = controlStrike(bombard(x, (y - i)), MINUS);
            if ((direction & PLUS) != 0)
                destroyed = controlStrike(bombard(x, (y + i)), PLUS);
            i++;
        } while (direction != 0);
        return destroyed;
    }

    void ruinBoat(int x, int y) {
        boolean destroyed = ruinLateral(x, y);
        if (!destroyed)
            ruinVertical(x, y);
    }

    SeaBattle.FireResult shootAndRuin(int x, int y) {
        SeaBattle.FireResult result = bombard(x, y);
        if (result == FireResult.HIT)
            ruinBoat(x, y);
        return result;
    }

    SeaBattle.FireResult bombard(int x, int y) {
        if (x < 0 || y < 0 || x >= seaBattle.getSizeX() || y >= seaBattle.getSizeY() ||
                strike >= 20 || area[x][y] != ' ')
            return FireResult.MISS;
        SeaBattle.FireResult result = seaBattle.fire(x, y);
        signShoot(x, y, result);
        if (result == FireResult.DESTROYED)
            signCrushed();
        print(printField);
        return result;
    }

    public void battleAlgorithm(SeaBattle seaBattle) {
        start(seaBattle);
        // стрельба по всем клеткам полным перебором
        for (int y = 0; y < seaBattle.getSizeX(); y++) {
            for (int x = 0; x < seaBattle.getSizeY(); x++) {
                // стреляем по клетке
                shootAndRuin(x, y);
            }
        }
    }

    public static void testMany() {
        System.out.println("Sea battle");
        double result = 0;
        for (int i = 0; i < 1000; i++) {
            SeaBattle seaBattle = new SeaBattle();
            new SeaBattleAlg().battleAlgorithm(seaBattle);
            result += seaBattle.getResult();
        }
        System.out.println(result / 1000);
    }

    public static void testOne() {
        System.out.println("Sea battle");
        SeaBattle seaBattle = new SeaBattle(true);
        new SeaBattleAlg().battleAlgorithm(seaBattle);
        System.out.println(seaBattle.getResult());
        System.out.println(seaBattle);
    }

    public static void main(String[] args) {
        testMany();
    }
}



