package com.ctrlz.yesterdays_weather.util

import com.gun0912.tedpermission.coroutine.TedPermission

suspend fun requestPermissions(vararg permissions: String): Boolean {
    val permissionResult = TedPermission.create()
        .setRationaleTitle("위치 권한")
        .setRationaleMessage("현재 위치의 날씨를 불러오려면 위치 권한을 허용해주세요.")
        .setDeniedTitle("권한 거부됨")
        .setDeniedMessage("권한을 거부하면 서비스를 정상적으로 이용할 수 없습니다.\n\n[설정] > [권한]에서 설정할 수 있습니다.")
        .setGotoSettingButtonText("설정")
        .setPermissions(*permissions)
        .check()
    return permissionResult.isGranted
}