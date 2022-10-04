package nl.vintik.sample

import kotlinx.coroutines.runBlocking
import nl.vintik.sample.model.Product

class ProductsController(
    private val productsService: ProductsService
) {
    fun execute(): List<Product> = runBlocking {
        productsService.findAllProducts()
    }
}