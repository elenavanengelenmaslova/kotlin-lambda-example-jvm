package nl.vintik.sample

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import nl.vintik.sample.model.Product
import nl.vintik.sample.util.logger
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest
import java.util.*

class ProductsService(private val productTable: DynamoDbAsyncTable<Product>) {
    suspend fun findAllProducts(): List<Product> {
        val products = Collections.synchronizedList(mutableListOf<Product>())

        logger().info("Parallel scans set to : $parallelScanTotalSegments with page size $parallelScanPageSize")

        coroutineScope {
            (0 until parallelScanTotalSegments).map { segment ->
                async(Dispatchers.IO) {
                    productTable.scan(
                        ScanEnhancedRequest.builder()
                            .segment(segment)
                            .totalSegments(parallelScanTotalSegments)
                            .limit(parallelScanPageSize)
                            .build()
                    ).items().subscribe {
                        products.add(it)
                    }.get()
                }
            }.awaitAll()
        }

        logger().info("number of Product: ${products.size}")
        return products
    }

    companion object {
        private const val parallelScanTotalSegments = 5
        private const val parallelScanPageSize = 25

    }
}