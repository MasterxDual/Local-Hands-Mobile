// ProductProvider.kt
package com.undef.localhandsbrambillafunes.data.local.model

import com.undef.localhandsbrambillafunes.data.local.entities.Product

//import com.undef.localhandsbrambillafunes.R

// Objeto que proporciona una lista de productos de ejemplo (para la simulacion y pruebas)
object ProductProvider {
    // Lista de productos de diferentes categorías de emprendedores locales
    val products = listOf(
        Product(
            id = 1,
            name = "Mermeladas Artesanales",
            description = "Mermeladas caseras elaboradas con frutas de la región, sin conservantes ni azúcares añadidos.",
            producer = "La Huerta de María",
            category = "Alimentos",
            images = listOf("https://cdn.pixabay.com/photo/2015/10/20/10/25/jams-997593_1280.jpg"),
            price = 450.0,
            location = "Villa General Belgrano",
            ownerId = null
        ),
        Product(
            id = 2,
            name = "Tejidos de Lana",
            description = "Bufandas, gorros y mantas tejidos a mano con lana de oveja de crianza local.",
            producer = "Tejedoras del Valle",
            category = "Textiles",
            images = listOf("https://cdn.pixabay.com/photo/2016/10/03/19/58/woolen-hat-1712687_960_720.jpg"),
            price = 3200.0,
            location = "La Cumbre",
            ownerId = null
        ),
        Product(
            id = 3,
            name = "Cosmética Natural",
            description = "Cremas y jabones 100% naturales elaborados con ingredientes locales y técnicas tradicionales.",
            producer = "Raíces Orgánicas",
            category = "Cosmética",
            images = listOf("https://cdn.pixabay.com/photo/2020/02/08/10/35/soap-4829708_1280.jpg"),
            price = 890.0,
            location = "Alta Gracia",
            ownerId = null
        ),
        Product(
            id = 4,
            name = "Cerveza Artesanal",
            description = "Cervezas elaboradas con recetas tradicionales y ingredientes locales de la región.",
            producer = "Cervecería del Monte",
            category = "Alimentos",
            images = listOf(
                "https://cdn.pixabay.com/photo/2019/04/10/08/02/craft-beer-4116364_960_720.jpg",
                "https://cdn.pixabay.com/photo/2017/06/24/23/41/beer-2439237_1280.jpg"
            ),
            price = 650.0,
            location = "Villa Carlos Paz",
            ownerId = null
        ),
        Product(
            id = 5,
            name = "Cerámica Decorativa",
            description = "Piezas de cerámica hechas a mano, cada una con un diseño único inspirado en la cultura local.",
            producer = "Barro y Alma",
            category = "Artesanías",
            images = listOf("https://cdn.pixabay.com/photo/2015/01/08/12/20/flowers-592829_960_720.jpg"),
            price = 2500.0,
            location = "Mina Clavero",
            ownerId = null
        ),
        Product(
            id = 6,
            name = "Quesos Artesanales",
            description = "Quesos elaborados con leche de cabras y vacas criadas en establecimientos locales.",
            producer = "Quesería San Juan",
            category = "Alimentos",
            images = listOf("https://cdn.pixabay.com/photo/2016/06/03/14/33/cheeses-1433514_1280.jpg"),
            price = 1200.0,
            location = "San Marcos Sierras",
            ownerId = null
        ),
        Product(
            id = 7,
            name = "Muebles Rústicos",
            description = "Muebles elaborados con madera local, diseñados con técnicas tradicionales de carpintería.",
            producer = "Maderas del Sur",
            category = "Decoración",
            images = listOf(
                "https://cdn.pixabay.com/photo/2016/11/22/19/11/brick-wall-1850095_960_720.jpg",
                "https://cdn.pixabay.com/photo/2017/09/09/18/25/living-room-2732939_1280.jpg"
            ),
            price = 18900.0,
            location = "Cosquín",
            ownerId = null
        ),
        Product(
            id = 8,
            name = "Velas Aromáticas",
            description = "Velas artesanales con fragancias locales, elaboradas con cera de abeja natural.",
            producer = "Esencias del Jardín",
            category = "Decoración",
            images = listOf("https://cdn.pixabay.com/photo/2014/10/04/02/35/candles-472379_960_720.jpg"),
            price = 750.0,
            location = "Capilla del Monte",
            ownerId = null
        ),
        Product(
            id = 9,
            name = "Aceites Esenciales",
            description = "Aceites esenciales 100% puros, extraídos de plantas locales mediante métodos tradicionales.",
            producer = "Aromas Naturales",
            category = "Cosmética",
            images = listOf("https://cdn.pixabay.com/photo/2017/06/08/22/17/essential-oils-2385087_960_720.jpg"),
            price = 1500.0,
            location = "Jesús María",
            ownerId = null
        ),
        Product(
            id = 10,
            name = "Textiles Indígenas",
            description = "Ponchos y mantas tradicionales tejidos por comunidades indígenas locales.",
            producer = "Artesanos del Pueblo",
            category = "Textiles",
            images = listOf("https://cdn.pixabay.com/photo/2021/07/02/03/18/culture-6380757_1280.jpg"),
            price = 5800.0,
            location = "Cruz del Eje",
            ownerId = null
        )
    )
}