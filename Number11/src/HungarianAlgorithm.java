import java.util.*;

public class HungarianAlgorithm {
    private static int[][] costMatrix;
    private static int n;

    public static void main(String[] args) {
        // Основной метод запускает ввод данных и решает задачу о назначении с помощью алгоритма Мака
        Scanner scanner = new Scanner(System.in);

        // Ввод количества работников и заданий
        System.out.print("Введите количество работников: ");
        n = scanner.nextInt();
        System.out.print("Введите количество заданий: ");
        int m = scanner.nextInt();

        if (n != m) {
            System.out.println("Матрица должна быть квадратной!");
            return;
        }

        costMatrix = new int[n][n];

        // Ввод матрицы затрат
        System.out.println("Введите матрицу затрат (каждая строка содержит " + n + " чисел):");
        for (int i = 0; i < n; i++) {
            System.out.print("Работник " + (i + 1) + ": ");
            for (int j = 0; j < n; j++) {
                costMatrix[i][j] = scanner.nextInt();
            }
        }

        solveWithMacMethod();
    }

    private static void solveWithMacMethod() {
        boolean[][] underlined = new boolean[n][n];
        boolean[] columnMarked = new boolean[n];
        boolean[] rowChecked = new boolean[n];
        boolean[] columnChecked = new boolean[n];

        // Шаг 1: Подчеркивание минимальных элементов в строках
        for (int i = 0; i < n; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (costMatrix[i][j] < min) {
                    min = costMatrix[i][j];
                }
            }
            for (int j = 0; j < n; j++) {
                if (costMatrix[i][j] == min) {
                    underlined[i][j] = true; // Подчеркиваем минимальный элемент
                }
            }
        }

        // Шаг 2: Отмечаем столбцы с более чем одним подчеркиванием
        for (int j = 0; j < n; j++) {
            int count = 0;
            for (int i = 0; i < n; i++) {
                if (underlined[i][j]) {
                    count++;
                }
            }
            if (count > 1) {
                columnMarked[j] = true; // Столбец помечен
            }
        }

        // Шаг 3: Корректировка матрицы
        adjustMatrixForMacMethod(underlined, columnMarked);
        printSolution(underlined);
    }
    
    private static void adjustMatrixForMacMethod(boolean[][] underlined, boolean[] columnMarked) {
        int minValue = Integer.MAX_VALUE;

        // Находим минимальный элемент среди непомеченных элементов
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!columnMarked[j] && costMatrix[i][j] < minValue) {
                    minValue = costMatrix[i][j];
                }
            }
        }

        // Корректируем элементы матрицы
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!columnMarked[j]) {
                    costMatrix[i][j] -= minValue; // Уменьшаем непомеченные элементы
                }
                if (columnMarked[j] && underlined[i][j]) {
                    costMatrix[i][j] += minValue; // Добавляем минимальное значение к пересечению
                }
            }
        }
    }

    private static void printSolution(boolean[][] underlined) {
        int totalCost = 0;
        System.out.println("Оптимальные назначения (работник -> задание):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (underlined[i][j]) {
                    System.out.println("Работник " + (i + 1) + " -> Задание " + (j + 1));
                    totalCost += costMatrix[i][j];
                    break;
                }
            }
        }
        System.out.println("Общие затраты: " + totalCost);
    }
}
