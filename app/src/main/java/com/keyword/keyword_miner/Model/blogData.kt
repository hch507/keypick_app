package com.keyword.keyword_miner.Model

import java.io.Serializable

data class blogData(
    var total: String,
    var data: ArrayList<String>,
    var blogname : ArrayList<String>
):Serializable
