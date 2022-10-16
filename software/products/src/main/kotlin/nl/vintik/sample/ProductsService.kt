package nl.vintik.sample

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import nl.vintik.sample.util.logger
import nl.vintik.sample.model.Product
import nl.vintik.sample.model.Product.Companion.schema
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import java.util.*

class ProductsService {
    suspend fun findAllProducts(): List<Product> {
        val products = Collections.synchronizedList(mutableListOf<Product>())
        val jobs = mutableListOf<Deferred<Void>>()

        logger().info("Parallel scans set to : $parallelScanTotalSegments with page size $parallelScanPageSize")

        coroutineScope {
            for (segment in 0 until parallelScanTotalSegments) {
                jobs.add(async(Dispatchers.IO) {
                    productTable.scan(
                        ScanEnhancedRequest.builder()
                            .segment(segment)
                            .totalSegments(parallelScanTotalSegments)
                            .limit(parallelScanPageSize)
                            .build()
                    ).items().subscribe {
                        products.add(it)
                    }.get()
                })
            }
        }
        jobs.awaitAll()

        logger().info("number of Product: ${products.size}")
        return products
    }

    companion object {
        private val dynamoDbAsyncClient = DynamoDbEnhancedAsyncClient.builder()
            .dynamoDbClient(
                DynamoDbAsyncClient.builder()
                    .region(Region.EU_WEST_1)
                    .build()
            )
            .build()

        private val productTable = dynamoDbAsyncClient.table(
            Product.TABLE_NAME,
            schema
        )

        private const val parallelScanTotalSegments = 5
        private const val parallelScanPageSize = 25

    }
}