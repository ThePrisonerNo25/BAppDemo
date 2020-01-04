package com.example.yangchaoming.bappdemo.fitness_game.bean

import android.os.Parcel
import android.os.Parcelable

data class MomentSecondaryCommentBean(
        var canDel: Boolean?,
        var content: String?,
        var name: String?,
        var replyToName: String
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readValue(Boolean::class.java.classLoader) as Boolean?,
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(canDel)
        writeString(content)
        writeString(name)
        writeString(replyToName)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MomentSecondaryCommentBean> = object : Parcelable.Creator<MomentSecondaryCommentBean> {
            override fun createFromParcel(source: Parcel): MomentSecondaryCommentBean = MomentSecondaryCommentBean(source)
            override fun newArray(size: Int): Array<MomentSecondaryCommentBean?> = arrayOfNulls(size)
        }
    }
}

//赛事动态2级评论/回复列表item {
//    canDel (boolean, optional): 当前回复item能否删除 (前端删除按钮显示控制) ,
//    content (string, optional): 评论/回复内容 ,
//    name (string, optional): 评论人姓名 ,
//    replyToName (string, optional): 被回复人姓名
//}

