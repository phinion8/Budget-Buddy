package com.phinion.studentexpensetracker.ui.screens.components.bottombar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.phinion.studentexpensetracker.ui.screens.components.models.BottomNavItem
import com.phinion.studentexpensetracker.ui.theme.bottomBackgroundColor
import com.phinion.studentexpensetracker.ui.theme.bottomBarContentColor

@Composable
fun BottomNavBar(
    bottomNavItems: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {

    val currentBackStackEntry = navController.currentBackStackEntryAsState()

    BottomNavigation(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = bottomBackgroundColor
    ) {


        bottomNavItems.forEach { item ->
            val selected = item.route == currentBackStackEntry.value?.destination?.route

            BottomNavigationItem(selected = selected, onClick = { onItemClick(item) },
                selectedContentColor = bottomBarContentColor,
                unselectedContentColor = Color.Gray,
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Icon(painter = item.icon, contentDescription = item.title)
                        AnimatedVisibility(visible = selected) {
                            Text(
                                text = item.title,
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp
                            )
                        }

                    }
                }
            )


        }


    }

}