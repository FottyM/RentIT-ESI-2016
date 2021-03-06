package com.rentit.sales.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentit.sales.application.dto.InvoiceEmailDTO;
import com.rentit.sales.application.dto.PurchaseOrderDTO;
import com.rentit.sales.application.service.InvoiceGateway;
import com.rentit.sales.application.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {
    @Autowired
    InvoiceGateway invoiceGateway;
    @Autowired
    SalesService salesService;
    @Autowired
    ObjectMapper objectMapper;


    @RequestMapping(method = GET, path = "/orders")
    public String listOrders(Model model) {

        List<PurchaseOrderDTO> purchaseOrderDTO=salesService.findPurcahseOrdersThatNeedInvoice();


        PurchaseOrderDTO po = new PurchaseOrderDTO();
        model.addAttribute("purchaseOrders",purchaseOrderDTO);
        model.addAttribute("invoiceEmailDTO", new InvoiceEmailDTO());
        return "dashboard/invoice/list";
    }
    @RequestMapping(method = POST, path = "/sendInvoice")
    public String sendInvoice(InvoiceEmailDTO po) throws Exception {


        JavaMailSender mailSender = new JavaMailSenderImpl();
        String invoice1 =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
                        "<invoice>\n"+
                        "	<purchaseOrderHRef>http://rentit.com/api/sales/orders/"+po.getPoId()+"</purchaseOrderHRef>\n"+
                        "	<total>"+po.getTotal()+"</total>\n"+
                        "</invoice>\n";

        MimeMessage rootMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(rootMessage, true);
        helper.setFrom("agabaisaacsoftwares@gmail.com");
        helper.setTo(po.getEmail());
        helper.setSubject("Invoice Purchase Order "+po.getPoId());
        helper.setText("Dear customer,\n\nPlease find attached the Invoice corresponding to your Purchase Order "+po.getPoId()+".\n\nKindly yours,\n\nRentIt Team!");

        helper.addAttachment("invoice-po-"+po.getPoId()+".xml", new ByteArrayDataSource(invoice1, "application/xml"));
         invoiceGateway.sendInvoice(rootMessage);
        return "dashboard/invoice/invoice";
    }

}
