package com.ctrlz.yesterdays_weather.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.gun0912.tedpermission.coroutine.TedPermission

suspend fun checkPermissions(
    context: Context
    // vararg permissions: String (쓸 일이 생기면)
): Boolean {
    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        return true
    val permissionResult = getPermissionResult()
    return permissionResult.isGranted
}

private suspend fun getPermissionResult() = TedPermission.create()
    .setRationaleTitle("위치 권한")
    .setRationaleMessage("현재 위치의 날씨를 불러오려면 위치 권한을 허용해주세요.")
    .setDeniedTitle("권한 거부됨")
    .setDeniedMessage("권한을 거부하면 서비스를 정상적으로 이용할 수 없습니다.\n\n[설정] > [권한]에서 설정할 수 있습니다.")
    .setGotoSettingButtonText("설정")
    .setPermissions(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    .check()