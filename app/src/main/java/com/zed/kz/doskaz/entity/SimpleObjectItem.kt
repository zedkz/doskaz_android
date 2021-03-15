package com.zed.kz.doskaz.entity

data class SimpleObjectItem(
    var addObjectList : List<AddObject> = listOf(),
    var type: String = "",
    var availabilityZone: AvailabilityZone? = null,
    var comment: String? = null,
    var recyclerHeight: Int = 0
)