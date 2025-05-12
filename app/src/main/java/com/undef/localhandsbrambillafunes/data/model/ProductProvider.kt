package com.undef.localhandsbrambillafunes.data.model

import com.undef.localhandsbrambillafunes.R


// Objeto que proporciona una lista de productos de ejemplo (para la simulacion y pruebas)
object ProductProvider {
    // Lista de productos de diferentes categorías de emprendedores locales
    val products = listOf(
        Product(
            id = "1",
            name = "Mermeladas Artesanales",
            description = "Mermeladas caseras elaboradas con frutas de la región, sin conservantes ni azúcares añadidos.",
            producer = "La Huerta de María",
            category = "Alimentos",
            imageId = R.drawable.mermeladas_artesanales
        ),
        Product(
            id = "2",
            name = "Tejidos de Lana",
            description = "Bufandas, gorros y mantas tejidos a mano con lana de oveja de crianza local.",
            producer = "Tejedoras del Valle",
            category = "Textiles",
            imageId = R.drawable.tejidos_de_lana
        ),
        Product(
            id = "3",
            name = "Cosmética Natural",
            description = "Cremas y jabones 100% naturales elaborados con ingredientes locales y técnicas tradicionales.",
            producer = "Raíces Orgánicas",
            category = "Cosmética",
            imageId = R.drawable.cosmetica_natural
        ),
        Product(
            id = "4",
            name = "Cerveza Artesanal",
            description = "Cervezas elaboradas con recetas tradicionales y ingredientes locales de la región.",
            producer = "Cervecería del Monte",
            category = "Alimentos",
            imageId = R.drawable.cerveza_artesanal
        ),
        Product(
            id = "5",
            name = "Cerámica Decorativa",
            description = "Piezas de cerámica hechas a mano, cada una con un diseño único inspirado en la cultura local.",
            producer = "Barro y Alma",
            category = "Artesanías",
            imageId = R.drawable.ceramica_decorativa
        ),
        Product(
            id = "6",
            name = "Quesos Artesanales",
            description = "Quesos elaborados con leche de cabras y vacas criadas en establecimientos locales.",
            producer = "Quesería San Juan",
            category = "Alimentos",
            imageId = R.drawable.quesos_artesanales
        ),
        Product(
            id = "7",
            name = "Muebles Rústicos",
            description = "Muebles elaborados con madera local, diseñados con técnicas tradicionales de carpintería.",
            producer = "Maderas del Sur",
            category = "Decoración",
            imageId = R.drawable.muebles_rusticos
        ),
        Product(
            id = "8",
            name = "Velas Aromáticas",
            description = "Velas artesanales con fragancias locales, elaboradas con cera de abeja natural.",
            producer = "Esencias del Jardín",
            category = "Decoración",
            imageId = R.drawable.velas_aromaticas
        ),
        Product(
            id = "9",
            name = "Aceites Esenciales",
            description = "Aceites esenciales 100% puros, extraídos de plantas locales mediante métodos tradicionales.",
            producer = "Aromas Naturales",
            category = "Cosmética",
            imageId = R.drawable.aceites_esenciales
        ),
        Product(
            id = "10",
            name = "Textiles Indígenas",
            description = "Ponchos y mantas tradicionales tejidos por comunidades indígenas locales.",
            producer = "Artesanos del Pueblo",
            category = "Textiles",
            imageId = R.drawable.textiles_indigenas
        )
    )

    // Función para obtener productos por categoría
    fun getProductsByCategory(category: String): List<Product> {
        return products.filter { it.category == category }
    }

    // Función para obtener un producto por su ID
    fun getProductById(id: String): Product? {
        return products.find { it.id == id }
    }
}