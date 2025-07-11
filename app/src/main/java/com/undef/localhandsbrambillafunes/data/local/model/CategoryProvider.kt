package com.undef.localhandsbrambillafunes.data.local.model

// Objeto que proporciona una lista de categorias de ejemplo (para la simulacion y pruebas)
object CategoryProvider {
    // Lista de diferentes categorías de emprendedores locales
    val categories = listOf(
        Category(
            id = 1,
            name = "Alimentos",
            description = "Diversidad de alimentos",
            images = listOf("https://cdn.pixabay.com/photo/2022/07/26/13/55/egg-7345934_1280.jpg")
        ),
        Category(
            id = 2,
            name = "Textiles",
            description = "Gran variedad de textiles",
            images = listOf("https://cdn.pixabay.com/photo/2012/11/11/18/23/fabric-65741_1280.jpg")
        ),
        Category(
            id = 3,
            name = "Artesanías",
            description = "Productos artesanos",
            images = listOf("https://cdn.pixabay.com/photo/2016/03/27/07/07/wood-1282229_1280.jpg")
        ),
        Category(
            id = 4,
            name = "Cosmética",
            description = "Productos de maquillaje",
            images = listOf("https://cdn.pixabay.com/photo/2021/12/01/08/45/beauty-6837031_1280.jpg")
        ),
    )
}
