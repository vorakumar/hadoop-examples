# hadoop-examples

### To setup docker container, run
```shell
./go setup
```

### Wordcount example
Counts frequencies of each word in the directory. 

Follow the comments in the `examples.hadoop.mapreduce.simple` package and fill in the missing code. Then run,

```shell
./go word-count  
```

### Amount By city example
Given following input data, calculate total amount spent by customers in different cities.

##### Customers 
<customerId, name, city> 

##### Orders 
<orderId, customerId, amount> 

##### Output
<City, Total Amount spenty by customers in that city>
 
Follow the comments in the `examples.hadoop.mapreduce.join` package and fill in the missing code. Then run,

```shell
./go amount-by-city  
```

