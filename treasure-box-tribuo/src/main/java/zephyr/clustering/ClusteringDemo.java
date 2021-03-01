package zephyr.clustering;

import org.tribuo.clustering.evaluation.ClusteringEvaluator;
import org.tribuo.clustering.example.ClusteringDataGenerator;
import org.tribuo.clustering.kmeans.KMeansTrainer;
import org.tribuo.util.Util;

public class ClusteringDemo {

    public static void main(String[] args) {
        // 单线程聚类
        var eval = new ClusteringEvaluator();

        var data = ClusteringDataGenerator.gaussianClusters(500, 1L);
        var test = ClusteringDataGenerator.gaussianClusters(500, 2L);

        var trainer = new KMeansTrainer(5,10, KMeansTrainer.Distance.EUCLIDEAN,1,1);
        var startTime = System.currentTimeMillis();
        var model = trainer.train(data);
        var endTime = System.currentTimeMillis();
        System.out.println("Training with 5 clusters took " + Util.formatDuration(startTime, endTime));

        var centroids = model.getCentroidVectors();
        for (var centroid : centroids) {
            System.out.println(centroid);
        }

        var trainEvaluation = eval.evaluate(model,data);
        System.out.println(trainEvaluation.toString());

        var testEvaluation = eval.evaluate(model,test);
        System.out.println(testEvaluation.toString());

        var mtData = ClusteringDataGenerator.gaussianClusters(2000, 1L);
        var mtTrainer = new KMeansTrainer(5,10, KMeansTrainer.Distance.EUCLIDEAN,4,1);
        var mtStartTime = System.currentTimeMillis();
        var mtModel = mtTrainer.train(mtData);
        var mtEndTime = System.currentTimeMillis();
        System.out.println("Training with 5 clusters on 4 threads took " + Util.formatDuration(mtStartTime,mtEndTime));

        var mtTestEvaluation = eval.evaluate(mtModel,test);
        System.out.println(mtTestEvaluation.toString());

        var overTrainer = new KMeansTrainer(20,10, KMeansTrainer.Distance.EUCLIDEAN,4,1);
        var overStartTime = System.currentTimeMillis();
        var overModel = overTrainer.train(mtData);
        var overEndTime = System.currentTimeMillis();
        System.out.println("Training with 20 clusters on 4 threads took " + Util.formatDuration(overStartTime,overEndTime));

        var overTestEvaluation = eval.evaluate(overModel,test);
        System.out.println(overTestEvaluation.toString());
    }

}
