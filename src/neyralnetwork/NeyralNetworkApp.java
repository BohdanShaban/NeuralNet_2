package neyralnetwork;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class NeyralNetworkApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*Neuron n1 = new Neuron(5);
    n1.printNeyron();

    Layer lr1 = new Layer(4, 5);

    lr1.printLayer();*/

        NeuralNetwork nn1 = new NeuralNetwork(4, new int[]{3,2,1});

        nn1.printNN();

        //выборка с определённой логикой (я уже забыл условия но сеть всеравно даёт правильный ответ :)))
        //МОЖЕТЕ ЗАКОММЕНТИРОВАТЬ НА СВОЙ ВКУС ЛЮБЫЕ ОДНУ ИЛИ ДВЕ ПАРЫ (ОТВЕТ-ВОПРОС), СЕТЬ СПРАВЛЯЕТСЯ.
        double[][] task1 = {
                {1.0,0.0,0.0,0.0},
                {0.0,1.0,0.0,0.0},
                {1.0,1.0,0.0,0.0},
                {0.0,0.0,1.0,0.0},
                {1.0,0.0,1.0,0.0},
                {0.0,1.0,1.0,0.0},
                {1.0,1.0,1.0,0.0},
                {0.0,0.0,0.0,1.0},
                //тестовые вопросы намеренно изымаются из обучающей выборки
                //чтобы создать ситуацию в которой сеть будет сама делать выводы
                //{1.0,0.0,0.0,1.0},
                //{0.0,1.0,0.0,1.0},
                {1.0,1.0,0.0,1.0},
                {0.0,0.0,1.0,1.0},
        };

        //ответы
        double[][] answ1 = {
                {1.0},
                {0.0},
                {0.0},
                {0.0},
                {0.0},
                {1.0},
                {1.0},
                {1.0},
                //ответы сеть должна угадать сама
                //{1.0},//ожидаемый ответ 1
                //{0.0},//ожидаемый ответ 2
                {0.0},
                {0.0},
        };


        nn1.trainNeuralNetwork(task1, answ1, 0.5, 0.05);

        System.out.println("Ожидаемый ответ 1 - 1");
        System.out.println("Ожидаемый ответ 2 - 0");
        System.out.println("Ответ 1 - "+nn1.setTask(new double[]{1.0,0.0,0.0,1.0})[0]);
        System.out.println("Ответ 2 - "+nn1.setTask(new double[]{0.0,1.0,0.0,1.0})[0]);

        /*nn1.printNN();

    NeuralNetwork nn2 = new NeuralNetwork(2, new int[]{3,1});

    double[][] task2 = {
    {0.0,0.0},
    {0.0,1.0},
    {1.0,0.0},
    {1.0,1.0},
    };

    double[][] answ2 = {
    {0.0},
    {1.0},
    {1.0},
    {0.0},
    };

    nn2.trainNeuralNetwork(task2, answ2, 0.9, 0.2);

    System.out.println("nn2 - "+nn2.setTask(task2[0])[0]);
    System.out.println("nn2 - "+nn2.setTask(task2[1])[0]);
    System.out.println("nn2 - "+nn2.setTask(task2[2])[0]);
    System.out.println("nn2 - "+nn2.setTask(task2[3])[0]);*/

        /*NeuralNetwork nn3 = new NeuralNetwork(7, new int[]{5,1});

    double[][] task3 = {
    {0.0,0.4,0.0,0.1,0.0,0.0,0.8},
    {0.7,0.0,0.4,0.0,0.1,0.0,0.0},
    {0.0,0.7,0.0,0.4,0.0,0.1,0.0},
    {1.0,0.0,0.7,0.0,0.4,0.0,0.1},
    {0.0,1.0,0.0,0.7,0.0,0.4,0.0},
    {0.0,0.0,1.0,0.0,0.7,0.0,0.4},
    {0.3,0.0,0.0,1.0,0.0,0.7,0.0},
    {0.0,0.3,0.0,0.0,1.0,0.0,0.7},
    {0.6,0.0,0.3,0.0,0.0,1.0,0.0},
    {0.0,0.6,0.0,0.3,0.0,0.0,1.0},
    {0.9,0.0,0.6,0.0,0.3,0.0,0.0},
    {0.0,0.9,0.0,0.6,0.0,0.3,0.0},
    {0.0,0.0,0.9,0.0,0.6,0.0,0.3},
    {0.2,0.0,0.0,0.9,0.0,0.6,0.0},
    {0.0,0.2,0.0,0.0,0.9,0.0,0.6},
    {0.5,0.0,0.2,0.0,0.0,0.9,0.0},
    {0.0,0.5,0.0,0.2,0.0,0.0,0.9},
    {0.8,0.0,0.5,0.0,0.2,0.0,0.0},
    {0.0,0.8,0.0,0.5,0.0,0.2,0.0},
    {0.0,0.0,0.8,0.0,0.5,0.0,0.2},
    {0.1,0.0,0.0,0.8,0.0,0.5,0.0},
    {0.0,0.1,0.0,0.0,0.8,0.0,0.5},
    {0.4,0.0,0.1,0.0,0.0,0.8,0.0},
    };

    double[][] answ3 = {
    {0.0},{0.8},{0.0},{0.0},{0.1},{0.0},{0.4},{0.0},{0.7},{0.0},{1.0},{0.0},{0.0},{0.3},{0.0},{0.6},{0.0},{0.9},{0.0},{0.0},{0.2},{0.0},{0.5}
    };


    nn3.trainNeuralNetwork(task3, answ3, 0.6, 0.02);

    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[0])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[1])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[2])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[3])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[4])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[5])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[6])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[7])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[8])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[9])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[10])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[11])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[12])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[13])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[14])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[15])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[16])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[17])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[18])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[19])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[20])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[21])[0]));
    System.out.println("time is 12 hours "+takeTime(nn3.setTask(task3[22])[0]));*/


        launch(args);
    }
    public static String takeTime(double d){
        int tm = (int)Math.round(d*10);
        if(tm==0)return "no events";
        return ""+(6*tm)+" min";
    };
}