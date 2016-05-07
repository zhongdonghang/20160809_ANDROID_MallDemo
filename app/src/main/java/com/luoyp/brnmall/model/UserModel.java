package com.luoyp.brnmall.model;

/**
 * Created by MnZi on 2016/4/28.
 */
public class UserModel {

    /**
     * LastVisitTime : /Date(1461806226000+0800)/
     * LastVisitIP :
     * LastVisitRgId : 0
     * RegisterTime : /Date(1461749366000+0800)/
     * RegisterIP :
     * RegisterRgId : 0
     * Gender : 0
     * RealName :
     * Bday : /Date(-2209017600000+0800)/
     * IdCard :
     * RegionId : 0
     * Address :
     * Bio :
     * Uid : 29
     * UserName : 18377102233
     * Email :
     * Mobile : 18377102233
     * Password : 90b8fa4f135162de0d6a83eb97959eff
     * UserRid : 7
     * StoreId : 0
     * MallAGid : 1
     * NickName : jsy3721567
     * Avatar :
     * PayCredits : 2
     * RankCredits : 2
     * VerifyEmail : 0
     * VerifyMobile : 0
     * LiftBanTime : /Date(-2209017600000+0800)/
     * Salt : 793997
     */

    private UserInfoBean UserInfo;
    /**
     * UserRid : 7
     * System : 0
     * Title : 铜牌会员
     * Avatar : ura_1409022108402328812.jpg
     * CreditsUpper : 180
     * CreditsLower : 0
     * LimitDays : 0
     */

    private UserRankInfoBean UserRankInfo;

    public UserInfoBean getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(UserInfoBean UserInfo) {
        this.UserInfo = UserInfo;
    }

    public UserRankInfoBean getUserRankInfo() {
        return UserRankInfo;
    }

    public void setUserRankInfo(UserRankInfoBean UserRankInfo) {
        this.UserRankInfo = UserRankInfo;
    }

    public static class UserInfoBean {
        private String LastVisitTime;
        private String LastVisitIP;
        private int LastVisitRgId;
        private String RegisterTime;
        private String RegisterIP;
        private int RegisterRgId;
        private int Gender;
        private String RealName;
        private String Bday;
        private String IdCard;
        private int RegionId;
        private String Address;
        private String Bio;
        private int Uid;
        private String UserName;
        private String Email;
        private String Mobile;
        private String Password;
        private int UserRid;
        private int StoreId;
        private int MallAGid;
        private String NickName;
        private String Avatar;
        private int PayCredits;
        private int RankCredits;
        private int VerifyEmail;
        private int VerifyMobile;
        private String LiftBanTime;
        private String Salt;

        public String getLastVisitTime() {
            return LastVisitTime;
        }

        public void setLastVisitTime(String LastVisitTime) {
            this.LastVisitTime = LastVisitTime;
        }

        public String getLastVisitIP() {
            return LastVisitIP;
        }

        public void setLastVisitIP(String LastVisitIP) {
            this.LastVisitIP = LastVisitIP;
        }

        public int getLastVisitRgId() {
            return LastVisitRgId;
        }

        public void setLastVisitRgId(int LastVisitRgId) {
            this.LastVisitRgId = LastVisitRgId;
        }

        public String getRegisterTime() {
            return RegisterTime;
        }

        public void setRegisterTime(String RegisterTime) {
            this.RegisterTime = RegisterTime;
        }

        public String getRegisterIP() {
            return RegisterIP;
        }

        public void setRegisterIP(String RegisterIP) {
            this.RegisterIP = RegisterIP;
        }

        public int getRegisterRgId() {
            return RegisterRgId;
        }

        public void setRegisterRgId(int RegisterRgId) {
            this.RegisterRgId = RegisterRgId;
        }

        public int getGender() {
            return Gender;
        }

        public void setGender(int Gender) {
            this.Gender = Gender;
        }

        public String getRealName() {
            return RealName;
        }

        public void setRealName(String RealName) {
            this.RealName = RealName;
        }

        public String getBday() {
            return Bday;
        }

        public void setBday(String Bday) {
            this.Bday = Bday;
        }

        public String getIdCard() {
            return IdCard;
        }

        public void setIdCard(String IdCard) {
            this.IdCard = IdCard;
        }

        public int getRegionId() {
            return RegionId;
        }

        public void setRegionId(int RegionId) {
            this.RegionId = RegionId;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getBio() {
            return Bio;
        }

        public void setBio(String Bio) {
            this.Bio = Bio;
        }

        public int getUid() {
            return Uid;
        }

        public void setUid(int Uid) {
            this.Uid = Uid;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String Password) {
            this.Password = Password;
        }

        public int getUserRid() {
            return UserRid;
        }

        public void setUserRid(int UserRid) {
            this.UserRid = UserRid;
        }

        public int getStoreId() {
            return StoreId;
        }

        public void setStoreId(int StoreId) {
            this.StoreId = StoreId;
        }

        public int getMallAGid() {
            return MallAGid;
        }

        public void setMallAGid(int MallAGid) {
            this.MallAGid = MallAGid;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String NickName) {
            this.NickName = NickName;
        }

        public String getAvatar() {
            return Avatar;
        }

        public void setAvatar(String Avatar) {
            this.Avatar = Avatar;
        }

        public int getPayCredits() {
            return PayCredits;
        }

        public void setPayCredits(int PayCredits) {
            this.PayCredits = PayCredits;
        }

        public int getRankCredits() {
            return RankCredits;
        }

        public void setRankCredits(int RankCredits) {
            this.RankCredits = RankCredits;
        }

        public int getVerifyEmail() {
            return VerifyEmail;
        }

        public void setVerifyEmail(int VerifyEmail) {
            this.VerifyEmail = VerifyEmail;
        }

        public int getVerifyMobile() {
            return VerifyMobile;
        }

        public void setVerifyMobile(int VerifyMobile) {
            this.VerifyMobile = VerifyMobile;
        }

        public String getLiftBanTime() {
            return LiftBanTime;
        }

        public void setLiftBanTime(String LiftBanTime) {
            this.LiftBanTime = LiftBanTime;
        }

        public String getSalt() {
            return Salt;
        }

        public void setSalt(String Salt) {
            this.Salt = Salt;
        }
    }

    public static class UserRankInfoBean {
        private int UserRid;
        private int System;
        private String Title;
        private String Avatar;
        private int CreditsUpper;
        private int CreditsLower;
        private int LimitDays;

        public int getUserRid() {
            return UserRid;
        }

        public void setUserRid(int UserRid) {
            this.UserRid = UserRid;
        }

        public int getSystem() {
            return System;
        }

        public void setSystem(int System) {
            this.System = System;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getAvatar() {
            return Avatar;
        }

        public void setAvatar(String Avatar) {
            this.Avatar = Avatar;
        }

        public int getCreditsUpper() {
            return CreditsUpper;
        }

        public void setCreditsUpper(int CreditsUpper) {
            this.CreditsUpper = CreditsUpper;
        }

        public int getCreditsLower() {
            return CreditsLower;
        }

        public void setCreditsLower(int CreditsLower) {
            this.CreditsLower = CreditsLower;
        }

        public int getLimitDays() {
            return LimitDays;
        }

        public void setLimitDays(int LimitDays) {
            this.LimitDays = LimitDays;
        }
    }
}
