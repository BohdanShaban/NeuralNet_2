package neyralnetwork;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.concurrent.Task;
/**
 *
 * @author user
 */
public class NeuralNetwork extends Task<Void> implements Serializable {

    private double[][] taskSave;

    private double[][] answerSave;

    private double learnCoefSave;

    private double shurenessSave;

    /**
     * Количество слоёв
     */
    private final int layersCount;

    /**
     * Количество сенсорных дендритов
     */
    private double[] sensors;

    /**
     * Массив слоёв
     */
    private final Layer[] layers;

    //private final Label label;
    /**
     * Конструктор нейронной сети
     * @param sens количество сенсорных нейронов
     * @param networkMap карта нейронной сети
     */
    public NeuralNetwork(int sens,int[] networkMap/*, Label lbl*/) {
        sensors=new double[sens];
        layersCount=networkMap.length;
        layers = new Layer[networkMap.length];
        initiateLayers(networkMap);
        //label = lbl;
    }
    /**
     * Инициализирует слои
     * @param networkMap карта слоёв
     */
    private void initiateLayers(int[] networkMap) {
        layers[0] = new Layer(networkMap[0], sensors.length);//винести окремо змінну?
        for (int i = 1; i < layersCount; i++) {
            layers[i] = new Layer(networkMap[i], networkMap[i-1]);
        }
    }

    /**
     * Получает ответ сети по указанному заданию
     * @param task задание
     * @return відповідь
     */
    public double[] getAnswer(double[] task){
        if(task.length!=sensors.length) throw new NotMatchNeyronSygnCount();
        sensors=task;
        /*for (int i = 0; i < sensors.length; i++) {//винести окремо змінну?
            sensors[i]=task[i];
        }*/
        return getAnswer();
    }

    /**
     * Получает ответ сети по предустановленному заданию(вопросу)
     * @return ответ
     */
    public double[] getAnswer(){
        layers[0].acceptSygnals(sensors);
        for (int i = 1; i < layersCount; i++) {
            layers[i].acceptSygnals(layers[i-1].giveSygnals());
        }
        return layers[layers.length-1].giveSygnals();
    }

    /**
     * Тренировка нейронной сети
     * @param task подборка заданий
     * @param answ подборка ответов
     * @param learnCoef коефициент обучения
     * @param shureness уверенность сети
     */
    public void trainNeuralNetwork(double[][] task, double[][] answ, double learnCoef, double shureness){
        if(task.length!=answ.length) throw new NotMatchTaskAnswCount();
        boolean glError;
        //int cykles = 0;
        double totalErr;
        do{
            totalErr=0;
            glError=false;
            for (int i = 0; i < task.length; i++) {
                if(task[i].length!=sensors.length) throw new NotMatchTaskAnswCount();
                double[] errors = getErrors(answ[i], getAnswer(task[i]));
                totalErr+=getTotalError(errors);
                if(isError(shureness, errors)){
                    backpropagateAndFix(errors,learnCoef);
                    glError=true;
                }
            }
            System.out.println("total error - "+totalErr);
            updateMessage("number - "+totalErr);
        }while(glError);
    }

    /**
     * Устанавливает параметры обучения сети
     * @param ts задания
     * @param an ответы
     * @param sh уверенность сети
     * @param lc коефициент обучения
     */
    public void setParameters(double[][] ts, double[][] an, double lc, double sh){
        taskSave = ts;
        answerSave = an;
        shurenessSave = sh;
        learnCoefSave = lc;
    }

    /**
     * Начинает обучение сети если данные загружены
     */
    public void startLearn(){
        if(taskSave==null&&answerSave==null)return;// доповнити перевіркою коефіціенту навчання і впевненості !!!!!!!!!!!!!!!!!!!!!!!!!!!!
        trainNeuralNetwork(taskSave, answerSave, learnCoefSave, shurenessSave);
    }

    @Override
    protected Void call() {
        startLearn();
        return null;
    }
    /**
     * Возвращает ошибки
     * @param rigthAnswers правильные ответы
     * @param answers ответы сети
     * @return ошибки
     */
    public double[] getErrors(double[] rigthAnswers, double[] answers){
        if(rigthAnswers.length!=answers.length) throw new NotMatchTaskAnswCount();
        double[] errs = new double[rigthAnswers.length];
        for (int i = 0; i < rigthAnswers.length; i++) {
            errs[i] = rigthAnswers[i]-answers[i];
        }
        return errs;
    }

    /**
     * Подсчитывает суммарную ошибку эпохи
     * @param err все ошибки
     * @return суммарная ошибка
     */
    public double getTotalError(double[] err){
        double totalErr=0;
        for (double d : err) {
            totalErr+=Math.abs(d);
        }
        return totalErr;
    }

    /**
     * Проверяет есть ли ошибка в ответе сети
     * @param shureness уверенность сети
     * @param errors ошибки
     * @return ответ есть ли ошибка
     */
    public boolean isError(double shureness,double[] errors){
        boolean isErr=false;
        for (double error : errors) {
            isErr = isErr ? true : Math.abs(error)>shureness;
        }
        return isErr;
    }

    /**
     * Обратное распространение ошибок
     * @param errs ошибки
     */
    private void backPropagateErrors(double[] errs){
        layers[layersCount-1].acceptErrors(errs);
        for (int i = layersCount-1; i > 0; i--) {
            layers[i-1].acceptErrors(layers[i].giveErrors());
        }
    }

    /**
     * Исправление весов
     * @param learnCoef коефициент обучения
     */
    private void fixWeights(double learnCoef){
        for (int i = layers.length-1; i > -1; i--) {
            layers[i].fixWeights(learnCoef);
        }
    }

    /**
     * Обратное распространение ошибок и исправление весов
     * @param errs ошибки
     * @param learnCoef коефициент обучения
     */
    private void backpropagateAndFix(double[] errs, double learnCoef){
        backPropagateErrors(errs);
        fixWeights(learnCoef);
    }

    /**
     * Распечатывает слой нейронов
     */
    public void printNN(){
        System.out.println("Layers count - "+layersCount);
        System.out.println("sensors count - "+sensors.length);
        int count = 0;
        for (Layer layer : layers) {
            System.out.println("");
            System.out.println("Layer #"+count);
            layer.printLayer();
            count++;
        }
    }

}