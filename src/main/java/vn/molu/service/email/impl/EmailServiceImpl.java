//package vn.molu.service.email.impl;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.stereotype.Service;
//import vn.mobifone.hr.common.Constants;
//import vn.mobifone.hr.common.utils.CommonUtil;
//import vn.mobifone.hr.domain.payroll.BasePrice;
//import vn.mobifone.hr.dto.payroll.HistoryGeneralParamsDTO;
//import vn.mobifone.hr.service.payroll.*;
//
//import javax.servlet.ServletContext;
//import java.io.File;
//import java.text.DecimalFormat;
//import java.util.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.util.regex.Pattern;
//
//@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "DuplicatedCode"})
//@Service
//public final class EmailServiceImpl implements EmailService {
//
//    private String MESSAGE = "";
//
//    private final Logger logger = Logger.getLogger(getClass().toString());
//
//    @Autowired
//    private ServletContext context;
//
//    @Autowired
//    private MessageSource messageSource;
//
//    @Autowired
//    private ActualSalaryService actualSalaryService;
//
//    @Override
//    public String paycheckGenerator(HistoryGeneralParamsDTO dto, BasePrice basePrice) {
//        if (dto != null) {
//            logger.log(Level.INFO, "generating paycheck with employeeId: " + dto.getEmployeeId());
//            try {
//                boolean isCombine = actualSalaryService
//                        .isCombine(CommonUtil.dateToStringConverter(dto.getMonth(), Constants.MONTH_FORMAT));
//                String templatePath = !isCombine ? context
//                        .getRealPath("/file/template/payroll/salary_notification/paycheck_template.jsp") : context
//                        .getRealPath("/file/template/payroll/salary_notification/combine/paycheck_template.jsp");
//                File template = new File(templatePath);
//                Document doc = Jsoup.parse(template, Constants.DEFAULT_FONT_ENCODING);
//                MESSAGE = doc.toString().trim();
//
//                DecimalFormat format = new DecimalFormat("#,###");
//
//                String basePriceStr = basePrice != null ? format.format(basePrice.getPrice()) : "0";
//
//                String month = CommonUtil.dateToStringConverter(dto.getMonth(), Constants.MONTH_FORMAT);
//                String[] monthArr = month.trim().split(Pattern.quote("/"));
//
//                double totalBeforeDeductSal = dto.getP1P2Salary() + dto.getTotalSubsidiary() +
//                        dto.getPerformanceP3Sal() + dto.getMidShiftSalary() + dto.getNightShiftSalary();
//
//                double totalDeduct = dto.getPersonalTotalInsurance() + dto.getPersonalIncomeTax() +
//                        dto.getSocialFund() + dto.getUnionFee() + dto.getOtherExpense();
//
//                double totalSalary = dto.getP1P2Salary() + dto.getTotalSubsidiary() + dto.getPerformanceP3Sal();
//
//                double settlementSalary = dto.getOtherSalary() + (totalSalary - totalDeduct - dto.getReceivedSalary());
//
//                int days = dto.getWorkDays() + dto.getOthersDays();
//                int salWorkDays = Math.min(days, dto.getStandardWorkDays());
//
//                messenger("{mainTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.mainTitle");
//
//                messenger("{monthTitle}", false,
//                        messageSource.getMessage("label.payroll.ntc.month",
//                                null, new Locale("vi")) + " " + monthArr[0] + " " +
//                                messageSource.getMessage("label.payroll.ntc.year",
//                                        null, new Locale("vi")) + " " + monthArr[1]);
//
//                messenger("{nameHeader}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.name");
//
//                messenger("{name}", false,
//                        dto.getEmployee().getLastName() + " " + dto.getEmployee().getFirstName());
//
//                messenger("{bankHeader}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.bankAccount");
//
//                messenger("{bank}", false, dto.getBankAccountNum() + " - " + dto.getBankName());
//
//                messenger("{departmentHeader}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.department");
//
//                messenger("{department}", false, dto.getEmployee().getDepartment().getName() +
//                        " - " + dto.getEmployee().getSubDepartment().getName());
//
//                messenger("{positionHeader}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.position");
//
//                messenger("{position}", false, (dto.getEmployee().getPosition() != null ?
//                        dto.getEmployee().getPosition().getName() : ""));
//
//                messenger("{indexHeader}", true, "label.common.index");
//
//                messenger("{contentHeader}", true,
//                        "label.payroll.ntc.email.message.payCheck.column.content");
//
//                messenger("{valueHeader}", true,
//                        "label.payroll.ntc.email.message.payCheck.column.value");
//
//                messenger("{paramTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.param");
//
//                messenger("{titleCoefficientTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.titleCoefficient");
//
//                messenger("{titleCoefficient}", false, String.valueOf(dto.getAverageTitleCoefficient()));
//
//                messenger("{regionalSubsidiaryTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.regionalSubsidiary");
//
//                messenger("{regionalSubsidiary}", false, String.valueOf(dto.getRegionalSubsidiary()));
//
//                messenger("{scarcitySubsidiaryTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.scarcitySubsidiary");
//
//                messenger("{scarcitySubsidiary}", false, String.valueOf(dto.getScarcitySubsidiary()));
//
//                messenger("{specialistSubsidiaryTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.specialistSubsidiary");
//
//                messenger("{specialistSubsidiary}", false, String.valueOf(dto.getSpecialistSubsidiary()));
//
//                messenger("{leaderSubsidiaryTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.leaderSubsidiary");
//
//                messenger("{leaderSubsidiary}", false, String.valueOf(dto.getLeaderSubsidiary()));
//
//                messenger("{otherSubsidiaryTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.otherSubsidiary");
//
//                messenger("{otherSubsidiary}", false, String.valueOf(dto.getOtherSubsidiary()));
//
//                messenger("{kpiTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.kpi");
//
//                messenger("{kpi}", false, String.format("%.2f", dto.getKpi()));
//
//                messenger("{workdayTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.workday");
//
//                messenger("{workday}", false, String.valueOf(dto.getStandardWorkDays()));
//
//                messenger("{actualWorkdayTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.actualWorkday");
//
//                messenger("{actualWorkday}", false, String.valueOf(salWorkDays));
//
//                messenger("{nightShiftTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.nightShift");
//
//                messenger("{nightShift}", false, String.valueOf(dto.getNightShiftDays()));
//
//                messenger("{basePriceP1P2Title}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.basePriceP1P2");
//
//                messenger("{basePriceP1P2}", false, "4,000,000");
//
//                messenger("{basePriceP3Title}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.basePriceP3");
//
//                messenger("{basePriceP3}", false, basePriceStr);
//
//                messenger("{totalBeforeDeductionGroupTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.beforeDeduct");
//
//                messenger("{totalBeforeDeduction}", false, format.format(totalBeforeDeductSal));
//
//                messenger("{p1P2SalaryTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.p1P2Salary");
//
//                messenger("{p1P2Salary}", false, format.format(dto.getP1P2Salary()));
//
//                messenger("{totalSubsidiariesTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.totalSubsidiaries");
//
//                messenger("{totalSubsidiaries}", false, format.format(dto.getTotalSubsidiary()));
//
//                messenger("{p3SalaryTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.p3Salary");
//
//                messenger("{p3Salary}", false, format.format(dto.getPerformanceP3Sal()));
//
//                messenger("{deductGroupTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.deduct");
//
//                messenger("{totalDeduction}", false, format.format(totalDeduct));
//
//                messenger("{socialInsuranceTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.socialInsurance");
//
//                messenger("{socialInsurance}", false, format.format(dto.getPersonalSocialInsurance()));
//
//                messenger("{healthInsuranceTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.healthInsurance");
//
//                messenger("{healthInsurance}", false, format.format(dto.getPersonalHealthInsurance()));
//
//                messenger("{unemploymentInsuranceTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.unemploymentInsurance");
//
//                messenger("{unemploymentInsurance}", false,
//                        format.format(dto.getPersonalUnemploymentInsurance()));
//
//                messenger("{personalIncomeTaxTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.personalIncomeTax");
//
//                messenger("{personalIncomeTax}", false, format.format(dto.getPersonalIncomeTax()));
//
//                messenger("{socialFundTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.socialFund");
//
//                messenger("{socialFund}", false, format.format(dto.getSocialFund()));
//
//                messenger("{unionFeeTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.unionFee");
//
//                messenger("{unionFee}", false, format.format(dto.getUnionFee()));
//
//                messenger("{otherDeductTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.otherDeduct");
//
//                messenger("{otherDeduct}", false, format.format(dto.getOtherExpense()));
//
//                messenger("{dependentDeductionTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.dependentDeduction");
//
//                messenger("{dependentDeduction}", false, format.format(dto.getDependentDeduction()));
//
//                messenger("{otherSalaryGroupTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.otherSalary");
//
//                messenger("{otherSalary}", false, format.format(dto.getOtherSalary()));
//
//                messenger("{totalPaymentGroupTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.totalPayment");
//
//                messenger("{grandTotalSalary}", false, format.format(dto.getReceivedSalary() +
//                        settlementSalary + dto.getMidShiftSalary() + dto.getNightShiftSalary()));
//
//                if (isCombine) {
//
//                    messenger("{settlementSalaryTitle}", true,
//                            "label.payroll.ntc.secondSettlementSal.alt");
//
//                    messenger("{settlementSalary}", false,
//                            format.format(dto.getReceivedSalary() + settlementSalary));
//
//                } else {
//
//                    messenger("{receivedTitle}", true,
//                            "label.payroll.ntc.email.message.payCheck.row.received");
//
//                    messenger("{received}", false, format.format(dto.getReceivedSalary()));
//
//                    messenger("{settlementSalaryTitle}", true,
//                            "label.payroll.ntc.email.message.payCheck.row.settlementSalary");
//
//                    messenger("{settlementSalary}", false, format.format(settlementSalary));
//
//                }
//
//                messenger("{midShiftSalaryTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.midShiftSalary");
//
//                messenger("{midShiftSalary}", false, format.format(dto.getMidShiftSalary()));
//
//                messenger("{nightShiftSalaryTitle}", true,
//                        "label.payroll.ntc.email.message.payCheck.row.nightShiftSalary");
//
//                messenger("{nightShiftSalary}", false, format.format(dto.getNightShiftSalary()));
//
//                return MESSAGE;
//            } catch (Exception e) {
//                logger.log(Level.SEVERE, e.getMessage(), e);
//            }
//        }
//        logger.log(Level.WARNING, "generating paycheck with employeeId NULL");
//        return "";
//    }
//
//    private void messenger(String param, boolean isTitle, String value) {
//        MESSAGE = isTitle ? MESSAGE.replaceAll(Pattern.quote(param),
//                messageSource.getMessage(value, null, new Locale("vi"))) :
//                MESSAGE.replaceAll(Pattern.quote(param), value);
//    }
//}
