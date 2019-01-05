package neyralnetwork;

import java.io.Serializable;
/**
 *
 * @author user
 */
public class Layer implements Serializable{

    /**
     * Массив нейронов слоя
     */
    private final Neuron[] neyrons;

    /**
     * Нейрон смещения
     */
    private final double bias;

    /**
     * Количество нейронов в слое
     */
    private final int neyronCount;

    /**
     * Количество нейронов в предыдущем слое
     */
    private final int prewNeuronCount;
    /**
     * Конструктор слоя
     * @param neuCount количество нейронов в слое
     * @param prewNeyCount количество нейронов в предыдущем слое
     */
    public Layer(int neuCount, int prewNeyCount) {
        neyronCount = neuCount;
        prewNeuronCount = prewNeyCount;
        neyrons = new Neuron[neyronCount];
        initiateNeyrons(prewNeyCount);
        bias = Math.random() < 0.5 ? -1.0 : 1.0;
    }

    /**
     * Инициализация нейронов
     * @param prewNeyCount количество нейронов в предыдущем слое
     */
    private void initiateNeyrons(int prewNeyCount){
        for (int i = 0; i < neyronCount; i++) {
            //+1 дендрит на нейрон зміщення
            neyrons[i] = new Neuron(prewNeyCount+1);
        }
    }

    /**
     * Отправляет сигналы нейронов слоя(текущего) в следующий слой
     * @return сигналы нейронов
     */
    public double[] giveSygnals(){
        double[] sygnals = new double[neyronCount];
        for (int i = 0; i < neyronCount; i++) {
            sygnals[i] = neyrons[i].giveSigmSignal();
        }
        /*int count=0;
        for (double sygnal : sygnals) {
            System.out.println("neyron #"+count+" sygn - "+sygnal);
            count++;
        }*/
        return sygnals;
    }

    /**
     * Принимает сигналы предыдущего слоя
     * @param sygnals сигналы предыдущего слоя
     */
    public void acceptSygnals(double[] sygnals){
        //if(sygnals.length!=neyrons.length) throw new NotMatchNeyronSygnCount();
        for (Neuron neyron : neyrons) {
            neyron.takeDendSygnals(sygnals, bias);
        }
    }

    /**
     * Принимает ошибки
     * @param errs ошибки
     */
    public void acceptErrors(double[] errs){
        if(neyrons.length!=errs.length) throw new NotMatchNeyronSygnCount();
        for (int i = 0; i < neyronCount; i++) {
            neyrons[i].takeError(errs[i]);
        }
    }

    /**
     * Передаёт ошибки следующему слою
     * @return ошибки
     */
    public double[] giveErrors(){
        /*double[][] layErr = new double[neyronCount][];
        for (int i = 0; i < neyronCount; i++) {
            layErr[i]=neyrons[i].giveErrors();
        }
        return layErr;*/
        double[] layErrs = new double[prewNeuronCount];
        for (int i = 0; i < prewNeuronCount; i++) {
            for (int j = 0; j < neyronCount; j++) {
                layErrs[i]+=neyrons[j].giveErrors()[i];
            }
        }
        return layErrs;
    }

    /**
     * Исправляет веса
     * @param learnCoef коефициент обучения
     */
    public void fixWeights(double learnCoef){
        for (Neuron neyron : neyrons) {
            neyron.fixWeight(learnCoef);
        }
    }

    /**
     * Метод распечатывает слой
     */
    public void printLayer(){
        System.out.println("neyCount - "+neyronCount);
        System.out.println("bias - "+bias);
        int cnt=0;
        for (Neuron neyron : neyrons) {
            System.out.println("");
            System.out.println("Neyron #"+cnt);
            neyron.printNeyron();
            cnt++;
        }
    }

}