package nl.vintik.sample

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
//import com.amazonaws.xray.interceptors.TracingInterceptor
import nl.vintik.sample.model.Product
//import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient

class KotlinLambda : RequestHandler<Map<String, String>, List<Product>> {
    override fun handleRequest(event: Map<String, String>, context: Context): List<Product> {
        return ProductsController(ProductsService(productTable)).execute()
    }

    private val dynamoDbAsyncClient = DynamoDbEnhancedAsyncClient.builder()
        .dynamoDbClient(
            DynamoDbAsyncClient.builder()
                .region(Region.EU_WEST_1)
//                .overrideConfiguration(
//                    ClientOverrideConfiguration.builder()
//                        .addExecutionInterceptor(TracingInterceptor())
//                        .build()
//                )
                .build()
        ).build()

    private val productTable = dynamoDbAsyncClient.table(
        Product.TABLE_NAME,
        Product.schema
    )
}