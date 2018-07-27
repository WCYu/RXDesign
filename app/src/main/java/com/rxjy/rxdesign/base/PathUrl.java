package com.rxjy.rxdesign.base;

/**
 * Created by 阿禹 on 2018/6/25.
 */

public class PathUrl {

    private static final String DES_API_GET = "http://api.wenes.cn/";//温特斯
    private static final String DES_API_LHSUBMIT = "http://app.wenes.cn/";//温特斯量房单号提交
    public static final String PD_API_HOST = "http://na.wenes.com/API/Contract/";
    private static final String DES_API_WTS = "http://nf.wenes.cn/api/";//温特斯红包获取
    private static final String DES_API_LH = "https://api.niujingji.cn:8183/";//
    private static final String RS_API_HOST = "https://api.dcwzg.com:9191/";   //线上
    private static final String FID_API_HOST = "http://wpsnew.rxjy.com:9090/";//发现模块数据线上
    private static final String FID_HOST = "http://wpsnew.rxjy.com/";//文章
    private static final String BAPI_HOSTMONEY = "http://idc.dcwzg.com:13207/";//线上钱包

    //温特斯
    public static String ZAISHILISTURL = DES_API_GET + "UC/GetUCList";//量房再施资料cardNo
    public static String JIEDANTURL = DES_API_GET + "JD/UpdateAPPsjsJD";//温特斯获取接单  ci_ReciveStatus 1:接单  2：拒单
    public static String GETXIANGMUTYPEURL = DES_API_GET + "jd/ProjectCatalogArray";//获取项目类型（属性）
    public static String GETLFINFOURL = DES_API_GET + "jd/APPClientInfo";//获取量房的信息
    public static String SAVELFURL = DES_API_GET + "JD/UpdateAPPsjsJD";//提交信息
    public static String BCLFURL = DES_API_GET + "jd/APPClientInfoUpdate";//保存量房的信息
    public static String GETLFIMGURL = DES_API_GET + "JD/GetAllImages";//获取所有图片信息
    public static String DELETEIMGURL = DES_API_GET + "jd/DeleteJDimages";//删除图片接口
    public static String GETXMDATAURL = DES_API_GET + "jd/ProjectCatalogArray"; //获取项目类型（属性）
    public static String GELFIMGURL = DES_API_GET + "JD/GetAllImages";//获取所有图片信息
    public static String QTDATAURL = DES_API_GET + "jd/TalkingVisitingArray";//回访数据
    public static String ADDHFURL = DES_API_GET + "jd/TalkingVisitingInsert/";//提交回访
    public static String KGJDIMGURL = DES_API_GET + "UC/GetAllImages";//开工交底获取图片
    public static String XJLISTURL = DES_API_GET + "UC/GetScheduleCheckList"; //获取巡检列表

    //温特斯量房单号提交
    public static String GETJIEDANURL = DES_API_LHSUBMIT + "order/orderInfo";//温特斯获取接单详情 rwdId
    public static String ZAITANLISTURL = DES_API_LHSUBMIT + "order/desingerOrder";//量房再谈资料mobile
    public static String TIJIAODANHAOURL = DES_API_LHSUBMIT + "order/saveClientInfoAndAuxiliary";//获取温特斯单号提交
    public static String DELETEIMGWSTURL = DES_API_LHSUBMIT + "api/DWorksDetail/deleteWorksDetail";//删除图片
    public static String FADATAURL = DES_API_LHSUBMIT + "api/program/getProjectBrief";//温特斯获取方案说明
    public static String FAIMGURL = DES_API_LHSUBMIT + "api/program/getProgrammerAPP";//温特斯获取方案图片
    public static String WTSSMLOGINURL = DES_API_LHSUBMIT + "bloc/cduan/PhoneLoginController";//温特斯扫码登陆

    //温特斯量房单号获取
    public static String GETDANHAOURL = DES_API_LH + "IDC/GetOrderNumber";//获取温特斯单号
    public static String JFINFOURL = DES_API_LH + "JTRenShi/APP_RS_GetIntegralPlate";

