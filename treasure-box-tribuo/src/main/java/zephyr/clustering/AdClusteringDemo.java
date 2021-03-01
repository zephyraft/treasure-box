package zephyr.clustering;

import com.oracle.labs.mlrg.olcut.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.tribuo.Dataset;
import org.tribuo.Example;
import org.tribuo.MutableDataset;
import org.tribuo.Prediction;
import org.tribuo.clustering.ClusterID;
import org.tribuo.clustering.ClusteringFactory;
import org.tribuo.clustering.evaluation.ClusteringEvaluation;
import org.tribuo.clustering.evaluation.ClusteringEvaluator;
import org.tribuo.clustering.kmeans.KMeansModel;
import org.tribuo.clustering.kmeans.KMeansTrainer;
import org.tribuo.datasource.ListDataSource;
import org.tribuo.impl.ArrayExample;
import org.tribuo.math.la.DenseVector;
import org.tribuo.provenance.SimpleDataSourceProvenance;
import org.tribuo.util.Util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于聚类的异常点检测
 *
 *
 */
@Slf4j
public class AdClusteringDemo {

    private static final ClusteringFactory clusteringFactory = new ClusteringFactory();

    public static void main(String[] args) throws NoSuchAlgorithmException {
        // 单线程聚类
        ClusteringEvaluator eval = new ClusteringEvaluator();

        Pair<Dataset<ClusterID>, Dataset<ClusterID>> pair = getDatasetPair();

        KMeansTrainer trainer = new KMeansTrainer(15,100, KMeansTrainer.Distance.EUCLIDEAN,1,1);
        long startTime = System.currentTimeMillis();
        KMeansModel model = trainer.train(pair.getA());
        long endTime = System.currentTimeMillis();

        log.info("Training took {}", Util.formatDuration(startTime, endTime));

        DenseVector[] centroids = model.getCentroidVectors();
        for (DenseVector centroid : centroids) {
            log.info("{}", centroid);
        }

        ClusteringEvaluation evalData = eval.evaluate(model, pair.getA());

        // 判断
        Map<Integer, Integer> centroidCountMap = new HashMap<>();
        evalData.getPredictions().forEach(prediction -> centroidCountMap.put(prediction.getOutput().getID(), centroidCountMap.getOrDefault(prediction.getOutput().getID(), 0) + 1));

        int minKey = 0;
        int minSize = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Integer> entry : centroidCountMap.entrySet()) {
            if (entry.getValue() < minSize) {
                minSize = entry.getValue();
                minKey = entry.getKey();
            }
        }

        log.info("minKey={}", minKey);

        ClusteringEvaluation evalTest = eval.evaluate(model, pair.getB());
        for (Prediction<ClusterID> clusterIDPrediction : evalTest.getPredictions()) {
            if (clusterIDPrediction.getOutput().getID() == minKey) {
                log.warn("{}", clusterIDPrediction.getExample());
            }
        }
    }

    private static Pair<Dataset<ClusterID>, Dataset<ClusterID>> getDatasetPair() throws NoSuchAlgorithmException {
        SimpleDataSourceProvenance trainingProvenance = new SimpleDataSourceProvenance("Generated clustering data", clusteringFactory);
        Dataset<ClusterID> data = new MutableDataset<>(new ListDataSource<>(getData(10000), clusteringFactory, trainingProvenance));
        Dataset<ClusterID> test = new MutableDataset<>(new ListDataSource<>(getData(50), clusteringFactory, trainingProvenance));
        return new Pair<>(data, test);
    }

    private static List<Example<ClusterID>> getData(int size) throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        List<Example<ClusterID>> trainingData = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            double num = random.nextDouble();
            double[] values;
            if (num > 0.99d) {
                values = new double[]{6000 + num * 100};
            } else {
                values = new double[]{num * 100};
            }

            int centroid = (int) values[0];
            trainingData.add(new ArrayExample<>(new ClusterID(centroid), new String[]{"latency"}, values));
        }
        return trainingData;
    }

}
