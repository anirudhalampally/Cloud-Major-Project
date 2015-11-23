#Team 6: Real Time Event Monitoring using spark

from pyspark import SparkContext
from pyspark.streaming import StreamingContext
import sys
import sys,os
from nltk.corpus import wordnet
import operator



# Create a local StreamingContext with two working thread and batch interval of 1 second
sc = SparkContext("", "tweet")
#ssc = StreamingContext(sc, 1)
stream = sc.textFile(sys.argv[1], 1)
#stream = stream.collect()
print "__________________________________________________________"
#print stream
stream = sc.textFile(sys.argv[1])
hashTags = stream.flatMap(lambda line: line.split(" ")).map(lambda word: (word, 1)).reduceByKey(lambda a, b: a + b)
hashTags.saveAsTextFile("test")
#print hashTags
print "__________________________________________________________"

'''topCounts60 = hashTags.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(60))
                  .map{case (topic, count) => (count, topic)}
                  .transform(_.sortByKey(false))

topCounts60.foreachRDD(rdd => {
      topList = rdd.take(10)
      println("\nPopular topics in last 60 seconds (%s total):".format(rdd.count()))
      topList.foreach{case (count, tag) => println("%s (%s tweets)".format(tag, count))}
})

topCounts10.foreachRDD(rdd => {
      topList = rdd.take(10)
      println("\nPopular topics in last 10 seconds (%s total):".format(rdd.count()))
      topList.foreach{case (count, tag) => println("%s (%s tweets)".format(tag, count))}
})'''

sc.stop()

