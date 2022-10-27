package nl.vintik.sample

import kotlinx.coroutines.runBlocking
import nl.vintik.sample.model.Product
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import java.math.BigDecimal
import java.math.RoundingMode
import java.net.URI
import java.security.SecureRandom
import java.util.*
import java.util.concurrent.CompletableFuture
import kotlin.random.Random

@Testcontainers
internal class ProductsServiceTest {

    @Test
    fun `should get 150 products from parallel scan`() = runBlocking {
        val result = ProductsService(productTable).findAllProducts()
        assertEquals(150, result.size)
    }

    companion object {
        private const val STRING_LENGTH = 10
        private val charPool: List<Char> = ('a'..'z') + ('A'..'Z')

        @Container
        var container: GenericContainer<*> = GenericContainer(
            DockerImageName.parse("amazon/dynamodb-local")
        ).withExposedPorts(8000)

        lateinit var productTable: DynamoDbAsyncTable<Product>

        private lateinit var dynamoDbEnhancedAsyncClient: DynamoDbEnhancedAsyncClient

        @BeforeAll
        @JvmStatic
        fun setupBeforeAll() {
            container.start()
            dynamoDbEnhancedAsyncClient = DynamoDbEnhancedAsyncClient.builder().dynamoDbClient(
                DynamoDbAsyncClient.builder()
                    .region(Region.EU_WEST_1)
                    .endpointOverride(URI.create("http://${container.host}:${container.firstMappedPort}"))
                    .credentialsProvider(
                        StaticCredentialsProvider.create(
                            AwsBasicCredentials.create("FAKE", "FAKE")
                        )
                    )
                    .build()
            ).build()
            setUpProducts(150)
        }

        @AfterAll
        @JvmStatic
        fun cleanUp() {
            productTable.deleteTable().join()
        }

        private fun setUpProducts(numVanProducts: Int) {
            productTable = dynamoDbEnhancedAsyncClient.table(Product.TABLE_NAME, Product.schema)
            productTable.createTable().join()

            val futures = (0 until numVanProducts).map {
                val product = Product(
                    UUID.randomUUID().toString(),
                    generateRandomName(),
                    generateRandomPrice()
                )
                productTable.putItem(product)

            }
            CompletableFuture.allOf(*futures.toTypedArray()).join()
        }

        private fun generateRandomPrice(): Float {
            val randomPrice = (0..10).random() + Random.nextDouble()
            return BigDecimal(randomPrice).setScale(2, RoundingMode.FLOOR).toFloat()
        }

        private fun generateRandomName(): String {
            val random = SecureRandom()
            val bytes = ByteArray(STRING_LENGTH)
            random.nextBytes(bytes)

            val randomString = (bytes.indices)
                .map {
                    charPool[random.nextInt(charPool.size)]
                }.joinToString("")

            return randomString
        }
    }
}