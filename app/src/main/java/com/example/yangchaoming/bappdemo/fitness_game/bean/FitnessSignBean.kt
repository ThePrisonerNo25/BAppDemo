package com.example.yangchaoming.bappdemo.fitness_game.bean

data class FitnessSignBean(
        var areaId : String?,
        var cityName : String?,
        var coachId : String?,
        var endTime : String?,
        var enterId : String?,
        var memberId : String?,
        var payAmt : String?,
        var promotionId : String?,
        var promotionName : String?,
        var provinceName : String?,
        var startTime : String?,
        var status : Int?
)

//areaId (string, optional): 赛区id ,
//cityName (string, optional): 城市名称 ,
//coachId (string, optional): 教练id ,
//endTime (string, optional): 赛事结束时间 ,
//enterId (string, optional): 赛事报名订单id ,
//memberId (string, optional): 用户id ,
//payAmt (string, optional): 报名费用 ,
//promotionId (string, optional): 赛事id ,
//promotionName (string, optional): 赛事名称 ,
//provinceName (string, optional): 赛区名 ,
//startTime (string, optional): 赛事开始时间 ,
//status (integer, optional): 赛事状态： 0 待生效（未支付） 1 生效（等待 教练提交资质审核资料） 2 退出比赛 -- 用户 自己操作退出。 用户退出之后 就可以报名其他的赛区了 3 取消（如：用户取消--未支付的情况） 100 组队成功！ 等待赛事开始。 101 已提交教练资料，审核中 102 资质审核不通过 201 等待组队 教练资料审核通过 还没邀约 任何人 202 组队中。。 203 组队失败 学员拒绝。 可以重新 再次发起邀约。状态变成 201 204 取消邀请
//701 退款 这种情况就是属于 赛事取消 702 退款中