    //温特斯红包获取  GetAPPRedPacket   RedBagCount
    public static String WTSREDLISTURL = DES_API_WTS + "LocalTask/GetAPPRedPacket";//获取红包列表

    //线上钱包
    public static String ZAJIFENURL = BAPI_HOSTMONEY + "/mainCase/payment/integral";//主案积分
    public static String MONEYURL = BAPI_HOSTMONEY + "investment/adminManager";//钱包信息行政经理
    public static String WTSMONEYURL = BAPI_HOSTMONEY + "mainCase/payment";//钱包主案
    public static String ZAURL = BAPI_HOSTMONEY + "mainCase/payment/resultAndProcess";//主案过程/结果（新）
    public static String TZFHURL = BAPI_HOSTMONEY + "investment/adminManager/dividend";//获取投资行政经理分红
    public static String TZJFURL = BAPI_HOSTMONEY + "investment/adminManager/rewardAndPunishment";//获取投资行政经理奖罚
    public static String ZAJFURL = BAPI_HOSTMONEY + "mainCase/payment/event";//主案奖罚

    //发现模块数据线上
    public static String FINDLISTURL = FID_API_HOST + "a/sap/sapArticle/getAppArticleList";//发现列表

    //文章
    public static String ISSHOWREDDOTURL = FID_HOST + "a/sap/sapArticle/isNewArticle";  //是否有新文章

    public static String GETTEXTAUTODATAURL = PD_API_HOST + "GetDesignerFloatSignList";//获取飘单数据

    //线上
    public static String SMLOGINURL = RS_API_HOST + "actionapi/AppLogin/EWMLogin";//扫码登陆
    public static String ISGENGXINURL = RS_API_HOST + "actionapi/AppCurrencyHome/IsAndroidUpdated";//判断是否有更新
    //    public static String NUMBERINFOURL = RS_API_HOST + "actionapi/AppCurrencyHome/GetCheckUserInfo_RXPT";//账号信息
    public static String NUMBERINFOURL = RS_API_HOST + "/actionapi/AppLogin/GetCheckUserInfo";//新账号信息
    //    public static String LOGINURL = RS_API_HOST + "actionapi/AppCurrencyHome/AppLogin";//登陆
    public static String LOGINURL = RS_API_HOST + "/actionapi/AppLogin/Login ";//新登陆
    public static String GETCAODEURL = RS_API_HOST + "actionapi/AppHome/GetVcodeUpdatePwd";//获取验证码
    public static String RENZHENGCODEURL = RS_API_HOST + "actionapi/AppLogin/GetInsideVcodeLanding";//新获取激活验证码
    public static String GETUSERINFOURL = RS_API_HOST + "/actionapi/AppCurrencyHome/GetAppCheckToken";//获取用户信息
    public static String MEMBERINFOURL = RS_API_HOST + "/actionapi/AN_Home/ShowMyInfo";//获取会员用户信息

