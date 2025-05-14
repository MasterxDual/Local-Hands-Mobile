package com.undef.localhandsbrambillafunes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.undef.localhandsbrambillafunes.data.model.Category
import com.undef.localhandsbrambillafunes.ui.navigation.AppScreens

/**
 * Un elemento de lista de categorias optimizado que usa AsyncImage para carga diferida
 * y reduce las recomposiciones innecesarias
 */
@Composable
fun CategoryListItem(category: Category, navController: NavController, modifier: Modifier) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable() {
                navController.navigate(route = AppScreens.ProductDetailScreen.createRoute(category.id))
            },
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            CategoryImage(category = category)
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(text = category.name, style = typography.bodyLarge)
            }

        }
    }
}

@Composable
fun CategoryImage(category: Category) {
    AsyncImage(
        model = category.images[0],
        contentDescription = category.name,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(80.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}
