package neyralnetwork;

import java.io.Serializable;
/**
 *
 * @author user
 */
public class Neuron implements Serializable {

    /**
     * Взвешенная сумма сигналов дендритов
     */
    private double e;

    /**
     * Вес дендритов
     */
    private final double[] dendritWeights;

    /**
     * Количество дендритов
     */
    private final int dendritCount;

    /**
     * Ошибка нейрона
     */
    private double error;

    /**
     * Сохраненные сигмоидные сигналы
     */
    private double[] sigmIn;

    /**
     * Входной сигнал нейрона смещения
     */
    private double biasIn;
    /**
     * Создаёт нейрон
     * @param dendCount кількість дендритів з врахуванням нейрону зміщення
     */
    public Neuron(int dendCount) {
        e=0.0;
        dendritCount = dendCount;
        dendritWeights = new double[dendritCount];
        error=0.0;
        initiateDenritWeights();
    }

    /**
     * Инициирует вес нейронов
     */
    private void initiateDenritWeights(){
        for (int i = 0; i < dendritWeights.length; i++) {
            //dendritWeights[i] = Math.random()<0.5 ? Math.random()*0.3+0.6 : -Math.random()*0.3-0.6;
            dendritWeights[i] = Math.random()<0.5 ? Math.random()*0.3+(15/dendritCount) : -Math.random()*0.3-(15/dendritCount);
        }
    }

    /**
     * Получает сигналы на дендриты с нейронов предыдущего слоя
     * @param dendSygn сигмоидные сигналы на дендриты
     * @param bias сигнал нейрона смещения
     */
    public void takeDendSygnals(double[] dendSygn,double bias){
        //+1 тому що сигнали дендритів надсилаються без врахування нейрону зміщення
        if(dendSygn.length+1!=dendritCount)throw new NotMatchSygnDendCount();
        sigmIn=dendSygn;
        biasIn=bias;
        e=0.0;
        //важливо щоб перебирались вхідні сигнали
        for (int i = 0; i < dendSygn.length; i++) {
            e+=dendSygn[i]*dendritWeights[i];
        }
        e+=bias*dendritWeights[dendritCount-1];
    }

    /**
     * Сигмоидный сигнал
     * @return сигмоиду сигнала
     */
    public double giveSigmSignal(){
        return 1/(1+Math.exp(-e));
    }

    /**
     * Принимает ошибку
     * @param err ошибка
     */
    public void takeError(double err){
        error=err;
    }

    /**
     * Раздает ошибки
     * @return ошибки
     */
    public double[] giveErrors(){//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //-1 нейрону зміщення не потрібна помилка
        double[] errors = new double[dendritCount-1];
        //-1 нейрону зміщення не потрібна помилка
        for (int i = 0; i < dendritCount-1; i++) {
            errors[i] = error*dendritWeights[i];
        }
        return errors;
    }

    /**
     * Исправляет веса
     * @param learnCoef коефициент обучения
     */
    public void fixWeight(double learnCoef){
        //-1 тому що нейрон зміщення виправляється окремо
        for (int i = 0; i < dendritCount-1; i++) {
            dendritWeights[i]+=sigmIn[i]*learnCoef*giveSigmSignal()*(1-giveSigmSignal())*error;
        }
        dendritWeights[dendritCount-1]+=biasIn*learnCoef*giveSigmSignal()*(1-giveSigmSignal())*error;
    }


    /**
     * Раcпечатывает состояние нейрона
     */
    public void printNeyron(){
        System.out.println("Dendrit count - "+dendritCount);
        int cnt = 0;
        for (double dendritWeight : dendritWeights) {
            System.out.println("dnd #"+cnt+" - "+dendritWeight);
            cnt++;
        }
    }
}