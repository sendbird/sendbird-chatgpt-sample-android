package com.sendbird.chatgpt.sample.main

import android.os.Bundle
import android.view.View
import com.sendbird.android.channel.GroupChannel
import com.sendbird.uikit.fragments.ChannelSettingsFragment
import com.sendbird.uikit.modules.ChannelSettingsModule
import com.sendbird.uikit.modules.components.ChannelSettingsMenuComponent

class CustomChannelSettingsFragment : ChannelSettingsFragment() {
    override fun onCreateModule(args: Bundle): ChannelSettingsModule {
        val module = super.onCreateModule(args)
        module.setChannelSettingsMenuComponent(CustomChannelSettingsMenuComponent())
        return module
    }

    class CustomChannelSettingsMenuComponent : ChannelSettingsMenuComponent() {
        override fun notifyChannelChanged(channel: GroupChannel) {
            super.notifyChannelChanged(channel)
            val moderationsItemView = rootView?.findViewById<View>(com.sendbird.uikit.R.id.moderations)
            moderationsItemView?.visibility = View.GONE
        }
    }
}
