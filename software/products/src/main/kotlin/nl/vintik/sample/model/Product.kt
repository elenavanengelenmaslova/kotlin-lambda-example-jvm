package nl.vintik.sample.model

import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@DynamoDbBean
data class Product(
    @get:DynamoDbPartitionKey
    var id: String,
    var name: String,
    var price: Float
) {
    constructor() : this("", "", 0.0F)

    companion object {
        const val TABLE_NAME = "Products-GraalVM-Example"
        val schema = TableSchema.builder(Product::class.java)
            .newItemSupplier { Product() }
            .addAttribute(
                StaticAttribute
                    .builder(Product::class.java, String::class.java)
                    .tags(StaticAttributeTags.primaryPartitionKey())
                    .name("id")
                    .getter { it.id }
                    .setter { product: Product, id: String -> product.id = id }
                    .build())
            .addAttribute(
                StaticAttribute
                    .builder(Product::class.java, String::class.java)
                    .getter { it.name }
                    .setter { product: Product, name: String -> product.name = name }
                    .name("name")
                    .build()
            )
            .addAttribute(
                StaticAttribute
                    .builder(Product::class.java, Float::class.java)
                    .name("price")
                    .getter { it.price }
                    .setter { product: Product, price: Float -> product.price = price }
                    .build()
            )
            .build()
    }

}