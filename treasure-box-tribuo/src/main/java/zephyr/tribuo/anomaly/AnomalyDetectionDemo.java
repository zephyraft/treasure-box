package zephyr.tribuo.anomaly;

import org.tribuo.anomaly.evaluation.AnomalyEvaluator;
import org.tribuo.anomaly.example.AnomalyDataGenerator;
import org.tribuo.anomaly.libsvm.LibSVMAnomalyModel;
import org.tribuo.anomaly.libsvm.LibSVMAnomalyTrainer;
import org.tribuo.anomaly.libsvm.SVMAnomalyType;
import org.tribuo.common.libsvm.KernelType;
import org.tribuo.common.libsvm.SVMParameters;
import org.tribuo.data.csv.CSVSaver;
import org.tribuo.util.Util;

import java.io.IOException;
import java.nio.file.Paths;

public class AnomalyDetectionDemo {

    public static void main(String[] args) throws IOException {
        var eval = new AnomalyEvaluator();

        // 测试用数据生成器，返回两组数据，一组全为正常数据，一组包含异常数据
        var pair = AnomalyDataGenerator.gaussianAnomaly(2000,0.2);
        var data = pair.getA();
        var test = pair.getB();

        var csvSaver = new CSVSaver();
        csvSaver.save(Paths.get("treasure-box-tribuo/data/anomaly.data"), data, "eventType");
        csvSaver.save(Paths.get("treasure-box-tribuo/data/anomalyTest.data"), test, "eventType");
        System.out.println("you can check generated data in dir treasure-box-tribuo/data");

        // 训练模型
        var params = new SVMParameters<>(new SVMAnomalyType(SVMAnomalyType.SVMMode.ONE_CLASS), KernelType.RBF);
        params.setGamma(1.0);
        params.setNu(0.1);
        var trainer = new LibSVMAnomalyTrainer(params);

        var startTime = System.currentTimeMillis();
        var model = trainer.train(data);
        var endTime = System.currentTimeMillis();
        System.out.println();
        System.out.println("Training took " + Util.formatDuration(startTime,endTime));

        System.out.println(((LibSVMAnomalyModel)model).getNumberOfSupportVectors());

        // 模型评估
        var testEvaluation = eval.evaluate(model,test);
        System.out.println(testEvaluation.toString());
        System.out.println(testEvaluation.confusionString());
    }

}
