package com.vavada.aso26.models

data class Config(val PrivacyPolicy:String, val Appsflyer:String, val Countries:String, val Onesignal:String, val FullAccess:Boolean,val Localization:Localization, val Support:String)
data class Localization(val AuthGetAccess:String, val ChangeNumberMessage:String, val ErrorPhone:String, val NextActionNo:String, val AuthPolicy:ColoredString, val ChangeNumberTitle:String, val ChangeNumberNo:String, val ErrorServer:String,val AuthSubtitle:ColoredString, val AuthEnterAsGuest:String, val NextActionTitle:String,val Support:String, val ErrorCode:String, val ChangeNumberYes:String, val AuthPolicyAcceptance:String, val NextActionMessage:String,val AuthTitle:ColoredString, val NextActionYes:String )
data class Game(val Spin:String,val Balance:String, val Win:String, val Authorize:String, val Welcome:String, val AvailableAfterAuth:String, val MinRate:String, val YourLevel:String)

