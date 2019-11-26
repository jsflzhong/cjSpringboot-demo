package reflection.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TwoBodCoeData extends BaseCoeData {
    /**
     * 1.现居住地址
     */
    //所在地区
    @JsonProperty("currentAddressDistrict")
    private String area;
    //详细地址
    @JsonProperty("currentAddressDetails")
    private String street;

    /**
     * 2.银行卡信息
     */
    //银行卡号1
    @JsonProperty("bankAccountNumber1")
    private String accountNbr;
    //请再次输入银行卡号
    @JsonProperty("retypebankAccountNo")
    private String accountNbrConfirm;
    //开户银行
    @JsonProperty("bankName")
    private String bankName;
    //个人月收入
    @JsonProperty("monthlyIncome")
    private BigDecimal netInCome;
    //选择银行代扣还款
    @JsonProperty("directDebit")
    private String needWithhold;

    /**
     * 3.单位信息
     */
    //单位名称
    @JsonProperty("employerName")
    private String employerName;
    //单位电话
    @JsonProperty("employerPhoneNo")
    private String officeTelNbr;
    //职业
    @JsonProperty("employeePosition")
    private String employerIndustry;

    /**
     * 4.家庭成员信息
     */
    //家庭成员手机号码
    @JsonProperty("familyMemberPhoneNo")
    private String familyPhoneNbr;
    //与申请人关系
    @JsonProperty("familyMemberRelationship")
    private String familyRelationshipType;

    /**
     * 5.紧急联系人信息
     */
    //紧急联系人姓名
    @JsonProperty("emergencyContactName")
    private String contactsName;
    //紧急联系人手机号码
    @JsonProperty("emergencyContactyPhoneNo")
    private String contactsPhoneNbr;

    /**
     * 6.贷款信息
     */
    //贷款产品
    @JsonProperty("loanProduct")
    private String prodName;
    //商品总价
    @JsonProperty("productPrice")
    private BigDecimal prodTotalPrice;
    //自付金额
    @JsonProperty("initialPayment")
    private BigDecimal dpAmount;
    //自付比例
    @JsonProperty("initialRate")
    private Integer dpPercentage;
    //分期期数
    @JsonProperty("tenure")
    private Integer nbrOfPeriod;
    //贷款本金
    @JsonProperty("loanCredit")
    private BigDecimal loanAmount;
    //首次还款日
    @JsonProperty("firstInstallmentDay")
    private Date repayFrom;
    //每月还款日
    @JsonProperty("installmentDay")
    private Integer repayDay;
    //每月应偿付金额
    @JsonProperty("monthlyPayableAmount")
    private BigDecimal instalAmt;
    //月灵活保障服务包费 //todo 确定
    @JsonProperty("monthlyFlexibilityPackageFee")
    private BigDecimal fpAmount;
    //TODO RBO doesn't have these three?
    //日贷款利率
    //日客户服务费率
    //月短信提醒服务包费

    /**
     * 7.商品信息
     */
    //商品1种类, 商品1类型, 商品1品牌, 商品1型号, 商品1价格
    @JsonProperty("category1")
    private String goods1Category;
    @JsonProperty("type1")
    private String goods1Model;
    @JsonProperty("brand1")
    private String goods1Brand;
    @JsonProperty("model1")
    private String goods1Type;
    @JsonProperty("price1")
    private BigDecimal goods1Price;
    //商品2种类, 商品2类型, 商品2品牌, 商品2型号, 商品2价格
    @JsonProperty("category2")
    private String goods2Category;
    @JsonProperty("type2")
    private String goods2Model;
    @JsonProperty("brand2")
    private String goods2Brand;
    @JsonProperty("model2")
    private String goods2Type;
    @JsonProperty("price2")
    private BigDecimal goods2Price;
    //商品3种类, 商品3类型, 商品3品牌, 商品3型号, 商品3价格
    @JsonProperty("category3")
    private String goods3Category;
    @JsonProperty("type3")
    private String goods3Model;
    @JsonProperty("brand3")
    private String goods3Brand;
    @JsonProperty("model3")
    private String goods3Type;
    @JsonProperty("price3")
    private BigDecimal goods3Price;
    //商品4种类, 商品4类型, 商品4品牌, 商品4型号, 商品4价格
    @JsonProperty("category4")
    private String goods4Category;
    @JsonProperty("type4")
    private String goods4Model;
    @JsonProperty("brand4")
    private String goods4Brand;
    @JsonProperty("model4")
    private String goods4Type;
    @JsonProperty("price4")
    private BigDecimal goods4Price;
    //商品5种类, 商品5类型, 商品5品牌, 商品5型号, 商品5价格
    @JsonProperty("category5")
    private String goods5Category;
    @JsonProperty("type5")
    private String goods5Model;
    @JsonProperty("brand5")
    private String goods5Brand;
    @JsonProperty("model5")
    private String goods5Type;
    @JsonProperty("price5")
    private BigDecimal goods5Price;

    /**
     * 8.门店信息
     */
    private String saName;
    private String saId;
    private String macAddress;
    private String ipAddress;
    private String posCode;
    private String posAddress;
    private String posName;


    private final StringBuilder LOG_RAW = new StringBuilder("现居住地址：")
            .append("所在地区={%s}, 详细地址={%s}")
            .append("银行卡信息：")
            .append("银行卡号1={%s}, 请再次输入银行卡号={%s}, 开户银行={%s}, 个人月收入(元)={%s}, 选择银行代扣还款={%s}")
            .append("单位信息：")
            .append("单位名称={%s}, 单位电话={%s}, 职业={%s}")
            .append("家庭成员信息：")
            .append("家庭成员手机号码={%s}, 与申请人关系={%s}")
            .append("紧急联系人信息：")
            .append("紧急联系人姓名={%s}, 紧急联系人手机号码={%s}")
            .append("贷款信息：")
            .append("贷款产品={%s}, 商品总价={%s}, 自付金额={%s}, 自付比例={%s}, 分期期数={%s}, 贷款本金={%s}, 首次还款日={%s}, 每月还款日={%s}, 每月应偿付金额={%s}, 月灵活保障服务包费={%s}, 日贷款利率={%s}, 日客户服务费率={%s}, 月短信提醒服务包费={%s}, ")
            .append("商品信息：")
            .append("商品1种类={%s}, 商品1类型={%s}, 商品1品牌={%s}, 商品1型号={%s}, 商品1价格={%s}")
            .append("商品2种类={%s}, 商品2类型={%s}, 商品2品牌={%s}, 商品2型号={%s}, 商品2价格={%s}")
            .append("商品3种类={%s}, 商品3类型={%s}, 商品3品牌={%s}, 商品3型号={%s}, 商品3价格={%s}")
            .append("商品4种类={%s}, 商品4类型={%s}, 商品4品牌={%s}, 商品4型号={%s}, 商品4价格={%s}")
            .append("商品5种类={%s}, 商品5类型={%s}, 商品5品牌={%s}, 商品5型号={%s}, 商品5价格={%s}")
            .append("门店信息：")
            .append("销售代表用户名={%s},销售代表代码={%s}, MAC地址={%s}, IP地址={%s}, 门店代码={%s}, 门店地址={%s}, 门店名称={%s}");


    public TwoBodCoeData() {

    }

    /*public String generateLogRaw() {
        return super.generateLogRaw(
                this.area, this.street,
                this.accountNbr, this.accountNbrConfirm, this.bankName, this.netInCome, this.needWithhold,
                this.employerName, this.officeTelNbr, this.employerIndustry,
                this.familyPhoneNbr, this.familyRelationshipType,
                this.contactsName, this.contactsPhoneNbr,
                this.prodName, this.prodTotalPrice, this.dpAmount, this.dpPercentage, this.nbrOfPeriod, this.loanAmount, this.repayFrom, this.repayDay, this.instalAmt, this.fpAmount, //todo other 3?
                this.goods1Category, this.goods1Model, this.goods1Brand, this.goods1Type, this.goods1Price,
                this.goods2Category, this.goods2Model, this.goods2Brand, this.goods2Type, this.goods2Price,
                this.goods3Category, this.goods3Model, this.goods3Brand, this.goods3Type, this.goods3Price,
                this.goods4Category, this.goods4Model, this.goods4Brand, this.goods4Type, this.goods4Price,
                this.goods5Category, this.goods5Model, this.goods5Brand, this.goods5Type, this.goods5Price);
    }*/


}