    public static String BANNERLISTGURL = RS_API_HOST + "actionapi/AppPort/GetAppHomeBanner";//首页banner
    public static String NEWYUANGONGURL = RS_API_HOST + "actionapi/GroupManage/GetAppState";//新入职员工信息
    public static String ICONIMGURL = RS_API_HOST + "actionapi/AppCurrencyHome/UploadAvatar";//上传头像cardNo
    public static String USERINFOIMGURL = RS_API_HOST + "actionapi/AppCurrencyHome/entryUpdateImages";//上传个人资料图片
    public static String SETUSERINFOURL = RS_API_HOST + "actionapi/AppCurrencyHome/GetUpdateUserData";//修改个人资料/入职资料
    public static String USERINFOURL = RS_API_HOST + "actionapi/AppCurrencyHome/GetUserData";//获取个人资料
    public static String BANKLISTURL = RS_API_HOST + "actionapi/AppCurrencyHome/GetBanklist";//银行列表/actionapi/AppCurrencyHome/GetBanklist
    public static String GETIMGSURL = RS_API_HOST + "actionapi/AppPort/GetImgList";//获取图片详情
    public static final String ZHUANZHNEGURL = RS_API_HOST + "actionapi/KGManage/ZhuanzhengBanli";  //申请转正
    public static final String ZHUANZHNEDATAGURL = RS_API_HOST + "actionapi/KGManage/GetAppZhuanzheng";//获取转正状态
    public static final String WEBURL_ENTRYJOBURL = RS_API_HOST + "home/ElectronicProtocol?";//入职需知，瑞祥之歌，瑞祥准则url
    public static String BANBENNOURL = RS_API_HOST + "actionapi/AppCurrencyHome/IsAndroidUpdated";  //版本号
    public static String HUANYINGDATAURL = RS_API_HOST + "actionapi/KGManage/GetWelcomes";  //获取欢迎信息
    public static String ISAGREEURL = RS_API_HOST + "actionapi/AppCurrencyHome/GetaIsAgreeElectronicProtocol";//是否同意协议
    public static String AGREEURL = RS_API_HOST + "actionapi/AppCurrencyHome/GetaAgreeElectronicProtocol";//同意协议
    public static String SETPSWURL = RS_API_HOST + "actionapi/AppHome/UpdatePassword_Vcode";//修改密码
    public static String TPKENURL = RS_API_HOST + "actionapi/AppCurrencyHome/GetAppCheckToken";// 检查Token
    public static String USERDATAURL = RS_API_HOST + "actionapi/AppCurrencyHome/GetUserData";// 用户信息
    public static String GETXINGXIANGURL = RS_API_HOST + "actionApi/TZManage/GetAreasListToTZ";//获取形象/环境主页
    public static String CHUFALISTRL = RS_API_HOST + "actionApi/TZManage/GetUserListToTZ";//投资获取处罚人员列表
    public static String GETXINGXIANIMGGURL = RS_API_HOST + "actionApi/TZManage/GetAreasStandardToTZ";//获取环境标准图片
    public static String USERTYPEURL = RS_API_HOST + "actionApi/TZManage/GetIsManageUser";//获取是否是管理层
    public static String GOMEDATAURL = RS_API_HOST + "actionapi/KGManage/GetNewsList";//获取首页信息
    public static String HOMEBANNERURL = RS_API_HOST + "actionapi/AppPort/GetAppHomeBanner";//获取首页Banner
    public static String ZHAOSHANGUSERURL = RS_API_HOST + "actionapi/AN_Home/ShowMyInfo";//获取招商用户信息
    public static String SETZHAOSHANGUSERURL = RS_API_HOST + "actionapi/AN_Home/UpdateMyInfo";//设置招商用户信息
    public static String IMGURL = RS_API_HOST + "actionapi/AN_Home/UpdateImages";//图片上传
    public static String QINGJIALISTURL = RS_API_HOST + "ActionApi/TZManage/GetLeaveList";//请假列表

    public static String MESSAGENUMURL = RS_API_HOST + "actionapi/AppCurrencyHome/GetAppNoticeNoReadCount";//获取消息数量
    public static String WBMESSAGENUMURL = RS_API_HOST + "actionapi/JiGuang/GetAppNoticeNoReadCount";//获取外部消息数量
    public static String MESSAGELISTURL = RS_API_HOST + "actionapi/AppCurrencyHome/GetAppNoticeGroup";//消息列表
    public static String WBMESSAGELISTURL = RS_API_HOST + "actionapi/JiGuang/GetAppNoticeGroup";//外部消息列表
    public static String REDTASKURL = RS_API_HOST + "actionapi/AppCurrencyHome/GetAppNoticeList";//红包任务列表
    public static String WBREDTASKURL = RS_API_HOST + "actionapi/JiGuang/GetAppNoticeList";//外部红包任务列表
    public static String TONGZHIINFOURL = RS_API_HOST + "actionapi/AppCurrencyHome/GetAppNoticeDetail";//获取通知详情
    public static String WBTONGZHIINFOURL = RS_API_HOST + "/actionapi/JiGuang/GetAppNoticeDetail";//获取外部通知详情

}
