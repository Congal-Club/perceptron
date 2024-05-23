import java.util.Random;
import java.util.Scanner;

public class XOR {
  public static void main(String[] args) {
    // Datos de entrada
    int[][] X = {
      {0, 0},
      {0, 1},
      {1, 0},
      {1, 1}
    };

    // Salida esperada
    int[] y = {0, 1, 1, 0};
    
    // Crear el perceptrón
    Perceptron xor = new Perceptron(2, 2, 0.1, 10000);
    
    // Entrenar el perceptrón
    xor.train(X, y);
    
    // Probar el perceptrón
    System.out.println("Tabla de verdad XOR - Predicciones del perceptrón:");
    
    for (int[] inputs : X) {
      int output = xor.predict(inputs);
      System.out.println(inputs[0] + " XOR " + inputs[1] + " = " + output);
    }
  }
}

class Perceptron {
  private double[][] weights1;
  private double[] weights2;
  private double learningRate;
  private int epochs;

  public Perceptron(int inputSize, int hiddenSize, double learningRate, int epochs) {
    weights1 = new double[inputSize][hiddenSize];
    weights2 = new double[hiddenSize];
    
    this.learningRate = learningRate;
    this.epochs = epochs;

    initializeWeights();
  }

  private void initializeWeights() {
    Random rand = new Random();
    for (int i = 0; i < weights1.length; i++) {
      for (int j = 0; j < weights1[i].length; j++) {
        weights1[i][j] = rand.nextDouble() - 0.5;
      }
    }
    
    for (int i = 0; i < weights2.length; i++) {
      weights2[i] = rand.nextDouble() - 0.5;
    }
  }

  private double sigmoid(double x) {
    return 1 / (1 + Math.exp(-x));
  }

  private double sigmoidDerivative(double x) {
    return x * (1 - x);
  }

  private double[] feedForward(int[] inputs) {
    double[] hiddenLayerOutput = new double[weights2.length];
    
    for (int i = 0; i < weights1[0].length; i++) {
      for (int j = 0; j < weights1.length; j++) {
        hiddenLayerOutput[i] += inputs[j] * weights1[j][i];
      }
      
      hiddenLayerOutput[i] = sigmoid(hiddenLayerOutput[i]);
    }
    
    return hiddenLayerOutput;
  }

  private double feedForwardOutput(int[] inputs) {
    double[] hiddenLayerOutput = feedForward(inputs);
    double output = 0;
      
    for (int i = 0; i < hiddenLayerOutput.length; i++) {
      output += hiddenLayerOutput[i] * weights2[i];
    }
      
    return sigmoid(output);
  }

  public void train(int[][] X, int[] y) {
    for (int epoch = 0; epoch < epochs; epoch++) {
      for (int i = 0; i < X.length; i++) {
        int[] inputs = X[i];
        double[] hiddenLayerOutput = feedForward(inputs);
        double output = feedForwardOutput(inputs);

        double error = y[i] - output;
        double deltaOutput = error * sigmoidDerivative(output);

        for (int j = 0; j < weights2.length; j++) {
          weights2[j] += learningRate * deltaOutput * hiddenLayerOutput[j];
        }

        for (int j = 0; j < hiddenLayerOutput.length; j++) {
          double deltaHidden = deltaOutput * weights2[j] * sigmoidDerivative(hiddenLayerOutput[j]);
          
          for (int k = 0; k < inputs.length; k++) {
            weights1[k][j] += learningRate * deltaHidden * inputs[k];
          }
        }
      }

      if (epoch % 1000 == 0 || epoch == epochs - 1) {
        System.out.println("Iteración: " + (epoch + 1) + "--------------------");
        System.out.println("Pesos de la capa oculta:");
        
        for (int j = 0; j < weights1[0].length; j++) {
          System.out.print("Neurona " + (j + 1) + ": ");
          
          for (int k = 0; k < weights1.length; k++) {
            System.out.print(weights1[k][j] + " ");
          }
          
          System.out.println();
        }
        
        System.out.println("Pesos de la capa de salida:");
        
        for (int j = 0; j < weights2.length; j++) {
          System.out.println("w" + (j + 1) + ": " + weights2[j]);
        }
        
        System.out.println("----------------------------------------");
      }
    }
  }

  public int predict(int[] inputs) {
    return (int) Math.round(feedForwardOutput(inputs));
  }
}
