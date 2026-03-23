package com.example.srbopoly.ui

import com.example.srbopoly.R

sealed class NavItem(val route: String, val label: String, val icon: Int)
{
    object Rankings : NavItem("rankings", "Rangiranje",R.drawable.rankings_icon)
    object Home : NavItem("home", "Početna", R.drawable.home_icon)
    object GameList: NavItem("list","Započete igre", R.drawable.list_icon)
}