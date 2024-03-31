package com.example.fitnesapp

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.showToast(text:String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

//fun Activity.openActivity(targetActivity: Class<*>) {
//    val intent = Intent(this, targetActivity)
//    startActivity(intent)
//}
fun Activity.openActivity(targetActivity: Class<*>) {
    val intent = Intent(this, targetActivity)
    startActivity(intent)
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
}


//fun AppCompatActivity.openFragment(newFragment: Fragment) {
//    supportFragmentManager
//        .beginTransaction()
//        .replace(R.id.placeHolder, newFragment)
//        .commit()
//}
fun AppCompatActivity.openFragment(newFragment: Fragment) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
    transaction.replace(R.id.placeHolder, newFragment)
    //transaction.addToBackStack(null) // Добавляем транзакцию в стек возврата
    transaction.commit()
}

var currentFragment:Fragment?= null

//fun Fragment.openFragment(newFragment: Fragment) {
//    val transaction= (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
//    transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
//    transaction.commit()
//    currentFragment= newFragment
//    (activity as AppCompatActivity).supportFragmentManager
//        .beginTransaction()
//        .replace(R.id.placeHolder, newFragment)
//        .commit()
//    currentFragment= newFragment
//
//}
fun Fragment.openFragment(newFragment: Fragment) {
    val transaction = (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
    transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
    transaction.replace(R.id.placeHolder, newFragment)
  //  transaction.addToBackStack(null) // Добавляем транзакцию в стек возврата
    transaction.commit()
}

