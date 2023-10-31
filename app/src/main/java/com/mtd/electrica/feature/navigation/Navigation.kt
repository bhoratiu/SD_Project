package com.mtd.electrica.feature.navigation

import AdminScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mtd.electrica.feature.admin.ui.ManageUsersScreen
import com.mtd.electrica.feature.signin.ui.SigninScreen
import com.mtd.electrica.feature.user.ui.UserScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "login") {
        composable("login") { SigninScreen(navController = navController) }
        composable("admin") { AdminScreen(navController = navController) }
        composable("user") { UserScreen(navController = navController) }
        composable("admin_manage_users") { ManageUsersScreen(navController = navController) }
    }
}