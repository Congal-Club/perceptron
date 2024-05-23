import java.util.Random;
import java.util.Scanner;

public class OR {
  public static void main(String[] args) {
    // Tabla de verdad para la compuerta OR
    float[][] entradas = {
      {1f, 1f, 1f},
      {1f, 1f, -1f},
      {1f, -1f, 1f},
      {1f, -1f, -1f},
    };

    float[] salidas = {1f, 1f, 1f, -1f};

    // Inicialización de pesos
    float w0 = new Random().nextFloat() * 2 - 1;
    float w1 = new Random().nextFloat() * 2 - 1;
    float w2 = new Random().nextFloat() * 2 - 1;

    // Tasa de aprendizaje
    float learningRate = (new Random().nextInt(8) + 2) / 10.0f;

    float y, error;
    boolean errorPresente;

    System.out.println("PERCEPTRÓN OR");
    System.out.println("Factor de aprendizaje: " + learningRate);
    System.out.println("w0: " + w0);
    System.out.println("w1: " + w1);
    System.out.println("w2: " + w2);

    int iteracion = 0;

    // Entrenamiento
    do {
      errorPresente = false;
      iteracion++;
      System.out.println("\nIteración: " + iteracion + "--------------------");

      for (int i = 0; i < entradas.length; i++) {
        y = w0 * entradas[i][0] + w1 * entradas[i][1] + w2 * entradas[i][2];
        y = (y >= 0) ? 1 : -1;

        error = salidas[i] - y;

        if (error != 0) {
          w0 += learningRate * error * entradas[i][0];
          w1 += learningRate * error * entradas[i][1];
          w2 += learningRate * error * entradas[i][2];
          errorPresente = true;
        }

        System.out.println("Entrada: [" + entradas[i][1] + ", " + entradas[i][2] + "] => Salida esperada: " + salidas[i] + ", Salida obtenida: " + y);
        System.out.println("Error: " + error);
        System.out.println("Pesos actualizados: w0=" + w0 + " w1=" + w1 + " w2=" + w2);
      }
    } while (errorPresente);

    System.out.println("\nPesos finales:");
    System.out.println("w0 = " + w0);
    System.out.println("w1 = " + w1);
    System.out.println("w2 = " + w2);

    Scanner sc = new Scanner(System.in);
    System.out.print("Ingresa el valor de X1: ");
    int input1 = sc.nextInt();
    System.out.print("Ingresa el valor de X2: ");
    int input2 = sc.nextInt();

    y = w0 * 1 + w1 * input1 + w2 * input2;
    y = (y >= 0) ? 1 : -1;

    System.out.println("La salida para (" + input1 + ", " + input2 + ") es: " + y);
  }
}
