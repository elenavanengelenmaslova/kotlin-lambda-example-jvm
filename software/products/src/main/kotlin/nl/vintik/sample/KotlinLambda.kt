package nl.vintik.sample

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import nl.vintik.sample.model.Product

class KotlinLambda : RequestHandler<Map<String, String>, List<Product>> {
    override fun handleRequest(event: Map<String, String>, context: Context): List<Product> {
        return ProductsController(ProductsService()).execute()
    }
}