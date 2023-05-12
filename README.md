# Sendbird ChatGPT Sample for Android
This is an example of [Sendbird ChatGPT](https://sendbird.com/docs/chat/v3/platform-api/bot/bot-overview#1-overview) for Android, implemented using [Sendbird UIKit](https://sendbird.com/docs/uikit/v3/android/overview).
<p>
Sendbird ChatGPT: Sendbird ChatGPT is a Sendbird Chat integration into your existing ChatGPT bot can provide customers with a more engaging and personalized conversation experience.
</p>

<p align="center">
  <img
    src="./channel_list.png"
    width="250"
  />
  <img
    src="./channel.png"
    width="250"
  />
  <img
    src="./channel_info.png"
    width="250"
  />
</p>


## Getting Started
1. Create your Sendbird application on [the dashboard](https://dashboard.sendbird.com/auth/signup).
2. [Register the ChatGPT bot](https://sendbird.com/developer/tutorials/chatbot-google-dialogflow) in your Sendbird application.
3. In this example, we're connecting to a test purpose Sendbird application and pre-defined bots. To connect yours, replace `APP_ID`, `ChatGPTInfo` and others in the `BaseApplication.kt` file as follows:

```kotlin
class BaseApplication : Application() {
    companion object {
        // TODO Replace with your own APP_ID
        const val APP_ID = "13B6D179-33A5-4C0D-9162-E11DAC9358FC"

        // TODO Replace with your own USER_ID
        const val USER_ID = "sendbird"

        // TODO Replace with your own NICKNAME
        const val NICKNAME = "sendbird"

        // TODO Replace with your own PROFILE_URL
        const val PROFILE_URL = ""

        // ...
    }

    // ...
}

object ChatGPTInfo {
    // TODO Replace with your own user ids of bots
    val chatGPTIds = listOf(
        "gpt_bot"
    )
}
```
4. Run `app`

## How to connect a channel to a bot
This sample supports chat with 3 different bots. They are trained with 3 different characteristics.
Currently, Only 1:1 chat with ChatGPT bot is supported. When you create a channel, you just need to enter the user ID of the bot you want to chat with.

```kotlin
fun startChatGPTBot() {
    val params = GroupChannelCreateParams()
    params.operatorUserIds = listOf(SendbirdUIKit.getAdapter().userInfo.userId)
    params.userIds = listOf(ChatGPTInfo.chatGPTIds.random())
    GroupChannel.createChannel(params, object : GroupChannelCallbackHandler {
        override fun onResult(channel: GroupChannel?, e: SendbirdException?) {
            if (e != null || channel == null) {
                return
            }
            startActivity(ChannelActivity.newIntent(requireContext(), channel.url))
        }
    })
}
```

For more information, see [our documentation](https://sendbird.com/docs/chat/v3/platform-api/bot/bot-overview#1-overview).
