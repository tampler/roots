# Roots interview task

Given a `Range[a:Int,b:Int]`, find the maximum depth of a square root from this range.
Examples: 

1. Range[2,4] is 1, since 2^2 = 4
2. Range[10,17] is 2, since 16 = 4^2 = (2^2)^2
and so on

Limits:
1. A <= B 
2. A >= 2, B <= 1e9

Current implementation
for the full range plain calculation gives OOM : Java heap pretty fast. For ZIO Streams, after 20+ mins of calculation for the full range, I got the following:

```bash
Ooops, I was too fast to celebrate. For Scala 2.13.2, after 20+ mins of calculation, I got the following:

java.lang.OutOfMemoryError: Java heap space
Dumping heap to java_pid5551.hprof ...
Heap dump file created [1193154698 bytes in 4,691 secs]
java.lang.OutOfMemoryError: Java heap space
        at scala.collection.mutable.ArrayBuilder$ofInt.mkArray(ArrayBuilder.scala:284)
        at scala.collection.mutable.ArrayBuilder$ofInt.resize(ArrayBuilder.scala:290)
        at scala.collection.mutable.ArrayBuilder.ensureSize(ArrayBuilder.scala:38)
        at scala.collection.mutable.ArrayBuilder$ofInt.addOne(ArrayBuilder.scala:295)
        at scala.collection.mutable.ArrayBuilder$ofInt.addOne(ArrayBuilder.scala:279)
        at zio.ChunkBuilder$$anon$1.addOne(ChunkBuilder.scala:58)
        at zio.ChunkBuilder$$anon$1.addOne(ChunkBuilder.scala:47)
```

Here's my JVM setup:
```bash
-Xss8M
-Xms3G
-Xmx4G
```
So for the 4G JVM Heap size and the Maximum range [2...1e9], the total Array size for this task exceeds the 4G heap, so I get an allocation error to simply store the data. ZIO works fine and the stream is lazily evaluated