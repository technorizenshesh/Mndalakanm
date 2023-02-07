package com.app.mndalakanm.utils

import com.app.mndalakanm.Model.ChildsList
import com.app.mndalakanm.Model.SuccessScreenshotRes
import com.app.mndalakanm.Model.SuccessSubsRes

interface ChildClickListener {
    fun onClick(position:Int,model: ChildsList)
}
interface SubClickListener {
    fun onClick(position:Int,model: SuccessSubsRes.SubsRes)
}
interface ScreenShotClickListener {
    fun onClick(position:Int,model: SuccessScreenshotRes.ScreenshotList)
}