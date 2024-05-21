//package vn.molu.service.email.impl;
//
//public class EmailSenderImpl {
//
//
//
//    List<HistoryGeneralParamsDTO> dtos = historyGeneralParamsService.getAllBy(month);
//
//    BasePrice basePrice = basePriceService.getBy("01/" + month);
//
//                for (HistoryGeneralParamsDTO dto : dtos) {
//        SalaryNotification notification = new SalaryNotification();
//
//        notification.setEmployee(dto.getEmployee());
//        notification.setMonth(dto.getMonth());
//        notification.setType(type);
//
//        notification.setMessage(StringEscapeUtils
//                .escapeHtml(emailService.paycheckGenerator(dto, basePrice)));
//
//        notification.setSms(false);
//
//        Date date = new Date();
//        notification.setCreatedDate(date);
//        notification.setModifiedDate(date);
//        notification.setUserCreated(SecurityUtil.getPrincipal().getUsername());
//        notification.setUserModified(notification.getUserCreated());
//
//        resultList.add(notification);
//
//    public String sendEmail(String month, String type) {
//
//        if (StringUtils.isNotBlank(month) && StringUtils.isNotBlank(type)) {
//
//            logger.log(Level.INFO, "sending SalaryNotifications with month: " +
//                    month.trim() + " and type: " + type.trim());
//
//            try {
//                List<SalaryNotification> notifications = getBy(month, type, null);
//
//                for (SalaryNotification notification : notifications) {
//                    List<String> employeeEmails = new ArrayList<>();
//                    employeeEmails.add(notification.getEmployee().getEmail().trim());
//
//                    String[] monthArr = month.trim().split(Pattern.quote("/"));
//                    String subject = messageSource.getMessage("label.payroll.ntc.email.subject.payCheck",
//                            null, new Locale("vi")) + " ";
//                    subject += messageSource.getMessage("label.payroll.ntc.month.alt",
//                            null, new Locale("vi")) + " " + monthArr[0] + " ";
//                    subject += messageSource.getMessage("label.payroll.ntc.year",
//                            null, new Locale("vi")) + " " + monthArr[1];
//
//                    EmailDTO email = new EmailDTO();
//                    email.setFrom(EMAIL_SENDER_USERNAME);
//                    email.setTo(employeeEmails);
//                    email.setSubject(subject);
//                    email.setMessage(StringEscapeUtils.unescapeHtml(notification.getMessage()));
//
//                    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//                    MimeMessageHelper helper =
//                            new MimeMessageHelper(mimeMessage, true, EMAIL_SENDER_DEFAULT_ENCODING);
//                    helper.setFrom(email.getFrom());
//
//                    if (!CollectionUtils.isEmpty(employeeEmails)) {
//                        for (String e : employeeEmails) {
//                            helper.addTo(e);
//                        }
//                    }
//
//                    helper.setSubject(email.getSubject());
//                    helper.setText(email.getMessage(), true);
//
//
//                    javaMailSender.send(mimeMessage);
//
//                    // update sent date
//                    Date date = new Date();
//                    notification.setSentDate(date);
//                }
//                createAll(notifications);
//            } catch (Exception e) {
//                logger.log(Level.SEVERE, e.getMessage(), e);
//            }
//            return "OK";
//        }
//        logger.log(Level.WARNING, "sending SalaryNotifications with month and type NULL");
//        return "";
//    }
//
//
//}
