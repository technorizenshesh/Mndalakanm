package com.app.mndalakanm.utils

import com.app.mndalakanm.Model.ChildsList

interface ChildClickListener {
    fun onClick(position:Int,model: ChildsList)
}