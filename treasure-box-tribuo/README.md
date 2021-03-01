# tribuo机器学习库

https://tribuo.org/

https://github.com/oracle/tribuo

## 无监督 vs. 有监督

### 有监督

> 通过已有的训练样本去训练得到一个最优模型，再利用这个模型将所有的输入映射为相应的输出，对输出进行简单的判断从而实现预测和分类的目的，也就具有了对未知数据进行预测和分类的能力。就如有标准答案的练习题，然后再去考试，相比没有答案的练习题然后去考试准确率更高。又如我们小的时候不知道牛和鸟是否属于一类，但当我们随着长大各种知识不断输入，我们脑中的模型越来越准确，判断动物也越来越准确。

主要分为分类和回归

### 无监督

> 我们事先没有任何训练样本，而需要直接对数据进行建模。比如我们去参观一个画展，我们完全对艺术一无所知，但是欣赏完多幅作品之后，我们也能把它们分成不同的派别。

无监督学习主要算法是聚类

## 评估模型

![bezdekIris.data](doc/Precisionrecall.svg)

- precision：精度，精确率是针对我们预测结果而言的，它表示的是预测为正的样本中有多少是真正的正样本。那么预测为正就有两种可能了，一种就是把正类预测为正类(TP)，另一种就是把负类预测为正类(FP)，也就是`P = TP / (TP + FP)`
- recall：召回率是针对我们原来的样本而言的，它表示的是样本中的正例有多少被预测正确了。那也有两种可能，一种是把原来的正类预测成正类(TP)，另一种就是把原来的负类预测为负类(FN)。`R = TP / (TP + FN)`

假设我们手上有60个正样本，40个负样本，我们要找出所有的正样本，系统查找出50个，其中只有40个是真正的正样本，计算上述各指标。
TP: 将正类预测为正类数  40
FN: 将正类预测为负类数  20
FP: 将负类预测为正类数  10
TN: 将负类预测为负类数  30
准确率(accuracy) = 预测对的/所有 = (TP+TN)/(TP+FN+FP+TN) = 70%
精确率(precision) = TP/(TP+FP) = 80%
召回率(recall) = TP/(TP+FN) = 2/3

## 分类

[zephyr.classification.ClassificationDemo](src/main/java/zephyr/classification/ClassificationDemo.java)

对离散型随机变量建模或预测的监督学习算法。使用案例包括邮件过滤、金融欺诈和预测雇员异动等输出为类别的任务。

## 聚类

[zephyr.classification.ClusteringDemo](src/main/java/zephyr/clustering/ClusteringDemo.java)

## 回归

[zephyr.classification.RegressionDemo](src/main/java/zephyr/regression/RegressionDemo.java)

回归方法是一种对数值型连续随机变量进行预测和建模的监督学习算法。使用案例一般包括房价预测、股票走势或测试成绩等连续变化的案例。

###评估回归模型

RMSE：均方根误差（RMSE）总结了回归模型的预测与我们在数据中观察到的值之间的误差大小。越低越好。理想模型为0
MAE：平均绝对误差（MAE）是模型误差的另一个总结。
R^2：R平方度量（也称为"确定系数"）总结了我们的模型可以解释观察到的结果中有多少变化。

## 异常检测

[zephyr.classification.AnomalyDetectionDemo](src/main/java/zephyr/anomaly/AnomalyDetectionDemo.java)


### SVM

支持向量机

内核

- LINEAR：线性，主要用于数据是线性可分离的时候

  ![bezdekIris.data](doc/gfglinear.png)

- POLY：多项式，主要用于图像处理
- RBF：高斯径向基函数，主要用于事先并不知晓的数据
- SIGMOID：sigmoid函数，主要用于神经网络代理