package app.komatatsu.todo

import com.google.gson.annotations.SerializedName

data class ToDo(
        @SerializedName("id")
        var id: Long,
        @SerializedName("title")
        var title: String,
        @SerializedName("check")
        var check: Boolean
)
