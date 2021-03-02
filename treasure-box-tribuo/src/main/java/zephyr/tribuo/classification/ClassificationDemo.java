package zephyr.tribuo.classification;

import com.oracle.labs.mlrg.olcut.provenance.ProvenanceUtil;
import org.tribuo.Model;
import org.tribuo.MutableDataset;
import org.tribuo.Trainer;
import org.tribuo.classification.Label;
import org.tribuo.classification.LabelFactory;
import org.tribuo.classification.evaluation.LabelEvaluator;
import org.tribuo.classification.sgd.linear.LogisticRegressionTrainer;
import org.tribuo.data.csv.CSVLoader;
import org.tribuo.evaluation.TrainTestSplitter;
import org.tribuo.impl.ListExample;

import java.io.IOException;
import java.nio.file.Paths;

public class ClassificationDemo {

    public static void main(String[] args) throws IOException {

        // 读取数据
        var labelFactory = new LabelFactory();
        var csvLoader = new CSVLoader<>(labelFactory);

        // 数据表头
        // 花萼长 花萼宽 花瓣长 花瓣宽 种类
        var irisHeaders = new String[]{"sepalLength", "sepalWidth", "petalLength", "petalWidth", "species"};
        // 定义数据 特征值列 输出列
        var irisesSource = csvLoader.loadDataSource(Paths.get("treasure-box-tribuo/data/bezdekIris.data"), "species", irisHeaders);
        // 拆分训练和测试集
        var irisSplitter = new TrainTestSplitter<>(irisesSource, 0.7, 1L);
        var trainingDataset = new MutableDataset<>(irisSplitter.getTrain());
        var testingDataset = new MutableDataset<>(irisSplitter.getTest());
        System.out.printf("Training data size = %d, number of features = %d, number of classes = %d%n", trainingDataset.size(), trainingDataset.getFeatureMap().size(), trainingDataset.getOutputInfo().size());
        System.out.printf("Testing data size = %d, number of features = %d, number of classes = %d%n", testingDataset.size(), testingDataset.getFeatureMap().size(), testingDataset.getOutputInfo().size());

        // 训练模型
        Trainer<Label> trainer = new LogisticRegressionTrainer();
        System.out.println(trainer.toString());
        Model<Label> irisModel = trainer.train(trainingDataset);

        // 评估模型
        var evaluator = new LabelEvaluator();
        var evaluation = evaluator.evaluate(irisModel, testingDataset);
        System.out.println(evaluation.toString());
        System.out.println(evaluation.getConfusionMatrix().toString());

        // 模型元数据
        var featureMap = irisModel.getFeatureIDMap();
        for (var v : featureMap) {
            System.out.println(v.toString());
            System.out.println();
        }

        // 模型来源
        var provenance = irisModel.getProvenance();
        System.out.println(ProvenanceUtil.formattedProvenanceString(provenance.getDatasetProvenance().getSourceProvenance()));
        System.out.println(ProvenanceUtil.formattedProvenanceString(provenance.getTrainerProvenance()));

        // 预测
        var predicted = irisModel.predict(
                new ListExample<>(
                        new Label("species"),
                        new String[]{"sepalLength", "sepalWidth", "petalLength", "petalWidth"},
                        new double[]{4.6, 3.1, 1.8, 0.1}
                )
        );
        System.out.printf("分类预测为%s", predicted.getOutput().getLabel());
    }

}
