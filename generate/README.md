# REST client generation

`.yaml` files are copied from [ya-client](https://github.com/golemfactory/ya-client/tree/master/specs).
In case the `ya-client` files are changed, please update them here also.

As prerequisite for generating, download `swagger-codegen` version 3 
from [here](https://mvnrepository.com/artifact/io.swagger.codegen.v3/swagger-codegen-cli/3.0.22) to this direcory.

To generate just run in this directory

```./generate.sh```

The script works only for Linux, sorry. But Windows version should be straightforward.

Files are generated to `../yajapi` directory. The following packages are to be updated in `src/main/java`

```
network.golem.yajapi.activity
network.golem.yajapi.market
network.golem.yajapi.payment
```

Some files after generation were changed manually. Here's the list of changes:
1. `network.golem.yajapi.activity.ApiClient` line 82
2. `network.golem.yajapi.market.ApiClient` line 82
3. `network.golem.yajapi.payment.ApiClient` line 82

As the comment for the above changes: for unknown reasons `swagger` incorrectly generates `OAuth` authorization method 
despite `.yaml` files are clearly defines differently.
