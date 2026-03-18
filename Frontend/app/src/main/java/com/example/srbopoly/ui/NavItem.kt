package com.example.srbopoly.ui

import com.example.srbopoly.R

sealed class NavItem(val route: String, val label: String, val icon: Int)
{
    object Rankings : NavItem("rankings", "Rankings",R.drawable.rankings_icon)
    object Home : NavItem("home", "Home", R.drawable.home_icon)
    object GameList: NavItem("list","Started Games", R.drawable.list_icon)
}